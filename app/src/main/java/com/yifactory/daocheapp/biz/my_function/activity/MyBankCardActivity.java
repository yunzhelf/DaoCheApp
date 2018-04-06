package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.BankCardBean;
import com.yifactory.daocheapp.biz.my_function.adapter.MyBankCardTypeAdapter;
import com.yifactory.daocheapp.event.AddBankCardSuccessMsg;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.CustomPopWindow;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBankCardActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rootLayout)
    AutoLinearLayout mRootViewLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.bank_card_type_rv)
    RecyclerView mRv_bankCardType;
    private List<BankCardBean.DataBean> dataList = new ArrayList<>();
    private MyBankCardTypeAdapter cardTypeAdapter;

    private CustomPopWindow mCustomPopWindow_JieBang;
    private String mUId;
    private Dialog mDialog;
    private int mPosition;
    private String mMark;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("我的银行卡");
        bar.setRightImageResource(R.drawable.tianjiayinhangka);
        return true;
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        initBankCardTypeRv();
    }

    private void initBankCardTypeRv() {
        cardTypeAdapter = new MyBankCardTypeAdapter();
        mRv_bankCardType.setLayoutManager(new LinearLayoutManager(this));
        mRv_bankCardType.setAdapter(cardTypeAdapter);
        cardTypeAdapter.setNewData(dataList);
        cardTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPosition = position;
                if (mMark.equals("setting")) {
                    bankCardItemClickEvent();
                } else if (mMark.equals("tixian")) {
                    BankCardBean.DataBean dataBean = dataList.get(position);
                    EventBus.getDefault().post(dataBean);
                    finish();
                }
            }
        });
    }

    private void bankCardItemClickEvent() {
        if (mCustomPopWindow_JieBang == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.pop_my_jiebang_bankcard_layout, null);
            TextView sureTv = (TextView) contentView.findViewById(R.id.sure_btn);
            TextView cancelTv = (TextView) contentView.findViewById(R.id.cancel_btn);
            mCustomPopWindow_JieBang = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.5f)
                    .create()
                    .showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCustomPopWindow_JieBang != null) {
                        mCustomPopWindow_JieBang.dissmiss();
                    }
                    if (v.getId() == R.id.sure_btn) {
                        deleteBankRecord();
                    } else if (v.getId() == R.id.cancel_btn) {

                    }
                }
            };
            sureTv.setOnClickListener(listener);
            cancelTv.setOnClickListener(listener);
        } else {
            mCustomPopWindow_JieBang.showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);
        }
    }

    private void getBankList() {
        RxHttpUtils.createApi(ApiService.class)
                .getUserBankRecord(mUId)
                .compose(Transformer.<BankCardBean>switchSchedulers())
                .subscribe(new CommonObserver<BankCardBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        requestFinish();
                    }

                    @Override
                    protected void onSuccess(BankCardBean bankCardBean) {
                        requestFinish();
                        if (bankCardBean != null && bankCardBean.getResponseState().equals("1")) {
                            dataList = bankCardBean.getData();
                            cardTypeAdapter.setNewData(dataList);
                        }
                    }
                });
    }

    private void deleteBankRecord() {
        RxHttpUtils.createApi(ApiService.class)
                .deleteBankRecord(dataList.get(mPosition).getSubId())
                .compose(Transformer.<BankCardBean>switchSchedulers())
                .subscribe(new CommonObserver<BankCardBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(BankCardBean bankCardBean) {
                        if (bankCardBean != null && bankCardBean.getResponseState().equals("1")) {
                            showToast(bankCardBean.getMsg());
                            dataList.remove(mPosition);
                            cardTypeAdapter.notifyItemRemoved(mPosition);
                        }
                    }
                });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        getIntentData();
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        mDialog = SDDialogUtil.newLoading(this, "加载中");
        mDialog.show();
        getBankList();
    }

    private void getIntentData() {
        mMark = getIntent().getStringExtra("mark");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_bank_card_activity;
    }

    @OnClick({R.id.naviFrameLeft, R.id.naviFrameRight})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.naviFrameRight:
                    Intent addBankCardIntent = new Intent(this, MyAddBankCardActivity.class);
                    startActivity(addBankCardIntent);
                    break;
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
    public void onRefresh() {
        getBankList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshListData(AddBankCardSuccessMsg addBankCardSuccessMsg) {
        getBankList();
    }
}
