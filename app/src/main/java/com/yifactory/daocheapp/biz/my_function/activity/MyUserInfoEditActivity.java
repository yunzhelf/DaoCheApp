package com.yifactory.daocheapp.biz.my_function.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.event.TiXianSuccessMsg;
import com.yifactory.daocheapp.utils.AddressPickTask;
import com.yifactory.daocheapp.utils.OssUploadImgUtil;
import com.yifactory.daocheapp.utils.OssServerUtil;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.CircleImageView;
import com.yifactory.daocheapp.widget.CustomPopWindow;
import com.yifactory.daocheapp.widget.TitleBar;
import com.yifactory.daocheapp.widget.addressSelected.entity.City;
import com.yifactory.daocheapp.widget.addressSelected.entity.County;
import com.yifactory.daocheapp.widget.addressSelected.entity.Province;
import com.yifactory.daocheapp.widget.flowLayout.FlowLayout;
import com.yifactory.daocheapp.widget.flowLayout.TagAdapter;
import com.yifactory.daocheapp.widget.flowLayout.TagFlowLayout;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
public class MyUserInfoEditActivity extends BaseActivity {
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final int TAKE_CAMERA_REQUEST_CODE = 2;
    private static final int PHOTO_CLIP = 3;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.edit_name_et)
    EditText nameEt;
    @BindView(R.id.edit_company_et)
    EditText companyEt;
    @BindView(R.id.edit_jobname_et)
    EditText jobNameEt;
    @BindView(R.id.edit_jobtime_et)
    EditText jobTimeTv;
    @BindView(R.id.edit_nowArea_tv)
    TextView nowAreaTv;
    @BindView(R.id.edit_jobArea_tv)
    TextView jobAreaTv;
    @BindView(R.id.rootLayout)
    AutoLinearLayout rootLayout;
    @BindView(R.id.user_head_iv)
    CircleImageView mUser_headIv;
    private String[] tagArray = {"运营管理", "销售管理", "市场营销", "衍生业务", "客户维护", "售后管理", "人事行政", "财务管理"};

    private UserBean.DataBean user;
    private String jobType;
    private File mFileUser;
    private CustomPopWindow mCustomPopWindow_SelectedPic;
    private Dialog mDialog;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    modifyRequest();
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

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("我的资料");
        bar.setRightText("完成");
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
        if (user != null) {
            initFlowLayout();
            initOtherViews();
        } else {
            finish();
        }
    }

    private String trainAttention;

    private void initFlowLayout() {
        mFlowLayout.setAdapter(new TagAdapter<String>(Arrays.asList(tagArray)) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(MyUserInfoEditActivity.this).inflate(R.layout.layout_my_userinfo_needs,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                trainAttention = "";
                if (selectPosSet.size() > 0) {
                    for (Integer integer : selectPosSet) {
                        trainAttention += tagArray[integer] + ",";
                    }
                    trainAttention = trainAttention.substring(0, trainAttention.length() - 1);
                }
            }
        });
    }

    private void initOtherViews() {
        Glide.with(this).load(user.getHeadImg()).into(mUser_headIv);
        nameEt.setText(user.getUserName());
        companyEt.setText(user.getCompanyName());
        jobNameEt.setText(user.getJobName());
        nowAreaTv.setText(user.getNowArea());
        jobTimeTv.setText(String.valueOf(user.getJobTime()));
        jobAreaTv.setText(user.getJobArea());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        File file = new File(Environment.getExternalStorageDirectory() + "/daocheuser_head.jpg");
        if (file.exists()) {
            file.delete();
        }
        user = (UserBean.DataBean) getIntent().getSerializableExtra("userInfo");
    }

    private void updateUserInfo() {
        if (mDialog == null) {
            mDialog = SDDialogUtil.newLoading(this, "请求中...");
        }
        if (mFileUser != null) {
            uploadHeadToALiYunServer();
        } else {
            modifyRequest();
        }
    }

    private String userHeadImgUrl;

    private void uploadHeadToALiYunServer() {
//        File headFile = new File(Environment.getExternalStorageDirectory().toString(), "/daocheuser_head.jpg");
        userHeadImgUrl = UUID.randomUUID().toString() + ".jpg";
        PutObjectRequest put = new PutObjectRequest(OssUploadImgUtil.USER_HEAD_BUKET, userHeadImgUrl, Environment.getExternalStorageDirectory().toString() + "/daocheuser_head.jpg");
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = OssServerUtil.getSingleTon().oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                /*Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());*/
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
        // task.cancel(); // 可以取消任务
// task.waitUntilFinished(); // 可以等待任务完成
    }


    private void modifyRequest() {
        Map<String, RequestBody> partMap = new HashMap<>();

        String company = companyEt.getText().toString();

        String name = nameEt.getText().toString();

        String jobName = jobNameEt.getText().toString();

        String nowArea = nowAreaTv.getText().toString().trim();

        String jobArea = jobAreaTv.getText().toString().trim();

        String jobTime = jobTimeTv.getText().toString().trim();


        RequestBody uIdBody = RequestBody.create(MediaType.parse("text/plain"), UserInfoUtil.getUserInfoBean(this).getUId());
        partMap.put("uId", uIdBody);
        if (!TextUtils.isEmpty(userHeadImgUrl)) {
            String headImg = "http://" + OssUploadImgUtil.USER_HEAD_BUKET + ".oss-cn-beijing.aliyuncs.com/" + userHeadImgUrl;
            RequestBody headImgBody = RequestBody.create(MediaType.parse("text/plain"), headImg);
            partMap.put("headImg", headImgBody);
            user.setHeadImg(headImg);
        }

        if (!TextUtils.isEmpty(name)) {
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
            partMap.put("userName", nameBody);
            user.setUserName(name);
        }
        if (!TextUtils.isEmpty(company)) {
            RequestBody companyBody = RequestBody.create(MediaType.parse("text/plain"), company);
            partMap.put("companyName", companyBody);
            user.setCompanyName(company);
        }
        if (!TextUtils.isEmpty(jobName)) {
            RequestBody jobNameBody = RequestBody.create(MediaType.parse("text/plain"), jobName);
            partMap.put("jobName", jobNameBody);
            user.setJobName(jobName);
        }
        if (!TextUtils.isEmpty(nowArea)) {
            RequestBody nowAreaBody = RequestBody.create(MediaType.parse("text/plain"), nowArea);
            partMap.put("nowArea", nowAreaBody);
            user.setNowArea(nowArea);
        }
        if (!TextUtils.isEmpty(jobArea)) {
            RequestBody jobAreaBody = RequestBody.create(MediaType.parse("text/plain"), jobArea);
            partMap.put("jobArea", jobAreaBody);
            user.setJobArea(jobArea);
        }
        if (!TextUtils.isEmpty(jobTime)) {
            RequestBody jobTimeBody = RequestBody.create(MediaType.parse("text/plain"), jobTime);
            partMap.put("jobTime", jobTimeBody);
            user.setJobTime(Integer.valueOf(jobTime));
        }
        if (!TextUtils.isEmpty(trainAttention)) {
            RequestBody trainAttentionBody = RequestBody.create(MediaType.parse("text/plain"), trainAttention);
            partMap.put("trainAttention", trainAttentionBody);
            user.setTrainAttention(trainAttention);
            Log.i("521", "modifyRequest: " + trainAttention);
        }

        RxHttpUtils.createApi(ApiService.class)
                .updateUser(partMap)
                .compose(Transformer.<UserBean>switchSchedulers())
                .subscribe(new CommonObserver<UserBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        closeDialog();
                    }

                    @Override
                    protected void onSuccess(UserBean userBean) {
                        closeDialog();
                        File file = new File(Environment.getExternalStorageDirectory() + "/daocheuser_head.jpg");
                        if (file.exists()) {
                            file.delete();
                        }
                        if (userBean != null) {
                            showToast(userBean.getMsg());
                            EventBus.getDefault().post(user);
                            EventBus.getDefault().post(new TiXianSuccessMsg());
                            finish();
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
        return R.layout.activity_my_user_info_edit;
    }

    private void selectLocation() {
        AddressPickTask task = new AddressPickTask(this);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                nowAreaTv.setText(province.getAreaName() + " " + city.getAreaName() + " " + county.getAreaName());
            }
        });
        task.execute("辽宁", "沈阳", "浑南新区");
    }

    private CustomPopWindow mCustomPopWindowArea;

    private void selectRoleEvent() {
        if (mCustomPopWindowArea == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.pop_register_area_select_layout, null);
            final TextView oneTv = (TextView) contentView.findViewById(R.id.one_tv);
            final TextView twoTv = (TextView) contentView.findViewById(R.id.two_tv);
            final TextView threeTv = (TextView) contentView.findViewById(R.id.three_tv);
            final TextView fourTv = (TextView) contentView.findViewById(R.id.four_tv);
            final TextView fiveTv = (TextView) contentView.findViewById(R.id.five_tv);
            final TextView sixTv = (TextView) contentView.findViewById(R.id.six_tv);
            mCustomPopWindowArea = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.5f)
                    .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .create()
                    .showAtLocation(rootLayout, Gravity.CENTER, 0, 0);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCustomPopWindowArea != null) {
                        mCustomPopWindowArea.dissmiss();
                    }
                    switch (v.getId()) {
                        case R.id.one_tv:
                            jobType = "4S店";
                            jobAreaTv.setText("4S店");
                            oneTv.setTextColor(Color.parseColor("#4087fd"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.two_tv:
                            jobType = "二手车";
                            jobAreaTv.setText("二手车");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#4087fd"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.three_tv:
                            jobType = "汽车超市";
                            jobAreaTv.setText("汽车超市");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#4087fd"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.four_tv:
                            jobType = "维修店";
                            jobAreaTv.setText("维修店");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#4087fd"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.five_tv:
                            jobType = "职业老师";
                            jobAreaTv.setText("职业老师");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#4087fd"));
                            sixTv.setTextColor(Color.parseColor("#666666"));
                            break;
                        case R.id.six_tv:
                            jobType = "媒体人";
                            jobAreaTv.setText("媒体人");
                            oneTv.setTextColor(Color.parseColor("#666666"));
                            twoTv.setTextColor(Color.parseColor("#666666"));
                            threeTv.setTextColor(Color.parseColor("#666666"));
                            fourTv.setTextColor(Color.parseColor("#666666"));
                            fiveTv.setTextColor(Color.parseColor("#666666"));
                            sixTv.setTextColor(Color.parseColor("#4087fd"));
                            break;
                    }
                }
            };
            oneTv.setOnClickListener(listener);
            twoTv.setOnClickListener(listener);
            threeTv.setOnClickListener(listener);
            fourTv.setOnClickListener(listener);
            fiveTv.setOnClickListener(listener);
            sixTv.setOnClickListener(listener);
        } else {
            mCustomPopWindowArea
                    .showAtLocation(rootLayout, Gravity.CENTER, 0, 0);
        }
    }

    @OnClick({R.id.naviFrameLeft, R.id.naviFrameRight, R.id.edit_nowArea_rl, R.id.edit_role_rl, R.id.head_layout})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.naviFrameRight:
                    updateUserInfo();
                    break;
                case R.id.edit_nowArea_rl:
                    selectLocation();
                    break;
                case R.id.edit_role_rl:
                    selectRoleEvent();
                    break;
                case R.id.head_layout:
                    MyUserInfoEditActivityPermissionsDispatcher.readAndWriteNeedsPermissionWithCheck(this);
                    break;
            }
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
                Environment.getExternalStorageDirectory(), "/daocheuser_head.jpg")));
        startActivityForResult(intent, TAKE_CAMERA_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_CAMERA_REQUEST_CODE:
                    File file = new File(Environment.getExternalStorageDirectory()
                            + "/daocheuser_head.jpg");//保存图片
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
                                mFileUser = saveFile(photo, Environment.getExternalStorageDirectory().toString(), "/daocheuser_head.jpg");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mUser_headIv.setImageBitmap(photo);
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
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void readAndWriteNeedsPermission() {
        selectPicPopupWindow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyUserInfoEditActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
}
