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
import com.yifactory.daocheapp.biz.my_function.my_tab.MyGradeLevelFragment;
import com.yifactory.daocheapp.biz.my_function.my_tab.MyStudyLevelFragment;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyStudyActivity extends BaseActivity {

    @BindView(R.id.xTabLayout)
    XTabLayout mXTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mLazyViewPager;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("学习时长");
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
    }

    @Override
    protected void initView() {
        initTabLayout();
    }

    private void initTabLayout() {
        String[] titleArray = {"学习轨迹", "等级轨迹"};
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(MyStudyLevelFragment.newInstance());
        fragmentList.add(MyGradeLevelFragment.newInstance());
        mLazyViewPager.setAdapter(new HomeRecommendInterviewVideoDetailsLazyFragmentAdapter(getSupportFragmentManager(), fragmentList, Arrays.asList(titleArray)));
        mXTabLayout.setupWithViewPager(mLazyViewPager);
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

    @Override
    protected void addListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_study;
    }
}
