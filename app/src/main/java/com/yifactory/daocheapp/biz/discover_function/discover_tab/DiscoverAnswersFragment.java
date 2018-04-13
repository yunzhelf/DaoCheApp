package com.yifactory.daocheapp.biz.discover_function.discover_tab;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.yifactory.daocheapp.bean.AddUserQuestionBean;
import com.yifactory.daocheapp.bean.GetUserQuestionListBean;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter.DiscoverAnswersOuterAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class DiscoverAnswersFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.editTextBodyLl)
    AutoLinearLayout edittextbody;
    @BindView(R.id.comment_et)
    EditText editText;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.answers_rv)
    RecyclerView mRv_answers;
    private DiscoverAnswersOuterAdapter mOuterAdapter;
    private String mUId = "";
    private Dialog mDialog;
    private int curPageNum = 0;

    public static DiscoverAnswersFragment newInstance() {
        DiscoverAnswersFragment fragment = new DiscoverAnswersFragment();
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
        EventBus.getDefault().register(this);
        LoginBean.DataBean userInfoBean = UserInfoUtil.getUserInfoBean(mActivity);
        if (userInfoBean != null) {
            mUId = userInfoBean.getUId();
        }
        mDialog = SDDialogUtil.newLoading(mActivity, "加载中");
        mDialog.show();
        getUserQuestionListData(ApiConstant.REQUEST_NORMAL, curPageNum);
    }

    private void getUserQuestionListData(final String requestMark, int pageNum) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mDialog.show();
        }
        RxHttpUtils
                .createApi(ApiService.class)
                .getUserQuestionList(mUId, pageNum, "")
                .compose(Transformer.<GetUserQuestionListBean>switchSchedulers())
                .subscribe(new CommonObserver<GetUserQuestionListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        cancelLoad();
                        failedRequest(requestMark, errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetUserQuestionListBean getUserQuestionListBean) {
                        cancelLoad();
                        if (getUserQuestionListBean.getResponseState().equals("1")) {
                            successRequest(requestMark, getUserQuestionListBean);
                        } else {
                            failedRequest(requestMark, getUserQuestionListBean.getMsg());
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

    private void successRequest(String requestMark, GetUserQuestionListBean getUserQuestionListBean) {
        List<GetUserQuestionListBean.DataBean> dataBeanList = getUserQuestionListBean.getData();
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

    private void cancelLoad() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initAnswersRv();
    }

    private void initAnswersRv() {
        mOuterAdapter = new DiscoverAnswersOuterAdapter(mUId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRv_answers.setLayoutManager(linearLayoutManager);
        mRv_answers.setAdapter(mOuterAdapter);
        mRv_answers.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAnswersData(AddUserQuestionBean.DataBean data){
        getUserQuestionListData(ApiConstant.REQUEST_REFRESH, curPageNum);
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mOuterAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                curPageNum++;
                getUserQuestionListData(ApiConstant.REQUEST_NORMAL, curPageNum);
            }
        }, mRv_answers);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_discover_answers;
    }

    @Override
    public void onRefresh() {
        curPageNum = 0;
        getUserQuestionListData(ApiConstant.REQUEST_REFRESH, curPageNum);
    }

}
