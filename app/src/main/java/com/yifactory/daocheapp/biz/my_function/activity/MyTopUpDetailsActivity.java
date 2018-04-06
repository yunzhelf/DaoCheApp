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
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.GetUserBalanceRecordBean;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.biz.my_function.adapter.MyTopUpDetailsAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.BaseSwipeRefreshLayout;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTopUpDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    BaseSwipeRefreshLayout mBaseSwipeRefreshLayout;
    @BindView(R.id.top_up_details_rv)
    RecyclerView mRv_topUpDetails;
    private String mUId;
    private Dialog mDialog;
    private MyTopUpDetailsAdapter mTopUpDetailsAdapter;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("账户明细");
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void addListener() {
        mBaseSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        initTopUpDetailsRv();
    }

    private void initTopUpDetailsRv() {
        mTopUpDetailsAdapter = new MyTopUpDetailsAdapter();
        mRv_topUpDetails.setLayoutManager(new LinearLayoutManager(this));
        mRv_topUpDetails.setAdapter(mTopUpDetailsAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        mDialog = SDDialogUtil.newLoading(this, "请求中");
        getUserBalanceRecord();
    }

    private void getUserBalanceRecord() {
        RxHttpUtils.createApi(ApiService.class)
                .getUserBalanceRecord(mUId)
                .compose(Transformer.<GetUserBalanceRecordBean>switchSchedulers())
                .subscribe(new CommonObserver<GetUserBalanceRecordBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        requestFinish();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetUserBalanceRecordBean getUserBalanceRecordBean) {
                        requestFinish();
                        if (getUserBalanceRecordBean.getResponseState().equals("1")) {
                            List<GetUserBalanceRecordBean.DataBean> dataBeanList = getUserBalanceRecordBean.getData();
                            if (dataBeanList != null) {
                                mTopUpDetailsAdapter.setNewData(dataBeanList);
                                if (dataBeanList.size() == 0) {
                                    showToast("暂无数据");
                                }
                            }
                        } else {
                            showToast(getUserBalanceRecordBean.getMsg());
                        }
                    }
                });
    }

    private void requestFinish() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mBaseSwipeRefreshLayout != null && mBaseSwipeRefreshLayout.isRefreshing()) {
            mBaseSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_top_up_details;
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
        getUserBalanceRecord();
    }
}
