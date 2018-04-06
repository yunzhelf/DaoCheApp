package com.yifactory.daocheapp.biz.home_function.home_tab;

import android.os.Bundle;

import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.widget.TitleBar;

public class HomePromoteProfitFragment extends BaseFragment {

    public static HomePromoteProfitFragment newInstance() {
        HomePromoteProfitFragment fragment = new HomePromoteProfitFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_home_promote_profit;
    }

}
