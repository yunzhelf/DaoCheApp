package com.yifactory.daocheapp.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.retrofit.RxHttpUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnBinder;

    protected ViewGroup mRootView;

    protected AutoRelativeLayout mContentRootView;

    protected TitleBar mTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        basicInitialize();
        mUnBinder = ButterKnife.bind(this);
        initData(savedInstanceState);
        initView();
        addListener();
    }

    private void basicInitialize() {
        basicFindViews();
        basicSetting();
    }

    private void basicSetting() {
        buildTitle();
        addContent();
    }

    private void addContent() {
        final int layoutId = getLayoutId();
        if (layoutId <= 0) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(layoutId, mContentRootView, true);
    }

    private void buildTitle() {
        final TitleBar bar = mTitleBar;
        if (bar == null)
            return;
        bar.setActivity(this);
        if (!buildTitle(mTitleBar))
            mTitleBar.setVisibility(View.GONE);
    }

    protected abstract boolean buildTitle(TitleBar bar);

    private void basicFindViews() {
        mRootView = (ViewGroup) findViewById(R.id.rootview_baseactivity);
        mContentRootView = (AutoRelativeLayout) findViewById(R.id.content);
        mTitleBar = (TitleBar) findViewById(R.id.titleBar);// TitleBar
    }

    protected abstract void addListener();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    public void showToast(String showInfo) {
        Toast.makeText(this, showInfo, Toast.LENGTH_SHORT).show();
    }
    public void showLongToast(String showInfo) {
        Toast.makeText(this, showInfo, Toast.LENGTH_LONG).show();
    }
    private Toast toast;
    public void showCustomToast(int xmlId, String msg, int duration) {
        if (toast == null) {
            toast = new Toast(this);
        }
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflater = this.getLayoutInflater();
        LinearLayout toastLayout = (LinearLayout) inflater.inflate(xmlId, null);
        TextView txtToast = (TextView) toastLayout.findViewById(R.id.txt_toast);
        txtToast.setText(msg);
        toast.setView(toastLayout);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        ImmersionBar.with(this).destroy();
//        RxHttpUtils.cancelAllRequest();
    }
}
