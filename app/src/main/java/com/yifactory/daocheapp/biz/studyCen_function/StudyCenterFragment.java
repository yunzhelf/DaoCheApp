package com.yifactory.daocheapp.biz.studyCen_function;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yifactory.daocheapp.MainActivity;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.GetStudyReocrdBean;
import com.yifactory.daocheapp.bean.GetUserBuyRecordBean;
import com.yifactory.daocheapp.bean.LevelListBean;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.biz.my_function.activity.MyBuyActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyGradeExplainActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyLoginActivity;
import com.yifactory.daocheapp.biz.my_function.activity.MyRegisterActivity;
import com.yifactory.daocheapp.biz.studyCen_function.activity.StudyCenterMyBuyVideoListActivity;
import com.yifactory.daocheapp.biz.studyCen_function.adapter.StudyCenMyReocrdVideoAdapter;
import com.yifactory.daocheapp.biz.video_function.VideoFragment;
import com.yifactory.daocheapp.event.TiXianSuccessMsg;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.CircleImageView;
import com.yifactory.daocheapp.widget.MaxRecyclerView;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StudyCenterFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.top_layout)
    AutoLinearLayout topLayout;
    @BindView(R.id.bottom_layout)
    AutoLinearLayout bottomLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.video_rv)
    MaxRecyclerView mRv_video;
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
    @BindView(R.id.yunying_tv)
    TextView mYunyingTv;
    @BindView(R.id.xiaoshou_tv)
    TextView mXiaoshouTv;
    @BindView(R.id.shichang_tv)
    TextView mShichangTv;
    @BindView(R.id.yansheng_tv)
    TextView mYanshengTv;
    @BindView(R.id.shouhou_tv)
    TextView mShouhouTv;
    @BindView(R.id.renshi_tv)
    TextView mRenshiTv;
    @BindView(R.id.kefu_tv)
    TextView mKefuTv;
    @BindView(R.id.caiwu_tv)
    TextView mCaiwuTv;

    @BindView(R.id.tab_layout)
    AutoLinearLayout mAll_tab;
    @BindView(R.id.study_record_tv)
    TextView mTv_studyRecord;
    @BindView(R.id.my_buy_tv)
    TextView mTv_myBuy;
    @BindView(R.id.video_type_layout)
    AutoLinearLayout mAll_videoType;

    private Dialog mDialog;

    private int mLevalTime = 0;
    private String mUId = "";
    private StudyCenMyReocrdVideoAdapter mVideoAdapter;

    public static StudyCenterFragment newInstance() {
        StudyCenterFragment fragment = new StudyCenterFragment();
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
        bar.setTitleText("学习中心");
        return true;
    }

    @Override
    protected void initData(Bundle arguments) {
        EventBus.getDefault().register(this);
        boolean isLine = new SPreferenceUtil(mActivity, "config.sp").getIsLine();
        if (isLine) {
            LoginBean.DataBean userInfoBean = UserInfoUtil.getUserInfoBean(mActivity);
            topLayout.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.GONE);
            if (userInfoBean != null) {
                mUId = userInfoBean.getUId();
            }
            if (mDialog == null) {
                mDialog = SDDialogUtil.newLoading(mActivity, "请求中...");
            }
            mDialog.show();
            getUserInfoById();
            getUserBuyRecord();
            getStudyReocrd();
        } else {
            bottomLayout.setVisibility(View.VISIBLE);
            topLayout.setVisibility(View.GONE);
        }
    }

    private void getStudyReocrd() {
        RxHttpUtils.createApi(ApiService.class)
                .getStudyReocrd(mUId)
                .compose(Transformer.<GetStudyReocrdBean>switchSchedulers())
                .subscribe(new CommonObserver<GetStudyReocrdBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetStudyReocrdBean getUserBuyRecordBean) {
                        if (getUserBuyRecordBean.getResponseState().equals("1")) {
                            List<PlayVideoBean.DataBean.HotBean> dataBeanList = getUserBuyRecordBean.getData();
                            mVideoAdapter.setNewData(dataBeanList);
                        } else {
                            showToast(getUserBuyRecordBean.getMsg());
                        }

                    }
                });
    }

    private void getUserBuyRecord() {
        RxHttpUtils.createApi(ApiService.class)
                .getUserBuyRecord(mUId)
                .compose(Transformer.<GetUserBuyRecordBean>switchSchedulers())
                .subscribe(new CommonObserver<GetUserBuyRecordBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetUserBuyRecordBean getUserBuyRecordBean) {
                        if (getUserBuyRecordBean.getResponseState().equals("1")) {
                            List<GetUserBuyRecordBean.DataBean> dataBeanList = getUserBuyRecordBean.getData();
                            GetUserBuyRecordBean.DataBean dataBean = dataBeanList.get(0);
                            int yyglCount = dataBean.getYyglCount();
                            mYunyingTv.setText(String.valueOf(yyglCount));
                            int xsglCount = dataBean.getXsglCount();
                            mXiaoshouTv.setText(String.valueOf(xsglCount));
                            int scyxCount = dataBean.getScyxCount();
                            mShichangTv.setText(String.valueOf(scyxCount));
                            int ysywCount = dataBean.getYsywCount();
                            mYanshengTv.setText(String.valueOf(ysywCount));
                            int shglCount = dataBean.getShglCount();
                            mShouhouTv.setText(String.valueOf(shglCount));
                            int rsxzCount = dataBean.getRsxzCount();
                            mRenshiTv.setText(String.valueOf(rsxzCount));
                            int kfglCount = dataBean.getKfglCount();
                            mKefuTv.setText(String.valueOf(kfglCount));
                            int cwglCount = dataBean.getCwglCount();
                            mCaiwuTv.setText(String.valueOf(cwglCount));
                        } else {
                            showToast(getUserBuyRecordBean.getMsg());
                        }

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getUserInfoById() {
        RxHttpUtils.createApi(ApiService.class)
                .getUserById(mUId, "")
                .compose(Transformer.<UserBean>switchSchedulers())
                .subscribe(new CommonObserver<UserBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        closeDialog();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(UserBean userBean) {
                        closeDialog();
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
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + learnTimeArray[1]/60 + "分钟");

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
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[2]-learnTimeArray[1])/60 + "分钟");

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
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[3]-learnTimeArray[2])/60 + "分钟");

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
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[4]-learnTimeArray[3])/60 + "分钟");

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
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[5]-learnTimeArray[4])/60 + "分钟");

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
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[6]-learnTimeArray[5])/60 + "分钟");

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
                mNeedTimeToNextGradeTv.setText("距离下一等级还差" + (learnTimeArray[7]-learnTimeArray[6])/60 + "分钟");

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
    protected void initView(Bundle savedInstanceState) {
        initVideoRv();
    }

    private void initVideoRv() {
        mVideoAdapter = new StudyCenMyReocrdVideoAdapter();
        mRv_video.setNestedScrollingEnabled(false);
        mRv_video.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv_video.setAdapter(mVideoAdapter);
        mVideoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.putExtra("videoInfo",mVideoAdapter.getData().get(i));
                intent.putExtra("play",true);
                startActivity(intent);
                getChildFragmentManager().beginTransaction().hide(StudyCenterFragment.this);
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

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_study_center;
    }

    @OnClick({R.id.study_record_tv, R.id.my_buy_tv, R.id.yunying_layout, R.id.xiaoshou_layout, R.id.shichang_layout,
            R.id.yansheng_layout, R.id.shouhou_layout, R.id.renshi_layout, R.id.kefu_layout, R.id.caiwu_layout, R.id.login_tv, R.id.register_tv})
    public void onClickTabEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.study_record_tv:
                    mRv_video.setVisibility(View.VISIBLE);
                    mAll_videoType.setVisibility(View.GONE);
                    mTv_studyRecord.setBackgroundResource(R.drawable.bg_study_center_tab_one);
                    mTv_studyRecord.setTextColor(Color.parseColor("#FFFFFF"));
                    mTv_myBuy.setTextColor(Color.parseColor("#000000"));
                    mTv_myBuy.setBackgroundResource(R.drawable.bg_study_center_tab_two_bg);
                    break;
                case R.id.my_buy_tv:
                    mRv_video.setVisibility(View.GONE);
                    mAll_videoType.setVisibility(View.VISIBLE);
                    mTv_studyRecord.setBackgroundResource(R.drawable.bg_study_center_tab_one_bg);
                    mTv_studyRecord.setTextColor(Color.parseColor("#000000"));
                    mTv_myBuy.setTextColor(Color.parseColor("#FFFFFF"));
                    mTv_myBuy.setBackgroundResource(R.drawable.bg_study_center_tab_two);
                    break;
                case R.id.yunying_layout:
                    skipVideoTypeActivity("运营管理");
                    break;
                case R.id.xiaoshou_layout:
                    skipVideoTypeActivity("销售管理");
                    break;
                case R.id.shichang_layout:
                    skipVideoTypeActivity("市场营销");
                    break;
                case R.id.yansheng_layout:
                    skipVideoTypeActivity("衍生业务");
                    break;
                case R.id.shouhou_layout:
                    skipVideoTypeActivity("售后管理");
                    break;
                case R.id.renshi_layout:
                    skipVideoTypeActivity("人事行政");
                    break;
                case R.id.kefu_layout:
                    skipVideoTypeActivity("客户维护");
                    break;
                case R.id.caiwu_layout:
                    skipVideoTypeActivity("财务管理");
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

    private void skipVideoTypeActivity(String titleStr) {
        Intent intent = new Intent(mActivity, StudyCenterMyBuyVideoListActivity.class);
        intent.putExtra("title", titleStr);
        startActivity(intent);
    }

    @OnClick({R.id.cur_grade_iv, R.id.next_grade_iv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.cur_grade_iv:
                case R.id.next_grade_iv:
                    Intent gradeExplainIntent = new Intent(mActivity, MyGradeExplainActivity.class);
                    startActivity(gradeExplainIntent);
                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        getUserInfoById();
        getUserBuyRecord();
        getStudyReocrd();
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
}

