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
import com.yifactory.daocheapp.bean.GetAttentionListBean;
import com.yifactory.daocheapp.biz.my_function.adapter.MyFocusAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFocusActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.my_focus_rv)
    RecyclerView mRv_myFocus;
    private MyFocusAdapter focusAdapter;
    private String mUId;
    private Dialog mDialog;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("我的关注");
        return true;
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        initMyFocusRv();
    }

    private void initMyFocusRv() {
        focusAdapter = new MyFocusAdapter(mUId);
        mRv_myFocus.setLayoutManager(new LinearLayoutManager(this));
        mRv_myFocus.setAdapter(focusAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        mDialog = SDDialogUtil.newLoading(this, "请求中");
        mDialog.show();
        getFocusList();
    }

    private void getFocusList() {
        RxHttpUtils.createApi(ApiService.class)
                .getAttentionList(mUId)
                .compose(Transformer.<GetAttentionListBean>switchSchedulers())
                .subscribe(new CommonObserver<GetAttentionListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        requestFinish();
                    }

                    @Override
                    protected void onSuccess(GetAttentionListBean getAttentionListBean) {
                        requestFinish();
                        List<GetAttentionListBean.DataBean> dataBeanList = getAttentionListBean.getData();
                        if (dataBeanList != null && dataBeanList.size() > 0) {
                            focusAdapter.setNewData(dataBeanList);
                        } else {
                            showToast("暂无数据");
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_focus;
    }

    @Override
    public void onRefresh() {
        getFocusList();
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
