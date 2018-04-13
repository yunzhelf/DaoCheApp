package com.yifactory.daocheapp.biz.discover_function.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.AddUserQuestionBean;
import com.yifactory.daocheapp.biz.discover_function.adapter.DiscoverPublishAdapter;
import com.yifactory.daocheapp.utils.OssUploadImgUtil;
import com.yifactory.daocheapp.utils.OssServerUtil;
import com.yifactory.daocheapp.utils.PictureCompressUtil;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DiscoverPublishAnswersActivity extends BaseActivity {

    @BindView(R.id.answer_et)
    EditText mEt_answer;
    @BindView(R.id.pic_rv)
    RecyclerView mRv_pic;
    private DiscoverPublishAdapter mPublishAdapter;
    private String mAnswerStr;
    private Dialog mDialog;
    private int uploadSuccessNum = 0;
    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String userHeadImgUrl = (String) msg.obj;
            switch (msg.what) {
                case 200:
                    uploadSuccessNum++;
                    String imgUrlStr = "http://" + OssUploadImgUtil.USER_HEAD_BUKET + ".oss-cn-beijing.aliyuncs.com/" + userHeadImgUrl;
                    uploadImgUrl.add(imgUrlStr);
                    Log.i("521", "handleMessage: 成功：" + uploadSuccessNum);
                    break;
                case 500:
                    closeDialog();
                    int mark = msg.arg1;
                    if (mark == 1) {
                        showToast("网络异常");
                    } else if (mark == 2) {
                        showToast("服务器异常");
                    }
                    finish();
                    break;
            }
            if (uploadSuccessNum == realPicUrlList.size()) {
                Log.i("521", "handleMessage: 请求了服务器...");
                requestServer();
            }
        }
    };

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("问答");
        bar.setRightText("发布");
        bar.setRightTextColor(Color.parseColor("#4087fd"));
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
        initPicRv();
    }

    private List<String> realPicUrlList = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();

    private void initPicRv() {
        realPicUrlList.add("伪视图");
        mPublishAdapter = new DiscoverPublishAdapter(realPicUrlList, this);
        mRv_pic.setLayoutManager(new GridLayoutManager(this, 4));
        mRv_pic.setAdapter(mPublishAdapter);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mDialog = SDDialogUtil.newLoading(this, "请求中...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftInput();
    }

    private void hideSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        if(imm != null){
            boolean isOpen=imm.isActive();
            if(isOpen){
                imm.hideSoftInputFromWindow(mEt_answer.getWindowToken(),InputMethodManager.SHOW_FORCED);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discover_publish_answers;
    }

    @OnClick({R.id.naviFrameLeft, R.id.naviFrameRight})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.naviFrameRight:
                    publishEvent();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults[0] == 0) {
                    selectPicEvent();
                }
                break;
        }
    }

    private void selectPicEvent() {
        PhotoPicker.builder()
                .setPhotoCount(9)
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(false)
                .start(DiscoverPublishAnswersActivity.this, PhotoPicker.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                handleUploadPicEvent(photos);
            }
        }
    }

    private ProgressDialog dialog;

    private void handleUploadPicEvent(ArrayList<String> photos) {
        if (dialog == null) {
            dialog = ProgressDialog.show(this, null, "数据处理中...", true, true);
        }
        PictureCompressUtil.getInstance().startCompress(this, photos, new PictureCompressUtil.CompressedPicResultCallBack() {
            @Override
            public void showResult(List<String> photos, List<File> list) {
                dialog.dismiss();
                mPublishAdapter.setFinish(true);
                fileList.addAll(list);
                realPicUrlList.clear();
                for (File file : list) {
                    realPicUrlList.add(file.getAbsolutePath());
                }
                mPublishAdapter.notifyDataSetChanged();
            }
        });
    }

    private void publishEvent() {
        mAnswerStr = mEt_answer.getText().toString().trim();
        if (TextUtils.isEmpty(mAnswerStr)) {
            showToast("请输入内容");
            return;
        }
        mDialog.show();
        if (fileList.size() > 0) {
            for (int i = 0; i < realPicUrlList.size(); i++) {
                String s = realPicUrlList.get(i);
                Log.i("521", "publishEvent: 图片路径："+s);
                uploadHeadToALiYunServer(s);
            }
        } else {
            requestServer();
        }
    }

    private void uploadHeadToALiYunServer(String imgUrl) {
        final String userHeadImgUrl = UUID.randomUUID().toString() + ".jpg";
        PutObjectRequest put = new PutObjectRequest(OssUploadImgUtil.USER_HEAD_BUKET, userHeadImgUrl, imgUrl);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.d("521", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = OssServerUtil.getSingleTon().oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                /*Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());*/
                Message msg = Message.obtain();
                msg.obj = userHeadImgUrl;
                msg.what = 200;
                handle.sendMessage(msg);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                Message msg = Message.obtain();
                if (clientExcepion != null) {
                    msg.arg1 = 1;
                }
                if (serviceException != null) {
                    msg.arg1 = 2;
                }
                msg.obj = userHeadImgUrl;
                msg.what = 500;
                handle.sendMessage(msg);
            }
        });
        // task.cancel(); // 可以取消任务
// task.waitUntilFinished(); // 可以等待任务完成
    }

    private List<String> uploadImgUrl = new ArrayList<>();

    private void requestServer() {
        Map<String, RequestBody> addUserQuestionMap = new HashMap<>();
        RequestBody uIdBody = RequestBody.create(MediaType.parse("text/plain"), UserInfoUtil.getUserInfoBean(this).getUId());
        RequestBody questionBody = RequestBody.create(MediaType.parse("text/plain"), mAnswerStr);
        addUserQuestionMap.put("uId", uIdBody);
        addUserQuestionMap.put("questionBody", questionBody);
        if (uploadImgUrl.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < uploadImgUrl.size(); i++) {
                String s = uploadImgUrl.get(i);
                stringBuilder.append(s);
                if (i != uploadImgUrl.size() - 1) {
                    stringBuilder.append(",");
                }
            }

            RequestBody fileBody = RequestBody.create(MediaType.parse("text/plain"), stringBuilder.toString());
            addUserQuestionMap.put("file", fileBody);
        }

        RxHttpUtils
                .createApi(ApiService.class)
                .addUserQuestion(addUserQuestionMap)
                .compose(Transformer.<AddUserQuestionBean>switchSchedulers())
                .subscribe(new CommonObserver<AddUserQuestionBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        closeDialog();
                    }

                    @Override
                    protected void onSuccess(AddUserQuestionBean response) {
                        closeDialog();
                        String msg = response.getMsg();
                        showToast(msg);
                        if(response.getData() != null && response.getData().size() > 0){
                            EventBus.getDefault().post(response.getData().get(0));
                        }
                        finish();
                    }
                });
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
