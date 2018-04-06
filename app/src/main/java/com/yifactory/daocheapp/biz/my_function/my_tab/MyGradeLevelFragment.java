package com.yifactory.daocheapp.biz.my_function.my_tab;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.Glide;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.GetLevalDataBean;
import com.yifactory.daocheapp.bean.GetStudyDateBean;
import com.yifactory.daocheapp.bean.LevelListBean;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.biz.my_function.adapter.MyStudyGradeLevelAdapter;
import com.yifactory.daocheapp.utils.DateUtil;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.CircleImageView;
import com.yifactory.daocheapp.widget.TitleBar;
import com.yifactory.daocheapp.widget.lazyviewpager.LazyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyGradeLevelFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LazyFragmentPagerAdapter.Laziable {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.user_head_iv)
    CircleImageView mUserHeadIv;
    @BindView(R.id.study_minute_tv)
    TextView mStudyMinuteTv;
    @BindView(R.id.study_day_tv)
    TextView mStudyDayTv;
    @BindView(R.id.cur_grade_iv)
    ImageView mCurGradeIv;
    @BindView(R.id.cur_grade_tv)
    TextView mCurGradeTv;
    @BindView(R.id.needTime_to_nextGrade_tv)
    TextView mNeedTimeToNextGradeTv;
    @BindView(R.id.next_grade_iv)
    ImageView mNextGradeIv;
    @BindView(R.id.next_grade_tv)
    TextView mNextGradeTv;
    @BindView(R.id.needTime_to_nextGrade_progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.day_tv)
    TextView dayTv;
    @BindView(R.id.week_tv)
    TextView weekTv;
    @BindView(R.id.month_tv)
    TextView monthTv;
    private int tabPosition = 0;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String mUId;
    private Dialog mDialog;
    private int mLevalTime;
    private String mTimeStr;
    private MyStudyGradeLevelAdapter mGradeLevelAdapter;
    private List<GetLevalDataBean.DataBean.DayLevalDataBean> mDataBeanListDay = new ArrayList<>();
    private List<GetLevalDataBean.DataBean.DayLevalDataBean> mDataBeanListWeek = new ArrayList<>();
    private List<GetLevalDataBean.DataBean.DayLevalDataBean> mDataBeanListMonth = new ArrayList<>();

    public static MyGradeLevelFragment newInstance() {
        MyGradeLevelFragment fragment = new MyGradeLevelFragment();
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
        initGradeLevelRv();
        initTimePicker();
    }

    private void initGradeLevelRv() {
        mGradeLevelAdapter = new MyStudyGradeLevelAdapter();
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mGradeLevelAdapter);
    }


    @Override
    protected void initData(Bundle arguments) {
        mUId = UserInfoUtil.getUserInfoBean(mActivity).getUId();
        mTimeStr = String.valueOf(System.currentTimeMillis() / 1000);
        if (mDialog == null) {
            mDialog = SDDialogUtil.newLoading(mActivity, "请求中...");
        }
        mDialog.show();
        getUserInfoById();
        getStudyDate();
    }

    private void getStudyDate() {
        RxHttpUtils.createApi(ApiService.class)
                .getLevalData(mUId, mTimeStr)
                .compose(Transformer.<GetLevalDataBean>switchSchedulers())
                .subscribe(new CommonObserver<GetLevalDataBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        closeDialog();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetLevalDataBean getLevalDataBean) {
                        closeDialog();
                        if (getLevalDataBean.responseState.equals("1")) {
                            mDataBeanListDay.clear();
                            mDataBeanListWeek.clear();
                            mDataBeanListMonth.clear();
                            List<GetLevalDataBean.DataBean> dataBeanList = getLevalDataBean.data;
                            if (dataBeanList != null && dataBeanList.size() > 0) {
                                GetLevalDataBean.DataBean dataBean = dataBeanList.get(0);
                                List<GetLevalDataBean.DataBean.DayLevalDataBean> dayLevalDataBeanList = dataBean.dayLevalData;
                                List<GetLevalDataBean.DataBean.WeekDataBean> weekDataBeanList = dataBean.weekData;
                                List<GetLevalDataBean.DataBean.MonthDataBean> monthDataBeanList = dataBean.monthData;

                                if (dayLevalDataBeanList.size()>0) {
                                    mDataBeanListDay.addAll(dayLevalDataBeanList);
                                }

                                if (weekDataBeanList.size() > 0) {
                                    for (GetLevalDataBean.DataBean.WeekDataBean weekDataBean : weekDataBeanList) {
                                        GetLevalDataBean.DataBean.DayLevalDataBean dayDataBean = new GetLevalDataBean.DataBean.DayLevalDataBean();
                                        dayDataBean.createTime = weekDataBean.createTime;
                                        dayDataBean.showTime = weekDataBean.showTime;
                                        mDataBeanListWeek.add(dayDataBean);
                                    }
                                }
                                if (monthDataBeanList.size() > 0) {
                                    for (GetLevalDataBean.DataBean.MonthDataBean monthDataBean : monthDataBeanList) {
                                        GetLevalDataBean.DataBean.DayLevalDataBean dayDataBean = new GetLevalDataBean.DataBean.DayLevalDataBean();
                                        dayDataBean.createTime = monthDataBean.createTime;
                                        dayDataBean.showTime = monthDataBean.showTime;
                                        mDataBeanListMonth.add(dayDataBean);
                                    }
                                }
                            }
                            if (tabPosition == 0) {
                                mGradeLevelAdapter.setNewData(mDataBeanListDay);
                            } else if (tabPosition == 1) {
                                mGradeLevelAdapter.setNewData(mDataBeanListWeek);
                            } else if (tabPosition == 2) {
                                mGradeLevelAdapter.setNewData(mDataBeanListMonth);
                            }
                        } else {
                            showToast(getLevalDataBean.msg);
                        }

                    }
                });
    }

    private void getUserInfoById() {
        RxHttpUtils.createApi(ApiService.class)
                .getUserById(mUId, "")
                .compose(Transformer.<UserBean>switchSchedulers())
                .subscribe(new CommonObserver<UserBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(UserBean userBean) {
                        if (userBean.getResponseState().equals("1")) {
                            UserBean.DataBean dataBean = userBean.getData().get(0);
                            int learnDays = dataBean.getLearnDays();
                            mStudyDayTv.setText(learnDays + "天");
                            mLevalTime = dataBean.getLevalTime();
                            mStudyMinuteTv.setText(dataBean.getLearnTime() / 60 + "分钟");
                            String headImg = dataBean.getHeadImg();
                            Glide.with(mActivity).load(headImg).into(mUserHeadIv);
                            getLevelList();
                        } else {
                            showToast(userBean.getMsg());
                        }

                    }
                });
    }

    private void getLevelList() {
        RxHttpUtils.createApi(ApiService.class)
                .levelList()
                .compose(Transformer.<LevelListBean>switchSchedulers())
                .subscribe(new CommonObserver<LevelListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        closeDialog();
                    }

                    @Override
                    protected void onSuccess(LevelListBean levelListBean) {
                        closeDialog();
                        if (levelListBean.getResponseState().equals("1")) {
                            List<LevelListBean.DataBean> dataBeanList = levelListBean.getData();
                            handleLevelTimeEvent(dataBeanList);
                            judgeWhichStage(mLevalTime);
                            mGradeLevelAdapter.setLearnTimeArray(learnTimeArray);
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
                mCurGradeIv.setImageResource(R.drawable.xiaobai_bottom);
                mCurGradeTv.setText("小白");
                mNextGradeIv.setImageResource(R.drawable.xiaoxie_bottom);
                mNextGradeTv.setText("小学");
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + learnTimeArray[1] / 60 + "分钟");

            } else if (learnTimeArray[0] < learnTime && learnTime < learnTimeArray[1]) {
                mCurGradeIv.setImageResource(R.drawable.xiaobai_bottom);
                mCurGradeTv.setText("小白");
                mNextGradeIv.setImageResource(R.drawable.xiaoxie_bottom);
                mNextGradeTv.setText("小学");
                int minute = (learnTimeArray[1] - learnTime) / 60;
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[1] - learnTimeArray[0];
                int first = learnTime - learnTimeArray[0];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
            } else if (learnTimeArray[1] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.xiaoxie_bottom);
                mCurGradeTv.setText("小学");
                mNextGradeIv.setImageResource(R.drawable.zhongxue_bottom);
                mNextGradeTv.setText("中学");
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[2] - learnTimeArray[1]) / 60 + "分钟");

            } else if (learnTimeArray[1] < learnTime && learnTime < learnTimeArray[2]) {
                mCurGradeIv.setImageResource(R.drawable.xiaoxie_bottom);
                mCurGradeTv.setText("小学");
                mNextGradeIv.setImageResource(R.drawable.zhongxue_bottom);
                mNextGradeTv.setText("中学");
                int minute = (learnTimeArray[2] - learnTime) / 60;
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[2] - learnTimeArray[1];
                int first = learnTime - learnTimeArray[1];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
            } else if (learnTimeArray[2] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.zhongxue_bottom);
                mCurGradeTv.setText("中学");
                mNextGradeIv.setImageResource(R.drawable.gaozhong_bottom);
                mNextGradeTv.setText("高中");
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[3] - learnTimeArray[2]) / 60 + "分钟");

            } else if (learnTimeArray[2] < learnTime && learnTime < learnTimeArray[3]) {
                mCurGradeIv.setImageResource(R.drawable.zhongxue_bottom);
                mCurGradeTv.setText("中学");
                mNextGradeIv.setImageResource(R.drawable.gaozhong_bottom);
                mNextGradeTv.setText("高中");
                int minute = (learnTimeArray[3] - learnTime) / 60;
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[3] - learnTimeArray[2];
                int first = learnTime - learnTimeArray[2];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
            } else if (learnTimeArray[3] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.gaozhong_bottom);
                mCurGradeTv.setText("高中");
                mNextGradeIv.setImageResource(R.drawable.xueshi_bottom);
                mNextGradeTv.setText("学士");
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[4] - learnTimeArray[3]) / 60 + "分钟");

            } else if (learnTimeArray[3] < learnTime && learnTime < learnTimeArray[4]) {
                mCurGradeIv.setImageResource(R.drawable.gaozhong_bottom);
                mCurGradeTv.setText("高中");
                mNextGradeIv.setImageResource(R.drawable.xueshi_bottom);
                mNextGradeTv.setText("学士");
                int minute = (learnTimeArray[4] - learnTime) / 60;
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[4] - learnTimeArray[3];
                int first = learnTime - learnTimeArray[3];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
            } else if (learnTimeArray[4] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.xueshi_bottom);
                mCurGradeTv.setText("学士");
                mNextGradeIv.setImageResource(R.drawable.shuoshi_bottom);
                mNextGradeTv.setText("硕士");
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[5] - learnTimeArray[4]) / 60 + "分钟");

            } else if (learnTimeArray[4] < learnTime && learnTime < learnTimeArray[5]) {
                mCurGradeIv.setImageResource(R.drawable.xueshi_bottom);
                mCurGradeTv.setText("学士");
                mNextGradeIv.setImageResource(R.drawable.shuoshi_bottom);
                mNextGradeTv.setText("硕士");
                int minute = (learnTimeArray[5] - learnTime) / 60;
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[5] - learnTimeArray[4];
                int first = learnTime - learnTimeArray[4];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
            } else if (learnTimeArray[5] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.shuoshi_bottom);
                mCurGradeTv.setText("硕士");
                mNextGradeIv.setImageResource(R.drawable.boshi_bottom);
                mNextGradeTv.setText("博士");
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[6] - learnTimeArray[5]) / 60 + "分钟");

            } else if (learnTimeArray[5] < learnTime && learnTime < learnTimeArray[6]) {
                mCurGradeIv.setImageResource(R.drawable.shuoshi_bottom);
                mCurGradeTv.setText("硕士");
                mNextGradeIv.setImageResource(R.drawable.boshi_bottom);
                mNextGradeTv.setText("博士");
                int minute = (learnTimeArray[6] - learnTime) / 60;
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[6] - learnTimeArray[5];
                int first = learnTime - learnTimeArray[5];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);

            } else if (learnTimeArray[6] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.boshi_bottom);
                mCurGradeTv.setText("博士");
                mNextGradeIv.setImageResource(R.drawable.wangzhe_bottom);
                mNextGradeTv.setText("王者");
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[7] - learnTimeArray[6]) / 60 + "分钟");

            } else if (learnTimeArray[6] < learnTime && learnTime < learnTimeArray[7]) {
                mCurGradeIv.setImageResource(R.drawable.boshi_bottom);
                mCurGradeTv.setText("博士");
                mNextGradeIv.setImageResource(R.drawable.wangzhe_bottom);
                mNextGradeTv.setText("王者");
                int minute = (learnTimeArray[7] - learnTime) / 60;
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + minute + "分钟");

                int second = learnTimeArray[7] - learnTimeArray[6];
                int first = learnTime - learnTimeArray[6];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
            } else if (learnTimeArray[7] == learnTime || learnTimeArray[7] > learnTime) {
                mCurGradeIv.setImageResource(R.drawable.boshi_bottom);
                mCurGradeTv.setText("博士");
                mNextGradeIv.setImageResource(R.drawable.wangzhe_bottom);
                mNextGradeTv.setText("王者");
                mNeedTimeToNextGradeTv.setText("您的等级已经达到王者");
            }
        }
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onLazyLoad() {

    }

    @OnClick({R.id.day_tv, R.id.week_tv, R.id.month_tv, R.id.select_day_iv})
    public void onClickTabEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.day_tv:
                    tabPosition = 0;
                    dayTv.setBackgroundResource(R.drawable.shape_sagement_interview_txt_checked_left);
                    dayTv.setTextColor(Color.parseColor("#FFFFFF"));
                    weekTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    weekTv.setTextColor(Color.parseColor("#000000"));
                    monthTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    monthTv.setTextColor(Color.parseColor("#000000"));
                    mGradeLevelAdapter.setNewData(mDataBeanListDay);
                    break;
                case R.id.week_tv:
                    tabPosition = 1;
                    dayTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    dayTv.setTextColor(Color.parseColor("#000000"));
                    weekTv.setBackgroundColor(Color.parseColor("#4087fd"));
                    weekTv.setTextColor(Color.parseColor("#FFFFFF"));
                    monthTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    monthTv.setTextColor(Color.parseColor("#000000"));
                    mGradeLevelAdapter.setNewData(mDataBeanListWeek);
                    break;
                case R.id.month_tv:
                    tabPosition = 2;
                    dayTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    dayTv.setTextColor(Color.parseColor("#000000"));
                    weekTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    weekTv.setTextColor(Color.parseColor("#000000"));
                    monthTv.setBackgroundResource(R.drawable.shape_sagement_interview_txt_checked_right);
                    monthTv.setTextColor(Color.parseColor("#FFFFFF"));
                    mGradeLevelAdapter.setNewData(mDataBeanListMonth);
                    break;
                case R.id.select_day_iv:
                    selectTimeEvent();
                    break;
            }
        }
    }

    private void selectTimeEvent() {
        if (pvTime != null) {
            pvTime.show();
        } else {
            initTimePicker();
        }
    }


    private TimePickerView pvTime;

    private void initTimePicker() {
        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {
                String time = DateUtil.getTime(date2);
                long timeL = DateUtil.string2Millis(time);
                mTimeStr = String.valueOf(timeL/1000);
                mDialog.show();
                getStudyDate();
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        Button tvSubmit = (Button) v.findViewById(R.id.btnSubmit);
                        Button ivCancel = (Button) v.findViewById(R.id.btnCancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(20)
                .setTitleSize(20)
                .setOutSideCancelable(true)
                .isCyclic(true)
                .setTextColorCenter(Color.BLACK)
                .setTitleColor(Color.BLACK)
                .isCenterLabel(false)
                .isDialog(true)
                .build();
        pvTime.setDate(Calendar.getInstance());
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_my_grade_level;
    }

    @Override
    public void onRefresh() {
        getUserInfoById();
    }
}
