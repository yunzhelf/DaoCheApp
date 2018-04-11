package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yifactory.daocheapp.MainActivity;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.event.WeChatLoginMsg;
import com.yifactory.daocheapp.utils.AppDavikActivityMgr;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.utils.ValidationUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyLoginActivity extends BaseActivity {

    @BindView(R.id.account_et)
    EditText mEt_account;
    @BindView(R.id.pwd_et)
    EditText mEt_pwd;
    private Dialog mDialog;
    private Dialog weChatDialog;

    private IWXAPI api;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().init();
        regToWx();
        mDialog = SDDialogUtil.newLoading(this, "登陆中...");
        weChatDialog = SDDialogUtil.newLoading(this, "请求中");

    }

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, ApiConstant.APP_ID_WE_CHAT, true);
        api.registerApp(ApiConstant.APP_ID_WE_CHAT);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.forget_password_tv, R.id.login_tv, R.id.login_btn, R.id.back_iv, R.id.wechat_login_iv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.forget_password_tv:
                    Intent resetPasswordIntent = new Intent(MyLoginActivity.this, MyResetPasswordActivity.class);
                    startActivity(resetPasswordIntent);
                    break;
                case R.id.login_tv:
                    Intent registerIntent = new Intent(MyLoginActivity.this, MyRegisterActivity.class);
                    startActivity(registerIntent);
                    break;
                case R.id.login_btn:
                    loginEvent();
                    break;
                case R.id.back_iv:
                    skipMainActivity();
                    break;
                case R.id.wechat_login_iv:
                    sendAuthRequest();
                    break;
            }
        }
    }

    private void sendAuthRequest() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "auth_request_daoche";
        api.sendReq(req);
    }

    private void loginEvent() {
        String accountStr = mEt_account.getText().toString().trim();
        if (TextUtils.isEmpty(accountStr)) {
            showToast("请输入您的账号");
            return;
        }
        if (!ValidationUtil.isPhone(accountStr)) {
            showToast("账号输入错误");
            return;
        }
        String pwdStr = mEt_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwdStr)) {
            showToast("请输入您的密码");
            return;
        }
        mDialog.show();
        final String uuId = UUID.randomUUID().toString().replace("-","");
        RxHttpUtils
                .createApi(ApiService.class)
                .login(accountStr, pwdStr, "", uuId)
                .compose(Transformer.<LoginBean>switchSchedulers())
                .subscribe(new CommonObserver<LoginBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        mDialog.dismiss();
                    }

                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        mDialog.dismiss();
                        handleLoginEvent(loginBean,uuId);
                    }
                });
    }

    private void handleLoginEvent(LoginBean loginBean,String uuid) {
        String msg = loginBean.getMsg();
        String responseState = loginBean.getResponseState();
        if (responseState.equals("1")) {
            AppDavikActivityMgr.getScreenManager().removeMainActivity();
           /* String wxId = loginBean.getData().get(0).getWxId();
            if (TextUtils.isEmpty(wxId)) {
                showToast("请您进行微信授权登陆");
            } else {
                UserInfoUtil.saveUserInfo(loginBean.getData().get(0), MyLoginActivity.this);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }*/
            SPreferenceUtil sp = new SPreferenceUtil(this, "config.sp");
            sp.setIsLine(true);
            sp.setUserUuid(uuid); //用于充值 、购买视频验证
            UserInfoUtil.saveUserInfo(loginBean.getData().get(0), MyLoginActivity.this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            showToast(msg);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            skipMainActivity();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void skipMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatLoginEvent(WeChatLoginMsg weChatLoginMsg) {
        if (weChatLoginMsg != null) {
            weChatDialog.show();
            String code = weChatLoginMsg.getCode();
            RxHttpUtils.getSInstance()
                    .baseUrl("https://api.weixin.qq.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .createSApi(ApiService.class)
                    .getWeChatAccessToken(ApiConstant.APP_ID_WE_CHAT, ApiConstant.APP_SECRET_WE_CHAT, code, "authorization_code")
                    .compose(Transformer.<String>switchSchedulers())
                    .subscribe(new CommonObserver<String>() {
                        @Override
                        protected void onError(String errorMsg) {
                            showToast(errorMsg);
                            weChatDialog.dismiss();
                        }

                        @Override
                        protected void onSuccess(String userWeChatInfo) {
                            try {
                                JSONObject jsonObject = new JSONObject(userWeChatInfo);
                                String accessTokenStr = jsonObject.optString("access_token");
                                String openidStr = jsonObject.optString("openid");
                                if (!TextUtils.isEmpty(accessTokenStr) && !TextUtils.isEmpty(openidStr)) {
                                    userWeChatInfoRequest(accessTokenStr, openidStr);
                                } else {
                                    weChatDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void userWeChatInfoRequest(String accessTokenStr, String openidStr) {
        RxHttpUtils.getSInstance()
                .baseUrl("https://api.weixin.qq.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .createSApi(ApiService.class)
                .getUserWeChatInfo(accessTokenStr, openidStr)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        weChatDialog.dismiss();
                    }

                    @Override
                    protected void onSuccess(String userWeChatInfo) {
                        weChatDialog.dismiss();
                        Log.i("521", "onSuccess: userWeChatInfo:" + userWeChatInfo);
                        try {
                            JSONObject jsonObject = new JSONObject(userWeChatInfo);
                            String unionidStr = jsonObject.optString("unionid");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
