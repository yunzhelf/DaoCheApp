package com.yifactory.daocheapp.biz.my_function.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.VerifyCodeBean;
import com.yifactory.daocheapp.utils.PhoneGetCodeUtil;
import com.yifactory.daocheapp.utils.ValidationUtil;
import com.yifactory.daocheapp.widget.CircleImageView;
import com.yifactory.daocheapp.widget.CustomPopWindow;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MyRegisterActivity extends BaseActivity {
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final int TAKE_CAMERA_REQUEST_CODE = 2;
    private static final int PHOTO_CLIP = 3;
    @BindView(R.id.rootLayout)
    AutoLinearLayout mRootViewLayout;
    @BindView(R.id.name_et)
    EditText mEt_name;
    @BindView(R.id.sex_tv)
    TextView mTv_sex;
    @BindView(R.id.mobile_phone_et)
    EditText mobilePhoneEt;
    @BindView(R.id.verifyCode_et)
    EditText verifyCodeEt;
    @BindView(R.id.verifyCode_tv)
    TextView mTv_sendVerifyCode;
    @BindView(R.id.pwd_et)
    EditText mEt_pwd;
    @BindView(R.id.again_pwd_et)
    EditText mEt_againPwd;
    @BindView(R.id.user_head_iv)
    CircleImageView mIv_userHead;
    private CustomPopWindow mCustomPopWindow_SelectedPic;
    private CustomPopWindow mCustomPopWindow_SelectedSex;
    private File mFileUser;
    private String mPhoneNumStr;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("注册");
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        File file = new File(Environment.getExternalStorageDirectory() + "/daocheuser_head.jpg");
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void addListener() {

    }

    @OnClick({R.id.naviFrameLeft, R.id.next_btn, R.id.verifyCode_tv, R.id.head_layout, R.id.register_sex_ly})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.next_btn:
                    nextEvent();
                    break;
                case R.id.verifyCode_tv:
                    verifyCodeEvent();
                    break;
                case R.id.head_layout:
                    MyRegisterActivityPermissionsDispatcher.readAndWriteNeedsPermissionWithCheck(MyRegisterActivity.this);
                    break;
                case R.id.register_sex_ly:
                    selectSexPopupWindow();
                    break;
            }
        }
    }

    private void selectSexPopupWindow() {
        if (mCustomPopWindow_SelectedSex == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.popup_window_select_sex, null);
            TextView menTv = (TextView) contentView.findViewById(R.id.men_tv);
            TextView womenTv = (TextView) contentView.findViewById(R.id.women_tv);
            TextView cancelTv = (TextView) contentView.findViewById(R.id.cancel_tv);

            mCustomPopWindow_SelectedSex = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.5f)
                    .create()
                    .showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCustomPopWindow_SelectedSex != null) {
                        mCustomPopWindow_SelectedSex.dissmiss();
                    }
                    switch (v.getId()) {
                        case R.id.men_tv:
                            mTv_sex.setText("男");
                            break;
                        case R.id.women_tv:
                            mTv_sex.setText("女");
                            break;
                    }
                }
            };
            menTv.setOnClickListener(listener);
            womenTv.setOnClickListener(listener);
            cancelTv.setOnClickListener(listener);
        } else {
            mCustomPopWindow_SelectedSex.showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);
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
                    .showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);

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
            mCustomPopWindow_SelectedPic.showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);
        }
    }

    private void nextEvent() {
        String nameStr = mEt_name.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            showToast("请输入姓名");
            return;
        }
        if (mFileUser == null) {
            showToast("请上传头像");
            return;
        }
        String sexStr = mTv_sex.getText().toString().trim();
        if (TextUtils.isEmpty(sexStr)) {
            showToast("请选择性别");
            return;
        }
        String phoneNumStr = mobilePhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumStr)) {
            showToast("请输入手机号");
            return;
        }

        if (!ValidationUtil.isPhone(phoneNumStr)) {
            showToast("手机号码输入错误");
            return;
        }
        String verifyCodeStr = verifyCodeEt.getText().toString().trim();
        if (TextUtils.isEmpty(verifyCodeStr)) {
            showToast("请输入验证码");
            return;
        }
        String pwdStr = mEt_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwdStr)) {
            showToast("请设置新密码");
            return;
        }
        String againPwdStr = mEt_againPwd.getText().toString().trim();
        if (TextUtils.isEmpty(againPwdStr)) {
            showToast("请再次输入新密码");
            return;
        }
        if (!pwdStr.equals(againPwdStr)) {
            showToast("两次输入密码不一致");
            return;
        }
        Intent registerSecondIntent = new Intent(this, MyRegisterNextActivity.class);
        registerSecondIntent.putExtra("name", nameStr);
        registerSecondIntent.putExtra("sex", sexStr);
        if (TextUtils.isEmpty(mPhoneNumStr)) {
            registerSecondIntent.putExtra("phone", phoneNumStr);
        } else {
            registerSecondIntent.putExtra("phone", mPhoneNumStr);
        }
        registerSecondIntent.putExtra("verifyCode", verifyCodeStr);
        registerSecondIntent.putExtra("pwd", pwdStr);
        startActivity(registerSecondIntent);
        finish();
    }

    private void verifyCodeEvent() {
        mPhoneNumStr = mobilePhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(mPhoneNumStr)) {
            showToast("请输入手机号");
            return;
        }

        if (!ValidationUtil.isPhone(mPhoneNumStr)) {
            showToast("手机号码输入错误");
            return;
        }

        RxHttpUtils.createApi(ApiService.class)
                .doSendCode(mPhoneNumStr, 0)
                .compose(Transformer.<VerifyCodeBean>switchSchedulers())
                .subscribe(new CommonObserver<VerifyCodeBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(VerifyCodeBean verifyCodeBean) {
                        if (verifyCodeBean.getResponseState().equals("1")) {
                            new PhoneGetCodeUtil(mTv_sendVerifyCode).onStart();
                        } else {
                            showToast(verifyCodeBean.getMsg());
                        }
                    }
                });
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
                            mIv_userHead.setImageBitmap(photo);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyRegisterActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void readAndWriteOnShowRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("提示：应用必要权限，没有此权限，您将无法上传头像！")
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

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void readAndWriteOnPermissionDenied() {
        showLongToast("您拒绝了我们必要的权限，没有此权限，您将无法上传头像！");
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void readAndWriteOnNeverAskAgain() {
        showLongToast("您拒绝了我们必要的权限，如有需要请前往设置中打开“存储”权限！");
    }
}
