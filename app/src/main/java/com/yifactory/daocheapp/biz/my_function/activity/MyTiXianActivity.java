package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.AddDepositeRecordBean;
import com.yifactory.daocheapp.bean.BankCardBean;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.event.TiXianSuccessMsg;
import com.yifactory.daocheapp.utils.MyInputFilter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.BaseSwipeRefreshLayout;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTiXianActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    BaseSwipeRefreshLayout mBaseSwipeRefreshLayout;
    @BindView(R.id.golden_tv)
    TextView mTv_golden;
    @BindView(R.id.renminbi_tv)
    TextView mTv_renMinBi;
    @BindView(R.id.money_et)
    EditText mEt_money;
    @BindView(R.id.bankCard_name_tv)
    TextView mTv_bankCardName;
    @BindView(R.id.weihao_tv)
    TextView mTv_weiHao;
    private BankCardBean.DataBean mDataBean;
    private Dialog mDialog;
    private String mUId;
    private String mRenMinBiStr = "0.00";

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("我的收益");
        return true;
    }

    @Override
    protected void addListener() {
        mBaseSwipeRefreshLayout.setOnRefreshListener(this);
        mEt_money.setFilters(new InputFilter[]{new MyInputFilter(2)});
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        mDialog = SDDialogUtil.newLoading(this, "请求中");
        mDialog.show();
        getUserInfoById();
        getBankList();
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
                            List<BankCardBean.DataBean> dataBeanList = bankCardBean.getData();
                            if (dataBeanList != null && dataBeanList.size() > 0) {
                                mDataBean = dataBeanList.get(0);
                                setBankCardInfo();
                            }
                        }
                    }
                });
    }


    private void getUserInfoById() {
        RxHttpUtils.createApi(ApiService.class)
                .getUserById(mUId, "")
                .compose(Transformer.<UserBean>switchSchedulers())
                .subscribe(new CommonObserver<UserBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        requestFinish();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(UserBean userBean) {
                        requestFinish();
                        if (userBean != null && userBean.getResponseState().equals("1")) {
                            UserBean.DataBean dataBean = userBean.getData().get(0);
                            handleEntityData(dataBean);
                        } else {
                            showToast(userBean.getMsg());
                        }
                    }
                });
    }

    private void handleEntityData(UserBean.DataBean dataBean) {
        double reciveBalance = dataBean.getReciveBalance();
        mTv_golden.setText(reciveBalance + "金币");
        double depositePercent = dataBean.getDepositePercent();
        double money = reciveBalance * depositePercent;
        java.text.DecimalFormat df = new DecimalFormat("0.00");
        mRenMinBiStr = df.format(money);
        mTv_renMinBi.setText("折合成人民币：" + mRenMinBiStr + "元");
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
        return R.layout.activity_my_ti_xian;
    }

    @OnClick({R.id.naviFrameLeft, R.id.bankCard_info_layout, R.id.sure_btn, R.id.clear_iv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.bankCard_info_layout:
                    Intent intent = new Intent(this, MyBankCardActivity.class);
                    intent.putExtra("mark", "tixian");
                    startActivity(intent);
                    break;
                case R.id.sure_btn:
                    sureTiXianEvent();
                    break;
                case R.id.clear_iv:
                    mEt_money.setText("");
                    break;
            }
        }
    }

    private void sureTiXianEvent() {
        if (mDataBean == null) {
            showToast("请选择银行卡");
            return;
        }
        String moneyStr = mEt_money.getText().toString().trim();
        if (TextUtils.isEmpty(moneyStr)) {
            showToast("请输入提现金额");
            return;
        }
        if (Double.valueOf(mRenMinBiStr) > 0) {
            double moneyD = Double.valueOf(moneyStr);
            if (Double.valueOf(mRenMinBiStr) < moneyD) {
                showToast("金额已超过可提现余额");
                return;
            }
            if (moneyD > 400) {
                showToast("最多可提现金额为400元");
                return;
            }
            sendTiXianRequest(moneyD);
        } else {
            showToast("操作失败，当前余额不足");
        }
    }

    private void sendTiXianRequest(double moneyD) {
        mDialog.show();
        RxHttpUtils.createApi(ApiService.class)
                .addDepositeRecord(mUId, String.valueOf(moneyD), mDataBean.getSubId())
                .compose(Transformer.<AddDepositeRecordBean>switchSchedulers())
                .subscribe(new CommonObserver<AddDepositeRecordBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        requestFinish();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(AddDepositeRecordBean addDepositeRecordBean) {
                        requestFinish();
                        showToast(addDepositeRecordBean.getMsg());
                        if (addDepositeRecordBean.getResponseState().equals("1")) {
                            EventBus.getDefault().post(new TiXianSuccessMsg());
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        getUserInfoById();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectBankCardInfoEvent(BankCardBean.DataBean dataBean) {
        mDataBean = dataBean;
        setBankCardInfo();
    }

    private void setBankCardInfo() {
        String bankName = mDataBean.getBankName();
        mTv_bankCardName.setText(bankName);
        String bankNum = mDataBean.getBankNum();
        String weiHaoStr = bankNum.substring(bankNum.length() - 4, bankNum.length());
        mTv_weiHao.setText("尾号" + weiHaoStr + "  储蓄卡");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
