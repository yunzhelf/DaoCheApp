package com.yifactory.daocheapp.biz.my_function.my_tab;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.CouponListBean;
import com.yifactory.daocheapp.biz.my_function.adapter.MyCouponAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.List;

import butterknife.BindView;

/**
 * Created by sunxj on 2018/3/13.
 */

public class MyCouponUsedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.unuse_rv)
    RecyclerView mRv_unuse;
    private List<CouponListBean.DataBean> couponList;
    private MyCouponAdapter myCouponAdapter;
    private Dialog mDialog;

    public static MyCouponUsedFragment newInstance() {
        MyCouponUsedFragment fragment = new MyCouponUsedFragment();
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

    private void initUnuseView(){
        myCouponAdapter = new MyCouponAdapter();
        mRv_unuse.setFocusableInTouchMode(false);
        mRv_unuse.requestFocus();
        mRv_unuse.setNestedScrollingEnabled(false);
        mRv_unuse.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv_unuse.setAdapter(myCouponAdapter);
        myCouponAdapter.setNewData(couponList);
    }

    @Override
    protected void initData(Bundle arguments) {
        getusedCouponList();
    }

    private void getusedCouponList(){
        String uId = UserInfoUtil.getUserInfoBean(mActivity).getUId();
        if(uId == null){
            showToast("没有用户信息");
            return;
        }
        mDialog = SDDialogUtil.newLoading(mActivity,"正在加载，请稍后");
        mDialog.show();
        RxHttpUtils.createApi(ApiService.class)
                .getCoupon(uId,1)
                .compose(Transformer.<CouponListBean>switchSchedulers())
                .subscribe(new CommonObserver<CouponListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        finishRefresh();
                    }

                    @Override
                    protected void onSuccess(CouponListBean couponListBean) {
                        finishRefresh();
                        int count = couponListBean.getData().size();
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

    private void finishRefresh(){
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(mDialog != null && mDialog.isShowing()){
            mDialog.cancel();
        }
    }

    @Override
    public void onRefresh() {
        getusedCouponList();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_my__coupon_unuse;
    }
}
