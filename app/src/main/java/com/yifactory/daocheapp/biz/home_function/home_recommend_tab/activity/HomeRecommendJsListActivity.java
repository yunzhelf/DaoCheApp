package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity;

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
import com.yifactory.daocheapp.bean.GetSysArmyAnasBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendJsListAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRecommendJsListActivity extends BaseActivity {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.js_rv)
    RecyclerView mRv_js;
    private Dialog mDialog;
    private HomeRecommendJsListAdapter mJsListAdapter;
    private int pageNum = 0;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("军师分享");
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
        initJsRv();
        iniRefreshLayout();
        mDialog.show();
        getSysArmyAnasData(ApiConstant.REQUEST_NORMAL, pageNum);
    }

    private void iniRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mJsListAdapter.setEnableLoadMore(false);
                pageNum = 0;
                getSysArmyAnasData(ApiConstant.REQUEST_REFRESH, pageNum);
            }
        });
        mJsListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum += 1;
                mRv_js.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSysArmyAnasData(ApiConstant.REQUEST_NORMAL, pageNum);
                    }
                }, 1000);
            }
        }, mRv_js);
    }

    private void initJsRv() {
        mJsListAdapter = new HomeRecommendJsListAdapter();
        mRv_js.setLayoutManager(new LinearLayoutManager(this));
        mRv_js.setAdapter(mJsListAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mDialog = SDDialogUtil.newLoading(this, "请求中...");
    }

    private void getSysArmyAnasData(final String requestMark, int pageNum) {
        RxHttpUtils.createApi(ApiService.class)
                .getSysArmyAnas(pageNum + "")
                .compose(Transformer.<GetSysArmyAnasBean>switchSchedulers())
                .subscribe(new CommonObserver<GetSysArmyAnasBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        cancelDialog();
                        failedRequest(requestMark, errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetSysArmyAnasBean getSysArmyAnasBean) {
                        cancelDialog();
                        if (getSysArmyAnasBean.getResponseState().equals("1")) {
                            successRequest(requestMark, getSysArmyAnasBean);
                        } else {
                            successRequestFinish(requestMark, getSysArmyAnasBean.getMsg());
                        }
                    }
                });
    }

    private void successRequestFinish(String requestMark, String msg) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mJsListAdapter.loadMoreEnd();
            if (pageNum != 0) {
                pageNum -= 1;
            }
        }
    }

    private void failedRequest(String requestMark, String errorMsg) {
        showToast(errorMsg);
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mJsListAdapter.loadMoreFail();
            if (pageNum != 0) {
                pageNum -= 1;
            }
        }
    }

    private void successRequest(String requestMark, GetSysArmyAnasBean getSysArmyAnasBean) {
        List<GetSysArmyAnasBean.DataBean> dataBeanList = getSysArmyAnasBean.getData();
        if (dataBeanList.size() > 0) {
            if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
                mJsListAdapter.loadMoreComplete();
                mJsListAdapter.addData(dataBeanList);
            } else {
                mJsListAdapter.setNewData(dataBeanList);
            }
        } else {
            if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
                mJsListAdapter.loadMoreEnd();
            } else {
                showToast("暂无数据");
            }
        }
    }

    private void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mJsListAdapter.setEnableLoadMore(true);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_recommend_js_list;
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
