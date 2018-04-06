package com.yifactory.daocheapp.biz.home_function;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.AppCtx;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.biz.home_function.activity.HomeMsgActivity;
import com.yifactory.daocheapp.biz.home_function.activity.HomeSearchActivity;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeAftermarketFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeCarSupermarketFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeIndustryAnalysisFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeInterviewFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeNewActivityFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeOfflineActivityFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomePromoteProfitFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeRecommendFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeSecondHandCarFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeShareFragment;
import com.yifactory.daocheapp.biz.home_function.home_tab.HomeTypeFragment;
import com.yifactory.daocheapp.event.HomeTabAnimMessage;
import com.yifactory.daocheapp.event.HomeTabChangedMessage;
import com.yifactory.daocheapp.event.HomeTabChangedTwoMessage;
import com.yifactory.daocheapp.utils.ScreenUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    private List<TextView> tabTitleList = new ArrayList<>();
    @BindView(R.id.tab_container_layout)
    AutoLinearLayout tabContainerLayout;
    @BindView(R.id.type_tv)
    TextView mTv_type;
    @BindView(R.id.recommend_tv)
    TextView mTv_recommend;
    @BindView(R.id.interview_tv)
    TextView mTv_interview;
    @BindView(R.id.share_tv)
    TextView mTv_share;
    @BindView(R.id.industry_analysis_tv)
    TextView mTv_industryAnalysis;
    @BindView(R.id.second_hand_car_tv)
    TextView mTv_secondHandCar;
    @BindView(R.id.auto_trade_tv)
    TextView mTv_autoTrade;
    @BindView(R.id.offline_activity_tv)
    TextView mTv_offlineActivity;
    @BindView(R.id.promote_profit_tv)
    TextView mTv_promoteProfit;
    @BindView(R.id.aftermarket_tv)
    TextView mTv_aftermarket;
    @BindView(R.id.new_activity_tv)
    TextView mTv_new;

    private HomeTypeFragment mHomeTypeFragment;
    private HomeRecommendFragment mHomeRecommendFragment;
    private HomeInterviewFragment mHomeInterviewFragment;
    private HomeShareFragment mHomeShareFragment;
    private HomeIndustryAnalysisFragment mHomeIndustryAnalysisFragment;
    private HomeSecondHandCarFragment mHomeSecondHandCarFragment;
    private HomeCarSupermarketFragment mHomeCarSupermarketFragment;
    private HomeOfflineActivityFragment mHomeOfflineActivityFragment;
    private HomePromoteProfitFragment mHomePromoteProfitFragment;
    private HomeNewActivityFragment homeNewActivityFragment;
    private HomeAftermarketFragment mHomeAftermarketFragment;
    private FragmentManager mChildFragmentManager;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {
        EventBus.getDefault().register(this);
        initTabTitleList();
        mChildFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();
        initHomeRecommendFragment(fragmentTransaction);
    }

    private void initTabTitleList() {
        tabTitleList.add(mTv_type);
        tabTitleList.add(mTv_recommend);
        tabTitleList.add(mTv_interview);
        tabTitleList.add(mTv_share);
        tabTitleList.add(mTv_industryAnalysis);
        tabTitleList.add(mTv_secondHandCar);
        tabTitleList.add(mTv_autoTrade);
//        tabTitleList.add(mTv_offlineActivity);
//        tabTitleList.add(mTv_promoteProfit);
        tabTitleList.add(mTv_new);
        tabTitleList.add(mTv_aftermarket);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @OnClick({R.id.type_tv, R.id.recommend_tv, R.id.interview_tv, R.id.share_tv, R.id.industry_analysis_tv,
            R.id.second_hand_car_tv, R.id.auto_trade_tv, R.id.offline_activity_tv, R.id.promote_profit_tv, R.id.aftermarket_tv, R.id.new_activity_tv})
    public void onClickTabEvent(View view) {
        if (view != null) {
            FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();
            hideAllFragments(fragmentTransaction);
            switch (view.getId()) {
                case R.id.type_tv:
                    changedTabStyle(0);
                    initHomeTypeFragment(fragmentTransaction);
                    break;
                case R.id.recommend_tv:
                    changedTabStyle(1);
                    initHomeRecommendFragment(fragmentTransaction);
                    break;
                case R.id.interview_tv:
                    changedTabStyle(2);
                    initHomeInterviewFragment(fragmentTransaction);
                    break;
                case R.id.share_tv:
                    changedTabStyle(3);
                    initHomeShareFragment(fragmentTransaction);
                    break;
                case R.id.industry_analysis_tv:
                    changedTabStyle(4);
                    initHomeIndustryAnalysisFragment(fragmentTransaction);
                    break;
                case R.id.second_hand_car_tv:
                    changedTabStyle(5);
                    initHomeSecondHandCarFragment(fragmentTransaction);
                    break;
                case R.id.auto_trade_tv:
                    changedTabStyle(6);
                    initHomeAutoTradeFragment(fragmentTransaction);
                    break;
//                case R.id.offline_activity_tv:
//                    changedTabStyle(7);
//                    initHomeOfflineActivityFragment(fragmentTransaction);
//                    break;
//                case R.id.promote_profit_tv:
//                    changedTabStyle(8);
//                    initHomePromoteProfitFragment(fragmentTransaction);
//                    break;
                case R.id.new_activity_tv:
                    changedTabStyle(7);
                    initHomeNewFragment(fragmentTransaction);
                    break;
                case R.id.aftermarket_tv:
                    changedTabStyle(8);
                    initHomeAftermarketFragment(fragmentTransaction);
                    break;
            }
        }
    }

    @OnClick({R.id.search_tv, R.id.msg_iv})
    public void onClickEvent(View view) {
        if (view != null) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.search_tv:
                    intent.setClass(mActivity, HomeSearchActivity.class);
                    break;
                case R.id.msg_iv:
                    intent.setClass(mActivity, HomeMsgActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

    private void changedTabStyle(int poi) {
        for (int i = 0; i < tabTitleList.size(); i++) {
            TextView tabTv = tabTitleList.get(i);
            if (i == poi) {
                tabTv.setBackgroundResource(R.drawable.bg_home_tab_selected);
                tabTv.setTextColor(Color.parseColor("#4087fd"));
            } else {
                tabTv.setBackgroundResource(R.drawable.bg_home_tab_default);
                tabTv.setTextColor(Color.parseColor("#333333"));
            }
        }
    }

    private void hideAllFragments(FragmentTransaction fragmentTransaction) {
        if (mHomeTypeFragment != null) {
            fragmentTransaction.hide(mHomeTypeFragment);
        }
        if (mHomeRecommendFragment != null) {
            fragmentTransaction.hide(mHomeRecommendFragment);
        }
        if (mHomeInterviewFragment != null) {
            fragmentTransaction.hide(mHomeInterviewFragment);
        }
        if (mHomeShareFragment != null) {
            fragmentTransaction.hide(mHomeShareFragment);
        }
        if (mHomeIndustryAnalysisFragment != null) {
            fragmentTransaction.hide(mHomeIndustryAnalysisFragment);
        }
        if (mHomeSecondHandCarFragment != null) {
            fragmentTransaction.hide(mHomeSecondHandCarFragment);
        }
        if (mHomeCarSupermarketFragment != null) {
            fragmentTransaction.hide(mHomeCarSupermarketFragment);
        }
        if (mHomeOfflineActivityFragment != null) {
            fragmentTransaction.hide(mHomeOfflineActivityFragment);
        }
        if (mHomePromoteProfitFragment != null) {
            fragmentTransaction.hide(mHomePromoteProfitFragment);
        }
        if (homeNewActivityFragment != null) {
            fragmentTransaction.hide(homeNewActivityFragment);
        }
        if (mHomeAftermarketFragment != null) {
            fragmentTransaction.hide(mHomeAftermarketFragment);
        }
    }

    private void initHomeTypeFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeTypeFragment == null) {
            mHomeTypeFragment = HomeTypeFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeTypeFragment);
        } else {
            fragmentTransaction.show(mHomeTypeFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomeRecommendFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeRecommendFragment == null) {
            mHomeRecommendFragment = HomeRecommendFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeRecommendFragment);
        } else {
            fragmentTransaction.show(mHomeRecommendFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomeInterviewFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeInterviewFragment == null) {
            mHomeInterviewFragment = HomeInterviewFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeInterviewFragment);
        } else {
            fragmentTransaction.show(mHomeInterviewFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomeShareFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeShareFragment == null) {
            mHomeShareFragment = HomeShareFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeShareFragment);
        } else {
            fragmentTransaction.show(mHomeShareFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomeIndustryAnalysisFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeIndustryAnalysisFragment == null) {
            mHomeIndustryAnalysisFragment = HomeIndustryAnalysisFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeIndustryAnalysisFragment);
        } else {
            fragmentTransaction.show(mHomeIndustryAnalysisFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomeSecondHandCarFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeSecondHandCarFragment == null) {
            mHomeSecondHandCarFragment = HomeSecondHandCarFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeSecondHandCarFragment);
        } else {
            fragmentTransaction.show(mHomeSecondHandCarFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomeAutoTradeFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeCarSupermarketFragment == null) {
            mHomeCarSupermarketFragment = HomeCarSupermarketFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeCarSupermarketFragment);
        } else {
            fragmentTransaction.show(mHomeCarSupermarketFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomeOfflineActivityFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeOfflineActivityFragment == null) {
            mHomeOfflineActivityFragment = HomeOfflineActivityFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeOfflineActivityFragment);
        } else {
            fragmentTransaction.show(mHomeOfflineActivityFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomePromoteProfitFragment(FragmentTransaction fragmentTransaction) {
        if (mHomePromoteProfitFragment == null) {
            mHomePromoteProfitFragment = HomePromoteProfitFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomePromoteProfitFragment);
        } else {
            fragmentTransaction.show(mHomePromoteProfitFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomeNewFragment(FragmentTransaction fragmentTransaction) {
        if (homeNewActivityFragment == null) {
            homeNewActivityFragment = HomeNewActivityFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, homeNewActivityFragment);
        } else {
            fragmentTransaction.show(homeNewActivityFragment);
        }
        fragmentTransaction.commit();
    }

    private void initHomeAftermarketFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeAftermarketFragment == null) {
            mHomeAftermarketFragment = HomeAftermarketFragment.newInstance();
            fragmentTransaction.add(R.id.fragment_container, mHomeAftermarketFragment);
        } else {
            fragmentTransaction.show(mHomeAftermarketFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void homeTabChangedEvent(HomeTabChangedMessage homeTabChangedMessage) {
        FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();
        hideAllFragments(fragmentTransaction);
        changedTabStyle(2);
        initHomeInterviewFragment(fragmentTransaction);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void homeTabChangedTwoEvent(HomeTabChangedTwoMessage homeTabChangedMessage) {
        FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();
        hideAllFragments(fragmentTransaction);
        changedTabStyle(0);
        initHomeTypeFragment(fragmentTransaction);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void homeTabAnim(HomeTabAnimMessage homeTabAnimMessage) {
        if (!AppCtx.isHomeTabPlayed) {
            tabContainerLayout.post(new Runnable() {
                @Override
                public void run() {
                    int width = tabContainerLayout.getWidth();
                    ObjectAnimator animator = ObjectAnimator.ofFloat(tabContainerLayout, "translationX", 0.0f, ScreenUtil.getScreenWidth(mActivity) - width, 0.0f);
                    animator.setDuration(2000);
                    animator.start();
                }
            });
            AppCtx.isHomeTabPlayed = !AppCtx.isHomeTabPlayed;
        }
    }
}
