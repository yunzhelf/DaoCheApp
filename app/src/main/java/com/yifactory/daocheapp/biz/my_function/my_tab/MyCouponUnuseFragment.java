package com.yifactory.daocheapp.biz.my_function.my_tab;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.CouponListBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendOperateManagerActivity;
import com.yifactory.daocheapp.biz.my_function.adapter.MyCouponAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.List;

import butterknife.BindView;

public class MyCouponUnuseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private String TAG = "coupon";
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.unuse_rv)
    RecyclerView mRv_unuse;
    private List<CouponListBean.DataBean> couponList;
    private MyCouponAdapter myCouponAdapter;
    private Dialog mDialog;

    public static MyCouponUnuseFragment newInstance() {
        MyCouponUnuseFragment fragment = new MyCouponUnuseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initUnuseView();
    }

    private void initUnuseView() {
        myCouponAdapter = new MyCouponAdapter();
        mRv_unuse.setFocusableInTouchMode(false);
        mRv_unuse.requestFocus();
        mRv_unuse.setNestedScrollingEnabled(false);
        mRv_unuse.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv_unuse.setAdapter(myCouponAdapter);
        myCouponAdapter.setNewData(couponList);
        myCouponAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                couponList = adapter.getData();
                if (getActivity().getIntent().getStringExtra("state") != null && getActivity().getIntent().getStringExtra("state").equals("buy")) {
                    getActivity().setResult(Activity.RESULT_OK,new Intent().putExtra("data",couponList.get(position)));
                    return;
                }

                Intent intent = new Intent(mActivity, HomeRecommendOperateManagerActivity.class);
                intent.putExtra("mark", "coupon");
                intent.putExtra("fcId",couponList.get(position).getCoupon().getFcId());
                intent.putExtra("scId", couponList.get(position).getCoupon().getScId());
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle arguments) {
        getUnuseCouponList();
    }

    private void getUnuseCouponList() {
        String uId = UserInfoUtil.getUserInfoBean(mActivity).getUId();
        if (uId == null) {
            showToast("没有用户信息");
            return;
        }
        mDialog = SDDialogUtil.newLoading(mActivity, "正在加载，请稍后");
        mDialog.show();
        RxHttpUtils.createApi(ApiService.class)
                .getCoupon(uId, 0)
                .compose(Transformer.<CouponListBean>switchSchedulers())
                .subscribe(new CommonObserver<CouponListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Log.e(TAG, errorMsg);
                        finishRefresh();
                    }

                    @Override
                    protected void onSuccess(CouponListBean couponListBean) {
                        finishRefresh();
                        int count = couponListBean.getData().size();
                        Log.e(TAG, "优惠卷数量" + count + "");
                        couponList = couponListBean.getData();
                        myCouponAdapter.setNewData(couponList);
                    }
                });
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onLazyLoad() {

    }

    private void finishRefresh() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancel();
        }
    }

    @Override
    public void onRefresh() {
        getUnuseCouponList();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_my__coupon_unuse;
    }
}
