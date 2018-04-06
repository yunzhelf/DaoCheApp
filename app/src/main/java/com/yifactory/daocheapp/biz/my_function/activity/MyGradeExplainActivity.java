package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.LevelListBean;
import com.yifactory.daocheapp.bean.UserBean;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyGradeExplainActivity extends BaseActivity {

    @BindView(R.id.xiaobai_time)
    TextView mXiaobaiTime;
    @BindView(R.id.xiaoxue_time)
    TextView mXiaoxueTime;
    @BindView(R.id.zhongxue_time)
    TextView mZhongxueTime;
    @BindView(R.id.gaozhong_tv)
    TextView mGaozhongTv;
    @BindView(R.id.xueshi_time)
    TextView mXueshiTime;
    @BindView(R.id.shuoshi_time)
    TextView mShuoshiTime;
    @BindView(R.id.boshi_time)
    TextView mBoshiTime;
    @BindView(R.id.wangzhe_time)
    TextView mWangzheTime;
    @BindView(R.id.left_tv)
    TextView mLeftTv;
    @BindView(R.id.middle1_tv)
    TextView mMiddle1Tv;
    @BindView(R.id.middle2_tv)
    TextView mMiddle2Tv;
    @BindView(R.id.middle3_tv)
    TextView mMiddle3Tv;
    @BindView(R.id.middle4_tv)
    TextView mMiddle4Tv;
    @BindView(R.id.middle5_tv)
    TextView mMiddle5Tv;
    @BindView(R.id.right_tv)
    TextView mRightTv;
    @BindView(R.id.xiaobai_progressbar)
    ProgressBar mXiaobaiProgressbar;
    @BindView(R.id.xiaoxue_progressbar)
    ProgressBar mXiaoxueProgressbar;
    @BindView(R.id.zhongxue_progressbar)
    ProgressBar mZhongxueProgressbar;
    @BindView(R.id.gaozhong_progressbar)
    ProgressBar mGaozhongProgressbar;
    @BindView(R.id.xueshi_progressbar)
    ProgressBar mXueshiProgressbar;
    @BindView(R.id.shuoshi_progressbar)
    ProgressBar mShuoshiProgressbar;
    @BindView(R.id.boshi_progressbar)
    ProgressBar mBoshiProgressbar;
    private Dialog mDialog;
    private int mLevalTime = 0;
    private List<TextView> levelTimeTvList = new ArrayList<>();
    @BindView(R.id.watch_time)
    TextView mTv_watchTime;
    @BindView(R.id.upgrade_time)
    TextView mTv_upgradeTime;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("等级说明");
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initLevelUi();
        getUserInfoById();
    }

    private void initLevelUi() {
        levelTimeTvList.add(mXiaobaiTime);
        levelTimeTvList.add(mXiaoxueTime);
        levelTimeTvList.add(mZhongxueTime);
        levelTimeTvList.add(mGaozhongTv);
        levelTimeTvList.add(mXueshiTime);
        levelTimeTvList.add(mShuoshiTime);
        levelTimeTvList.add(mBoshiTime);
        levelTimeTvList.add(mWangzheTime);
    }

    private void getUserInfoById() {
        if (mDialog == null) {
            mDialog = SDDialogUtil.newLoading(this, "请求中...");
        }
        mDialog.show();
        String uId = UserInfoUtil.getUserInfoBean(this).getUId();
        RxHttpUtils.createApi(ApiService.class)
                .getUserById(uId, "")
                .compose(Transformer.<UserBean>switchSchedulers())
                .subscribe(new CommonObserver<UserBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        closeDialog();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(UserBean userBean) {
                        if (userBean.getResponseState().equals("1")) {
                            UserBean.DataBean dataBean = userBean.getData().get(0);
                            mLevalTime = dataBean.getLevalTime();
                            mTv_watchTime.setText(dataBean.getLearnTime() / 60 + "");
                            getLevelList();
                        } else {
                            closeDialog();
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
                TextView textView = levelTimeTvList.get(i);
                int levelTime = dataBean.getLevelTime();
                learnTimeArray[i] = levelTime;
                int minute = levelTime / 60;
                textView.setText(minute + "分钟");
            }
        }
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_grade_explain;
    }

    @OnClick({R.id.naviFrameLeft})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
            }
        }
    }

    private void judgeWhichStage(int learnTime) {
        if (learnTimeArray.length > 0) {
            if (learnTimeArray[0] == learnTime) {
                mTv_upgradeTime.setText("距离下一级还有" + learnTimeArray[1] / 60 + "分钟");

            } else if (learnTimeArray[0] < learnTime && learnTime < learnTimeArray[1]) {
                mLeftTv.setVisibility(View.VISIBLE);
                int minute = (learnTimeArray[1] - learnTime) / 60;
                mLeftTv.setText("还有" + minute + "分钟可升级");
                mTv_upgradeTime.setText("距离下一级还有" + minute + "分钟");

                int second = learnTimeArray[1] - learnTimeArray[0];
                int first = learnTime - learnTimeArray[0];
                int progress = first * 100 / second;
                mXiaobaiProgressbar.setProgress(progress);
                setPosWay1(mXiaobaiProgressbar, mLeftTv);
            } else if (learnTimeArray[1] == learnTime) {
                mTv_upgradeTime.setText("距离下一级还有" + (learnTimeArray[2] - learnTimeArray[1]) / 60 + "分钟");

            } else if (learnTimeArray[1] < learnTime && learnTime < learnTimeArray[2]) {
                mMiddle1Tv.setVisibility(View.VISIBLE);
                int minute = (learnTimeArray[2] - learnTime) / 60;
                mMiddle1Tv.setText("还有" + minute + "分钟可升级");
                mTv_upgradeTime.setText("距离下一级还有" + minute + "分钟");

                int second = learnTimeArray[2] - learnTimeArray[1];
                int first = learnTime - learnTimeArray[1];
                int progress = first * 100 / second;
                mXiaobaiProgressbar.setProgress(100);
                mXiaoxueProgressbar.setProgress(progress);
                setPosWay1(mXiaoxueProgressbar, mMiddle1Tv);
            } else if (learnTimeArray[2] == learnTime) {
                mTv_upgradeTime.setText("距离下一级还有" + (learnTimeArray[3] - learnTimeArray[2]) / 60 + "分钟");

            } else if (learnTimeArray[2] < learnTime && learnTime < learnTimeArray[3]) {
                mMiddle2Tv.setVisibility(View.VISIBLE);
                int minute = (learnTimeArray[3] - learnTime) / 60;
                mMiddle2Tv.setText("还有" + minute + "分钟可升级");
                mTv_upgradeTime.setText("距离下一级还有" + minute + "分钟");

                int second = learnTimeArray[3] - learnTimeArray[2];
                int first = learnTime - learnTimeArray[2];
                int progress = first * 100 / second;
                mXiaobaiProgressbar.setProgress(100);
                mXiaoxueProgressbar.setProgress(100);
                mZhongxueProgressbar.setProgress(progress);
                setPosWay1(mZhongxueProgressbar, mMiddle2Tv);
            } else if (learnTimeArray[3] == learnTime) {
                mTv_upgradeTime.setText("距离下一级还有" + (learnTimeArray[4] - learnTimeArray[3]) / 60 + "分钟");

            } else if (learnTimeArray[3] < learnTime && learnTime < learnTimeArray[4]) {
                mMiddle3Tv.setVisibility(View.VISIBLE);
                int minute = (learnTimeArray[4] - learnTime) / 60;
                mMiddle3Tv.setText("还有" + minute + "分钟可升级");
                mTv_upgradeTime.setText("距离下一级还有" + minute + "分钟");

                int second = learnTimeArray[4] - learnTimeArray[3];
                int first = learnTime - learnTimeArray[3];
                int progress = first * 100 / second;
                mXiaobaiProgressbar.setProgress(100);
                mXiaoxueProgressbar.setProgress(100);
                mZhongxueProgressbar.setProgress(100);
                mGaozhongProgressbar.setProgress(progress);
                setPosWay1(mGaozhongProgressbar, mMiddle3Tv);
            } else if (learnTimeArray[4] == learnTime) {
                mTv_upgradeTime.setText("距离下一级还有" + (learnTimeArray[5] - learnTimeArray[4]) / 60 + "分钟");

            } else if (learnTimeArray[4] < learnTime && learnTime < learnTimeArray[5]) {
                mMiddle4Tv.setVisibility(View.VISIBLE);
                int minute = (learnTimeArray[5] - learnTime) / 60;
                mMiddle4Tv.setText("还有" + minute + "分钟可升级");
                mTv_upgradeTime.setText("距离下一级还有" + minute + "分钟");

                int second = learnTimeArray[5] - learnTimeArray[4];
                int first = learnTime - learnTimeArray[4];
                int progress = first * 100 / second;
                mXiaobaiProgressbar.setProgress(100);
                mXiaoxueProgressbar.setProgress(100);
                mZhongxueProgressbar.setProgress(100);
                mGaozhongProgressbar.setProgress(100);
                mXueshiProgressbar.setProgress(progress);
                setPosWay1(mXueshiProgressbar, mMiddle4Tv);
            } else if (learnTimeArray[5] == learnTime) {
                mTv_upgradeTime.setText("距离下一级还有" + (learnTimeArray[6] - learnTimeArray[5]) / 60 + "分钟");

            } else if (learnTimeArray[5] < learnTime && learnTime < learnTimeArray[6]) {
                mMiddle5Tv.setVisibility(View.VISIBLE);
                int minute = (learnTimeArray[6] - learnTime) / 60;
                mMiddle5Tv.setText("还有" + minute + "分钟可升级");
                mTv_upgradeTime.setText("距离下一级还有" + minute + "分钟");

                int second = learnTimeArray[6] - learnTimeArray[5];
                int first = learnTime - learnTimeArray[5];
                int progress = first * 100 / second;
                mXiaobaiProgressbar.setProgress(100);
                mXiaoxueProgressbar.setProgress(100);
                mZhongxueProgressbar.setProgress(100);
                mGaozhongProgressbar.setProgress(100);
                mXueshiProgressbar.setProgress(100);
                mShuoshiProgressbar.setProgress(progress);
                setPosWay1(mShuoshiProgressbar, mMiddle5Tv);
            } else if (learnTimeArray[6] == learnTime) {
                mTv_upgradeTime.setText("距离下一级还有" + (learnTimeArray[7] - learnTimeArray[6]) / 60 + "分钟");

            } else if (learnTimeArray[6] < learnTime && learnTime < learnTimeArray[7]) {
                mRightTv.setVisibility(View.VISIBLE);
                int minute = (learnTimeArray[7] - learnTime) / 60;
                mRightTv.setText("还有" + minute + "分钟可升级");
                mTv_upgradeTime.setText("距离下一级还有" + minute + "分钟");

                int second = learnTimeArray[7] - learnTimeArray[6];
                int first = learnTime - learnTimeArray[6];
                int progress = first * 100 / second;
                mXiaobaiProgressbar.setProgress(100);
                mXiaoxueProgressbar.setProgress(100);
                mZhongxueProgressbar.setProgress(100);
                mGaozhongProgressbar.setProgress(100);
                mXueshiProgressbar.setProgress(100);
                mShuoshiProgressbar.setProgress(100);
                mBoshiProgressbar.setProgress(progress);
                setPosWay1(mBoshiProgressbar, mRightTv);
            } else if (learnTimeArray[7] == learnTime) {
                mTv_upgradeTime.setText("已达到王者级别");
            }
        }
    }

    private void setPosWay1(final ProgressBar progressBar, final TextView textView) {
        textView.post(new Runnable() {
            @Override
            public void run() {
                setPos(progressBar, textView);
            }
        });
    }

    public void setPos(ProgressBar progressBar, TextView textView) {
        int pro = progressBar.getProgress();
        int width = progressBar.getWidth();
        int move = width * pro / 100;
        textView.setTranslationX(move);
    }
}
