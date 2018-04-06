package com.yifactory.daocheapp.biz.home_function.home_tab;

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
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.GetCategoryListBean;
import com.yifactory.daocheapp.biz.home_function.home_tab.adapter.HomeTypeSecondHandCarOperManAdapter;
import com.yifactory.daocheapp.event.HomeTabAnimMessage;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class HomeTypeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.management_rv)
    RecyclerView mRv_operationsManagement;
    private Dialog mDialog;
    private HomeTypeSecondHandCarOperManAdapter mOperManAdapter;
    private SPreferenceUtil mEightModuleFcIdSp;

    public static HomeTypeFragment newInstance() {
        HomeTypeFragment fragment = new HomeTypeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {
        mEightModuleFcIdSp = new SPreferenceUtil(mActivity, "eightModuleFcId.sp");
        mDialog = SDDialogUtil.newLoading(mActivity, "请求中");
        getCategoryListData(ApiConstant.REQUEST_NORMAL);
    }

    private void getCategoryListData(String requestMark) {
        /*if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mDialog.show();
        }*/
        RxHttpUtils.createApi(ApiService.class)
                .getCategoryList()
                .compose(Transformer.<GetCategoryListBean>switchSchedulers(mDialog))
                .subscribe(new CommonObserver<GetCategoryListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        cancelDialog();
                    }

                    @Override
                    protected void onSuccess(GetCategoryListBean getCategoryListBean) {
                        cancelDialog();
                        if (getCategoryListBean.getResponseState().equals("1")) {
                            List<GetCategoryListBean.DataBean> dataBeanList = getCategoryListBean.getData();
                            mOperManAdapter.setNewData(getCategoryListBean.getData());
                            for (GetCategoryListBean.DataBean dataBean : getCategoryListBean.getData()) {
                                String firstCategoryContent = dataBean.getFirstCategoryContent();
                                String fcId = dataBean.getFcId();
                                Log.i("521", "onSuccess: firstCategoryContent:" + firstCategoryContent + "===fcId:" + fcId);
                                mEightModuleFcIdSp.setEightModuleFcId(firstCategoryContent, fcId);
                                for (GetCategoryListBean.DataBean.CategorySecondListBean item:dataBean.getCategorySecondList()){
                                    String firstCategoryContent1 = item.getSecondContent();
                                    String fcId1 = item.getScId();
                                    Log.i("521", "onSuccess: firstCategoryContent:" + firstCategoryContent1 + "===fcId:" + fcId1);
                                    mEightModuleFcIdSp.setEightModuleFcId(firstCategoryContent1, fcId1);
                                }
                            }
                            dataBeanList.remove(dataBeanList.size() - 1);
                        } else {
                            showToast(getCategoryListBean.getMsg());
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

    @Override
    protected void initView(Bundle savedInstanceState) {
        initOperationsManagementRv();
    }

    private void initOperationsManagementRv() {
        mOperManAdapter = new HomeTypeSecondHandCarOperManAdapter();
        mRv_operationsManagement.setNestedScrollingEnabled(false);
        mRv_operationsManagement.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv_operationsManagement.setAdapter(mOperManAdapter);
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_type_home;
    }

    @Override
    public void onRefresh() {
        getCategoryListData(ApiConstant.REQUEST_REFRESH);
    }
}
