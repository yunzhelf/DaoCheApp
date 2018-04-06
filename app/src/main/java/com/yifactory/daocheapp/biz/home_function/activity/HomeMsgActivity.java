package com.yifactory.daocheapp.biz.home_function.activity;

import android.app.Dialog;
import android.graphics.Color;
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
import com.yifactory.daocheapp.bean.BaseBean;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.bean.MsgBean;
import com.yifactory.daocheapp.biz.home_function.adapter.HomeMsgAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeMsgActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.msg_rv)
    RecyclerView mRv_msg;
    List<MsgBean.DataBean> list = new ArrayList<>();
    private int pageNum = 0;
    private Dialog mDialog;
    HomeMsgAdapter msgAdapter;
    private String mUId;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("消息");
        bar.setRightText("清空");
        bar.setRightTextColor(Color.parseColor("#4087fd"));
        return true;
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        initMsgRv();
    }

    private void initMsgRv() {
        msgAdapter = new HomeMsgAdapter();
        mRv_msg.setLayoutManager(new LinearLayoutManager(this));
        mRv_msg.setAdapter(msgAdapter);
        msgAdapter.setNewData(list);
        msgAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum += 1;
                getMsg(ApiConstant.REQUEST_NORMAL, pageNum);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mDialog = SDDialogUtil.newLoading(HomeMsgActivity.this, "请求中");
        LoginBean.DataBean userInfoBean = UserInfoUtil.getUserInfoBean(HomeMsgActivity.this);
        if (userInfoBean != null) {
            mUId = userInfoBean.getUId();
        }

        getMsg(ApiConstant.REQUEST_REFRESH, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_msg;
    }

    @OnClick({R.id.naviFrameLeft, R.id.naviFrameRight})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.naviFrameRight:
                    String msgIds = "";
                    list = msgAdapter.getData();
                    for (int i = 0; i < list.size(); i++) {
                        if (i == list.size() - 1) {
                            msgIds += list.get(i).sumId;
                        } else {
                            msgIds += list.get(i).sumId + ",";
                        }
                    }
                    removeMsg(msgIds);
                    break;
            }
        }
    }

    private void removeMsg(String ids) {
        mDialog.show();
        RxHttpUtils.createApi(ApiService.class)
                .removeMsg(null, mUId)
                .compose(Transformer.<BaseBean>switchSchedulers())
                .subscribe(new CommonObserver<BaseBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    protected void onSuccess(BaseBean getSystemInfoBean) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        if (getSystemInfoBean.responseState.equals("1")) {
                            msgAdapter.getData().clear();
                            msgAdapter.notifyDataSetChanged();
                        } else {
                            showToast(getSystemInfoBean.msg);
                        }
                    }
                });
    }

    private void getMsg(final String requestMark, int pageNum) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mDialog.show();
        }
        RxHttpUtils.createApi(ApiService.class)
                .getMsgList(mUId, pageNum + "")
                .compose(Transformer.<MsgBean>switchSchedulers())
                .subscribe(new CommonObserver<MsgBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        cancelDialog();
                    }

                    @Override
                    protected void onSuccess(MsgBean getSysArmyAnasBean) {
                        cancelDialog();
                        if (getSysArmyAnasBean.responseState.equals("1")) {
                            handleEntityData(requestMark, getSysArmyAnasBean);
                        } else {
                            showToast(getSysArmyAnasBean.msg);
                        }
                    }
                });
    }

    private void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void handleEntityData(String requestMark, MsgBean getSysArmyAnasBean) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            msgAdapter.addData(getSysArmyAnasBean.data);
            msgAdapter.loadMoreEnd();
        } else {
            msgAdapter.setNewData(getSysArmyAnasBean.data);
        }
    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        getMsg(ApiConstant.REQUEST_REFRESH, 0);
    }
}
