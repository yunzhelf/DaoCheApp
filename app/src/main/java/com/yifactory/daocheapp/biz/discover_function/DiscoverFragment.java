package com.yifactory.daocheapp.biz.discover_function;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.biz.discover_function.activity.DiscoverPublishAnswersActivity;
import com.yifactory.daocheapp.biz.discover_function.activity.DiscoverPublishMoodActivity;
import com.yifactory.daocheapp.biz.discover_function.discover_tab.DiscoverAnswersFragment;
import com.yifactory.daocheapp.biz.discover_function.discover_tab.DiscoverMoodFragment;
import com.yifactory.daocheapp.biz.my_function.activity.MyLoginActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyRegisterActivity;
import com.yifactory.daocheapp.event.DiscoverAnswersHideSoftMsg;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.topLayout)
    AutoLinearLayout topLayout;
    @BindView(R.id.bottom_layout)
    AutoLinearLayout bottomLayout;
    @BindView(R.id.mood_tv)
    TextView mTv_mood;
    @BindView(R.id.answers_tv)
    TextView mTv_answers;

    private int tabPosition = 0;

    private DiscoverMoodFragment mDiscoverMoodFragment;
    private DiscoverAnswersFragment mDiscoverAnswersFragment;
    private FragmentManager mChildFragmentManager;

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("发现");
        bar.setRightImageResource(R.drawable.fabu);
        return true;
    }

    @Override
    protected void initData(Bundle arguments) {
        if (new SPreferenceUtil(mActivity, "config.sp").getIsLine()) {
            topLayout.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.GONE);
            mChildFragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();
            initDiscoverMoodFragment(fragmentTransaction);
        } else {
            topLayout.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_discover;
    }

    @OnClick({R.id.naviFrameRight, R.id.login_tv, R.id.register_tv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameRight:
                    Intent intent = new Intent();
                    if (!new SPreferenceUtil(mActivity, "config.sp").getIsLine()) {
                        showToast("请登录");
                        return;
                    }
                    if (tabPosition == 0) {
                        intent.setClass(mActivity, DiscoverPublishMoodActivity.class);
                    } else if (tabPosition == 1) {
                        intent.setClass(mActivity, DiscoverPublishAnswersActivity.class);
                    }
                    startActivity(intent);
                    break;
                case R.id.login_tv:
                    Intent loginIntent = new Intent(mActivity, MyLoginActivity.class);
                    startActivity(loginIntent);
                    break;
                case R.id.register_tv:
                    Intent registerIntent = new Intent(mActivity, MyRegisterActivity.class);
                    startActivity(registerIntent);
                    break;
            }
        }
    }

    @OnClick({R.id.mood_tv, R.id.answers_tv})
    public void onClickTabEvent(View view) {
        if (view != null) {
            FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();
            hideAllFragments(fragmentTransaction);
            switch (view.getId()) {
                case R.id.mood_tv:
                    EventBus.getDefault().post(new DiscoverAnswersHideSoftMsg());
                    tabPosition = 0;
                    mTv_mood.setBackgroundResource(R.drawable.bg_study_center_tab_one);
                    mTv_mood.setTextColor(Color.parseColor("#FFFFFF"));
                    mTv_answers.setTextColor(Color.parseColor("#000000"));
                    mTv_answers.setBackgroundResource(R.drawable.bg_study_center_tab_two_bg);
                    initDiscoverMoodFragment(fragmentTransaction);
                    break;
                case R.id.answers_tv:
                    tabPosition = 1;
                    mTv_mood.setBackgroundResource(R.drawable.bg_study_center_tab_one_bg);
                    mTv_mood.setTextColor(Color.parseColor("#000000"));
                    mTv_answers.setTextColor(Color.parseColor("#FFFFFF"));
                    mTv_answers.setBackgroundResource(R.drawable.bg_study_center_tab_two);
                    initDiscoverAnswersFragment(fragmentTransaction);
                    break;
            }
        }
    }

    private void hideAllFragments(FragmentTransaction fragmentTransaction) {
        if (mDiscoverMoodFragment != null) {
            fragmentTransaction.hide(mDiscoverMoodFragment);
        }
        if (mDiscoverAnswersFragment != null) {
            fragmentTransaction.hide(mDiscoverAnswersFragment);
        }
    }

    private void initDiscoverMoodFragment(FragmentTransaction fragmentTransaction) {
        if (mDiscoverMoodFragment == null) {
            mDiscoverMoodFragment = DiscoverMoodFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mDiscoverMoodFragment);
        } else {
            fragmentTransaction.show(mDiscoverMoodFragment);
        }
        fragmentTransaction.commit();
    }

    private void initDiscoverAnswersFragment(FragmentTransaction fragmentTransaction) {
        if (mDiscoverAnswersFragment == null) {
            mDiscoverAnswersFragment = DiscoverAnswersFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mDiscoverAnswersFragment);
        } else {
            fragmentTransaction.show(mDiscoverAnswersFragment);
        }
        fragmentTransaction.commit();
    }
}
