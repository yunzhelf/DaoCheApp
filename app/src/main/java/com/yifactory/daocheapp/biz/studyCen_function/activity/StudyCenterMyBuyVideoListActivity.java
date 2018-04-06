package com.yifactory.daocheapp.biz.studyCen_function.activity;

import android.app.Dialog;
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
import com.yifactory.daocheapp.bean.GetUserBuyedBean;
import com.yifactory.daocheapp.biz.studyCen_function.adapter.StudyCenMyBuyVideoAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StudyCenterMyBuyVideoListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.video_rv)
    RecyclerView mRv_video;
    private String mFcId;
    private String mUId;
    private Dialog mDialog;
    private int mPageNum = 0;
    private StudyCenMyBuyVideoAdapter mVideoAdapter;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mVideoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPageNum++;
                getUserBuyedData(ApiConstant.REQUEST_NORMAL, mPageNum);
            }
        }, mRv_video);
    }

    @Override
    protected void initView() {
        initVideoRv();
    }

    private void initVideoRv() {
        mVideoAdapter = new StudyCenMyBuyVideoAdapter();
        mRv_video.setNestedScrollingEnabled(false);
        mRv_video.setLayoutManager(new LinearLayoutManager(this));
        mRv_video.setAdapter(mVideoAdapter);
        mVideoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                int buyState = mVideoAdapter.getData().get(i).getBuyState();
                if (buyState == 0) {//未购买
                    showToast("您还未购买视频");
                } else {

                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        mDialog = SDDialogUtil.newLoading(this, "请求中");
        getIntentData();
        mDialog.show();
        getUserBuyedData(ApiConstant.REQUEST_NORMAL, mPageNum);
    }

    private void getUserBuyedData(final String requestMark, int pageNum) {
        RxHttpUtils.createApi(ApiService.class)
                .getUserBuyed(mUId, mFcId, pageNum)
                .compose(Transformer.<GetUserBuyedBean>switchSchedulers())
                .subscribe(new CommonObserver<GetUserBuyedBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        cancelDialog();
                        handleError(requestMark, errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetUserBuyedBean getUserBuyedBean) {
                        cancelDialog();
                        if (getUserBuyedBean.getResponseState().equals("1")) {
                            handleSuccess(requestMark, getUserBuyedBean);
                        } else {
                            handleError(requestMark, getUserBuyedBean.getMsg());
                        }
                    }
                });
    }

    private void handleError(String requestMark, String errorMsg) {
        showToast(errorMsg);
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            if (mPageNum != 0) {
                mPageNum -= 1;
                mVideoAdapter.loadMoreFail();
            }
        }
    }

    private void handleSuccess(String requestMark, GetUserBuyedBean getUserBuyedBean) {
        List<GetUserBuyedBean.DataBean> dataBeanList = getUserBuyedBean.getData();
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mVideoAdapter.addData(dataBeanList);
            if (mPageNum > 0) {
                if (dataBeanList.size() > 0) {
                    mVideoAdapter.loadMoreComplete();
                } else {
                    mVideoAdapter.loadMoreEnd();
                }
            } else {
                if (dataBeanList.size() == 0) {
                    showToast("暂无数据");
                }
            }
        } else {
            mVideoAdapter.setNewData(dataBeanList);
            if (dataBeanList.size() == 0) {
                showToast("暂无数据");
            }
        }
    }

    private void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void getIntentData() {
        String title = getIntent().getStringExtra("title");
        mTitleBar.setTitleText(title);

        SPreferenceUtil eightModuleFcIdSp = new SPreferenceUtil(this, "eightModuleFcId.sp");
        mFcId = eightModuleFcIdSp.getEightModuleFcId(title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_study_center_my_buy_video_list;
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
        mPageNum = 0;
        getUserBuyedData(ApiConstant.REQUEST_REFRESH, mPageNum);
    }
}
