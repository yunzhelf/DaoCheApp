package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.AppCtx;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.widget.TitleBar;

import butterknife.BindView;

public class HomeRecommendPersonalIntroduceFragment extends BaseFragment {
    @BindView(R.id.photo_iv)
    ImageView photo;
    @BindView(R.id.creator_tv)
    TextView creator;
    @BindView(R.id.jian_jie)
    TextView jianJie;
    @BindView(R.id.chengjiu)
    TextView chengJiu;
    @BindView(R.id.jing_li)
    TextView jingLi;
    private String rId;

    public static HomeRecommendPersonalIntroduceFragment newInstance(String rId) {
        HomeRecommendPersonalIntroduceFragment fragment = new HomeRecommendPersonalIntroduceFragment();
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

    public void setData(PlayVideoBean.DataBean.ResourceBean.CreatorBean videoBean) {
        Glide.with(AppCtx.getC()).load(videoBean.getTeacherHeadImg()).into(photo);
        creator.setText(videoBean.getTeacherName() + " | " + videoBean.getTeacherGoodAtArea());
        jianJie.setText(videoBean.getTeacherSelfAssessment());
        chengJiu.setText(videoBean.getTeacherOldHonor());
        jingLi.setText(videoBean.getTeacherJobUbdergo());
    }
    @Override
    protected void addListener() {

    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_home_recommend_personal_introduce;
    }

}
