package com.yifactory.daocheapp.biz.my_function.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.GetLevalDataBean;

public class MyStudyGradeLevelAdapter extends BaseQuickAdapter<GetLevalDataBean.DataBean.DayLevalDataBean, BaseViewHolder> {

    private int[] learnTimeArray = new int[8];

    public MyStudyGradeLevelAdapter() {
        super(R.layout.item_my_study_grade_level);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetLevalDataBean.DataBean.DayLevalDataBean item) {
        ProgressBar progressBar = helper.getView(R.id.progressbar);
        TextView dateTv = helper.getView(R.id.date_tv);
        ImageView iconIv1 = helper.getView(R.id.icon_1);
        ImageView iconIv2 = helper.getView(R.id.icon_2);
        ImageView personIv = helper.getView(R.id.person_iv);

        String createTime = item.createTime;
        if (!TextUtils.isEmpty(createTime)) {
            String[] dateArray = createTime.split("-");
            dateTv.setText(dateArray[1] + "月" + dateArray[2] + "日");
        }

        int showTime = item.showTime;
        if (learnTimeArray.length > 0) {
            judgeWhichStage(showTime, iconIv1, iconIv2, progressBar, personIv);
        }
    }

    public void setLearnTimeArray(int[] learnTimeArray) {
        this.learnTimeArray = learnTimeArray;
        notifyDataSetChanged();
    }

    private void judgeWhichStage(int learnTime, ImageView mCurGradeIv, ImageView mNextGradeIv, ProgressBar mProgressBar, ImageView personIv) {
        if (learnTimeArray.length > 0) {
            if (learnTimeArray[0] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.xiaobai_bottom);
                mNextGradeIv.setImageResource(R.drawable.xiaoxie_bottom);

            } else if (learnTimeArray[0] < learnTime && learnTime < learnTimeArray[1]) {
                mCurGradeIv.setImageResource(R.drawable.xiaobai_bottom);
                mNextGradeIv.setImageResource(R.drawable.xiaoxie_bottom);

                int second = learnTimeArray[1] - learnTimeArray[0];
                int first = learnTime - learnTimeArray[0];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
                setPosWay(mProgressBar, personIv);

            } else if (learnTimeArray[1] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.xiaoxie_bottom);
                mNextGradeIv.setImageResource(R.drawable.zhongxue_bottom);

            } else if (learnTimeArray[1] < learnTime && learnTime < learnTimeArray[2]) {
                mCurGradeIv.setImageResource(R.drawable.xiaoxie_bottom);
                mNextGradeIv.setImageResource(R.drawable.zhongxue_bottom);

                int second = learnTimeArray[2] - learnTimeArray[1];
                int first = learnTime - learnTimeArray[1];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
                setPosWay(mProgressBar, personIv);

            } else if (learnTimeArray[2] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.zhongxue_bottom);
                mNextGradeIv.setImageResource(R.drawable.gaozhong_bottom);

            } else if (learnTimeArray[2] < learnTime && learnTime < learnTimeArray[3]) {
                mCurGradeIv.setImageResource(R.drawable.zhongxue_bottom);
                mNextGradeIv.setImageResource(R.drawable.gaozhong_bottom);

                int second = learnTimeArray[3] - learnTimeArray[2];
                int first = learnTime - learnTimeArray[2];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
                setPosWay(mProgressBar, personIv);

            } else if (learnTimeArray[3] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.gaozhong_bottom);
                mNextGradeIv.setImageResource(R.drawable.xueshi_bottom);

            } else if (learnTimeArray[3] < learnTime && learnTime < learnTimeArray[4]) {
                mCurGradeIv.setImageResource(R.drawable.gaozhong_bottom);
                mNextGradeIv.setImageResource(R.drawable.xueshi_bottom);

                int second = learnTimeArray[4] - learnTimeArray[3];
                int first = learnTime - learnTimeArray[3];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
                setPosWay(mProgressBar, personIv);

            } else if (learnTimeArray[4] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.xueshi_bottom);
                mNextGradeIv.setImageResource(R.drawable.shuoshi_bottom);

            } else if (learnTimeArray[4] < learnTime && learnTime < learnTimeArray[5]) {
                mCurGradeIv.setImageResource(R.drawable.xueshi_bottom);
                mNextGradeIv.setImageResource(R.drawable.shuoshi_bottom);

                int second = learnTimeArray[5] - learnTimeArray[4];
                int first = learnTime - learnTimeArray[4];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
                setPosWay(mProgressBar, personIv);

            } else if (learnTimeArray[5] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.shuoshi_bottom);
                mNextGradeIv.setImageResource(R.drawable.boshi_bottom);

            } else if (learnTimeArray[5] < learnTime && learnTime < learnTimeArray[6]) {
                mCurGradeIv.setImageResource(R.drawable.shuoshi_bottom);
                mNextGradeIv.setImageResource(R.drawable.boshi_bottom);

                int second = learnTimeArray[6] - learnTimeArray[5];
                int first = learnTime - learnTimeArray[5];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
                setPosWay(mProgressBar, personIv);

            } else if (learnTimeArray[6] == learnTime) {
                mCurGradeIv.setImageResource(R.drawable.boshi_bottom);
                mNextGradeIv.setImageResource(R.drawable.wangzhe_bottom);

            } else if (learnTimeArray[6] < learnTime && learnTime < learnTimeArray[7]) {
                mCurGradeIv.setImageResource(R.drawable.boshi_bottom);
                mNextGradeIv.setImageResource(R.drawable.wangzhe_bottom);

                int second = learnTimeArray[7] - learnTimeArray[6];
                int first = learnTime - learnTimeArray[6];
                int progress = first * 100 / second;
                mProgressBar.setProgress(progress);
                setPosWay(mProgressBar, personIv);

            } else if (learnTimeArray[7] == learnTime || learnTimeArray[7] > learnTime) {
                mCurGradeIv.setImageResource(R.drawable.boshi_bottom);
                mNextGradeIv.setImageResource(R.drawable.wangzhe_bottom);
                personIv.setVisibility(View.GONE);
            }
        }
    }

    private void setPosWay(final ProgressBar progressBar, final ImageView textView) {
        textView.post(new Runnable() {
            @Override
            public void run() {
                setPos(progressBar, textView);
            }
        });
    }

    private void setPos(ProgressBar progressBar, ImageView textView) {
        int pro = progressBar.getProgress();
        int width = progressBar.getWidth();
        int move = width * pro / 100;
        textView.setTranslationX(move);
    }
}
