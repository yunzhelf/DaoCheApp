package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.fragment;

import android.os.Bundle;

import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.widget.ExpandableTextView;
import com.yifactory.daocheapp.widget.TitleBar;

import butterknife.BindView;

public class HomeRecommendContentIntroduceFragment extends BaseFragment {

    @BindView(R.id.video_introduce_etv)
    ExpandableTextView mEtv_videoIntroduce;
    @BindView(R.id.video_content_etv)
    ExpandableTextView mEtv_videoContent;
    @BindView(R.id.video_fit_crowd_etv)
    ExpandableTextView mEtv_fitCrowd;
    @BindView(R.id.video_get_earnings_etv)
    ExpandableTextView mEtv_getEarnings;
    private String rId;

    public static HomeRecommendContentIntroduceFragment newInstance(String rId) {
        HomeRecommendContentIntroduceFragment fragment = new HomeRecommendContentIntroduceFragment();
        Bundle args = new Bundle();
        args.putString("rId", rId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.rId = getArguments().getString("rId");
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
        return R.layout.fragment_home_recommend_content_introduce;
    }

    public void setData(PlayVideoBean.DataBean.ResourceBean videoBean) {
        mEtv_videoIntroduce.setText(videoBean.getVideoDetail());
        mEtv_videoContent.setText(videoBean.getVideoContent());
        mEtv_fitCrowd.setText(videoBean.getSuitPerson());
        mEtv_getEarnings.setText(videoBean.getGoldCount() + "");
    }
}
