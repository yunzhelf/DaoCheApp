package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.MainActivity;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.AppCtx;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendInterviewVideoDetailsLazyFragmentAdapter;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.fragment.HomeRecommendContentIntroduceFragment;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.fragment.HomeRecommendPersonalIntroduceFragment;
import com.yifactory.daocheapp.event.BuyVideoSuccessMsg;
import com.yifactory.daocheapp.utils.Formatter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRecommendInterviewVideoDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rootLayout)
    AutoLinearLayout rootLayout;
    @BindView(R.id.xTabLayout)
    XTabLayout mXTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mLazyViewPager;
    @BindView(R.id.video_iv)
    ImageView videoIv;
    @BindView(R.id.title_tv)
    TextView title;
    @BindView(R.id.play_tv)
    TextView play;
    @BindView(R.id.content_tv)
    TextView content;
    @BindView(R.id.play_count_tv)
    TextView count;
    @BindView(R.id.long_time)
    TextView longTime;
    @BindView(R.id.price_tv)
    TextView priceTv;

    public PlayVideoBean.DataBean.HotBean videoInfo;
    private String mUId = "";
    private SPreferenceUtil mSPreferenceUtil;
    private Dialog mDialog;
    private HomeRecommendPersonalIntroduceFragment mHomeRecommendPersonalIntroduceFragment;
    private HomeRecommendContentIntroduceFragment mHomeRecommendContentIntroduceFragment;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("详情");
        return true;
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        initTabLayout();
    }

    private void initTabLayout() {
        String[] titleArray = {"人物介绍", "内容简介"};
        List<BaseFragment> fragmentList = new ArrayList<>();
        mHomeRecommendPersonalIntroduceFragment = HomeRecommendPersonalIntroduceFragment.newInstance(videoInfo.getRId());
        mHomeRecommendContentIntroduceFragment = HomeRecommendContentIntroduceFragment.newInstance(videoInfo.getRId());
        fragmentList.add(mHomeRecommendPersonalIntroduceFragment);
        fragmentList.add(mHomeRecommendContentIntroduceFragment);
        mLazyViewPager.setAdapter(new HomeRecommendInterviewVideoDetailsLazyFragmentAdapter(getSupportFragmentManager(), fragmentList, Arrays.asList(titleArray)));
        mXTabLayout.setupWithViewPager(mLazyViewPager);
        getVideoDetail();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mSPreferenceUtil = new SPreferenceUtil(this, "config.sp");
        LoginBean.DataBean userInfoBean = UserInfoUtil.getUserInfoBean(this);
        if (userInfoBean != null) {
            mUId = userInfoBean.getUId();
        }
        videoInfo = getIntent().getParcelableExtra("videoInfo");
        mDialog = SDDialogUtil.newLoading(this, "请求中");
    }

    private void getVideoDetail() {
        RxHttpUtils.createApi(ApiService.class)
                .playVideo(videoInfo.getRId(), mUId)
                .compose(Transformer.<PlayVideoBean>switchSchedulers(mDialog))
                .subscribe(new CommonObserver<PlayVideoBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        requestFinish();
                    }

                    @Override
                    protected void onSuccess(PlayVideoBean playVideoBean) {
                        rootLayout.setVisibility(View.VISIBLE);
                        requestFinish();
                        if (playVideoBean.getResponseState().equals("1")) {
                            if (playVideoBean.getData().size() > 0) {
                                setData(playVideoBean.getData().get(0).getResource());
                            }
                        } else {
                            showToast(playVideoBean.getMsg());
                        }
                    }
                });
    }

    private void requestFinish() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void setData(final PlayVideoBean.DataBean.ResourceBean data) {
        Glide.with(AppCtx.getC()).load(data.getIndexImg()).into(videoIv);
        title.setText(data.getTitle());
        final int goldCount = data.getGoldCount();
        final int buyState = data.getBuyState();
        if (goldCount > 0) {
            if (mSPreferenceUtil.getIsLine()) {
                if (buyState == 0) {
                    play.setText("购买");
                } else {
                    play.setText("播放");
                }
            } else {
                play.setText("购买");
            }
            priceTv.setText(goldCount + "金币");
        } else {
            priceTv.setText("免费");
            play.setText("播放");
        }
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goldCount > 0) {
                    if (mSPreferenceUtil.getIsLine()) {
                        if (buyState == 1) {
                            toPlayVideo();
                        } else {
                            startActivity(new Intent(HomeRecommendInterviewVideoDetailsActivity.this, HomeRecommendVideoBuyDetailsActivity.class).putExtra("data", data));
                        }
                    } else {
                        showToast("请登陆");
                    }
                } else {
                    toPlayVideo();
                }
            }
        });
        content.setText(data.getVideoContent());
        count.setText(data.getShowCounts() + "次");
        longTime.setText("时长: " + Formatter.formatTime(data.getTotalMinute()));

        PlayVideoBean.DataBean.ResourceBean.CreatorBean creator = data.getCreator();
        if (mHomeRecommendPersonalIntroduceFragment != null) {
            mHomeRecommendPersonalIntroduceFragment.setData(creator);
        }
        if (mHomeRecommendContentIntroduceFragment != null) {
            mHomeRecommendContentIntroduceFragment.setData(data);
        }
    }

    private void toPlayVideo() {
        Intent intent = new Intent(HomeRecommendInterviewVideoDetailsActivity.this, MainActivity.class);
        intent.putExtra("videoInfo", videoInfo);
        intent.putExtra("play", true);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_recommend_interview_video_details;
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
    public void onRefresh() {
        getVideoDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void buyVideoSuccess(BuyVideoSuccessMsg buyVideoSuccessMsg) {
        getVideoDetail();
    }
}
