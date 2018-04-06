package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.GetCategoryListBean;
import com.yifactory.daocheapp.biz.my_function.adapter.MyVideoTypeListAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.widget.SDProgressDialog;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyVideoTypeListActivity extends BaseActivity {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.video_type_rv)
    RecyclerView videoTypeRv;

    private MyVideoTypeListAdapter videoTypeListAdapter;
    private List<GetCategoryListBean.DataBean> dataArray = new ArrayList<>();
    private Dialog mDialog;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("视频类型");
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
    }

    @Override
    protected void initView() {
        videoTypeListAdapter = new MyVideoTypeListAdapter(R.layout.item_video_type_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        videoTypeRv.setLayoutManager(linearLayoutManager);
        videoTypeRv.setAdapter(videoTypeListAdapter);
        videoTypeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(MyVideoTypeListActivity.this,MyVideoTypeSecondListActivity.class);
                i.putExtra("videoType",dataArray.get(position));
                startActivity(i);
            }
        });

        getVideoType();
    }

    private void getVideoType(){
        mDialog = SDDialogUtil.newLoading(this,"正在加载，请稍后");
        mDialog.show();
        RxHttpUtils.createApi(ApiService.class)
                .getCategoryList()
                .compose(Transformer.<GetCategoryListBean>switchSchedulers())
                .subscribe(new CommonObserver<GetCategoryListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        if(mDialog.isShowing()){
                            mDialog.cancel();
                        }
                    }

                    @Override
                    protected void onSuccess(GetCategoryListBean getCategoryListBean) {
                        if(getCategoryListBean != null && getCategoryListBean.getResponseState().equals("1")){
                            dataArray = getCategoryListBean.getData();
                            videoTypeListAdapter.setNewData(dataArray);
                            if(mDialog.isShowing()){
                                mDialog.cancel();
                            }
                        }
                    }
                });
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_video_type;
    }
}
