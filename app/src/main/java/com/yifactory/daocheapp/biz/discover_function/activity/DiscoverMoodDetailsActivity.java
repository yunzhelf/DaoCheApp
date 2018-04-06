package com.yifactory.daocheapp.biz.discover_function.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.AddShowMoodCommentBean;
import com.yifactory.daocheapp.bean.DiscoverCommentBean;
import com.yifactory.daocheapp.bean.GetShowMoodCommentBean;
import com.yifactory.daocheapp.bean.GetShowMoodListBean;
import com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter.DiscoverMoodInnerAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SoftInputUtils;
import com.yifactory.daocheapp.utils.SoftKeyBoardListener;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.CircleImageView;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DiscoverMoodDetailsActivity extends BaseActivity implements DiscoverMoodInnerAdapter.ReplayCallBack, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rootLayout)
    AutoRelativeLayout rootLayout;
    private GetShowMoodListBean.DataBean mDataBean;
    @BindView(R.id.head_iv)
    CircleImageView headIv;
    @BindView(R.id.nickname_tv)
    TextView mTv_nickName;
    @BindView(R.id.job_attr_tv)
    TextView mTv_jobAttr;
    @BindView(R.id.grade_tv)
    TextView mTv_grade;
    @BindView(R.id.question_tv)
    TextView mTv_question;
    private Dialog mDialog;
    private String mUId;

    @BindView(R.id.editTextBodyLl)
    AutoLinearLayout edittextbody;
    @BindView(R.id.comment_et)
    EditText editText;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.comment_detail_rv)
    RecyclerView mRv_commentDetails;
    private DiscoverMoodInnerAdapter mInnerAdapter;

    private View mViewLeft;
    private View mViewRight;
    private TextView mTitleTv;

    private DiscoverCommentBean mCommentBean;
    private boolean isFirstLoad = true;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("详情");
        return true;
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        listenSoftInputHiddenAndShown();
    }

    private void listenSoftInputHiddenAndShown() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                if (edittextbody.isShown()) {
                    edittextbody.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    protected void initView() {
        initMoodCommentDetailsRv();
    }

    private void initMoodCommentDetailsRv() {
        mInnerAdapter = new DiscoverMoodInnerAdapter(this, mUId);
        View headerView = getLayoutInflater().inflate(R.layout.header_discover_answers_details, null);
        mViewLeft = headerView.findViewById(R.id.view_left);
        mViewRight = headerView.findViewById(R.id.view_right);
        mTitleTv = (TextView) headerView.findViewById(R.id.title_tv);
        mInnerAdapter.addHeaderView(headerView);
        mRv_commentDetails.setLayoutManager(new LinearLayoutManager(this));
        mRv_commentDetails.setAdapter(mInnerAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        mDialog = SDDialogUtil.newLoading(this, "加载中");
        getIntentData();
        setDefaultData();
        mDialog.show();

        getShowMoodComment(ApiConstant.REQUEST_NORMAL);
    }

    private void getShowMoodComment(String requestMark) {
        RxHttpUtils
                .createApi(ApiService.class)
                .getShowMoodComment(mDataBean.getSmId())
                .compose(Transformer.<GetShowMoodCommentBean>switchSchedulers())
                .subscribe(new CommonObserver<GetShowMoodCommentBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        closeDialog();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetShowMoodCommentBean getShowMoodCommentBean) {
                        rootLayout.setVisibility(View.VISIBLE);
                        closeDialog();
                        if (getShowMoodCommentBean.getResponseState().equals("1")) {
                            List<GetShowMoodCommentBean.DataBean> dataBeanList = getShowMoodCommentBean.getData();
                            if (dataBeanList != null && dataBeanList.size() > 0) {
                                mInnerAdapter.setNewData(dataBeanList);
                                mViewLeft.setVisibility(View.VISIBLE);
                                mViewRight.setVisibility(View.VISIBLE);
                                mTitleTv.setText("热门评论");
                                mTitleTv.setTextColor(Color.parseColor("#333333"));
                                if (!isFirstLoad) {
                                    mRv_commentDetails.scrollToPosition(mInnerAdapter.getItemCount() - 1);
                                } else {
                                    isFirstLoad = false;
                                }
                            } else {
                                mViewLeft.setVisibility(View.GONE);
                                mViewRight.setVisibility(View.GONE);
                                mTitleTv.setText("暂无评论！");
                                mTitleTv.setTextColor(Color.parseColor("#999999"));
                            }
                        } else {
                            showToast(getShowMoodCommentBean.getMsg());
                        }
                    }
                });
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void setDefaultData() {
        GetShowMoodListBean.DataBean.CreatorBean creator = mDataBean.getCreator();
        Glide.with(this).load(creator.getHeadImg()).into(headIv);
        mTv_nickName.setText(creator.getUserName());
        mTv_grade.setText(creator.getUserLeval());
        mTv_jobAttr.setText(creator.getJobArea());
        mTv_question.setText(mDataBean.getMoodBody());
    }

    private void getIntentData() {
        mDataBean = (GetShowMoodListBean.DataBean) getIntent().getSerializableExtra("dataBean");
    }

    @OnClick({R.id.naviFrameLeft, R.id.comment_tv, R.id.comment_iv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    if (edittextbody.isShown()) {
                        updateEditTextBodyVisible(View.GONE);
                    }
                    finish();
                    break;
                case R.id.comment_tv:
                    mCommentBean = new DiscoverCommentBean();
                    mCommentBean.setrId("");
                    mCommentBean.setReciverName("");
                    updateEditTextBodyVisible(View.VISIBLE);
                    break;
                case R.id.comment_iv:
                    String commentContentStr = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(commentContentStr)) {
                        showToast("请输入评论内容");
                        return;
                    }
                    updateEditTextBodyVisible(View.GONE);
                    publishComment(commentContentStr);
                    break;
            }
        }
    }

    private void publishComment(String commentContentStr) {
        mDialog.show();
        RxHttpUtils
                .createApi(ApiService.class)
                .addShowMoodComment(mUId, mCommentBean.getrId(), mDataBean.getSmId(), commentContentStr)
                .compose(Transformer.<AddShowMoodCommentBean>switchSchedulers())
                .subscribe(new CommonObserver<AddShowMoodCommentBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        closeDialog();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(AddShowMoodCommentBean addShowMoodCommentBean) {
                        closeDialog();
                        if (addShowMoodCommentBean.getResponseState().equals("1")) {
                            getShowMoodComment(ApiConstant.REQUEST_REFRESH);
                        } else {
                            showToast(addShowMoodCommentBean.getMsg());
                        }
                    }
                });
    }


    private void updateEditTextBodyVisible(int visibility) {
        edittextbody.setVisibility(visibility);
        if (View.VISIBLE == visibility) {
            editText.requestFocus();
            editText.setText("");
            String reciverName = mCommentBean.getReciverName();
            if (!TextUtils.isEmpty(reciverName)) {
                editText.setHint("回复@" + reciverName + ":");
            } else {
                editText.setHint("");
            }
            SoftInputUtils.showSoftInput(editText);
        } else if (View.GONE == visibility) {
            SoftInputUtils.hideSoftInput(editText);
        }
    }


    @Override
    public void replay(DiscoverCommentBean commentBean) {
        this.mCommentBean = commentBean;
        updateEditTextBodyVisible(View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discover_mood_details;
    }

    @Override
    public void onRefresh() {
        getShowMoodComment(ApiConstant.REQUEST_REFRESH);
    }
}
