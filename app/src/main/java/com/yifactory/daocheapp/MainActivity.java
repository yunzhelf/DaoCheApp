package com.yifactory.daocheapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.app.AppCtx;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.biz.discover_function.DiscoverFragment;
import com.yifactory.daocheapp.biz.home_function.HomeFragment;
import com.yifactory.daocheapp.biz.my_function.MyFragment;
import com.yifactory.daocheapp.biz.my_function.activity.MyLoginActivity;
import com.yifactory.daocheapp.biz.studyCen_function.StudyCenterFragment;
import com.yifactory.daocheapp.biz.video_function.VideoFragment;
import com.yifactory.daocheapp.event.AppExitMsg;
import com.yifactory.daocheapp.utils.AppDavikActivityMgr;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static String EXIST = "exit";
    @BindView(R.id.mainActivity_root_view)
    AutoLinearLayout mRootViewLayout;
    @BindView(R.id.layout_bottom)
    AutoLinearLayout layoutBottom;
    @BindView(R.id.tab_home_iv)
    ImageView mIv_tabHome;
    @BindView(R.id.tab_discover_iv)
    ImageView mIv_tabDiscover;
    @BindView(R.id.tab_video_iv)
    ImageView mIv_tabVideo;
    @BindView(R.id.tab_studyCenter_iv)
    ImageView mIv_tabStudyCenter;
    @BindView(R.id.tab_my_iv)
    ImageView mIv_tabMy;

    @BindView(R.id.tab_home_tv)
    TextView mTv_tabHome;
    @BindView(R.id.tab_discover_tv)
    TextView mTv_tabDiscover;
    @BindView(R.id.tab_studyCenter_tv)
    TextView mTv_tabStudyCenter;
    @BindView(R.id.tab_my_tv)
    TextView mTv_tabMy;

    private List<TextView> tabTvList = new ArrayList<>();

    private FragmentManager mSupportFragmentManager;
    private HomeFragment mHomeFragment;
    private DiscoverFragment mDiscoverFragment;
    private VideoFragment mVideoFragment;
    private StudyCenterFragment mStudyCenterFragment;
    private MyFragment mMyFragment;
    private PlayVideoBean.DataBean.HotBean videoInfo;
    private SPreferenceUtil mSPreferenceUtil;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void addListener() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        boolean isPlay = intent.getBooleanExtra("play", false);
        videoInfo = intent.getParcelableExtra("videoInfo");
        if (isPlay) {
            FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
            hideAllFragments(fragmentTransaction);
            initVideoFragment(fragmentTransaction);
            changeTabStyle(-1);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        AppDavikActivityMgr.getScreenManager().addActivity(this);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mSPreferenceUtil = new SPreferenceUtil(this, "config.sp");
        boolean isLine = mSPreferenceUtil.getIsLine();
        if (isLine) {
            mTv_tabMy.setText("我的");
        } else {
            mTv_tabMy.setText("未登录");
        }
        tabTvList.add(mTv_tabHome);
        tabTvList.add(mTv_tabDiscover);
        tabTvList.add(mTv_tabStudyCenter);
        tabTvList.add(mTv_tabMy);
        mSupportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        initHomeFragment(fragmentTransaction);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.tab_home_layout, R.id.tab_discover_layout, R.id.tab_video_layout,
            R.id.tab_studyCenter_layout, R.id.tab_my_layout})
    public void onTabClickEvent(View view) {
        if (view != null) {
            FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
            hideAllFragments(fragmentTransaction);
            switch (view.getId()) {
                case R.id.tab_home_layout:
                    initHomeFragment(fragmentTransaction);
                    changeTabStyle(0);
                    break;
                case R.id.tab_discover_layout:
                    initDiscoverFragment(fragmentTransaction);
                    changeTabStyle(1);
                    break;
                case R.id.tab_video_layout:
                    initVideoFragment(fragmentTransaction);
                    changeTabStyle(-1);
                    break;
                case R.id.tab_studyCenter_layout:
                    initStudyCenterFragment(fragmentTransaction);
                    changeTabStyle(2);
                    break;
                case R.id.tab_my_layout:
                    initMyFragment(fragmentTransaction);
                    changeTabStyle(3);
                    break;
            }
        }
    }

    private void changeTabStyle(int poi) {
        for (int i = 0; i < tabTvList.size(); i++) {
            TextView tv = tabTvList.get(i);
            if (i == poi) {
                tv.setTextColor(Color.parseColor("#4087fd"));
            } else {
                tv.setTextColor(Color.parseColor("#666666"));
            }
        }
        switch (poi) {
            case -1:
                mIv_tabHome.setImageResource(R.drawable.tab_shouye);
                mIv_tabDiscover.setImageResource(R.drawable.tab_faxian);
                mIv_tabVideo.setImageResource(R.drawable.tab_bofang);
                mIv_tabStudyCenter.setImageResource(R.drawable.tab_xuexizhongxin);
                mIv_tabMy.setImageResource(R.drawable.tab_wode);
                break;
            case 0:
                mIv_tabHome.setImageResource(R.drawable.tab_shouye_sel);
                mIv_tabDiscover.setImageResource(R.drawable.tab_faxian);
                mIv_tabVideo.setImageResource(R.drawable.tab_bofang);
                mIv_tabStudyCenter.setImageResource(R.drawable.tab_xuexizhongxin);
                mIv_tabMy.setImageResource(R.drawable.tab_wode);
                break;
            case 1:
                mIv_tabHome.setImageResource(R.drawable.tab_shouye);
                mIv_tabDiscover.setImageResource(R.drawable.tab_faxian_sel);
                mIv_tabVideo.setImageResource(R.drawable.tab_bofang);
                mIv_tabStudyCenter.setImageResource(R.drawable.tab_xuexizhongxin);
                mIv_tabMy.setImageResource(R.drawable.tab_wode);
                break;
            case 2:
                mIv_tabHome.setImageResource(R.drawable.tab_shouye);
                mIv_tabDiscover.setImageResource(R.drawable.tab_faxian);
                mIv_tabVideo.setImageResource(R.drawable.tab_bofang);
                mIv_tabStudyCenter.setImageResource(R.drawable.tab_xuexizhongxin_sel);
                mIv_tabMy.setImageResource(R.drawable.tab_wode);
                break;
            case 3:
                mIv_tabHome.setImageResource(R.drawable.tab_shouye);
                mIv_tabDiscover.setImageResource(R.drawable.tab_faxian);
                mIv_tabVideo.setImageResource(R.drawable.tab_bofang);
                mIv_tabStudyCenter.setImageResource(R.drawable.tab_xuexizhongxin);
                mIv_tabMy.setImageResource(R.drawable.tab_wode_sel);
                break;
        }
    }

    private void initHomeFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeFragment);
        } else {
            fragmentTransaction.show(mHomeFragment);
        }
        fragmentTransaction.commit();
    }

    private void initDiscoverFragment(FragmentTransaction fragmentTransaction) {
        if (mDiscoverFragment == null) {
            mDiscoverFragment = DiscoverFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mDiscoverFragment);
        } else {
            fragmentTransaction.show(mDiscoverFragment);
        }
        fragmentTransaction.commit();
    }

    private void initVideoFragment(FragmentTransaction fragmentTransaction) {
        if (mVideoFragment == null) {
            mVideoFragment = VideoFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mVideoFragment);
        } else {
            fragmentTransaction.show(mVideoFragment);
        }
        fragmentTransaction.commit();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (videoInfo != null) {
                    EventBus.getDefault().post(videoInfo);
                }
                videoInfo = null;
            }
        }, 10);
    }

    private void initStudyCenterFragment(FragmentTransaction fragmentTransaction) {
        if (mStudyCenterFragment == null) {
            mStudyCenterFragment = StudyCenterFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mStudyCenterFragment);
        } else {
            fragmentTransaction.show(mStudyCenterFragment);
        }
        fragmentTransaction.commit();
    }

    private void initMyFragment(FragmentTransaction fragmentTransaction) {
        if (mMyFragment == null) {
            mMyFragment = MyFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mMyFragment);
        } else {
            fragmentTransaction.show(mMyFragment);
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragments(FragmentTransaction fragmentTransaction) {
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mDiscoverFragment != null) {
            fragmentTransaction.hide(mDiscoverFragment);
        }
        if (mVideoFragment != null) {
            fragmentTransaction.hide(mVideoFragment);
        }
        if (mStudyCenterFragment != null) {
            fragmentTransaction.hide(mStudyCenterFragment);
        }
        if (mMyFragment != null) {
            fragmentTransaction.hide(mMyFragment);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exitEvent(AppExitMsg appExitMsg) {
        AppCtx.isHomeTabPlayed = false;
        UserInfoUtil.clearUserInfoBean(this);
        if(mSPreferenceUtil == null){
            mSPreferenceUtil = new SPreferenceUtil(this,"config.sp");
        }
        mSPreferenceUtil.setIsLine(false);
        Intent intent = new Intent(this, MyLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
