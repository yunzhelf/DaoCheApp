package com.yifactory.daocheapp.biz.my_function.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.PasswordBean;
import com.yifactory.daocheapp.bean.VerifyCodeBean;
import com.yifactory.daocheapp.utils.PhoneGetCodeUtil;
import com.yifactory.daocheapp.utils.ValidationUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyResetPasswordActivity extends BaseActivity {
    @BindView(R.id.forget_mobile_et)
    EditText mobileEt;
    @BindView(R.id.forget_veritycode_et)
    EditText verityCodeEt;
    @BindView(R.id.forget_new_psw_et)
    EditText newPswEt;
    @BindView(R.id.forget_confirm_psw_et)
    EditText confirmEt;
    @BindView(R.id.forget_send_verity_tv)
    TextView sendVerityTv;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("重置密码");
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
    }

    private void updatePassword(){
        String mobile = mobileEt.getText().toString();
        String verityCode = verityCodeEt.getText().toString();
        String newPassword = newPswEt.getText().toString();
        String confirmPassword = confirmEt.getText().toString();
        if(TextUtils.isEmpty(mobile)){
            showToast("请输入手机号");
            return;
        }
        if (!ValidationUtil.isPhone(mobile)) {
            showToast("手机号码输入错误");
            return;
        }
        if(TextUtils.isEmpty(verityCode)){
            showToast("请输入验证码");
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            showToast("请输入新密码");
            return;
        }
        if(TextUtils.isEmpty(confirmPassword)){
            showToast("请再次输入新密码");
            return;
        }
        if( !newPassword.equals(confirmPassword)){
            showToast("两次输入密码不一致");
            return;
        }
        Map<String, RequestBody> bodyMap = new HashMap<>();
        RequestBody mobileBody = RequestBody.create(MediaType.parse("text/plain"),mobile);
        RequestBody verityCodeBody = RequestBody.create(MediaType.parse("text/plain"),verityCode);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"),newPassword);
        bodyMap.put("verifCode",verityCodeBody);
        bodyMap.put("mobile",mobileBody);
        bodyMap.put("password",passwordBody);

        RxHttpUtils.createApi(ApiService.class)
                .updateUserPassword(bodyMap)
                .compose(Transformer.<PasswordBean>switchSchedulers())
                .subscribe(new CommonObserver<PasswordBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(PasswordBean passwordBean) {
                        if (passwordBean.getResponseState().equals("1")) {
                            showToast("修改成功");
                            finish();
                        } else {
                            showToast(passwordBean.getMsg());
                        }
                    }
                });
    }

    private void verifyCodeEvent() {
        String phoneNumStr = mobileEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumStr)) {
            showToast("请输入手机号");
            return;
        }

        if (!ValidationUtil.isPhone(phoneNumStr)) {
            showToast("手机号码输入错误");
            return;
        }

        RxHttpUtils.createApi(ApiService.class)
                .doSendCode(phoneNumStr, 1)
                .compose(Transformer.<VerifyCodeBean>switchSchedulers())
                .subscribe(new CommonObserver<VerifyCodeBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(VerifyCodeBean verifyCodeBean) {
                        if (verifyCodeBean.getResponseState().equals("1")) {
                            new PhoneGetCodeUtil(sendVerityTv).onStart();
                        } else {
                            showToast(verifyCodeBean.getMsg());
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void addListener() {

    }

    @OnClick({R.id.naviFrameLeft,R.id.forget_finish_btn,R.id.forget_send_verity_tv})
    public void onClickEvent(View view){
        if(view != null){
            switch (view.getId()){
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.forget_finish_btn:
                    updatePassword();
                    break;
                case R.id.forget_send_verity_tv:
                    verifyCodeEvent();
                    break;
            }
        }
    }
}
