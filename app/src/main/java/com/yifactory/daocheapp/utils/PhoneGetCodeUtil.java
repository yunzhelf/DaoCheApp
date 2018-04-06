package com.yifactory.daocheapp.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

public class PhoneGetCodeUtil {

    private TextView mTextView;
    private TimeCount mTimeCount;

    public PhoneGetCodeUtil(TextView textView) {
        mTextView = textView;
        mTimeCount = new TimeCount(60000, 1000);
    }

    public void onStart() {
        this.mTimeCount.start();
    }

    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mTextView.setClickable(false);
            mTextView.setText(l / 1000 + "(s)");
        }

        @Override
        public void onFinish() {
            mTextView.setClickable(true);
            mTextView.setText("获取验证码");
        }
    }
}
