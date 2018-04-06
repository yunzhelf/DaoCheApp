package com.yifactory.daocheapp.biz.my_function.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendInterviewVideoDetailsLazyFragmentAdapter;
import com.yifactory.daocheapp.biz.my_function.my_tab.MyCouponExpiredFragment;
import com.yifactory.daocheapp.biz.my_function.my_tab.MyCouponUnuseFragment;
import com.yifactory.daocheapp.biz.my_function.my_tab.MyCouponUsedFragment;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCouponActivity extends BaseActivity {
    @BindView(R.id.coupon_tab)
    XTabLayout mCouponTabLayout;
    @BindView(R.id.coupon_vp)
    ViewPager viewPager;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("我的优惠券");
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
        initCouponView();
    }


    private void initCouponView() {
        String[] titleList = {"未使用", "已使用", "已过期"};
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(MyCouponUnuseFragment.newInstance());
        fragmentList.add(MyCouponUsedFragment.newInstance());
        fragmentList.add(MyCouponExpiredFragment.newInstance());
        viewPager.setAdapter(new HomeRecommendInterviewVideoDetailsLazyFragmentAdapter(
                getSupportFragmentManager(), fragmentList, Arrays.asList(titleList)));
        mCouponTabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

    @OnClick({R.id.naviFrameLeft})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
            }
        }
    }
}
