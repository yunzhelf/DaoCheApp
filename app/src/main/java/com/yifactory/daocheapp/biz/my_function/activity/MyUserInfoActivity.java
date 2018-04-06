package com.yifactory.daocheapp.biz.my_function.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.widget.CircleImageView;
import com.yifactory.daocheapp.widget.TitleBar;
import com.yifactory.daocheapp.widget.flowLayout.FlowLayout;
import com.yifactory.daocheapp.widget.flowLayout.TagAdapter;
import com.yifactory.daocheapp.widget.flowLayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class MyUserInfoActivity extends BaseActivity {

    @BindView(R.id.user_head_iv)
    CircleImageView userHeadIv;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.userinfo_name_tv)
    TextView nameTv;
    @BindView(R.id.userinfo_company_tv)
    TextView companyTv;
    @BindView(R.id.userinfo_jobname_tv)
    TextView jobNameTv;
    @BindView(R.id.userinfo_nowarea)
    TextView nowAreaTv;
    @BindView(R.id.userinfo_jobArea_tv)
    TextView jobAreaTv;
    @BindView(R.id.userinfo_jobtime_tv)
    TextView jobTimeTv;

    private UserBean.DataBean user;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("我的资料");
        bar.setRightText("编辑");
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

    private void initFlowLayout() {
        String trainAttention = user.getTrainAttention();
        if (TextUtils.isEmpty(trainAttention)) {
            return;
        }
        String[] tagArray = trainAttention.split(",");
        mFlowLayout.setAdapter(new TagAdapter<String>(Arrays.asList(tagArray)) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(MyUserInfoActivity.this).inflate(R.layout.layout_my_userinfo_needs2,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    private void initOtherViews() {
        Glide.with(this).load(user.getHeadImg()).into(userHeadIv);
        nameTv.setText(user.getUserName());
        companyTv.setText(user.getCompanyName());
        jobNameTv.setText(user.getJobName());
        nowAreaTv.setText(user.getNowArea());
        jobTimeTv.setText(String.valueOf(user.getJobTime()) + "年");
        jobAreaTv.setText(user.getJobArea());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        EventBus.getDefault().register(this);
        user = (UserBean.DataBean) getIntent().getSerializableExtra("userInfo");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UserBean.DataBean user) {
        this.user = user;
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_user_info;
    }

    @OnClick({R.id.naviFrameLeft, R.id.naviFrameRight})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.naviFrameRight:
                    Intent userInfoEditIntent = new Intent(this, MyUserInfoEditActivity.class);
                    userInfoEditIntent.putExtra("userInfo", user);
                    startActivity(userInfoEditIntent);
                    break;
            }
        }
    }
}
