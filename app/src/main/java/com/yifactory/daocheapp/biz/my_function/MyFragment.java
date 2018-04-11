package com.yifactory.daocheapp.biz.my_function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.LevelListBean;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.biz.my_function.activity.MyAnswerActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyBuyActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyCouponActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyFocusActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyGradeExplainActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyLecturerApplyActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyLecturerApplyUploadVideoActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyLoginActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyQuestionActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyRegisterActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MySetActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyShareApplyActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyStudyActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyTiXianActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyTopUpActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyUserInfoActivity;
import com.yifactory.daocheapp.event.TiXianSuccessMsg;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.BaseSwipeRefreshLayout;
import com.yifactory.daocheapp.widget.CircleImageView;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    BaseSwipeRefreshLayout mBaseSwipeRefreshLayout;
    @BindView(R.id.bottom_layout)
    AutoLinearLayout bottomLayout;
    @BindView(R.id.user_head_iv)
    CircleImageView userHeadIv;
    @BindView(R.id.username_tv)
    TextView userNameTv;
    @BindView(R.id.company_tv)
    TextView companyTv;
    @BindView(R.id.learndate_tv)
    TextView learnDateTv;
    @BindView(R.id.money_one_tv)
    TextView reciveBalanceTv;
    @BindView(R.id.money_two_tv)
    TextView goldBalanceTv;
    @BindView(R.id.study_time_tv)
    TextView studyTimeTv;
    @BindView(R.id.coupon_count_tv)
    TextView couponCountTv;
    @BindView(R.id.question_count_tv)
    TextView questionCountTv;
    @BindView(R.id.answer_count_tv)
    TextView answerCountTv;
    @BindView(R.id.buy_count_tv)
    TextView buyCountTv;
    @BindView(R.id.focus_count_tv)
    TextView focusCountTv;

    @BindView(R.id.grade_iv1)
    ImageView mIv_grade1;
    @BindView(R.id.grade_iv2)
    ImageView mIv_grade2;
    @BindView(R.id.grade_tv1)
    TextView mTv_grade1;
    @BindView(R.id.grade_tv2)
    TextView mTv_grade2;
    @BindView(R.id.laststudytime_tv)
    TextView lastStudyTimeTv;
    @BindView(R.id.laststudytime_pbar)
    ProgressBar lastStudyTimeProgressBar;
    @BindView(R.id.lecturer_apply_tv)
    TextView mTv_lecturerApply;
    @BindView(R.id.lecturer_apply_state_tv)
    TextView mTv_lecturerApplyState;
    @BindView(R.id.lecturer_apply_iv)
    ImageView mIv_lecturerApply;
    @BindView(R.id.share_apply_tv)
    TextView mTv_shareApply;

    private UserBean.DataBean user;
    private int mTeachState = 0;//讲师申请状态
    private int mShareState = 0;
    private boolean mIsLine;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
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
        bar.setTitleText("我的");
        bar.setRightImageResource(R.drawable.shezhi);
        return true;
    }

    @Override
    protected void initData(Bundle arguments) {
        EventBus.getDefault().register(this);
        mIsLine = new SPreferenceUtil(mActivity, "config.sp").getIsLine();
        if (mIsLine) {
            mBaseSwipeRefreshLayout.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.GONE);
            getUserInfoById();
        } else {
            mBaseSwipeRefreshLayout.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initUser() {
        try {
            String studyDay = UserInfoUtil.getUserInfoBean(mActivity).getCreateTime();
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = format.parse(studyDay);
            long endTime = new Date().getTime();
            long time = (endTime - startDate.getTime()) / (24 * 60 * 60 * 1000);
            learnDateTv.setText(time + "天");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String headImg = user.getHeadImg();
        Glide.with(mActivity).load(headImg).into(userHeadIv);
        userNameTv.setText(user.getUserName());
        companyTv.setText(user.getCompanyName() + "/" + user.getJobName());

//        lastStudyTimeTv.setText("距离下一等级还差" + String.valueOf(user.getLastStudyTime()) + "分钟");
//        lastStudyTimeProgressBar.setProgress(user.getLastStudyTime());

        reciveBalanceTv.setText(String.valueOf(user.getReciveBalance()) + "金币");
        goldBalanceTv.setText(String.valueOf(user.getGoldBalance()) + "金币");
        studyTimeTv.setText(user.getLearnTime() / 60 + "分钟");
        couponCountTv.setText(String.valueOf(user.getDiscountCouponCounts()) + "张");
        questionCountTv.setText(String.valueOf(user.getQuestionCounts()) + "个");
        answerCountTv.setText(String.valueOf(user.getAnswerCounts()) + "个");
        buyCountTv.setText(String.valueOf(user.getBuyCounts()) + "个");
        focusCountTv.setText(String.valueOf(user.getAttentionCounts()) + "个");

        mTeachState = user.getTeachState();
        switch (mTeachState) {
            case 1:
                mTv_lecturerApplyState.setText("审核中");
                mTv_lecturerApplyState.setVisibility(View.VISIBLE);
                break;
            case 3:
                mTv_lecturerApply.setText("上传视频马上赚钱");
                mTv_lecturerApplyState.setVisibility(View.GONE);
                break;
        }
        mShareState = user.getShareState();
        switch (mShareState) {
            case 3:
                mTv_shareApply.setText("分享视频马上赚钱");
                break;
        }
    }

    private void getLevelList() {
        RxHttpUtils.createApi(ApiService.class)
                .levelList()
                .compose(Transformer.<LevelListBean>switchSchedulers())
                .subscribe(new CommonObserver<LevelListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(LevelListBean levelListBean) {
                        if (levelListBean.getResponseState().equals("1")) {
                            List<LevelListBean.DataBean> dataBeanList = levelListBean.getData();
                            handleLevelTimeEvent(dataBeanList);
                            judgeWhichStage(user.getLevalTime());
                        } else {
                            showToast(levelListBean.getMsg());
                        }
                    }
                });
    }

    private int[] learnTimeArray = new int[8];

    private void handleLevelTimeEvent(List<LevelListBean.DataBean> dataBeanList) {
        if (dataBeanList != null && dataBeanList.size() > 0) {
            for (int i = 0; i < dataBeanList.size(); i++) {
                LevelListBean.DataBean dataBean = dataBeanList.get(i);
                int levelTime = dataBean.getLevelTime();
                learnTimeArray[i] = levelTime;
            }
        }
    }

    private void judgeWhichStage(int learnTime) {
        if (learnTimeArray.length > 0) {
            if (learnTimeArray[0] == learnTime) {
                mIv_grade1.setImageResource(R.drawable.xiaobai_bottom);
                mTv_grade1.setText("小白");
                mIv_grade2.setImageResource(R.drawable.xiaoxie_bottom);
                mTv_grade2.setText("小学");
                lastStudyTimeTv.setText("距离下一等级还差" + learnTimeArray[1] / 60 + "分钟");
            } else if (learnTimeArray[0] < learnTime && learnTime < learnTimeArray[1]) {
                mIv_grade1.setImageResource(R.drawable.xiaobai_bottom);
                mTv_grade1.setText("小白");
                mIv_grade2.setImageResource(R.drawable.xiaoxie_bottom);
                mTv_grade2.setText("小学");
                int minute = (learnTimeArray[1] - learnTime) / 60;
                lastStudyTimeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[1] - learnTimeArray[0];
                int first = learnTime - learnTimeArray[0];
                int progress = first * 100 / second;
                lastStudyTimeProgressBar.setProgress(progress);
            } else if (learnTimeArray[1] == learnTime) {
                mIv_grade1.setImageResource(R.drawable.xiaoxie_bottom);
                mTv_grade1.setText("小学");
                mIv_grade2.setImageResource(R.drawable.zhongxue_bottom);
                mTv_grade2.setText("中学");
                lastStudyTimeTv.setText("距离下一等级还差" + (learnTimeArray[2] - learnTimeArray[1]) / 60 + "分钟");

            } else if (learnTimeArray[1] < learnTime && learnTime < learnTimeArray[2]) {
                mIv_grade1.setImageResource(R.drawable.xiaoxie_bottom);
                mTv_grade1.setText("小学");
                mIv_grade2.setImageResource(R.drawable.zhongxue_bottom);
                mTv_grade2.setText("中学");
                int minute = (learnTimeArray[2] - learnTime) / 60;
                lastStudyTimeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[2] - learnTimeArray[1];
                int first = learnTime - learnTimeArray[1];
                int progress = first * 100 / second;
                lastStudyTimeProgressBar.setProgress(progress);
            } else if (learnTimeArray[2] == learnTime) {
                mIv_grade1.setImageResource(R.drawable.zhongxue_bottom);
                mTv_grade1.setText("中学");
                mIv_grade2.setImageResource(R.drawable.gaozhong_bottom);
                mTv_grade2.setText("高中");
                lastStudyTimeTv.setText("距离下一等级还差" + (learnTimeArray[3] - learnTimeArray[2]) / 60 + "分钟");

            } else if (learnTimeArray[2] < learnTime && learnTime < learnTimeArray[3]) {
                mIv_grade1.setImageResource(R.drawable.zhongxue_bottom);
                mTv_grade1.setText("中学");
                mIv_grade2.setImageResource(R.drawable.gaozhong_bottom);
                mTv_grade2.setText("高中");
                int minute = (learnTimeArray[3] - learnTime) / 60;
                lastStudyTimeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[3] - learnTimeArray[2];
                int first = learnTime - learnTimeArray[2];
                int progress = first * 100 / second;
                lastStudyTimeProgressBar.setProgress(progress);
            } else if (learnTimeArray[3] == learnTime) {
                mIv_grade1.setImageResource(R.drawable.gaozhong_bottom);
                mTv_grade1.setText("高中");
                mIv_grade2.setImageResource(R.drawable.xueshi_bottom);
                mTv_grade2.setText("学士");
                lastStudyTimeTv.setText("距离下一等级还差" + (learnTimeArray[4] - learnTimeArray[3]) / 60 + "分钟");

            } else if (learnTimeArray[3] < learnTime && learnTime < learnTimeArray[4]) {
                mIv_grade1.setImageResource(R.drawable.gaozhong_bottom);
                mTv_grade1.setText("高中");
                mIv_grade2.setImageResource(R.drawable.xueshi_bottom);
                mTv_grade2.setText("学士");
                int minute = (learnTimeArray[4] - learnTime) / 60;
                lastStudyTimeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[4] - learnTimeArray[3];
                int first = learnTime - learnTimeArray[3];
                int progress = first * 100 / second;
                lastStudyTimeProgressBar.setProgress(progress);
            } else if (learnTimeArray[4] == learnTime) {
                mIv_grade1.setImageResource(R.drawable.xueshi_bottom);
                mTv_grade1.setText("学士");
                mIv_grade2.setImageResource(R.drawable.shuoshi_bottom);
                mTv_grade2.setText("硕士");
                lastStudyTimeTv.setText("距离下一等级还差" + (learnTimeArray[5] - learnTimeArray[4]) / 60 + "分钟");

            } else if (learnTimeArray[4] < learnTime && learnTime < learnTimeArray[5]) {
                mIv_grade1.setImageResource(R.drawable.xueshi_bottom);
                mTv_grade1.setText("学士");
                mIv_grade2.setImageResource(R.drawable.shuoshi_bottom);
                mTv_grade2.setText("硕士");
                int minute = (learnTimeArray[5] - learnTime) / 60;
                lastStudyTimeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[5] - learnTimeArray[4];
                int first = learnTime - learnTimeArray[4];
                int progress = first * 100 / second;
                lastStudyTimeProgressBar.setProgress(progress);
            } else if (learnTimeArray[5] == learnTime) {
                mIv_grade1.setImageResource(R.drawable.shuoshi_bottom);
                mTv_grade1.setText("硕士");
                mIv_grade2.setImageResource(R.drawable.boshi_bottom);
                mTv_grade2.setText("博士");
                lastStudyTimeTv.setText("距离下一等级还差" + (learnTimeArray[6] - learnTimeArray[5]) / 60 + "分钟");

            } else if (learnTimeArray[5] < learnTime && learnTime < learnTimeArray[6]) {
                mIv_grade1.setImageResource(R.drawable.shuoshi_bottom);
                mTv_grade1.setText("硕士");
                mIv_grade2.setImageResource(R.drawable.boshi_bottom);
                mTv_grade2.setText("博士");
                int minute = (learnTimeArray[6] - learnTime) / 60;
                lastStudyTimeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[6] - learnTimeArray[5];
                int first = learnTime - learnTimeArray[5];
                int progress = first * 100 / second;
                lastStudyTimeProgressBar.setProgress(progress);
            } else if (learnTimeArray[6] == learnTime) {
                mIv_grade1.setImageResource(R.drawable.boshi_bottom);
                mTv_grade1.setText("博士");
                mIv_grade2.setImageResource(R.drawable.wangzhe_bottom);
                mTv_grade2.setText("王者");
                lastStudyTimeTv.setText("距离下一等级还差" + (learnTimeArray[7] - learnTimeArray[6]) / 60 + "分钟");

            } else if (learnTimeArray[6] < learnTime && learnTime < learnTimeArray[7]) {
                mIv_grade1.setImageResource(R.drawable.boshi_bottom);
                mTv_grade1.setText("博士");
                mIv_grade2.setImageResource(R.drawable.wangzhe_bottom);
                mTv_grade2.setText("王者");
                int minute = (learnTimeArray[7] - learnTime) / 60;
                lastStudyTimeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[7] - learnTimeArray[6];
                int first = learnTime - learnTimeArray[6];
                int progress = first * 100 / second;
                lastStudyTimeProgressBar.setProgress(progress);
            } else if (learnTimeArray[7] == learnTime || learnTimeArray[7] > learnTime) {
                mIv_grade1.setImageResource(R.drawable.boshi_bottom);
                mTv_grade1.setText("博士");
                mIv_grade2.setImageResource(R.drawable.wangzhe_bottom);
                mTv_grade2.setText("王者");
                lastStudyTimeTv.setText("您已经达到王者等级");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void getUserInfoById() {
        String uId = UserInfoUtil.getUserInfoBean(mActivity).getUId();
        RxHttpUtils.createApi(ApiService.class)
                .getUserById(uId, "")
                .compose(Transformer.<UserBean>switchSchedulers())
                .subscribe(new CommonObserver<UserBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        refreshFinish();
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onSuccess(UserBean userBean) {
                        refreshFinish();
                        if (userBean != null && userBean.getResponseState().equals("1")) {
                            user = userBean.getData().get(0);
                            initUser();
                            getLevelList();
                        } else {
                            Toast.makeText(getActivity(), userBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void refreshFinish() {
        if (mBaseSwipeRefreshLayout != null && mBaseSwipeRefreshLayout.isRefreshing()) {
            mBaseSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void addListener() {
        mBaseSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_my;
    }

    @OnClick({R.id.naviFrameRight, R.id.lecturer_apply_layout, R.id.share_apply_layout, R.id.user_head_iv, R.id.grade_iv1,
            R.id.grade_iv2, R.id.ti_xian_btn, R.id.top_up_btn, R.id.my_study_layout, R.id.my_coupon_layout, R.id.my_question_layout, R.id.my_answer_layout,
            R.id.my_buy_layout, R.id.my_focus_layout, R.id.my_invite_friend_earn_money_layout, R.id.login_tv, R.id.register_tv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameRight:
                    if (mIsLine) {
                        Intent setIntent = new Intent(mActivity, MySetActivity.class);
                        startActivity(setIntent);
                    } else {
                        showToast("请登陆");
                    }
                    break;
                case R.id.lecturer_apply_layout:
                    Intent lecturerApplyIntent = new Intent();
                    if (mTeachState == 0) {
                        lecturerApplyIntent.setClass(mActivity, MyLecturerApplyActivity.class);
                    } else if (mTeachState == 1) {
                        showToast("审核中");
                        return;
                    } else if (mTeachState == 2) {
                        showToast("审核未通过，请重新申请");
                        lecturerApplyIntent.setClass(mActivity, MyLecturerApplyActivity.class);
                    } else if (mTeachState == 3) {
                        lecturerApplyIntent.setClass(mActivity, MyLecturerApplyUploadVideoActivity.class);
                    }
                    startActivity(lecturerApplyIntent);
                    break;
                case R.id.share_apply_layout:
                    if (mShareState == 0 || mShareState == 2) {
                        Intent shareApplyIntent = new Intent(mActivity, MyShareApplyActivity.class);
                        startActivity(shareApplyIntent);
                    } else if (mShareState == 1) {
                        showCustomToast(R.layout.layout_my_custom_toast, "审核中，请耐心等待～", 0);
                    } else if (mShareState == 3) {//已通过
                        showCustomToast(R.layout.layout_my_custom_toast, "可以分享视频赚钱啦～", 0);
                    }
                    break;
                case R.id.user_head_iv:
                    Intent userInfoIntent = new Intent(mActivity, MyUserInfoActivity.class);
                    userInfoIntent.putExtra("userInfo", user);
                    startActivity(userInfoIntent);
                    break;
                case R.id.grade_iv1:
                case R.id.grade_iv2:
                    Intent gradeExplainIntent = new Intent(mActivity, MyGradeExplainActivity.class);
                    startActivity(gradeExplainIntent);
                    break;
                case R.id.ti_xian_btn:
                    Intent tiXianIntent = new Intent(mActivity, MyTiXianActivity.class);
                    startActivity(tiXianIntent);
                    break;
                case R.id.top_up_btn:
                    Intent topUpIntent = new Intent(mActivity, MyTopUpActivity.class);
                    startActivity(topUpIntent);
                    break;
                case R.id.my_study_layout:
                    Intent studyIntent = new Intent(mActivity, MyStudyActivity.class);
                    startActivity(studyIntent);
                    break;
                case R.id.my_coupon_layout:
                    Intent couponIntent = new Intent(mActivity, MyCouponActivity.class);
                    startActivity(couponIntent);
                    break;
                case R.id.my_question_layout:
                    Intent questIntent = new Intent(mActivity, MyQuestionActivity.class);
                    startActivity(questIntent);
                    break;
                case R.id.my_answer_layout:
                    Intent answerIntent = new Intent(mActivity, MyAnswerActivity.class);
                    startActivity(answerIntent);
                    break;
                case R.id.my_buy_layout:
                    Intent butIntent = new Intent(mActivity, MyBuyActivity.class);
                    startActivity(butIntent);
                    break;
                case R.id.my_focus_layout:
                    Intent myFocusIntent = new Intent(mActivity, MyFocusActivity.class);
                    startActivity(myFocusIntent);
                    break;
                case R.id.my_invite_friend_earn_money_layout:
                    showCustomToast(R.layout.layout_my_custom_toast, "暂未开通，请耐心等待～", 0);
                    break;
                case R.id.login_tv:
                    Intent loginIntent = new Intent(mActivity, MyLoginActivity.class);
                    startActivity(loginIntent);
                    break;
                case R.id.register_tv:
                    Intent registerIntent = new Intent(mActivity, MyRegisterActivity.class);
                    startActivity(registerIntent);
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tiXianSuccessEvent(TiXianSuccessMsg tiXianSuccessMsg) {
        getUserInfoById();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        if (learnTimeArray.length == 0) {
            getLevelList();
        }
        getUserInfoById();
    }
}
