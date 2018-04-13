package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.allen.retrofit.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.AppCtx;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.BaseBean;
import com.yifactory.daocheapp.bean.CouponListBean;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.biz.my_function.activity.MyCouponActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyTopUpActivity;
import com.yifactory.daocheapp.event.BuyVideoSuccessMsg;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.SharePreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRecommendVideoBuyDetailsActivity extends BaseActivity {
    @BindView(R.id.video_iv)
    ImageView videoIv;
    @BindView(R.id.title_tv)
    TextView title;
    @BindView(R.id.content_tv)
    TextView content;
    @BindView(R.id.play_count_tv)
    TextView count;
    @BindView(R.id.time_long)
    TextView timeLong;
    @BindView(R.id.price_tv)
    TextView price;
    @BindView(R.id.is_user)
    TextView isUser;
    @BindView(R.id.you_hui_layout)
    LinearLayout youHuiLy;
    @BindView(R.id.yu_e)
    TextView yuE;
    @BindView(R.id.chong)
    TextView chongZhi;
    @BindView(R.id.que_ren)
    TextView queRen;
    @BindView(R.id.is_can_pay)
    TextView isCanPay;
    PlayVideoBean.DataBean.ResourceBean data;
    String ucId;
    Double bable;
    private Dialog mDialog;
    private String mUId;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("购买详情");
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        data = getIntent().getParcelableExtra("data");
        if (data != null) {
            setData();
        }
    }

    void setData() {
        Glide.with(AppCtx.getC()).load(data.getIndexImg()).into(videoIv);
        title.setText(data.getTitle());
        content.setText(data.getVideoContent());
        count.setText(data.getShowCounts() + "");
        timeLong.setText("时长: " + data.getTotalMinute());
        price.setText(data.getGoldCount() + "金币");
        getUnuseCouponList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfoById();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_recommend_video_buy_details;
    }

    private void getUnuseCouponList() {
        LoginBean.DataBean userInfoBean = UserInfoUtil.getUserInfoBean(this);
        if (userInfoBean == null) {
            showToast("没有用户信息");
            return;
        } else {
            mUId = userInfoBean.getUId();
        }
        RxHttpUtils.createApi(ApiService.class)
                .getCoupon(mUId, 0)
                .compose(Transformer.<CouponListBean>switchSchedulers())
                .subscribe(new CommonObserver<CouponListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        String s = errorMsg;
                    }

                    @Override
                    protected void onSuccess(CouponListBean couponListBean) {
                        Boolean state = false;
                        for (CouponListBean.DataBean item : couponListBean.getData()) {
                            if (item.getCoupon().getScId() != null && item.getCoupon().getScId().equals(data.getScId())) {
                                if (item.getCoupon().getMinPrice() >= data.getGoldCount()) {
                                    state = true;
                                }
                            }
                        }
                        if (!state) {
                            isUser.setText("无可用");
                        } else {
                            isUser.setText("");
                            youHuiLy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivityForResult(new Intent(HomeRecommendVideoBuyDetailsActivity.this, MyCouponActivity.class).putExtra("state", "buy"), 200);
                                }
                            });
                        }

                    }
                });
    }

    private void getUserInfoById() {
        String uId = UserInfoUtil.getUserInfoBean(HomeRecommendVideoBuyDetailsActivity.this).getUId();
        RxHttpUtils.createApi(ApiService.class)
                .getUserById(uId, "")
                .compose(Transformer.<UserBean>switchSchedulers())
                .subscribe(new CommonObserver<UserBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        String s = errorMsg;
                    }

                    @Override
                    protected void onSuccess(UserBean userBean) {
                        if (userBean != null && userBean.getResponseState().equals("1")) {
                            yuE.setText(userBean.getData().get(0).getGoldBalance() + "");
                            bable = userBean.getData().get(0).getGoldBalance();
                            if (userBean.getData().get(0).getGoldBalance() > data.getGoldCount()) {
                                isCanPay.setText("");
                            } else {
                                isCanPay.setText("(不足支付)");
                            }
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
        super.onActivityResult(requestCode, resultCode, data1);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 200:
                    CouponListBean.DataBean dataBean = (CouponListBean.DataBean) data1.getSerializableExtra("data");
                    isUser.setText(String.valueOf(data.getGoldCount() * (1 - dataBean.getCoupon().getPercent())));
                    ucId = dataBean.getUcId();
                    if (bable != null) {
                        if ((data.getGoldCount() * (1 - dataBean.getCoupon().getPercent()) > bable)) {
                            isCanPay.setText("(不足支付)");
                        } else {
                            isCanPay.setText("");
                        }
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.naviFrameLeft, R.id.chong, R.id.que_ren})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.que_ren:
                    pay();
                    break;
                case R.id.chong:
                    startActivity(new Intent(HomeRecommendVideoBuyDetailsActivity.this, MyTopUpActivity.class));
                    break;
            }
        }
    }

    private void pay() {
        String uId = UserInfoUtil.getUserInfoBean(HomeRecommendVideoBuyDetailsActivity.this).getUId();
        String uuId = new SPreferenceUtil(this,"config.sp").getUserUuid();
        if(uuId == null || uId == null){
            showToast("请登录");
            return;
        }
        if(isCanPay.getText().equals("(不足支付)")){
            Intent i = new Intent(HomeRecommendVideoBuyDetailsActivity.this,MyTopUpActivity.class);
            startActivity(i);
            return;
        }

        mDialog = SDDialogUtil.newLoading(HomeRecommendVideoBuyDetailsActivity.this, "正在加载，请稍后");
        mDialog.show();

        RxHttpUtils.createApi(ApiService.class)
                .buyVideo(uId, data.getRId(), ucId, uuId)
                .compose(Transformer.<BaseBean>switchSchedulers())
                .subscribe(new CommonObserver<BaseBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.cancel();
                        }
                    }

                    @Override
                    protected void onSuccess(BaseBean userBean) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.cancel();
                        }
                        ToastUtils.showToast(userBean.msg);
                        if (userBean.responseState.equals("1")) {
                            EventBus.getDefault().post(new BuyVideoSuccessMsg());
                            finish();
                        }
                    }
                });

    }
}
