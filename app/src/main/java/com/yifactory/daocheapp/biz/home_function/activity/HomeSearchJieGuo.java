package com.yifactory.daocheapp.biz.home_function.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.VideoListBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendInterviewVideoDetailsActivity;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendVideoAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeSearchJieGuo extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.video_rv)
    RecyclerView mRv_video;
    private Dialog mDialog;
    HomeRecommendVideoAdapter videoAdapter;
    private int pageNum = 0;
    String searchContent;
    private String mUId;

    @Override
    public void onRefresh() {
        getOneVideoList(ApiConstant.REQUEST_NORMAL, 0);
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return true;
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        initVideoRv();
    }

    private List<PlayVideoBean.DataBean.HotBean> managementTitleArray = new ArrayList<>();

    private void initVideoRv() {
        videoAdapter = new HomeRecommendVideoAdapter();
        mRv_video.setFocusableInTouchMode(false);
        mRv_video.requestFocus();
        mRv_video.setNestedScrollingEnabled(false);
        mRv_video.setLayoutManager(new LinearLayoutManager(HomeSearchJieGuo.this));
        mRv_video.setAdapter(videoAdapter);
        videoAdapter.setNewData(managementTitleArray);
        videoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum += 1;
                getOneVideoList(ApiConstant.REQUEST_NORMAL, pageNum);
            }
        });
        videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<PlayVideoBean.DataBean.HotBean> dataArray = videoAdapter.getData();
                startActivity(new Intent(HomeSearchJieGuo.this, HomeRecommendInterviewVideoDetailsActivity.class).putExtra("videoInfo", dataArray.get(position)));
            }
        });
    }

    private void getOneVideoList(final String requestMark, int pageNum) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mDialog.show();
        }
        RxHttpUtils.createApi(ApiService.class)
                .search(searchContent, mUId, pageNum + "")
                .compose(Transformer.<VideoListBean>switchSchedulers())
                .subscribe(new CommonObserver<VideoListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        cancelDialog();
                    }

                    @Override
                    protected void onSuccess(VideoListBean getSysArmyAnasBean) {
                        cancelDialog();
                        if (getSysArmyAnasBean.responseState.equals("1")) {
                            handleEntityData(requestMark, getSysArmyAnasBean);
                        } else {
                            showToast(getSysArmyAnasBean.msg);
                        }
                    }
                });
    }

    private void handleEntityData(String requestMark, VideoListBean getSysArmyAnasBean) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            videoAdapter.addData(getSysArmyAnasBean.data);
            videoAdapter.loadMoreEnd();
        } else {
            videoAdapter.setNewData(getSysArmyAnasBean.data);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        searchContent = getIntent().getStringExtra("content");
        mTitleBar.setTitleText(searchContent + "");
        mDialog = SDDialogUtil.newLoading(HomeSearchJieGuo.this, "请求中");
        LoginBean.DataBean userInfoBean = UserInfoUtil.getUserInfoBean(HomeSearchJieGuo.this);
        if (userInfoBean != null) {
            mUId = userInfoBean.getUId();
        }

        getOneVideoList(ApiConstant.REQUEST_NORMAL, 0);
    }

    private void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sousuo_jie_guo;
    }
}
