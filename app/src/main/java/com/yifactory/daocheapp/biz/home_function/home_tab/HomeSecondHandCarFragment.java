package com.yifactory.daocheapp.biz.home_function.home_tab;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.VideoListBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendInterviewVideoDetailsActivity;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendVideoAdapter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

public class HomeSecondHandCarFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.banner)
    BGABanner mBGABanner;
    @BindView(R.id.video_rv)
    RecyclerView mRv_video;
    private Dialog mDialog;
    HomeRecommendVideoAdapter videoAdapter;
    private int pageNum = 0;
    public static HomeSecondHandCarFragment newInstance() {
        HomeSecondHandCarFragment fragment = new HomeSecondHandCarFragment();
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
        mDialog = SDDialogUtil.newLoading(mActivity, "请求中");
        getSystemInfoData(ApiConstant.REQUEST_NORMAL);
        getOneVideoList(ApiConstant.REQUEST_NORMAL, 0);
    }

    private void getSystemInfoData(String requestMark) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mDialog.show();
        }
        RxHttpUtils.createApi(ApiService.class)
                .getSystemInfo()
                .compose(Transformer.<GetSystemInfoBean>switchSchedulers())
                .subscribe(new CommonObserver<GetSystemInfoBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        cancelDialog();
                    }

                    @Override
                    protected void onSuccess(GetSystemInfoBean getSystemInfoBean) {
                        cancelDialog();
                        if (getSystemInfoBean.getResponseState().equals("1")) {
                            handleEntityData(getSystemInfoBean);
                        } else {
                            showToast(getSystemInfoBean.getMsg());
                        }
                    }
                });
    }

    private void handleEntityData(GetSystemInfoBean getSystemInfoBean) {
        if (getSystemInfoBean != null) {
            List<GetSystemInfoBean.DataBean> dataBeanList = getSystemInfoBean.getData();
            if (dataBeanList != null && dataBeanList.size() > 0) {
                GetSystemInfoBean.DataBean dataBean = dataBeanList.get(0);
                if (dataBean != null) {
                    List<GetSystemInfoBean.DataBean.BannersBean> bannersBeanList = dataBean.getBanners();
                    if (bannersBeanList != null && bannersBeanList.size() > 0) {
                        List<String> imgUrlList = new ArrayList<>();
                        for (GetSystemInfoBean.DataBean.BannersBean bannersBean : bannersBeanList) {
                            if (bannersBean.getBannerLeval() == 4) {
                                imgUrlList.add(ApiConstant.BASE_URL + bannersBean.getBgImg());
                            }
                        }
                        if (imgUrlList.size() > 0) {
                            setAdvData(imgUrlList);
                        }
                    }

                }
            }
        }
    }

    private void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initVideoRv();
    }

    private List<PlayVideoBean.DataBean.HotBean> managementTitleArray = new ArrayList<>();

    private void initVideoRv() {
        videoAdapter = new HomeRecommendVideoAdapter();
        mRv_video.setFocusableInTouchMode(false);
        mRv_video.requestFocus();
        mRv_video.setNestedScrollingEnabled(false);
        mRv_video.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv_video.setAdapter(videoAdapter);
        videoAdapter.setNewData(managementTitleArray);
        videoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum += 1;
                getOneVideoList(ApiConstant.REQUEST_NORMAL, pageNum);
            }
        });
        videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<PlayVideoBean.DataBean.HotBean> dataArray = videoAdapter.getData();
                startActivity(new Intent(getActivity(), HomeRecommendInterviewVideoDetailsActivity.class).putExtra("videoInfo", dataArray.get(position)));
            }
        });
    }

    private void getOneVideoList(final String requestMark, int pageNum) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mDialog.show();
        }
        SPreferenceUtil mEightModuleFcIdSp = new SPreferenceUtil(mActivity, "eightModuleFcId.sp");
        String fcId = mEightModuleFcIdSp.getEightModuleFcId("二手车");
        RxHttpUtils.createApi(ApiService.class)
                .getHomeROMVideoList(fcId, pageNum + "", null, null, null, null)
                .compose(Transformer.<VideoListBean>switchSchedulers())
                .subscribe(new CommonObserver<VideoListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        cancelDialog();
                    }

                    @Override
                    protected void onSuccess(VideoListBean getSysArmyAnasBean) {
                        cancelDialog();
                        if (getSysArmyAnasBean.responseState.equals("1")) {
                            handleEntityData(requestMark, getSysArmyAnasBean);
                        } else {
                            showToast(getSysArmyAnasBean.msg);
                        }
                    }
                });
    }

    private void handleEntityData(String requestMark, VideoListBean getSysArmyAnasBean) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            videoAdapter.addData(getSysArmyAnasBean.data);
            videoAdapter.loadMoreEnd();
        } else {
            videoAdapter.setNewData(getSysArmyAnasBean.data);
        }
    }

    @Override
    protected void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_interview_home;
    }

    @Override
    public void onRefresh() {
//        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
//            mSwipeRefreshLayout.setRefreshing(false);
//        }
        getSystemInfoData(ApiConstant.REQUEST_NORMAL);
        getOneVideoList(ApiConstant.REQUEST_NORMAL, 0);
    }

    private void setAdvData(List<String> imgUrlList) {
        List<String> imgTitleList = new ArrayList<>();
        for (int i = 0; i < imgUrlList.size(); i++) {
            imgTitleList.add("");
        }
        BGABanner.Adapter<ImageView, String> bgaBannerAdapter = new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(mActivity).setDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.drawable.banner1)).load(model).into(itemView);
            }
        };
        mBGABanner.setAdapter(bgaBannerAdapter);
        mBGABanner.setData(imgUrlList, imgTitleList);
    }

}
