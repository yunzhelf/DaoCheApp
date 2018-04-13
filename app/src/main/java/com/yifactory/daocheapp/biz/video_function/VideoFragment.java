package com.yifactory.daocheapp.biz.video_function;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.utils.NetWatchdog;
import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.AddStudyRecordBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.StsToken;
import com.yifactory.daocheapp.bean.TwoVideoListBean;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendInterviewVideoDetailsActivity;
import com.yifactory.daocheapp.biz.video_function.activity.VideoAuthorHomePageActivity;
import com.yifactory.daocheapp.biz.video_function.activity.VideoFullScreenActivity;
import com.yifactory.daocheapp.biz.video_function.adapter.VideoAuthorOtherProductionAdapter;
import com.yifactory.daocheapp.biz.video_function.adapter.VideoHotRecommendAdapter;
import com.yifactory.daocheapp.utils.AliyunPlayerUtils;
import com.yifactory.daocheapp.utils.Formatter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Completed;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Idle;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Paused;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Prepared;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Started;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Stopped;

public class VideoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.author_production_rv)
    RecyclerView mRv_authorProduction;
    @BindView(R.id.hot_recommend_rv)
    RecyclerView mRv_hotRecommend;
    @BindView(R.id.tv_guanzhu)
    TextView mTv_guanZhu;
    @BindView(R.id.author_name_tv)
    TextView authorNameTv;
    @BindView(R.id.playVideo_play_count_tv)
    TextView playCountTv;
    @BindView(R.id.totalDuration)
    TextView totalDurationTv;
    @BindView(R.id.progress)
    SeekBar seekBar;
    @BindView(R.id.currentPosition)
    TextView currentPositionTv;
    @BindView(R.id.author_level_tv)
    TextView authorLevelTv;
    @BindView(R.id.author_other_works_tv)
    TextView authorOtherWorksTv;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.play_iv)
    ImageView playIv;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;

    private VideoAuthorOtherProductionAdapter productionAdapter;
    private List<PlayVideoBean.DataBean.TeacherOtherBean> dataProductionArray = new ArrayList<>();
    private VideoHotRecommendAdapter hotRecommendAdapter;
    private List<PlayVideoBean.DataBean.HotBean> dataHotArray = new ArrayList<>();
    private PlayVideoBean.DataBean playVideoObj;
    private PlayVideoBean.DataBean.HotBean videoInfo;
    private IAliyunVodPlayer.PlayerState mPlayerState;
    private AliyunVidSts mVidSts;
    private List<String> logStrs = new ArrayList<>();
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
    private int page = 0;
    private NetWatchdog netWatchDog;
    private AlertDialog netChangeDialog;
    private Dialog mDialog;
    private Dialog vDialog;
    private long studyTime = 0; //秒

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    loadingProgress.setVisibility(View.GONE);
                    if(AliyunPlayerUtils.isCompleted){
                        playIv.setImageResource(R.drawable.bofanganniu);
                    }else{
                        playIv.setImageResource(R.drawable.bofangzanting);
                    }
                    break;
                case 1:
                    showVideoProgressInfo();
                    break;
                case 2:
                    playIv.setImageResource(R.drawable.bofanganniu);
                    break;
                case 3:
                    playIv.setImageResource(R.drawable.bofanganniu);
                    break;
            }
        }
    };


    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("播放");
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        return true;
    }

    @Override
    protected void initData(Bundle arguments) {
        EventBus.getDefault().register(this);
        AliyunPlayerUtils.handler = handler;
        startNetWatch();
        if (AliyunPlayerUtils.aliyunVodPlayer != null) {
            int duration = (int) AliyunPlayerUtils.aliyunVodPlayer.getDuration();
            totalDurationTv.setText(Formatter.formatTime(duration));
        }
    }

    private void startNetWatch() {
        netWatchDog = new NetWatchdog(mActivity);
        netWatchDog.setNetChangeListener(new NetWatchdog.NetChangeListener() {
            @Override
            public void onWifiTo4G() {
                if (AliyunPlayerUtils.aliyunVodPlayer.isPlaying()) {
                    AliyunPlayerUtils.aliyunVodPlayer.pause();
                }
                if (netChangeDialog == null || !netChangeDialog.isShowing()) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
                    alertDialog.setTitle("切换到4G网络");
                    alertDialog.setMessage("是否继续播放");
                    alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            IAliyunVodPlayer.PlayerState playerState = AliyunPlayerUtils.aliyunVodPlayer.getPlayerState();
                            if (playerState == IAliyunVodPlayer.PlayerState.Idle ||
                                    playerState == IAliyunVodPlayer.PlayerState.Completed ||
                                    playerState == IAliyunVodPlayer.PlayerState.Stopped) {
//                                mAutoPlay = true;
                                AliyunPlayerUtils.seekTo(seekBar.getProgress());
                                prepareAsync();
                            } else if (playerState == AliyunVodPlayer.PlayerState.Paused) {
                                AliyunPlayerUtils.aliyunVodPlayer.resume();
                            } else {
                                AliyunPlayerUtils.seekTo(seekBar.getProgress());
                                AliyunPlayerUtils.aliyunVodPlayer.start();
                            }
                        }
                    });
                    alertDialog.setNegativeButton("取消", null);
                    netChangeDialog = alertDialog.create();
                    netChangeDialog.show();
                }

                Toast.makeText(mActivity.getApplicationContext(), "切换到4G网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void on4GToWifi() {
                Toast.makeText(mActivity, "wifi网络已连接", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNetDisconnected() {
                Toast.makeText(mActivity, "网络已断开", Toast.LENGTH_SHORT).show();
            }
        });
        netWatchDog.startWatch();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initVideoView();
        initAuthorProductionRv();
        initHotRecommendRv();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (AliyunPlayerUtils.aliyunVodPlayer.isPlaying()) {
                AliyunPlayerUtils.aliyunVodPlayer.pause();
                playIv.setImageResource(R.drawable.bofanganniu);
            }
            if (videoInfo != null && UserInfoUtil.getUserInfoBean(mActivity) != null) {
                long curtime = System.currentTimeMillis();
                addStudyReocrd(curtime - studyTime);
            }
        } else {
            studyTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AliyunPlayerUtils.isCompleted) {
            playIv.setImageResource(R.drawable.bofanganniu);
            int curPosition = (int) AliyunPlayerUtils.aliyunVodPlayer.getCurrentPosition();
            seekBar.setProgress(curPosition);
            currentPositionTv.setText(Formatter.formatTime(curPosition));
            AliyunPlayerUtils.aliyunVodPlayer.prepareAsync(mVidSts);
        } else if (AliyunPlayerUtils.aliyunVodPlayer.getPlayerState() == Started) {
            playIv.setImageResource(R.drawable.bofangzanting);
        }
        AliyunPlayerUtils.handler = handler;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        AliyunPlayerUtils.aliyunVodPlayer.stop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void videoInfoEventBus(PlayVideoBean.DataBean.HotBean videoInfo) {
        this.videoInfo = videoInfo;
        AliyunPlayerUtils.isCompleted = false;
        AliyunPlayerUtils.mAutoPlay = true;
        vDialog = SDDialogUtil.newLoading(mActivity, "请求中");
        vDialog.show();
        if (videoInfo.getVideoPath() != null && videoInfo.getUId() != null) {
            getPlayVideoEvent();
            getSTStoken();
            loadingProgress.setVisibility(View.VISIBLE);
        } else {
            showToast("数据出错");
        }
    }

    private void getSTStoken() {
        RxHttpUtils.createApi(ApiService.class)
                .getSTSToken()
                .compose(Transformer.<StsToken>switchSchedulers())
                .subscribe(new CommonObserver<StsToken>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast("认证获取失败");
                        if (vDialog.isShowing()) {
                            vDialog.dismiss();
                        }
                    }

                    @Override
                    protected void onSuccess(StsToken stsToken) {
                        if (stsToken != null) {
                            StsToken.DataBean token = stsToken.getData().get(0);
                            Log.e("sunxj", "accessKeyId=" + token.getAccessKeyId() + ",accesskeySecrty = " + token.getAccessKeySecret()
                                    + ",Expiration=" + token.getExpiration() + ",token=" + token.getSecurityToken());
                            preparePlayVideoView(token);
                        }
                    }
                });
    }

    private void getPlayVideoEvent() {
        RxHttpUtils.createApi(ApiService.class)
                .playVideo(videoInfo.getRId(), videoInfo.getUId())
                .compose(Transformer.<PlayVideoBean>switchSchedulers())
                .subscribe(new CommonObserver<PlayVideoBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Log.e("sunxj", errorMsg);
                        finishRefresh();
                    }

                    @Override
                    protected void onSuccess(PlayVideoBean playVideoBean) {
                        playVideoObj = playVideoBean.getData().get(0);
                        initPlayVideo();
                        finishRefresh();
                    }
                });
    }

    private void addStudyReocrd(long lastTime) {
        String rId = videoInfo.getRId();
        String uId = UserInfoUtil.getUserInfoBean(mActivity).getUId();
        String time = String.valueOf(lastTime / 1000);

        RxHttpUtils.createApi(ApiService.class)
                .addStudyRecord(rId, uId, time)
                .compose(Transformer.<AddStudyRecordBean>switchSchedulers())
                .subscribe(new CommonObserver<AddStudyRecordBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(AddStudyRecordBean addStudyRecordBean) {
                        Log.e("videoFragment", addStudyRecordBean.getMsg());
                    }
                });

    }

    private void initPlayVideo() {
        PlayVideoBean.DataBean.ResourceBean bean = playVideoObj.getResource();
        if (bean != null) {
            playCountTv.setText(String.valueOf(bean.getShowCounts()) + "次");
            if (bean.getCreator() != null) {
                authorNameTv.setText("作者：" + bean.getCreator().getUserName());
                authorLevelTv.setText(bean.getCreator().getUserLeval());
                authorOtherWorksTv.setText(bean.getCreator().getUserName() + ": 其它作品");
                if (playVideoObj.getResource().getCreator().getAttentioned() == 0) {
                    mTv_guanZhu.setText("+ 关注");
                } else {
                    mTv_guanZhu.setText("取消关注");
                    mTv_guanZhu.setBackgroundResource(R.drawable.shape_gray_corner2);
                }
            }
            dataProductionArray = playVideoObj.getTeacherOther();
            productionAdapter.setNewData(dataProductionArray);
            dataHotArray = playVideoObj.getHot();
            hotRecommendAdapter.setNewData(dataHotArray);
        }
    }

    private void prepareAsync() {
        if (mVidSts != null) {
            AliyunPlayerUtils.aliyunVodPlayer.prepareAsync(mVidSts);
        }
    }

    //准备播放
    private void preparePlayVideoView(StsToken.DataBean sts) {
        if (AliyunPlayerUtils.aliyunVodPlayer != null) {
            AliyunPlayerUtils.aliyunVodPlayer.stop();
            playIv.setImageResource(R.drawable.bofanganniu);
        }
        if (surfaceView != null) {
            surfaceView.setVisibility(View.GONE);
            surfaceView.setVisibility(View.VISIBLE);
        }
        mVidSts = new AliyunVidSts();
        mVidSts.setVid(videoInfo.getVideoPath());
        mVidSts.setAcId(sts.getAccessKeyId());
        mVidSts.setAkSceret(sts.getAccessKeySecret());
        mVidSts.setSecurityToken(sts.getSecurityToken());
        AliyunPlayerUtils.mVidSts = mVidSts;
        AliyunPlayerUtils.aliyunVodPlayer.prepareAsync(mVidSts);
        if (vDialog != null && vDialog.isShowing()) {
            vDialog.dismiss();
        }
        studyTime = System.currentTimeMillis(); //获取当前时间为学习时长准备
    }

    private void playVideo() {
        AliyunPlayerUtils.aliyunVodPlayer.prepareAsync(mVidSts);
        AliyunPlayerUtils.isCompleted = false;
        mPlayerState = AliyunPlayerUtils.aliyunVodPlayer.getPlayerState();
        if (mPlayerState == Idle || mPlayerState == Stopped || mPlayerState == Completed) {
            if (surfaceView != null) {
                surfaceView.setVisibility(View.GONE);
                surfaceView.setVisibility(View.VISIBLE);
            }
            AliyunPlayerUtils.aliyunVodPlayer.stop();
            AliyunPlayerUtils.aliyunVodPlayer.prepareAsync(mVidSts);
        } else if (mPlayerState == Prepared) {
            AliyunPlayerUtils.aliyunVodPlayer.start();
            playIv.setImageResource(R.drawable.bofangzanting);
        } else if (mPlayerState == Started) {
            AliyunPlayerUtils.aliyunVodPlayer.pause();
            playIv.setImageResource(R.drawable.bofanganniu);
        } else if (mPlayerState == Paused) {
            AliyunPlayerUtils.aliyunVodPlayer.resume();
            playIv.setImageResource(R.drawable.bofangzanting);
        }

    }

    private void initVideoView() {
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                AliyunPlayerUtils.aliyunVodPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                AliyunPlayerUtils.aliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, VideoFullScreenActivity.class);
                i.putExtra("rId", videoInfo.getRId());
                startActivity(i);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(mVidSts);
                    }
                }, 500);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (AliyunPlayerUtils.aliyunVodPlayer != null) {
                    AliyunPlayerUtils.seekTo(seekBar.getProgress());
                    logStrs.add(format.format(new Date()) + "开始播放");
                    if (AliyunPlayerUtils.isCompleted) {
                        //播放完成了，不能seek了
                        AliyunPlayerUtils.inSeek = false;
                    } else {
                        AliyunPlayerUtils.inSeek = true;
                    }
                }
            }
        });
    }

    private void showVideoProgressInfo() {
        if (AliyunPlayerUtils.aliyunVodPlayer != null && !AliyunPlayerUtils.inSeek) {
            int curPosition = (int) AliyunPlayerUtils.aliyunVodPlayer.getCurrentPosition();
            currentPositionTv.setText(Formatter.formatTime(curPosition));
            int duration = (int) AliyunPlayerUtils.aliyunVodPlayer.getDuration();
            totalDurationTv.setText(Formatter.formatTime(duration));
            int bufferPosition = AliyunPlayerUtils.aliyunVodPlayer.getBufferingPosition();
            seekBar.setMax(duration);
            seekBar.setSecondaryProgress(bufferPosition);
            seekBar.setProgress(curPosition);
        }
    }

    private void initHotRecommendRv() {
        hotRecommendAdapter = new VideoHotRecommendAdapter();
        mRv_hotRecommend.setNestedScrollingEnabled(false);
        mRv_hotRecommend.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv_hotRecommend.setAdapter(hotRecommendAdapter);
        hotRecommendAdapter.setNewData(dataHotArray);
        hotRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PlayVideoBean.DataBean.HotBean dataBean = new PlayVideoBean.DataBean.HotBean();
                dataBean.setVideoPath(dataHotArray.get(position).getVideoPath());
                dataBean.setRId(dataHotArray.get(position).getRId());
                dataBean.setUId(dataHotArray.get(position).getUId());
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                Intent intent = new Intent(getActivity(), HomeRecommendInterviewVideoDetailsActivity.class);
                intent.putExtra("videoInfo", dataHotArray.get(position));
                startActivity(intent);
            }
        });
    }

    private void initAuthorProductionRv() {
        productionAdapter = new VideoAuthorOtherProductionAdapter();
        mRv_authorProduction.setNestedScrollingEnabled(false);
        mRv_authorProduction.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRv_authorProduction.setAdapter(productionAdapter);
        productionAdapter.setNewData(dataProductionArray);
        productionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PlayVideoBean.DataBean.HotBean dataBean = new PlayVideoBean.DataBean.HotBean();
                dataBean.setVideoPath(dataProductionArray.get(position).getVideoPath());
                dataBean.setRId(dataProductionArray.get(position).getRId());
                dataBean.setUId(dataProductionArray.get(position).getUId());
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
                Intent intent = new Intent(getActivity(), HomeRecommendInterviewVideoDetailsActivity.class);
                intent.putExtra("videoInfo", dataBean);
                startActivity(intent);
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
        return R.layout.fragment_video;
    }

    private void finishRefresh() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        if (videoInfo != null) {
            getPlayVideoEvent();
        } else {
            finishRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.author_layout, R.id.tv_guanzhu, R.id.play_iv, R.id.previous_iv, R.id.next_iv,
            R.id.hot_list_reflash_ly, R.id.friend_layout, R.id.friend_circle_layout})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.author_layout:
                    if (playVideoObj == null || playVideoObj.getResource() == null) {
                        showToast("没有作者数据");
                        return;
                    }
                    Intent intent = new Intent(mActivity, VideoAuthorHomePageActivity.class);
                    intent.putExtra("author", playVideoObj.getResource().getCreator());
                    startActivity(intent);
                    break;
                case R.id.tv_guanzhu:
                    guanZhuEvent();
                    break;
                case R.id.play_iv:
                    playVideo();
                    break;
                case R.id.previous_iv:
//                    AliyunPlayerUtils.seekTo((int)AliyunPlayerUtils.aliyunVodPlayer.getCurrentPosition() - 5000);
                    AliyunPlayerUtils.seekTo(seekBar.getProgress() - 5000);
                    break;
                case R.id.next_iv:
//                    AliyunPlayerUtils.seekTo((int)AliyunPlayerUtils.aliyunVodPlayer.getCurrentPosition() + 5000);
                    AliyunPlayerUtils.seekTo(seekBar.getProgress() + 5000);
                    break;
                case R.id.hot_list_reflash_ly:
                    getHostlistEvent();
                    break;
                case R.id.friend_layout:
                    shareEvent(1);
                    break;
                case R.id.friend_circle_layout:
                    shareEvent(2);
                    break;
            }
        }
    }

    private void shareEvent(int type) {
        if (new SPreferenceUtil(mActivity, "config.sp").getIsLine()) {
            int shareState = UserInfoUtil.getUserInfoBean(mActivity).getShareState();
            if (shareState == 0) {
                showToast("您还未发起分享申请");
            } else if (shareState == 1) {
                showToast("分享申请审核中");
            } else if (shareState == 2) {
                showToast("分享申请已驳回，请重新申请");
            } else {
                if (videoInfo != null) {
                    shareTypeEvent(type);
                } else {
                    showToast("请选择视频");
                }
            }
        } else {
            showToast("请登陆");
        }
    }

    private void shareTypeEvent(int type) {
        IWXAPI api = WXAPIFactory.createWXAPI(mActivity, ApiConstant.APP_ID_WE_CHAT, false);
        api.registerApp(ApiConstant.APP_ID_WE_CHAT);
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoInfo.getVideoPath();
        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = videoInfo.getTitle();
        msg.description = videoInfo.getVideoDetail();
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        switch (type) {
            case 1:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case 2:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void guanZhuEvent() {
        if (playVideoObj == null || playVideoObj.getResource() == null || playVideoObj.getResource().getCreator() == null) {
            showToast("没有作者数据");
            return;
        }
        final int isFocus = playVideoObj.getResource().getCreator().getAttentioned();
        String uId = UserInfoUtil.getUserInfoBean(mActivity).getUId();
        String attentionId = playVideoObj.getResource().getCreator().getUId();
        if (attentionId == null) {
            attentionId = "";
        }
        ApiService api = RxHttpUtils.createApi(ApiService.class);
        Observable<PlayVideoBean.DataBean.ResourceBean.CreatorBean> observable;
        if (isFocus == 1) {
            observable = api.deleteUserAttention(uId, attentionId);
        } else {
            observable = api.addUserAttention(uId, attentionId);
        }
        observable.compose(Transformer.<PlayVideoBean.DataBean.ResourceBean.CreatorBean>switchSchedulers())
                .subscribe(new CommonObserver<PlayVideoBean.DataBean.ResourceBean.CreatorBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(PlayVideoBean.DataBean.ResourceBean.CreatorBean creatorBean) {
                        if (isFocus == 0) {  //关注成功
                            mTv_guanZhu.setBackgroundResource(R.drawable.shape_gray_corner2);
                            mTv_guanZhu.setText("取消关注");
                            playVideoObj.getResource().getCreator().setAttentioned(1);
                            showToast("关注成功");
                        } else {   //取消成功
                            mTv_guanZhu.setBackgroundResource(R.drawable.shape_bule);
                            mTv_guanZhu.setText("+ 关注");
                            playVideoObj.getResource().getCreator().setAttentioned(0);
                            showToast("取消关注成功");
                        }
                    }
                });
    }

    private void getHostlistEvent() {
        if (playVideoObj == null || playVideoObj.getResource() == null) {
            showToast("没有更多数据");
            return;
        }
        mDialog = SDDialogUtil.newLoading(mActivity, "正在加载，请稍后");
        mDialog.show();
        page++;
        String scId = playVideoObj.getResource().getScId(); //当前播放视频的scId  获取热门视频列表
        RxHttpUtils.createApi(ApiService.class)
                .getHotList(scId, String.valueOf(6), String.valueOf(page))
                .compose(Transformer.<TwoVideoListBean>switchSchedulers())
                .subscribe(new CommonObserver<TwoVideoListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        if (mDialog.isShowing()) {
                            mDialog.cancel();
                        }
                    }

                    @Override
                    protected void onSuccess(TwoVideoListBean videoListBean) {
                        if (mDialog.isShowing()) {
                            mDialog.cancel();
                        }
                        if (videoListBean != null && videoListBean.responseState.equals("1")) {
                            dataHotArray = videoListBean.data;
                            hotRecommendAdapter.setNewData(dataHotArray);
                            if (videoListBean.data.size() < 1) {
                                page = 0;
                            }
                        }
                    }
                });
    }

}
