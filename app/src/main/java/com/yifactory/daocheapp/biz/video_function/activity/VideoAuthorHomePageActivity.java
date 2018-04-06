package com.yifactory.daocheapp.biz.video_function.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoAuthorHomePageActivity extends BaseActivity {
    @BindView(R.id.author_head_iv)
    ImageView headerIv;
    @BindView(R.id.author_name_tv)
    TextView authorNameTv;
    @BindView(R.id.author_introduction_tv)
    TextView authorIntroductionTv;
    @BindView(R.id.author_detail_tv)
    TextView authorDetailTv;

    private PlayVideoBean.DataBean.ResourceBean.CreatorBean author;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
        initViewValues();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().init();
        Intent i = getIntent();
        if(i != null){
            author = i.getParcelableExtra("author");
        }
    }

    private void initViewValues(){
        com.yifactory.glide.GlideImageLoader
                .create(headerIv)
                .load(author.getHeadImg(),new RequestOptions().skipMemoryCache(true));
        authorNameTv.setText(author.getUserName());
        authorIntroductionTv.setText(author.getUserLeval());
        authorDetailTv.setText(author.getTeacherSelfAssessment());
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_author_home_page;
    }

    @OnClick({R.id.back_iv})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.back_iv:
                    finish();
                    break;
            }
        }
    }
}
