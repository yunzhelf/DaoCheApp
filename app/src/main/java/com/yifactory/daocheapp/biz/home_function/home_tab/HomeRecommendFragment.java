package com.yifactory.daocheapp.biz.home_function.home_tab;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.yifactory.daocheapp.bean.GetIndexNewsBean;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.VideoListBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendBannerDetailActivity;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendHeadLineActivity;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendInterviewVideoDetailsActivity;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendJsListActivity;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendOperateManagerActivity;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendManagementAdapter;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendVideoAdapter;
import com.yifactory.daocheapp.event.HomeTabAnimMessage;
import com.yifactory.daocheapp.event.HomeTabChangedMessage;
import com.yifactory.daocheapp.event.HomeTabChangedThreeMessage;
import com.yifactory.daocheapp.event.HomeTabChangedTwoMessage;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

public class HomeRecommendFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_container)
    AutoLinearLayout layout_container;
    @BindView(R.id.banner)
    BGABanner mBGABanner;
    @BindView(R.id.management_rv)
    RecyclerView mRv_management;
    @BindView(R.id.daochetoutiao_iv)
    ImageView mIv_daochetoutiao;
    @BindView(R.id.toutiao_tv)
    TextView mTv_touTiao;
    @BindView(R.id.army_tv)
    TextView mTv_army;
    @BindView(R.id.video_rv)
    RecyclerView mRv_video;
    private Dialog mDialog;
    private HomeRecommendManagementAdapter mManagementAdapter;


    public static HomeRecommendFragment newInstance() {
        HomeRecommendFragment fragment = new HomeRecommendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {
        mDialog = SDDialogUtil.newLoading(mActivity, "请求中...");
        getSystemInfoData(ApiConstant.REQUEST_NORMAL);
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
                        EventBus.getDefault().post(new HomeTabAnimMessage());
                        cancelDialog();
                        layout_container.setVisibility(View.VISIBLE);
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
                        List<GetSystemInfoBean.DataBean.BannersBean> imgUrlList = new ArrayList<>();
                        for (GetSystemInfoBean.DataBean.BannersBean bannersBean : bannersBeanList) {
                            if (bannersBean.getBannerLeval() == 0) {
                                imgUrlList.add(bannersBean);
                            }
                        }
                        if (imgUrlList.size() > 0) {
                            setAdvData(imgUrlList);
                        }
                    }

                    List<GetSystemInfoBean.DataBean.IndexBtnsBean> indexBtnsBeanList = dataBean.getIndexBtns();
                    if (indexBtnsBeanList != null && indexBtnsBeanList.size() > 0) {
                        indexBtnsBeanList.remove(0);
                        mManagementAdapter.setNewData(indexBtnsBeanList);
                    }

                    List<GetSystemInfoBean.DataBean.SysIndexNewsBean> sysIndexNewsBeanList = dataBean.getSysIndexNews();
                    if (sysIndexNewsBeanList != null && sysIndexNewsBeanList.size() > 0) {
                        GetSystemInfoBean.DataBean.SysIndexNewsBean sysIndexNewsBean = sysIndexNewsBeanList.get(0);
                        if (sysIndexNewsBean != null) {
                            String title = sysIndexNewsBean.getTitle();
                            if (!TextUtils.isEmpty(title)) {
                                mTv_touTiao.setText(title);
                            }
                        }
                    }

                    List<GetSystemInfoBean.DataBean.SysArmyAnasBean> sysArmyAnasBeanList = dataBean.getSysArmyAnas();
                    if (sysArmyAnasBeanList != null && sysArmyAnasBeanList.size() > 0) {
                        GetSystemInfoBean.DataBean.SysArmyAnasBean sysArmyAnasBean = sysArmyAnasBeanList.get(0);
                        String title = sysArmyAnasBean.getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            mTv_army.setText(title);
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
        loadGifTouTiao();
        initManagementRv();
        initVideoRv();
    }

    private void loadGifTouTiao() {
        Glide.with(mActivity).load(R.drawable.daochetoutiao_gif).into(mIv_daochetoutiao);
    }

    private void initVideoRv() {
        HomeRecommendVideoAdapter videoAdapter = new HomeRecommendVideoAdapter();
        mRv_video.setFocusableInTouchMode(false);
        mRv_video.requestFocus();
        mRv_video.setNestedScrollingEnabled(false);
        mRv_video.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv_video.setAdapter(videoAdapter);
        videoAdapter.setNewData(managementTitleArray);
        videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, HomeRecommendInterviewVideoDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<PlayVideoBean.DataBean.HotBean> managementTitleArray = new ArrayList<>();

    private void initManagementRv() {
        mManagementAdapter = new HomeRecommendManagementAdapter();
        mRv_video.setFocusableInTouchMode(false);
        mRv_video.requestFocus();
        mRv_management.setNestedScrollingEnabled(false);
        mRv_management.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mRv_management.setAdapter(mManagementAdapter);
        mManagementAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GetSystemInfoBean.DataBean.IndexBtnsBean indexBtnsBean = mManagementAdapter.getData().get(position);
                List<GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean> categorySecondList = indexBtnsBean.getCategorySecondList();
                if (categorySecondList != null && categorySecondList.size() > 0) {
                    Intent intent = new Intent(mActivity, HomeRecommendOperateManagerActivity.class);
                    intent.putExtra("mark", "recommend");
                    intent.putExtra("indexBtnsBean", indexBtnsBean);
                    startActivity(intent);
                } else {
                    showToast("暂无相关视频");
                }
            }
        });
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
        return R.layout.fragment_recommend_home;
    }

    private void setAdvData(List<GetSystemInfoBean.DataBean.BannersBean> imgUrlList) {
        List<String> imgTitleList = new ArrayList<>();
        for (int i = 0; i < imgUrlList.size(); i++) {
            imgTitleList.add("");
        }
        BGABanner.Adapter<ImageView, GetSystemInfoBean.DataBean.BannersBean> bgaBannerAdapter = new BGABanner.Adapter<ImageView, GetSystemInfoBean.DataBean.BannersBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, final GetSystemInfoBean.DataBean.BannersBean model, int position) {
                Glide.with(mActivity).setDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.drawable.banner1)).load(model.getBgImg()).into(itemView);
            }
        };
        mBGABanner.setAdapter(bgaBannerAdapter);
        mBGABanner.setData(imgUrlList, imgTitleList);
        mBGABanner.setDelegate(new BGABanner.Delegate<ImageView,GetSystemInfoBean.DataBean.BannersBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, GetSystemInfoBean.DataBean.BannersBean model, int position) {
                Intent i = new Intent(mActivity, HomeRecommendBannerDetailActivity.class);
                i.putExtra("banner", model);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRefresh() {
        getSystemInfoData(ApiConstant.REQUEST_REFRESH);
    }

    @OnClick({R.id.js_layout, R.id.interview_more_layout, R.id.head_msg_layout})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.js_layout:
                    Intent intent = new Intent(mActivity, HomeRecommendJsListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.interview_more_layout:
                    EventBus.getDefault().post(new HomeTabChangedMessage());
                    break;
                case R.id.head_msg_layout:
                    Intent intent1 = new Intent(mActivity, HomeRecommendHeadLineActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }
}
