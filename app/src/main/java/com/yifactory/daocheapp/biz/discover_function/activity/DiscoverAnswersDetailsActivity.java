package com.yifactory.daocheapp.biz.discover_function.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.yifactory.daocheapp.bean.AddRewardBean;
import com.yifactory.daocheapp.bean.AddUserQuestionAnswerBean;
import com.yifactory.daocheapp.bean.DiscoverCommentBean;
import com.yifactory.daocheapp.bean.GetUserQuestionAnswerBean;
import com.yifactory.daocheapp.bean.GetUserQuestionListBean;
import com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter.DiscoverAnswersInnerAdapter;
import com.yifactory.daocheapp.utils.MyInputFilter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SoftInputUtils;
import com.yifactory.daocheapp.utils.SoftKeyBoardListener;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.CircleImageView;
import com.yifactory.daocheapp.widget.CustomPopWindow;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DiscoverAnswersDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, DiscoverAnswersInnerAdapter.ReplayCallBack, DiscoverAnswersInnerAdapter.DaShangCallBack {
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
    private DiscoverAnswersDetailsActivity context;
    @BindView(R.id.editTextBodyLl)
    AutoLinearLayout edittextbody;
    @BindView(R.id.comment_et)
    EditText editText;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.comment_detail_rv)
    RecyclerView mRv_commentDetails;
    @BindView(R.id.rootLayout)
    AutoRelativeLayout rootLayout;

    private Dialog mDialog;
    private DiscoverAnswersInnerAdapter mInnerAdapter;
    private String mUId;
    private DiscoverCommentBean mCommentBean;
    private GetUserQuestionListBean.DataBean mDataBean;
    private View mViewLeft;
    private View mViewRight;
    private TextView mTitleTv;
    private EditText mJinBiEt;

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
        initCommentDetailsRv();
    }

    private void initCommentDetailsRv() {
        mInnerAdapter = new DiscoverAnswersInnerAdapter(this, mUId, this);
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
        mUId = UserInfoUtil.getUserInfoBean(this).getUId();
        context = this;
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mDialog = SDDialogUtil.newLoading(this, "请求中");
        getIntentData();
        mDialog.show();
        requestMoreAnswersData(ApiConstant.REQUEST_NORMAL);
    }

    private void getIntentData() {
        mDataBean = (GetUserQuestionListBean.DataBean) getIntent().getSerializableExtra("dataBean");
        GetUserQuestionListBean.DataBean.CreatorBean creator = mDataBean.getCreator();
        Glide.with(context).load(creator.getHeadImg()).into(headIv);
        mTv_nickName.setText(creator.getUserName());
        mTv_grade.setText(creator.getUserLeval());
        mTv_jobAttr.setText(creator.getJobArea());
        mTv_question.setText(mDataBean.getQuestionBody());
    }

    private void requestMoreAnswersData(String requestMark) {
        String uId = UserInfoUtil.getUserInfoBean(this).getUId();
        RxHttpUtils
                .createApi(ApiService.class)
                .getUserQuestionAnswer(mDataBean.getQId(), uId)
                .compose(Transformer.<GetUserQuestionAnswerBean>switchSchedulers())
                .subscribe(new CommonObserver<GetUserQuestionAnswerBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        closeDialog();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetUserQuestionAnswerBean getUserQuestionListBean) {
                        rootLayout.setVisibility(View.VISIBLE);
                        closeDialog();
                        if (getUserQuestionListBean.getResponseState().equals("1")) {
                            List<GetUserQuestionListBean.DataBean.AnswersBean> answersBeanList = getUserQuestionListBean.getData();
                            if (answersBeanList != null && answersBeanList.size() > 0) {
                                mInnerAdapter.setNewData(answersBeanList);
                                mViewLeft.setVisibility(View.VISIBLE);
                                mViewRight.setVisibility(View.VISIBLE);
                                mTitleTv.setText("热门评论");
                                mTitleTv.setTextColor(Color.parseColor("#333333"));
                            } else {
                                mViewLeft.setVisibility(View.GONE);
                                mViewRight.setVisibility(View.GONE);
                                mTitleTv.setText("暂无评论！");
                                mTitleTv.setTextColor(Color.parseColor("#999999"));
                            }
                        } else {
                            showToast(getUserQuestionListBean.getMsg());
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


    @OnClick({R.id.naviFrameLeft, R.id.give_him_advice_tv, R.id.comment_iv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    if (edittextbody.isShown()) {
                        updateEditTextBodyVisible(View.GONE);
                    }
                    finish();
                    break;
                case R.id.give_him_advice_tv:
                    DiscoverCommentBean commentBean = new DiscoverCommentBean();
                    commentBean.setrId("");
                    commentBean.setReciverName("");
                    this.mCommentBean = commentBean;
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
                .addUserQuestionAnswer(mDataBean.getQId(), mUId, mCommentBean.getrId(), commentContentStr)
                .compose(Transformer.<AddUserQuestionAnswerBean>switchSchedulers())
                .subscribe(new CommonObserver<AddUserQuestionAnswerBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        closeDialog();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(AddUserQuestionAnswerBean getUserQuestionListBean) {
                        closeDialog();
                        if (getUserQuestionListBean.getResponseState().equals("1")) {
                            List<GetUserQuestionListBean.DataBean.AnswersBean> answersBeanList = mInnerAdapter.getData();
                            List<GetUserQuestionListBean.DataBean.AnswersBean> answersBeanList1 = getUserQuestionListBean.getData();
                            answersBeanList.addAll(answersBeanList1);
                            mInnerAdapter.setNewData(answersBeanList);
                            if (answersBeanList.size() > 0) {
                                mInnerAdapter.setNewData(answersBeanList);
                                mViewLeft.setVisibility(View.VISIBLE);
                                mViewRight.setVisibility(View.VISIBLE);
                                mTitleTv.setText("热门评论");
                                mTitleTv.setTextColor(Color.parseColor("#333333"));
                            } else {
                                mViewLeft.setVisibility(View.GONE);
                                mViewRight.setVisibility(View.GONE);
                                mTitleTv.setText("暂无评论！");
                                mTitleTv.setTextColor(Color.parseColor("#999999"));
                            }
                            mRv_commentDetails.scrollToPosition(mInnerAdapter.getItemCount() - 1);
                        } else {
                            showToast(getUserQuestionListBean.getMsg());
                        }
                    }
                });
    }


    private void updateEditTextBodyVisible(int visibility) {
        edittextbody.setVisibility(visibility);
        if (View.VISIBLE == visibility) {
            editText.requestFocus();
            editText.setText("");
            if (!TextUtils.isEmpty(mCommentBean.getReciverName())) {
                editText.setHint("回复@" + mCommentBean.getReciverName() + ":");
            } else {
                editText.setHint("");
            }
            SoftInputUtils.showSoftInput(editText);
        } else if (View.GONE == visibility) {
            SoftInputUtils.hideSoftInput(editText);
        }
    }

    @Override
    public void onRefresh() {
        requestMoreAnswersData(ApiConstant.REQUEST_REFRESH);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discover_answers_details;
    }

    @Override
    public void replay(DiscoverCommentBean commentBean) {
        this.mCommentBean = commentBean;
        updateEditTextBodyVisible(View.VISIBLE);
    }

    private String aId;

    @Override
    public void shang(String aId) {
        this.aId = aId;
        daShangMsg();
    }

    private CustomPopWindow mCustomPopWindow_DaShang;

    public void daShangMsg() {
        if (mCustomPopWindow_DaShang == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.pop_discover_answers_shang_layout, null);
            ImageView closeIv = (ImageView) contentView.findViewById(R.id.close_iv);

            mJinBiEt = (EditText) contentView.findViewById(R.id.jinbi_et);
            mJinBiEt.setFilters(new InputFilter[]{new MyInputFilter(2)});
            TextView daShangTv = (TextView) contentView.findViewById(R.id.dashang_tv);
            mCustomPopWindow_DaShang = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.5f)
                    .create()
                    .showAtLocation(rootLayout, Gravity.CENTER, 0, 0);
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCustomPopWindow_DaShang != null) {
                        mCustomPopWindow_DaShang.dissmiss();
                    }
                }
            });
            daShangTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String jinBiStr = mJinBiEt.getText().toString().trim();
                    if (TextUtils.isEmpty(jinBiStr)) {
                        showToast("请输入金币个数");
                        return;
                    }
                    if (mCustomPopWindow_DaShang != null) {
                        mCustomPopWindow_DaShang.dissmiss();
                    }
                    addReward();
                }
            });
        } else {
            mJinBiEt.setText("");
            mCustomPopWindow_DaShang.showAtLocation(rootLayout, Gravity.CENTER, 0, 0);
        }
    }

    private void addReward() {
        String jinBiStr = mJinBiEt.getText().toString().trim();
        mDialog.show();
        RxHttpUtils
                .createApi(ApiService.class)
                .addReward(mUId, aId, Float.valueOf(jinBiStr))
                .compose(Transformer.<AddRewardBean>switchSchedulers())
                .subscribe(new CommonObserver<AddRewardBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        closeDialog();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(AddRewardBean addRewardBean) {
                        closeDialog();
                        showToast(addRewardBean.getMsg());
                    }
                });
    }
}
