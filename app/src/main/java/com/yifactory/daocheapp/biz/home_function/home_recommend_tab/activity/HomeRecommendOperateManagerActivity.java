package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.MainActivity;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.GetCategoryListBean;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.VideoListBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendManagementTitleAdapter;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendVideoAdapter;
import com.yifactory.daocheapp.event.HomeTabChangedTwoMessage;
import com.yifactory.daocheapp.utils.AppDavikActivityMgr;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRecommendOperateManagerActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.management_title_rv)
    RecyclerView mRv_managementTitle;
    @BindView(R.id.more_iv)
    ImageView mIv_more;
    private List<PlayVideoBean.DataBean.HotBean> dataArray = new ArrayList<>();
    private HomeRecommendVideoAdapter videoAdapter;
    @BindView(R.id.video_rv)
    RecyclerView mRv_video;

    @BindView(R.id.bg_view)
    View bg_view;

    @BindView(R.id.time_goUpAndDown)
    AutoLinearLayout mTime_goUpAndDown;
    @BindView(R.id.time_all_tv)
    TextView mTimeAll;
    @BindView(R.id.time_more_little_tv)
    TextView mTimeMoreLittleTv;
    @BindView(R.id.time_little_more_tv)
    TextView mTimeLittleMoreTv;

    @BindView(R.id.buy_count)
    AutoLinearLayout mBuy_count;
    @BindView(R.id.all_tv)
    TextView mAllTv;
    @BindView(R.id.more_little_tv)
    TextView mMoreLittleTv;
    @BindView(R.id.little_more_tv)
    TextView mLittleMoreTv;

    @BindView(R.id.time_long)
    AutoLinearLayout mTime_long;
    @BindView(R.id.all_tv2)
    TextView mAllTv2;
    @BindView(R.id.ten_minute_below_tv)
    TextView mTenMinuteBelowTv;
    @BindView(R.id.ten_to_fifth_minute_tv)
    TextView mTenToFifthMinuteTv;
    @BindView(R.id.fifth_to_twenty_minute_tv)
    TextView mFifthToTwentyMinuteTv;
    @BindView(R.id.twenty_minute_above_tv)
    TextView mTwentyMinuteAboveTv;

    private List<GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean> mCategorySecondList = new ArrayList<>();
    private HomeRecommendManagementTitleAdapter mManagementTitleAdapter;
    private Dialog mDialog;
    private String scId, orderBy, minTotalMinute, maxTotalMinute;
    private int pageNum = 0;
    private int categoryPosition = 0;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setRightImageResource(R.drawable.fenlei);
        return true;
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        videoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum += 1;
                mRv_video.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getVideoList(ApiConstant.REQUEST_NORMAL, pageNum);
                    }
                }, 1000);
            }
        }, mRv_video);
    }

    @Override
    protected void initView() {
        initManagementTitleRv();
        initVideoRv();
    }

    private void initVideoRv() {
        videoAdapter = new HomeRecommendVideoAdapter();
        mRv_video.setLayoutManager(new LinearLayoutManager(this));
        mRv_video.setAdapter(videoAdapter);
        videoAdapter.setNewData(dataArray);
        videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dataArray = videoAdapter.getData();
                startActivity(new Intent(HomeRecommendOperateManagerActivity.this, HomeRecommendInterviewVideoDetailsActivity.class).putExtra("videoInfo", dataArray.get(position)));
            }
        });
    }

    private List<GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean> mTempCategorySecondList = new ArrayList<>();

    private void initManagementTitleRv() {
        mManagementTitleAdapter = new HomeRecommendManagementTitleAdapter();
        mRv_managementTitle.setLayoutManager(new GridLayoutManager(this, 4));
        mRv_managementTitle.setAdapter(mManagementTitleAdapter);
        if (mCategorySecondList.size() > 8) {
            mIv_more.setVisibility(View.VISIBLE);
            mTempCategorySecondList.addAll(mCategorySecondList.subList(0, 8));
            mManagementTitleAdapter.setNewData(mTempCategorySecondList);
        } else {
            mIv_more.setVisibility(View.GONE);
            mManagementTitleAdapter.setNewData(mCategorySecondList);
        }
        if (mCategorySecondList.size() > 0) {
            mCategorySecondList.get(categoryPosition).isSelected = true;
            scId = mCategorySecondList.get(categoryPosition).getScId();
            pageNum = 0;
            mDialog.show();
            getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
        }
        mManagementTitleAdapter.notifyDataSetChanged();
        mManagementTitleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean item : mCategorySecondList) {
                    item.isSelected = false;
                }
                mCategorySecondList.get(position).isSelected = true;
                adapter.notifyDataSetChanged();
                hideAllShow();
                scId = mCategorySecondList.get(position).getScId();
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mDialog = SDDialogUtil.newLoading(this, "请求中...");
        getIntentData();
    }

    private void getVideoList(final String requestMark, int pageNum) {
        RxHttpUtils.createApi(ApiService.class)
                .getHomeROMVideoList(scId, String.valueOf(pageNum), null, orderBy, minTotalMinute, maxTotalMinute)
                .compose(Transformer.<VideoListBean>switchSchedulers())
                .subscribe(new CommonObserver<VideoListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        cancelDialog();
                        failedRequest(requestMark, errorMsg);
                    }

                    @Override
                    protected void onSuccess(VideoListBean getSysArmyAnasBean) {
                        cancelDialog();
                        if (getSysArmyAnasBean.responseState.equals("1")) {
                            handleUserQuestionListBean(requestMark, getSysArmyAnasBean);

                            handleEntityData(requestMark, getSysArmyAnasBean);
                        } else {
                            failedRequest(requestMark, getSysArmyAnasBean.msg);
                        }
                    }
                });
    }

    private void failedRequest(String requestMark, String errorMsg) {
        showToast(errorMsg);
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            videoAdapter.loadMoreFail();
            if (pageNum != 0) {
                pageNum -= 1;
            }
        }
    }

    private void handleUserQuestionListBean(String requestMark, VideoListBean videoListBean) {
        List<PlayVideoBean.DataBean.HotBean> hotBeanList = videoListBean.data;
        if (hotBeanList.size() > 0) {
            if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
                videoAdapter.loadMoreComplete();
                videoAdapter.addData(hotBeanList);
            } else {
                videoAdapter.setNewData(hotBeanList);
            }
        } else {
            if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
                videoAdapter.loadMoreEnd();
            } else {
                showToast("暂无数据");
            }
        }
    }

    private void handleEntityData(String requestMark, VideoListBean videoListBean) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            videoAdapter.addData(videoListBean.data);
        } else {
            videoAdapter.setNewData(videoListBean.data);
        }
    }

    private void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            videoAdapter.setEnableLoadMore(true);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        String mark = intent.getStringExtra("mark");
        categoryPosition = intent.getIntExtra("position", 0);
        if (mark.equals("type")) {
            GetCategoryListBean.DataBean dataBean = (GetCategoryListBean.DataBean) intent.getSerializableExtra("dataBean");
            String firstCategoryContent = dataBean.getFirstCategoryContent();
            mTitleBar.setTitleText(firstCategoryContent);
            List<GetCategoryListBean.DataBean.CategorySecondListBean> categorySecondList = dataBean.getCategorySecondList();
            for (GetCategoryListBean.DataBean.CategorySecondListBean categorySecondListBean : categorySecondList) {
                GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean secondListBean = new GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean();
                secondListBean.setCreateTime(categorySecondListBean.getCreateTime());
                secondListBean.setDeleteFlag(categorySecondListBean.getDeleteFlag());
                secondListBean.setFcId(categorySecondListBean.getFcId());
                secondListBean.setScId(categorySecondListBean.getScId());
                secondListBean.setSecondContent(categorySecondListBean.getSecondContent());
                secondListBean.setUpdateTime(categorySecondListBean.getUpdateTime());
                mCategorySecondList.add(secondListBean);
            }
        } else if (mark.equals("recommend")) {
            GetSystemInfoBean.DataBean.IndexBtnsBean indexBtnsBean = (GetSystemInfoBean.DataBean.IndexBtnsBean) intent.getSerializableExtra("indexBtnsBean");
            String firstCategoryContent = indexBtnsBean.getFirstCategoryContent();
            mTitleBar.setTitleText(firstCategoryContent);
            List<GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean> categorySecondList = indexBtnsBean.getCategorySecondList();
            mCategorySecondList.addAll(categorySecondList);
        } else if (mark.equals("coupon")) {
            String fcId = getIntent().getStringExtra("fcId");
            String scId = getIntent().getStringExtra("scId");
            getCategoryData(fcId,scId);
        }
    }

    private void getCategoryData(final String fcId, final String scId){
//        final Dialog vDialog = SDDialogUtil.newLoading(this,"请求中");
//        vDialog.show();
        mDialog.show();
        RxHttpUtils.createApi(ApiService.class)
                .getCategoryList()
                .compose(Transformer.<GetCategoryListBean>switchSchedulers())
                .subscribe(new CommonObserver<GetCategoryListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
//                        vDialog.dismiss();
                    }

                    @Override
                    protected void onSuccess(GetCategoryListBean getCategoryListBean) {
//                        vDialog.dismiss();
                        if(getCategoryListBean != null && getCategoryListBean.getResponseState().equals("1")){
                            for(GetCategoryListBean.DataBean category : getCategoryListBean.getData()){
                                if(category.getFcId().equals(fcId)){
                                    mTitleBar.setTitleText(category.getFirstCategoryContent());
                                    List<GetCategoryListBean.DataBean.CategorySecondListBean> categorySecondList = category.getCategorySecondList();
                                    int i = 0;
                                    for (GetCategoryListBean.DataBean.CategorySecondListBean categorySecondListBean : categorySecondList) {
                                        GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean secondListBean = new GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean();
                                        secondListBean.setCreateTime(categorySecondListBean.getCreateTime());
                                        secondListBean.setDeleteFlag(categorySecondListBean.getDeleteFlag());
                                        secondListBean.setFcId(categorySecondListBean.getFcId());
                                        secondListBean.setScId(categorySecondListBean.getScId());
                                        secondListBean.setSecondContent(categorySecondListBean.getSecondContent());
                                        secondListBean.setUpdateTime(categorySecondListBean.getUpdateTime());
                                        mCategorySecondList.add(secondListBean);
                                        if(scId != null && secondListBean.getScId().equals(scId)){
                                            categoryPosition = i;
                                        }
                                        i++;
                                    }
                                }
                            }
                            initManagementTitleRv();
                        }
                    }
                });
    }

    @OnClick({R.id.naviFrameLeft, R.id.naviFrameRight, R.id.more_iv, R.id.time_layout, R.id.buy_count_layout,
            R.id.time_long_layout, R.id.bg_view})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.naviFrameRight:
                    /*AppDavikActivityMgr.getScreenManager().removeMainActivity();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);*/
                    EventBus.getDefault().post(new HomeTabChangedTwoMessage());
                    finish();
                    break;
                case R.id.more_iv:
                    hideAllShow();
                    showMoreTabEvent();
                    break;
                case R.id.time_layout:
                    isShowBg(true);
                    isShowTimeGoUpAndDown(true);
                    isShowBuyCountEvent(false);
                    isShowTimeLongEvent(false);
                    break;
                case R.id.buy_count_layout:
                    isShowBg(true);
                    isShowTimeGoUpAndDown(false);
                    isShowBuyCountEvent(true);
                    isShowTimeLongEvent(false);
                    break;
                case R.id.time_long_layout:
                    isShowBg(true);
                    isShowTimeGoUpAndDown(false);
                    isShowBuyCountEvent(false);
                    isShowTimeLongEvent(true);
                    break;
                case R.id.bg_view:
                    hideAllShow();
                    break;
            }
        }
    }

    private boolean isShowMore = false;

    private void showMoreTabEvent() {
        isShowMore = !isShowMore;
        if (isShowMore) {
            mManagementTitleAdapter.setNewData(mCategorySecondList);
        } else {
            mManagementTitleAdapter.setNewData(mTempCategorySecondList);
        }
    }

    private void hideAllShow() {
        isShowTimeGoUpAndDown(false);
        isShowTimeLongEvent(false);
        isShowBuyCountEvent(false);
        isShowBg(false);
    }

    private void isShowTimeGoUpAndDown(boolean isShow) {
        if (isShow) {
            mTime_goUpAndDown.setVisibility(View.VISIBLE);
        } else {
            mTime_goUpAndDown.setVisibility(View.GONE);
        }
    }

    private void isShowBuyCountEvent(boolean isShow) {
        if (isShow) {
            mBuy_count.setVisibility(View.VISIBLE);
        } else {
            mBuy_count.setVisibility(View.GONE);
        }
    }

    private void isShowTimeLongEvent(boolean isShow) {
        if (isShow) {
            mTime_long.setVisibility(View.VISIBLE);
        } else {
            mTime_long.setVisibility(View.GONE);
        }
    }

    private void isShowBg(boolean isShow) {
        if (isShow) {
            bg_view.setVisibility(View.VISIBLE);
        } else {
            bg_view.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.all_tv, R.id.more_little_tv, R.id.little_more_tv, R.id.all_tv2,
            R.id.ten_minute_below_tv, R.id.ten_to_fifth_minute_tv, R.id.fifth_to_twenty_minute_tv,
            R.id.twenty_minute_above_tv, R.id.time_all_tv, R.id.time_more_little_tv, R.id.time_little_more_tv})
    public void caseSelectedEvent(View v) {
        if (v != null) {
            isShowBg(false);
            if (v.getId() == R.id.time_all_tv) {
                mTimeAll.setTextColor(Color.parseColor("#4087fd"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowTimeGoUpAndDown(false);
                clear();
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.time_more_little_tv) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#4087fd"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowTimeGoUpAndDown(false);
                clear();
                orderBy = "2";
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.time_little_more_tv) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#4087fd"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowTimeGoUpAndDown(false);
                clear();
                orderBy = "-2";
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.all_tv) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#4087fd"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowBuyCountEvent(false);
                clear();
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.more_little_tv) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#4087fd"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowBuyCountEvent(false);
                clear();
                orderBy = "-1";
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.little_more_tv) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#4087fd"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowBuyCountEvent(false);
                clear();
                orderBy = "1";
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.all_tv2) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#4087fd"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowTimeLongEvent(false);
                clear();
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.ten_minute_below_tv) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#4087fd"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowTimeLongEvent(false);
                clear();
                maxTotalMinute = "10";
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.ten_to_fifth_minute_tv) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#4087fd"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowTimeLongEvent(false);
                clear();
                minTotalMinute = "10";
                maxTotalMinute = "15";
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.fifth_to_twenty_minute_tv) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#4087fd"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#666666"));
                isShowTimeLongEvent(false);
                clear();
                minTotalMinute = "15";
                maxTotalMinute = "20";
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            } else if (v.getId() == R.id.twenty_minute_above_tv) {
                mTimeAll.setTextColor(Color.parseColor("#666666"));
                mTimeMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mTimeLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv.setTextColor(Color.parseColor("#666666"));
                mMoreLittleTv.setTextColor(Color.parseColor("#666666"));
                mLittleMoreTv.setTextColor(Color.parseColor("#666666"));
                mAllTv2.setTextColor(Color.parseColor("#666666"));
                mTenMinuteBelowTv.setTextColor(Color.parseColor("#666666"));
                mTenToFifthMinuteTv.setTextColor(Color.parseColor("#666666"));
                mFifthToTwentyMinuteTv.setTextColor(Color.parseColor("#666666"));
                mTwentyMinuteAboveTv.setTextColor(Color.parseColor("#4087fd"));
                isShowTimeLongEvent(false);
                clear();
                minTotalMinute = "20";
                pageNum = 0;
                mDialog.show();
                getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
            }
        }
    }

    void clear() {
        orderBy = null;
        minTotalMinute = null;
        maxTotalMinute = null;
    }

    @Override
    public void onRefresh() {
        hideAllShow();
        pageNum = 0;
        getVideoList(ApiConstant.REQUEST_REFRESH, pageNum);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_recommend_operate_manager;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
