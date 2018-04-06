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
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.GetCategoryListBean;
import com.yifactory.daocheapp.biz.my_function.adapter.MyVideoTypeListAdapter;
import com.yifactory.daocheapp.biz.my_function.adapter.MyVideoTypeSecondListAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyVideoTypeSecondListActivity extends BaseActivity {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.video_type_rv)
    RecyclerView videoTypeRv;

    private MyVideoTypeSecondListAdapter videoTypeListAdapter;
    private List<GetCategoryListBean.DataBean.CategorySecondListBean> dataArray = new ArrayList<>();


    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("视频类型");
        bar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent != null){
            GetCategoryListBean.DataBean type = (GetCategoryListBean.DataBean)intent.getSerializableExtra("videoType");
            if(type != null){
                dataArray = type.getCategorySecondList();
            }
        }
    }

    @Override
    protected void initView() {

        videoTypeListAdapter = new MyVideoTypeSecondListAdapter(R.layout.item_video_type_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        videoTypeRv.setLayoutManager(linearLayoutManager);
        videoTypeRv.setAdapter(videoTypeListAdapter);
        videoTypeListAdapter.setNewData(dataArray);
        videoTypeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(dataArray.get(position));
                Intent intent = new Intent(MyVideoTypeSecondListActivity.this,MyLecturerUploadVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
