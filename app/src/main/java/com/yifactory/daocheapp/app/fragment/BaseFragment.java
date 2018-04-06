package com.yifactory.daocheapp.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.widget.TitleBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected ViewGroup mViewGroup;

    protected LinearLayout mContentRootView;

    protected TitleBar mTitleBar;

    /**
     * 贴附的activity
     */
    protected Activity mActivity;

    /**
     * 根view
     */
    protected View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;

    /**
     * 是否加载完成
     * 当执行完onCreateView,View的初始化方法后即为true
     */
    protected boolean mIsPrepare;

    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_base, container, false);

        basicInitialize();

        mUnBinder = ButterKnife.bind(this, mRootView);

        initData(getArguments());

        initView(savedInstanceState);

        mIsPrepare = true;

        onLazyLoad();

        addListener();

        return mRootView;
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
        final int layoutId = setLayoutResourceId();
        if (layoutId <= 0) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        inflater.inflate(layoutId, mContentRootView, true);
    }

    private void buildTitle() {
        final TitleBar bar = mTitleBar;
        if (bar == null)
            return;
        bar.setActivity(mActivity);
        if (!buildTitle(mTitleBar))
            mTitleBar.setVisibility(View.GONE);
    }

    protected abstract boolean buildTitle(TitleBar bar);

    private void basicFindViews() {
        mViewGroup = (ViewGroup) mRootView.findViewById(R.id.rootview_basefragment);// 最外层
        mContentRootView = (LinearLayout) mRootView.findViewById(R.id.content_fragment);// 内容页
        mTitleBar = (TitleBar) mRootView.findViewById(R.id.titleBar);// TitleBar
    }

    /**
     * 初始化数据，接收到的从其他地方传递过来的参数
     */
    protected abstract void initData(Bundle arguments);

    /**
     * 初始化View
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 设置监听事件
     */
    protected abstract void addListener();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
        }
    }

    /**
     * 懒加载，仅当用户可见且view初始化结束后才会执行
     */
    protected abstract void onLazyLoad();

    /**
     * 设置根布局资源id
     */
    protected abstract int setLayoutResourceId();

    public void showToast(String showInfo) {
        Toast.makeText(mActivity, showInfo, Toast.LENGTH_SHORT).show();
    }
    private Toast toast;
    public void showCustomToast(int xmlId, String msg, int duration) {
        if (toast == null) {
            toast = new Toast(mActivity);
        }
        toast.setDuration(duration);
        toast.setGravity(Gravity.BOTTOM, 0, 530);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        LinearLayout toastLayout = (LinearLayout) inflater.inflate(xmlId, null);
        TextView txtToast = (TextView) toastLayout.findViewById(R.id.txt_toast);
        txtToast.setText(msg);
        toast.setView(toastLayout);
        toast.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }
}
