package com.yifactory.daocheapp.biz.my_function.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.AddUserBalanceBean;
import com.yifactory.daocheapp.bean.AddUserBalanceBean2;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.event.TiXianSuccessMsg;
import com.yifactory.daocheapp.utils.PayResult;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.BaseSwipeRefreshLayout;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTopUpActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    BaseSwipeRefreshLayout mBaseSwipeRefreshLayout;
    @BindView(R.id.mine_jinbi_tv)
    TextView mTv_mineJinBi;
    @BindView(R.id.zhifubao_iv)
    ImageView mIv_zhiFuBao;
    @BindView(R.id.wechat_iv)
    ImageView mIv_weChat;
    @BindView(R.id.one_layout)
    AutoLinearLayout oneLayout;
    @BindView(R.id.two_layout)
    AutoLinearLayout twoLayout;
    @BindView(R.id.three_layout)
    AutoLinearLayout threeLayout;
    @BindView(R.id.four_layout)
    AutoLinearLayout fourLayout;
    @BindView(R.id.five_layout)
    AutoLinearLayout fiveLayout;
    @BindView(R.id.six_layout)
    AutoLinearLayout sixLayout;
    private String payWay = "1";
    private String jinBi = "";
    private String mUId;
    private Dialog mDialog;

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MyTopUpActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new TiXianSuccessMsg());
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MyTopUpActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("充值");
        bar.setRightText("明细");
        return true;
    }

    @Override
    protected void addListener() {
        mBaseSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        mDialog = SDDialogUtil.newLoading(this, "请求中");
        mDialog.show();
        getUserInfoById();
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

    private void requestFinish() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mBaseSwipeRefreshLayout != null && mBaseSwipeRefreshLayout.isRefreshing()) {
            mBaseSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void handleEntityData(UserBean.DataBean dataBean) {
        double reciveBalance = dataBean.getGoldBalance();
        mTv_mineJinBi.setText(reciveBalance + "");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_top_up;
    }

    @OnClick({R.id.naviFrameLeft, R.id.naviFrameRight, R.id.zhifubao_layout, R.id.wechat_layout, R.id.one_layout, R.id.two_layout,
            R.id.three_layout, R.id.four_layout, R.id.five_layout, R.id.six_layout, R.id.sure_topup_tv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.naviFrameRight:
                    Intent topUpDetailsIntent = new Intent(this, MyTopUpDetailsActivity.class);
                    startActivity(topUpDetailsIntent);
                    break;
                case R.id.zhifubao_layout:
                    payWay = "1";
                    mIv_zhiFuBao.setVisibility(View.VISIBLE);
                    mIv_weChat.setVisibility(View.GONE);
                    break;
                case R.id.wechat_layout:
                    payWay = "0";
                    mIv_zhiFuBao.setVisibility(View.GONE);
                    mIv_weChat.setVisibility(View.VISIBLE);
                    break;
                case R.id.one_layout:
                    jinBi = "18";
                    oneLayout.setBackgroundResource(R.drawable.shape_blue_corner_stroke);
                    twoLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    threeLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fourLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fiveLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    sixLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    break;
                case R.id.two_layout:
                    jinBi = "38";
                    oneLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    twoLayout.setBackgroundResource(R.drawable.shape_blue_corner_stroke);
                    threeLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fourLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fiveLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    sixLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    break;
                case R.id.three_layout:
                    jinBi = "58";
                    oneLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    twoLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    threeLayout.setBackgroundResource(R.drawable.shape_blue_corner_stroke);
                    fourLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fiveLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    sixLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    break;
                case R.id.four_layout:
                    jinBi = "98";
                    oneLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    twoLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    threeLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fourLayout.setBackgroundResource(R.drawable.shape_blue_corner_stroke);
                    fiveLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    sixLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    break;
                case R.id.five_layout:
                    jinBi = "198";
                    oneLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    twoLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    threeLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fourLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fiveLayout.setBackgroundResource(R.drawable.shape_blue_corner_stroke);
                    sixLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    break;
                case R.id.six_layout:
                    jinBi = "398";
                    oneLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    twoLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    threeLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fourLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    fiveLayout.setBackgroundResource(R.drawable.shape_gray_corner_stroke);
                    sixLayout.setBackgroundResource(R.drawable.shape_blue_corner_stroke);
                    break;
                case R.id.sure_topup_tv:
                    sureTopupEvent();
                    break;
            }
        }
    }

    private void sureTopupEvent() {
        if (TextUtils.isEmpty(jinBi)) {
            showToast("请选择充值金额");
            return;
        }
        topUpRequest();
    }

    private void topUpRequest() {
        Log.i("521", "topUpRequest: payWay:" + payWay + "===mUId:" + mUId + "===jinBi:" + jinBi);
        mDialog.show();
        if (payWay.equals("1")) {
            RxHttpUtils.createApi(ApiService.class)
                    .addUserBalance2(payWay, mUId, jinBi, jinBi)
                    .compose(Transformer.<AddUserBalanceBean2>switchSchedulers())
                    .subscribe(new CommonObserver<AddUserBalanceBean2>() {
                        @Override
                        protected void onError(String errorMsg) {
                            requestFinish();
                            showToast(errorMsg);
                        }

                        @Override
                        protected void onSuccess(AddUserBalanceBean2 addUserBalanceBean) {
                            requestFinish();
                            if (addUserBalanceBean.getResponseState().equals("1")) {
                                final String s = addUserBalanceBean.getData().get(0);
                                Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        PayTask alipay = new PayTask(MyTopUpActivity.this);
                                        Map<String, String> result = alipay.payV2(s, true);
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };

                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else {
                                showToast(addUserBalanceBean.getMsg());
                            }
                        }
                    });
        } else {
            RxHttpUtils.createApi(ApiService.class)
                    .addUserBalance(payWay, mUId, jinBi, jinBi)
                    .compose(Transformer.<AddUserBalanceBean>switchSchedulers())
                    .subscribe(new CommonObserver<AddUserBalanceBean>() {
                        @Override
                        protected void onError(String errorMsg) {
                            requestFinish();
                            showToast(errorMsg);
                        }

                        @Override
                        protected void onSuccess(AddUserBalanceBean addUserBalanceBean) {
                            requestFinish();
                            if (addUserBalanceBean.getResponseState().equals("1")) {
                                AddUserBalanceBean.DataBean dataBean = addUserBalanceBean.getData().get(0);
                                final IWXAPI api = WXAPIFactory.createWXAPI(MyTopUpActivity.this, dataBean.getAppid(), true);
                                api.registerApp(dataBean.getAppid());
                                PayReq req = new PayReq();
                                String timestamp = dataBean.getTimestamp();
                                req.timeStamp = timestamp;
                                String sign = dataBean.getSign();
                                req.sign = sign;
                                String partnerid = dataBean.getPartnerid();
                                req.partnerId = partnerid;
                                String nonceStr = dataBean.getNoncestr();
                                req.nonceStr = nonceStr;
                                String prepayId = dataBean.getPrepayid();
                                req.prepayId = prepayId;
                                String packageValue = dataBean.getPackageX();
                                req.packageValue = packageValue;
                                String appId = dataBean.getAppid();
                                req.appId = appId;
                                api.sendReq(req);
                            } else {
                                showToast(addUserBalanceBean.getMsg());
                            }
                        }
                    });
        }

    }


    @Override
    public void onRefresh() {
        getUserInfoById();
    }
}
