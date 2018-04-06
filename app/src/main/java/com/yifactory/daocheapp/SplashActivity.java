package com.yifactory.daocheapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.biz.my_function.activity.MyLoginActivity;
import com.yifactory.daocheapp.utils.SPreferenceUtil;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().init();
        judgeIsLoginApp();
    }

    private void judgeIsLoginApp() {
        sendDelayedEvent(1);
        /*if (new SPreferenceUtil(this, "config.sp").getIsLine()) {
        } else {
            sendDelayedEvent(2);
        }*/
    }

    private static final int SKIP_ACTIVITY = 1;

    private void sendDelayedEvent(int mark) {
        Message msg = Message.obtain();
        msg.arg1 = mark;
        msg.what = SKIP_ACTIVITY;
        new MyHandler(SplashActivity.this).sendMessageDelayed(msg, 1000);
    }

    /**
     * 静态内部类Handler，防止内存泄漏
     */
    private static class MyHandler extends Handler {

        private final WeakReference<SplashActivity> mGuideActivityWeakReference;

        private MyHandler(SplashActivity guideActivity) {
            this.mGuideActivityWeakReference = new WeakReference<>(guideActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            SplashActivity guideActivity = mGuideActivityWeakReference.get();
            if (guideActivity != null) {
                guideActivity.onReceiveMsg(msg);
            }
        }
    }

    private void onReceiveMsg(Message msg) {
        switch (msg.what) {
            case SKIP_ACTIVITY:
                Intent intent = new Intent();
                if (msg.arg1 == 1) {
                    intent.setClass(this, MainActivity.class);
                } else if (msg.arg1 == 2) {
                    intent.setClass(this, MyLoginActivity.class);
                }
                startActivity(intent);
                finish();
                break;
        }
    }
}
