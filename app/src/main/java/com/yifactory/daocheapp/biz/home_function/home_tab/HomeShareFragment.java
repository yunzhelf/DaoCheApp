package com.yifactory.daocheapp.biz.home_function.home_tab;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;
import com.yifactory.daocheapp.bean.HomeShareTypeBean;
import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.VideoListBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendInterviewVideoDetailsActivity;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter.HomeRecommendVideoAdapter;
import com.yifactory.daocheapp.biz.home_function.home_tab.adapter.HomeShareTypeSelectAdapter;
import com.yifactory.daocheapp.utils.DateUtil;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.CustomPopWindow;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

public class HomeShareFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.banner)
    BGABanner mBGABanner;
    @BindView(R.id.select_layout)
    AutoLinearLayout selectLayout;
    @BindView(R.id.video_rv)
    RecyclerView mRv_video;
    private String[] picUrlArray = {"https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/cat.jpg",
            "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/girl.jpg"};
    private List<PlayVideoBean.DataBean.HotBean> managementTitleArray = new ArrayList<>();

    private TimePickerView pvTime;
    private CustomPopWindow mCustomPopWindowType;
    private Dialog mDialog;
    private int pageNum = 0;
    HomeRecommendVideoAdapter hotRecommendAdapter;
    String endTime, scId;

    public static HomeShareFragment newInstance() {
        HomeShareFragment fragment = new HomeShareFragment();
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
        initTimePicker();
        mDialog = SDDialogUtil.newLoading(mActivity, "请求中");
        getSystemInfoData(ApiConstant.REQUEST_NORMAL);
        getOneVideoList(ApiConstant.REQUEST_REFRESH, 0);
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
                            if (bannersBean.getBannerLeval() == 1) {
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

    private void getOneVideoList(final String requestMark, int pageNum) {
        if (requestMark.equals(ApiConstant.REQUEST_NORMAL)) {
            mDialog.show();
        }
        LoginBean.DataBean userInfoBean = UserInfoUtil.getUserInfoBean(getActivity());
        String mUId = null;
        if (userInfoBean != null) {
            mUId = userInfoBean.getUId();
        }
        RxHttpUtils.createApi(ApiService.class)
                .getShareVideoList(pageNum + "", endTime, scId, mUId)
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
            hotRecommendAdapter.addData(getSysArmyAnasBean.data);
            hotRecommendAdapter.loadMoreEnd();
        } else {
            hotRecommendAdapter.setNewData(getSysArmyAnasBean.data);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setAdvData(Arrays.asList(picUrlArray));
        initVideoRv();
    }

    private void setAdvData(List<String> imgUrlList) {
        List<String> imgTitleList = new ArrayList<>();
        for (int i = 0; i < imgUrlList.size(); i++) {
            imgTitleList.add("");
        }
        BGABanner.Adapter<ImageView, String> bgaBannerAdapter = new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(mActivity).setDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.drawable.banner1).dontAnimate()).load(model).into(itemView);
            }
        };
        mBGABanner.setAdapter(bgaBannerAdapter);
        mBGABanner.setData(imgUrlList, imgTitleList);
    }

    private void initVideoRv() {
        hotRecommendAdapter = new HomeRecommendVideoAdapter();
        mRv_video.setFocusableInTouchMode(false);
        mRv_video.requestFocus();
        mRv_video.setNestedScrollingEnabled(false);
        mRv_video.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv_video.setAdapter(hotRecommendAdapter);
        hotRecommendAdapter.setNewData(managementTitleArray);
        hotRecommendAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum += 1;
                Log.i("521", "onLoadMoreRequested: 加载更多数据");
                mRv_video.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getOneVideoList(ApiConstant.REQUEST_NORMAL, pageNum);
                    }
                }, 1000);
            }
        }, mRv_video);
        hotRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<PlayVideoBean.DataBean.HotBean> dataArray = hotRecommendAdapter.getData();
                startActivity(new Intent(getActivity(), HomeRecommendInterviewVideoDetailsActivity.class).putExtra("videoInfo", dataArray.get(position)));
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
        return R.layout.fragment_home_share;
    }

    @Override
    public void onRefresh() {
//        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
//            mSwipeRefreshLayout.setRefreshing(false);
//        }
        getOneVideoList(ApiConstant.REQUEST_REFRESH, 0);
    }

    @OnClick({R.id.time_layout, R.id.type_layout})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.time_layout:
                    selectTimeEvent();
                    break;
                case R.id.type_layout:
                    selectTypeEvent();
                    break;
            }
        }
    }

    private void selectTimeEvent() {
        if (pvTime != null) {
            pvTime.show();
        }
    }

    private void initTimePicker() {
        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {
                String time = DateUtil.getTime(date2);
                Long timeL = DateUtil.getTime(time);
                endTime = String.valueOf(timeL);
                getOneVideoList(ApiConstant.REQUEST_REFRESH, 0);
                Log.i("521", "onTimeSelect: time:" + time);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        Button tvSubmit = (Button) v.findViewById(R.id.btnSubmit);
                        Button ivCancel = (Button) v.findViewById(R.id.btnCancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(20)
                .setTitleSize(20)
                .setOutSideCancelable(true)
                .isCyclic(true)
                .setTextColorCenter(Color.BLACK)
                .setTitleColor(Color.BLACK)
                .isCenterLabel(false)
                .isDialog(true)
                .build();
        pvTime.setDate(Calendar.getInstance());
    }

    private void selectTypeEvent() {
        if (mCustomPopWindowType == null) {
            View contentView = LayoutInflater.from(mActivity).inflate(R.layout.pop_home_share_type_layout, null);
            handleType(contentView);
            mCustomPopWindowType = new CustomPopWindow.PopupWindowBuilder(mActivity)
                    .setView(contentView)
                    .enableBackgroundDark(true)
                    .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .create()
                    .showAsDropDown(selectLayout, 0, 0);
        } else {
            mCustomPopWindowType
                    .showAsDropDown(selectLayout, 0, 0);
        }
    }

    private String[] typeArray = {"全部", "运营管理", "销售管理", "市场营销", "衍生业务", "客户维护", "售后管理", "人事行政", "财务管理"};
    private List<HomeShareTypeBean> mHomeShareTypeBeanList = new ArrayList<>();

    private void handleType(View contentView) {
        for (int i = 0; i < typeArray.length; i++) {
            HomeShareTypeBean typeBean = new HomeShareTypeBean();
            typeBean.setTitle(typeArray[i]);
            if (i == 0) {
                typeBean.setSelected(true);
            }
            mHomeShareTypeBeanList.add(typeBean);
        }
        final HomeShareTypeSelectAdapter typeSelectAdapter = new HomeShareTypeSelectAdapter(mHomeShareTypeBeanList);
        RecyclerView typeRv = (RecyclerView) contentView.findViewById(R.id.type_rv);
        typeRv.setLayoutManager(new GridLayoutManager(mActivity, 3));
        typeRv.setAdapter(typeSelectAdapter);
        typeSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<HomeShareTypeBean> data = typeSelectAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    HomeShareTypeBean typeBean = data.get(i);
                    if (position == i) {
                        typeBean.setSelected(true);
                    } else {
                        typeBean.setSelected(false);
                    }
                }
                SPreferenceUtil mEightModuleFcIdSp = new SPreferenceUtil(mActivity, "eightModuleFcId.sp");
                scId = mEightModuleFcIdSp.getEightModuleFcId(data.get(position).getTitle());
                getOneVideoList(ApiConstant.REQUEST_REFRESH, 0);
                typeSelectAdapter.notifyDataSetChanged();
                if (mCustomPopWindowType != null) {
                    mCustomPopWindowType.dissmiss();
                }
            }
        });
    }
}
