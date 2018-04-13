package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.GetIndexNewsBean;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendHeadLineAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.widget.BaseSwipeRefreshLayout;
import com.yifactory.daocheapp.widget.SDProgressDialog;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRecommendHeadLineActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.head_line_rv)
    RecyclerView recyclerView;
    @BindView(R.id.head_line_rl)
    BaseSwipeRefreshLayout swipeRefreshLayout;

    private HomeRecommendHeadLineAdapter adapter;
    private List<GetSystemInfoBean.DataBean.SysIndexNewsBean> newsList = new ArrayList<>();
    private int page = 0;
    private Dialog mDialog;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("道车头条");
        return true;
    }

    @Override
    protected void addListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        adapter = new HomeRecommendHeadLineAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(newsList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HomeRecommendHeadLineActivity.this,HomeRecommendHeadLineDetailActivity.class);
                intent.putExtra("new",newsList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mDialog = SDDialogUtil.newLoading(this,"请求中");
        getIndexNews();
    }

    private void getIndexNews(){
        if(mDialog != null){
            mDialog.show();
        }
        RxHttpUtils.createApi(ApiService.class)
                .getIndexNews(page,10)
                .compose(Transformer.<GetIndexNewsBean>switchSchedulers())
                .subscribe(new CommonObserver<GetIndexNewsBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        finishRefresh();
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(GetIndexNewsBean sysIndexNewsBean) {
                        finishRefresh();
                        if(sysIndexNewsBean != null && sysIndexNewsBean.getResponseState().equals("1")){
                            newsList = sysIndexNewsBean.getData();
                            adapter.setNewData(newsList);
                        }
                    }
                });
    }

    private void finishRefresh(){
        if(mDialog != null){
            mDialog.cancel();
        }
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        getIndexNews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_recommend_head_line;
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
}
