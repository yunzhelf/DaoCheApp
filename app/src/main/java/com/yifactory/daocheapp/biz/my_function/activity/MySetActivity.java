package com.yifactory.daocheapp.biz.my_function.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.MainActivity;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.event.AppExitMsg;
import com.yifactory.daocheapp.utils.AppApplicationMgr;
import com.yifactory.daocheapp.utils.ButtonDialogFragment;
import com.yifactory.daocheapp.utils.DataCleanUtils;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.versionCode;

public class MySetActivity extends BaseActivity {

    @BindView(R.id.app_version_code_tv)
    TextView mTv_versionCode;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("设置");
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        getVersionName();
    }

    private void getVersionName() {
        String versionName = AppApplicationMgr.getVersionName(this);
        if (!TextUtils.isEmpty(versionName)) {
            mTv_versionCode.setText(versionName);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_set;
    }

    @OnClick({R.id.naviFrameLeft, R.id.my_bank_card_layout,R.id.exit_btn, R.id.modify_pwd_layout, R.id.clear_cache_tv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.my_bank_card_layout:
                    Intent myBankCardIntent = new Intent(this, MyBankCardActivity.class);
                    myBankCardIntent.putExtra("mark", "setting");
                    startActivity(myBankCardIntent);
                    break;
                case R.id.exit_btn:
                    EventBus.getDefault().post(new AppExitMsg());
                    finish();
                    break;
                case R.id.modify_pwd_layout:
                    Intent resetPasswordIntent = new Intent(this, MyResetPasswordActivity.class);
                    startActivity(resetPasswordIntent);
                    break;
                case R.id.clear_cache_tv:
                    clearCacheOperate();
                    break;
            }
        }
    }

    private void clearCacheOperate() {
        ButtonDialogFragment buttonDialogFragment = new ButtonDialogFragment();
        buttonDialogFragment.show("提示：", "是否清除缓存？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    DataCleanUtils.clearAllCache(MySetActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showToast("已清除缓存");
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, getSupportFragmentManager());
    }
}
