package com.yifactory.daocheapp.biz.discover_function.discover_tab;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.EditText;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.GetShowMoodListBean;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter.DiscoverMoodOuterAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SoftInputUtils;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

import butterknife.BindView;

public class DiscoverMoodFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.editTextBodyLl)
    AutoLinearLayout edittextbody;
    @BindView(R.id.comment_et)
    EditText editText;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mood_rv)
    RecyclerView mRv_mood;
    private Dialog mDialog;
    private String mUId = "";
    private DiscoverMoodOuterAdapter mOuterAdapter;
    private int curPageNum = 0;

    public static DiscoverMoodFragment newInstance() {
        DiscoverMoodFragment fragment = new DiscoverMoodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {
        LoginBean.DataBean userInfoBean = UserInfoUtil.getUserInfoBean(mActivity);
        if (userInfoBean != null) {
            mUId = userInfoBean.getUId();
        }
        mDialog = SDDialogUtil.newLoading(mActivity, "加载中");
        getShowMoodList(ApiConstant.REQUEST_NORMAL, curPageNum);
    }

    private void getShowMoodList(final String requestMark, int pageNum) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mDialog.show();
        }
        RxHttpUtils
                .createApi(ApiService.class)
                .getShowMoodList(mUId, pageNum)
                .compose(Transformer.<GetShowMoodListBean>switchSchedulers())
                .subscribe(new CommonObserver<GetShowMoodListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        cancelLoad();
                        failedRequest(requestMark, errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetShowMoodListBean getShowMoodListBean) {
                        cancelLoad();
                        if (getShowMoodListBean.getResponseState().equals("1")) {
                            successRequest(requestMark, getShowMoodListBean);
                        } else {
                            failedRequest(requestMark, getShowMoodListBean.getMsg());
                        }
                    }
                });
    }

    private void failedRequest(String requestMark, String errorMsg) {
        showToast(errorMsg);
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mOuterAdapter.loadMoreFail();
            if (curPageNum != 0) {
                curPageNum -= 1;
            }
        }
    }

    private void successRequest(String requestMark, GetShowMoodListBean getShowMoodListBean) {
        List<GetShowMoodListBean.DataBean> dataBeanList = getShowMoodListBean.getData();
        if (dataBeanList.size() > 0) {
            if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
                mOuterAdapter.loadMoreComplete();
                mOuterAdapter.addData(dataBeanList);
            } else {
                mOuterAdapter.setNewData(dataBeanList);
            }
        } else {
            if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
                mOuterAdapter.loadMoreEnd();
            } else {
                showToast("暂无数据");
            }
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initMoodRv();
    }

    private void initMoodRv() {
        mOuterAdapter = new DiscoverMoodOuterAdapter(mUId);
        RecyclerView.ItemAnimator animator = mRv_mood.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        mRv_mood.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv_mood.setAdapter(mOuterAdapter);
        mRv_mood.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(mActivity).resumeRequests();
                } else {
                    Glide.with(mActivity).pauseRequests();
                }
            }
        });
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mOuterAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                curPageNum++;
                getShowMoodList(ApiConstant.REQUEST_NORMAL, curPageNum);
            }
        }, mRv_mood);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onRefresh() {
        curPageNum = 0;
        getShowMoodList(ApiConstant.REQUEST_REFRESH, curPageNum);
    }


    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_discover_mood;
    }

    private void cancelLoad() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void updateEditTextBodyVisible(int visibility) {
        edittextbody.setVisibility(visibility);
        if (View.VISIBLE == visibility) {
            editText.requestFocus();
            editText.setText("");
//            editText.setHint("回复@" + commentBean.getReciverName() + ":");
            SoftInputUtils.showSoftInput(editText);
        } else if (View.GONE == visibility) {
            SoftInputUtils.hideSoftInput(editText);
        }
    }
}
