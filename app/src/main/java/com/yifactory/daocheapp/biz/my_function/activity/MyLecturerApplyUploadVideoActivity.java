package com.yifactory.daocheapp.biz.my_function.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.MainActivity;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.TwoVideoListBean;
import com.yifactory.daocheapp.bean.VideoListBean;
import com.yifactory.daocheapp.biz.my_function.adapter.MyUploadVideoAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyLecturerApplyUploadVideoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.upload_video_rv)
    RecyclerView mRv_uploadVideo;
    private List<PlayVideoBean.DataBean.HotBean> dataArray = new ArrayList<>();
    private MyUploadVideoAdapter videoAdapter;
    private Dialog mDialog;
    private int page = 0;
    private int pageSize = 10;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        initVideoRv();
    }

    private void initVideoRv() {
        videoAdapter = new MyUploadVideoAdapter();
        mRv_uploadVideo.setNestedScrollingEnabled(false);
        mRv_uploadVideo.setLayoutManager(new LinearLayoutManager(this));
        mRv_uploadVideo.setAdapter(videoAdapter);
        videoAdapter.setNewData(dataArray);
        videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyLecturerApplyUploadVideoActivity.this, MainActivity.class);
                intent.putExtra("videoInfo",dataArray.get(position));
                intent.putExtra("play",true);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        EventBus.getDefault().register(this);
        getMyUploadVideoList();
    }

    private void getMyUploadVideoList(){
        mDialog = SDDialogUtil.newLoading(this,"正在加载");
        mDialog.show();
        RxHttpUtils.createApi(ApiService.class)
                .getUserUpload(UserInfoUtil.getUserInfoBean(this).getUId(),page,pageSize)
                .compose(Transformer.<TwoVideoListBean>switchSchedulers())
                .subscribe(new CommonObserver<TwoVideoListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        finishRefresh();
                    }

                    @Override
                    protected void onSuccess(TwoVideoListBean getMyUploadVideoBean) {
                        if(getMyUploadVideoBean != null && getMyUploadVideoBean.responseState.equals("1")){
                            dataArray = getMyUploadVideoBean.data;
                            videoAdapter.setNewData(dataArray);
                        }
                        finishRefresh();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void uploadVideoEvent(PlayVideoBean.DataBean.HotBean video){
        dataArray.add(video);
        videoAdapter.setNewData(dataArray);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lecturer_apply_upload_video;
    }

    @OnClick({R.id.back_layout, R.id.lecturer_info_layout, R.id.upload_layout})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.back_layout:
                    finish();
                    break;
                case R.id.lecturer_info_layout:
                    Intent lecturerInfoIntent = new Intent(this, MyLecturerInfoActivity.class);
                    startActivity(lecturerInfoIntent);
                    break;
                case R.id.upload_layout:
                    Intent lecturerUploadVideoIntent = new Intent(this, MyLecturerUploadVideoActivity.class);
                    startActivity(lecturerUploadVideoIntent);
                    break;
            }
        }
    }

    private void finishRefresh(){
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(mDialog.isShowing()){
            mDialog.cancel();
        }
    }

    @Override
    public void onRefresh() {
        getMyUploadVideoList();
    }
}
