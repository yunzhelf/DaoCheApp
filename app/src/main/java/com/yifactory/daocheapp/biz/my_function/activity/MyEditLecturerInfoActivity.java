package com.yifactory.daocheapp.biz.my_function.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.TeacherBean;
import com.yifactory.daocheapp.utils.OssServerUtil;
import com.yifactory.daocheapp.utils.OssUploadImgUtil;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.widget.CustomPopWindow;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MyEditLecturerInfoActivity extends BaseActivity {
    @BindView(R.id.edit_phone_iv)
    ImageView phoneIv;
    @BindView(R.id.edit_introduce_et)
    EditText introduceEt;
    @BindView(R.id.edit_achievement_et)
    EditText achievementEt;
    @BindView(R.id.edit_experience_et)
    EditText experienceEt;
    @BindView(R.id.rootLayout)
    AutoLinearLayout rootLayout;

    private TeacherBean.DataBean teacherInfo;
    private CustomPopWindow mCustomPopWindow_SelectedPic;
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final int TAKE_CAMERA_REQUEST_CODE = 2;
    private static final int PHOTO_CLIP = 3;
    private File mFileUser;
    private Dialog mDialog;


    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("编辑资料");
        bar.setRightText("完成");
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
        initViewValue();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        teacherInfo = getIntent().getParcelableExtra("teacherInfo");

    }

    private void initViewValue(){
        if(teacherInfo != null){
            RequestOptions options = new RequestOptions().placeholder(R.drawable.lecturer_photo);
            Glide.with(this).load(teacherInfo.getHeadImg()).apply(options).into(phoneIv);
            introduceEt.setText(teacherInfo.getSelfAssessment());
            achievementEt.setText(teacherInfo.getOldHonor());
            experienceEt.setText(teacherInfo.getJobUbdergo());
        }
    }

    private void selectPicPopupWindow() {
        if (mCustomPopWindow_SelectedPic == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.popup_window_select_pic, null);
            TextView localTv = (TextView) contentView.findViewById(R.id.local_tv);
            TextView takePhotoTv = (TextView) contentView.findViewById(R.id.take_photo_tv);
            TextView cancelTv = (TextView) contentView.findViewById(R.id.cancel_tv);

            mCustomPopWindow_SelectedPic = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.5f)
                    .create()
                    .showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCustomPopWindow_SelectedPic != null) {
                        mCustomPopWindow_SelectedPic.dissmiss();
                    }
                    switch (v.getId()) {
                        case R.id.take_photo_tv:
                            takeCamera();
                            break;
                        case R.id.local_tv:
                            takePhoto();
                            break;
                    }
                }
            };
            localTv.setOnClickListener(listener);
            takePhotoTv.setOnClickListener(listener);
            cancelTv.setOnClickListener(listener);
        } else {
            mCustomPopWindow_SelectedPic.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0);
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
    }

    private void takeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), "/daocheteacher_head.jpg")));
        startActivityForResult(intent, TAKE_CAMERA_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_CAMERA_REQUEST_CODE:
                    File file = new File(Environment.getExternalStorageDirectory()
                            + "/daocheteacher_head.jpg");//保存图片
                    if (file.exists()) {
                        photoClip(Uri.fromFile(file));
                    }
                    break;
                case TAKE_PHOTO_REQUEST_CODE:
                    if (data != null) {
                        Uri uri = data.getData();
                        photoClip(uri);
                    }
                    break;
                case PHOTO_CLIP:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = extras.getParcelable("data");
                            try {
                                mFileUser = saveFile(photo, Environment.getExternalStorageDirectory().toString(), "/daocheteacher_head.jpg");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Glide.with(MyEditLecturerInfoActivity.this).load(photo).into(phoneIv);
                        }
                    }
                    break;
            }
        }
    }

    public static File saveFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    private void photoClip(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 248);
        intent.putExtra("outputY", 293);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }

    private void updateTeacherInfo(){
        if(teacherInfo == null){
            showToast("数据错误");
            return;
        }
        mDialog = SDDialogUtil.newLoading(this,"请求中...");
        mDialog.show();
        if(mFileUser != null){
            uploadHeadToALiYunServer();
        }else{
            updateTeacherRequest();
        }
    }

    private String userHeadImgUrl;

    private void uploadHeadToALiYunServer() {
        userHeadImgUrl = UUID.randomUUID().toString() + ".jpg";
        PutObjectRequest put = new PutObjectRequest(OssUploadImgUtil.USER_HEAD_BUKET, userHeadImgUrl, Environment.getExternalStorageDirectory().toString() + "/daocheteacher_head.jpg");
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });
        OSSAsyncTask task = OssServerUtil.getSingleTon().oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Message msg = Message.obtain();
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
                msg.what = 500;
                handle.sendMessage(msg);
            }
        });
    }

    private void updateTeacherRequest(){

        String trId = teacherInfo.getTrId();
        String name = teacherInfo.getName();
        if(TextUtils.isEmpty(trId) || TextUtils.isEmpty(name)){
            showToast("没有讲师信息");
            return;
        }

        Map<String, RequestBody> param = new HashMap<>();
        RequestBody trIdBody = RequestBody.create(MediaType.parse("text/plain"),trId);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"),name);
        RequestBody introduceBody = RequestBody.create(MediaType.parse("text/plain"),introduceEt.getText().toString());
        RequestBody achievementBody = RequestBody.create(MediaType.parse("text/plain"),achievementEt.getText().toString());
        RequestBody experienceBody = RequestBody.create(MediaType.parse("text/plain"),experienceEt.getText().toString());
        param.put("trId",trIdBody);
        param.put("name",nameBody);
        param.put("selfAssessment",introduceBody);
        param.put("oldHonor",achievementBody);
        param.put("jobUbdergo",experienceBody);
        if (!TextUtils.isEmpty(userHeadImgUrl)) {
            String headImg = "http://" + OssUploadImgUtil.USER_HEAD_BUKET + ".oss-cn-beijing.aliyuncs.com/" + userHeadImgUrl;
            RequestBody headImgBody = RequestBody.create(MediaType.parse("text/plain"), headImg);
            param.put("headImg", headImgBody);
        }
        RxHttpUtils.createApi(ApiService.class)
                .updateRegistTeacher(param)
                .compose(Transformer.<TeacherBean>switchSchedulers())
                .subscribe(new CommonObserver<TeacherBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(TeacherBean teacherBean) {
                        if(teacherBean != null && teacherBean.getResponseState().equals("1")){
                            teacherInfo.setHeadImg(Environment.getExternalStorageDirectory() + "/daocheteacher_head.jpg");
                            teacherInfo.setSelfAssessment(introduceEt.getText().toString());
                            teacherInfo.setOldHonor(achievementEt.getText().toString());
                            teacherInfo.setJobUbdergo(experienceEt.getText().toString());
                            Intent i = new Intent();
                            i.putExtra("teacher",teacherInfo);
                            setResult(RESULT_OK,i);
                            showToast("修改成功");
                            onBackPressed();
                        }
                    }
                });

    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_edit_lecturer_info;
    }

    @OnClick({R.id.naviFrameLeft, R.id.naviFrameRight, R.id.edit_phone_iv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.naviFrameRight:
                    updateTeacherInfo();
                    break;
                case R.id.edit_phone_iv:
                    MyEditLecturerInfoActivityPermissionsDispatcher.readAndWriteNeedsPermissionWithCheck(this);
                    break;
            }
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void readAndWriteNeedsPermission() {
        selectPicPopupWindow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyEditLecturerInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void readAndWriteOnShowRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("提示：应用必要权限，没有此权限，您将无法修改头像！")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("去申请", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void readAndWriteOnPermissionDenied() {
        showLongToast("您拒绝了我们必要的权限，没有此权限，您将无法修改头像！");
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void readAndWriteOnNeverAskAgain() {
        showLongToast("您拒绝了我们必要的权限，如有需要请前往设置中打开“存储”或者“相机”权限！");
    }

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    updateTeacherRequest();
                    break;
                case 500:
                    closeDialog();
                    int mark = msg.arg1;
                    if (mark == 1) {
                        showToast("网络异常");
                    } else if (mark == 2) {
                        showToast("服务器异常，请稍后再试");
                    }
                    break;
            }
        }
    };
}
