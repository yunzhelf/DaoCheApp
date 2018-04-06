package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.GetUserQuestionListBean;
import com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter.DiscoverAnswersOuterAdapter;
import com.yifactory.daocheapp.biz.my_function.adapter.MyQuestionAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyQuestionActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MyQuestionAdapter.RecyclerVScrollEventCallBack {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.answers_rv)
    RecyclerView mRv_answers;
    private DiscoverAnswersOuterAdapter mOuterAdapter;
    private String mUId;
    private Dialog mDialog;
    private int mCurPageNum = 0;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("我的提问");
        return true;
    }

    @Override
    protected void initView() {
        initAnswersRv();
    }

    private void initAnswersRv() {
        mOuterAdapter = new DiscoverAnswersOuterAdapter(mUId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRv_answers.setLayoutManager(linearLayoutManager);
        mRv_answers.setAdapter(mOuterAdapter);
        mRv_answers.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(MyQuestionActivity.this).resumeRequests();
                } else {
                    Glide.with(MyQuestionActivity.this).pauseRequests();
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        mDialog = SDDialogUtil.newLoading(this, "加载中");
        mDialog.show();
        getUserQuestionListData(ApiConstant.REQUEST_NORMAL, mCurPageNum);
    }

    private void getUserQuestionListData(final String requestMark, int pageNum) {
        RxHttpUtils
                .createApi(ApiService.class)
                .getUserQuestionList(mUId, pageNum, mUId)
                .compose(Transformer.<GetUserQuestionListBean>switchSchedulers())
                .subscribe(new CommonObserver<GetUserQuestionListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        requestFinish();
                        requestError(requestMark, errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetUserQuestionListBean getUserQuestionListBean) {
                        requestFinish();
                        if (getUserQuestionListBean.getResponseState().equals("1")) {
                            requestSuccess(requestMark, getUserQuestionListBean);
                        } else {
                            requestError(requestMark, getUserQuestionListBean.getMsg());
                        }
                    }
                });
    }

    private void requestSuccess(String requestMark, GetUserQuestionListBean getUserQuestionListBean) {
        List<GetUserQuestionListBean.DataBean> dataBeanList = getUserQuestionListBean.getData();
        if (dataBeanList.size() > 0) {
            if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
                mOuterAdapter.loadMoreComplete();
                mOuterAdapter.addData(dataBeanList);
            } else {
                mOuterAdapter.setNewData(dataBeanList);
            }
        } else {
            if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
                mOuterAdapter.loadMoreEnd();
            } else {
                showToast("暂无数据");
            }
        }
    }

    private void requestError(String requestMark, String errorMsg) {
        showToast(errorMsg);
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mOuterAdapter.loadMoreFail();
            if (mCurPageNum != 0) {
                mCurPageNum -= 1;
            }
        }
    }

    private void requestFinish() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mOuterAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mCurPageNum++;
                getUserQuestionListData(ApiConstant.REQUEST_NORMAL, mCurPageNum);
            }
        }, mRv_answers);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_question;
    }

    @Override
    public void onRefresh() {
        mCurPageNum = 0;
        getUserQuestionListData(ApiConstant.REQUEST_REFRESH, mCurPageNum);
    }

    public void scrollEvent(int position) {
        mRv_answers.scrollToPosition(position);
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
