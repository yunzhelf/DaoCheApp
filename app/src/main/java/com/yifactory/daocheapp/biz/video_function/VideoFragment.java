package com.yifactory.daocheapp.biz.video_function;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.logreport.FullScreenEvent;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.utils.NetWatchdog;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.fragment.BaseFragment;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.StsToken;
import com.yifactory.daocheapp.bean.TwoVideoListBean;
import com.yifactory.daocheapp.bean.VideoListBean;
import com.yifactory.daocheapp.biz.video_function.activity.VideoAuthorHomePageActivity;
//import com.yifactory.daocheapp.biz.video_function.activity.VideoFullScreenActivity;
import com.yifactory.daocheapp.biz.video_function.adapter.VideoAuthorOtherProductionAdapter;
import com.yifactory.daocheapp.biz.video_function.adapter.VideoHotRecommendAdapter;
import com.yifactory.daocheapp.utils.Formatter;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.SDProgressDialog;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private VideoAuthorOtherProductionAdapter productionAdapter;
    private List<PlayVideoBean.DataBean.TeacherOtherBean> dataProductionArray = new ArrayList<>();
    private VideoHotRecommendAdapter hotRecommendAdapter;
    private List<PlayVideoBean.DataBean.HotBean> dataHotArray = new ArrayList<>();
    private PlayVideoBean.DataBean playVideoObj;
    private PlayVideoBean.DataBean.HotBean videoInfo;
    private AliyunVodPlayer aliyunVodPlayer;
    private IAliyunVodPlayer.PlayerState mPlayerState;
    private AliyunVidSts mVidSts;
    private boolean inSeek = false;
    private boolean isCompleted = false;
    private List<String> logStrs = new ArrayList<>();
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
    private int page = 0;
    private NetWatchdog netWatchDog;
    private AlertDialog netChangeDialog;
    private boolean mAutoPlay = false;
    private Dialog mDialog;

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("播放");
        return true;
    }

    @Override
    protected void initData(Bundle arguments) {
        aliyunVodPlayer = new AliyunVodPlayer(mActivity);
        aliyunVodPlayer.setVolume(100);
        EventBus.getDefault().register(this);
        startNetWatch();
    }

    private void startNetWatch(){
        netWatchDog = new NetWatchdog(mActivity);
        netWatchDog.setNetChangeListener(new NetWatchdog.NetChangeListener() {
            @Override
            public void onWifiTo4G() {
                if (aliyunVodPlayer.isPlaying()) {
                    aliyunVodPlayer.pause();
                }
                if (netChangeDialog == null || !netChangeDialog.isShowing()) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
                    alertDialog.setTitle("切换到4G网络");
                    alertDialog.setMessage("是否继续播放");
                    alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            IAliyunVodPlayer.PlayerState playerState = aliyunVodPlayer.getPlayerState();
                            if(playerState == IAliyunVodPlayer.PlayerState.Idle ||
                                    playerState == IAliyunVodPlayer.PlayerState.Completed ||
                                    playerState == IAliyunVodPlayer.PlayerState.Stopped){
//                                mAutoPlay = true;
                                seekTo(seekBar.getProgress());
                                prepareAsync();
                            }
                            else if (playerState == AliyunVodPlayer.PlayerState.Paused) {
                                aliyunVodPlayer.resume();
                            } else {
                                seekTo(seekBar.getProgress());
                                aliyunVodPlayer.start();
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
        if(aliyunVodPlayer.isPlaying()) {
            aliyunVodPlayer.pause();
            playIv.setImageResource(R.drawable.bofanganniu);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(aliyunVodPlayer.isPlaying()) {
            aliyunVodPlayer.pause();
            playIv.setImageResource(R.drawable.bofanganniu);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        aliyunVodPlayer.stop();
        aliyunVodPlayer.release();
        stopUpdateTimer();
        progressUpdateTimer = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void videoInfoEventBus(PlayVideoBean.DataBean.HotBean videoInfo){
        this.videoInfo = videoInfo;
        if(videoInfo.getVideoPath() != null && videoInfo.getUId() != null){
            getPlayVideoEvent();
            getSTStoken();
        }else{
            showToast("数据出错");
        }
    }

    private void getSTStoken(){
        RxHttpUtils.createApi(ApiService.class)
                .getSTSToken()
                .compose(Transformer.<StsToken>switchSchedulers())
                .subscribe(new CommonObserver<StsToken>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast("认证获取失败");
                        if(mDialog.isShowing()){
                            mDialog.cancel();
                        }
                    }

                    @Override
                    protected void onSuccess(StsToken stsToken) {
                        if(stsToken != null){
                            StsToken.DataBean token = stsToken.getData().get(0);
                            Log.e("sunxj","accessKeyId="+ token.getAccessKeyId() + ",accesskeySecrty = " + token.getAccessKeySecret()
                                    + ",Expiration=" + token.getExpiration() + ",token=" + token.getSecurityToken());
                            preparePlayVideoView(token);
                        }
                    }
                });
    }

    private void getPlayVideoEvent(){
        RxHttpUtils.createApi(ApiService.class)
                .playVideo(videoInfo.getRId(),videoInfo.getUId())
                .compose(Transformer.<PlayVideoBean>switchSchedulers())
                .subscribe(new CommonObserver<PlayVideoBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Log.e("sunxj",errorMsg);
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

    private void initPlayVideo(){
        PlayVideoBean.DataBean.ResourceBean bean = playVideoObj.getResource();
        if(bean != null){
            playCountTv.setText(String.valueOf(bean.getShowCounts()) + "次");
            if(bean.getCreator() != null){
                authorNameTv.setText("作者：" + bean.getCreator().getUserName());
                authorLevelTv.setText(bean.getCreator().getUserLeval());
                authorOtherWorksTv.setText(bean.getCreator().getUserName() + ": 其它作品");
                if(playVideoObj.getResource().getCreator().getAttentioned() == 0){
                    mTv_guanZhu.setText("+ 关注");
                }else{
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
            aliyunVodPlayer.prepareAsync(mVidSts);
        }
    }

    //准备播放
    private void preparePlayVideoView(StsToken.DataBean sts){
        if(aliyunVodPlayer != null){
            aliyunVodPlayer.stop();
            playIv.setImageResource(R.drawable.bofanganniu);
        }
        if(surfaceView != null){
            surfaceView.setVisibility(View.GONE);
            surfaceView.setVisibility(View.VISIBLE);
        }
        mVidSts = new AliyunVidSts();
        mVidSts.setVid(videoInfo.getVideoPath());
        mVidSts.setAcId(sts.getAccessKeyId());
        mVidSts.setAkSceret(sts.getAccessKeySecret());
        mVidSts.setSecurityToken(sts.getSecurityToken());
        aliyunVodPlayer.prepareAsync(mVidSts);
        aliyunVodPlayer.setVolume(50);
    }

    private void playVideo(){
        aliyunVodPlayer.prepareAsync(mVidSts);
        isCompleted = false;
        mPlayerState = aliyunVodPlayer.getPlayerState();
        if(mPlayerState == Idle || mPlayerState == Stopped || mPlayerState == Completed){
            if(surfaceView != null){
                surfaceView.setVisibility(View.GONE);
                surfaceView.setVisibility(View.VISIBLE);
            }
            aliyunVodPlayer.stop();
            aliyunVodPlayer.prepareAsync(mVidSts);
        }else if (mPlayerState == Prepared){
            aliyunVodPlayer.start();
            playIv.setImageResource(R.drawable.bofangzanting);
        }else if (mPlayerState == Started){
            aliyunVodPlayer.pause();
            playIv.setImageResource(R.drawable.bofanganniu);
        }else if (mPlayerState == Paused){
            aliyunVodPlayer.resume();
            playIv.setImageResource(R.drawable.bofangzanting);
        }

    }

    private void initVideoView(){
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                aliyunVodPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                aliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(mActivity, VideoFullScreenActivity.class);
                i.putExtra("curPosition",aliyunVodPlayer.getCurrentPosition());
                startActivity(i);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(mVidSts);
                    }
                },500);*/
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
                if (aliyunVodPlayer != null) {
                    seekTo(seekBar.getProgress());
                    logStrs.add(format.format(new Date()) + "开始播放");
                    if (isCompleted) {
                        //播放完成了，不能seek了
                        inSeek = false;
                    } else {
                        inSeek = true;
                    }
                }
            }
        });

        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        aliyunVodPlayer.setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        aliyunVodPlayer.setVideoScalingMode(IAliyunVodPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        aliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                //准备完成触发
                if(mAutoPlay && mVidSts != null){
                    aliyunVodPlayer.start();
                }
            }
        });
        aliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {
                //首帧显示触发
                firstFrameStart();
            }
        });
        aliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int arg0, int arg1, String msg) {
                //出错时处理，查看接口文档中的错误码和错误消息
            }
        });
        aliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                //播放正常完成时触发
                isCompleted = true;
                inSeek = false;
                aliyunVodPlayer.prepareAsync(mVidSts);
                playIv.setImageResource(R.drawable.bofanganniu);
                showVideoProgressInfo();
                stopUpdateTimer();
            }
        });
        aliyunVodPlayer.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                //seek完成时触发
                logStrs.add(format.format(new Date()) + "播放完成");
                inSeek = false;
            }
        });
        aliyunVodPlayer.setOnStoppedListner(new IAliyunVodPlayer.OnStoppedListener() {
            @Override
            public void onStopped() {
                //使用stop功能时触发
            }
        });
        aliyunVodPlayer.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                //视频清晰度切换成功后触发
            }
            @Override
            public void onChangeQualityFail(int code, String msg) {
                //视频清晰度切换失败时触发
            }
        });

    }

    private void firstFrameStart() {

        //首帧显示之后再开始更新进度信息。
        inSeek = false;
        showVideoProgressInfo();
        Map<String, String> debugInfo = aliyunVodPlayer.getAllDebugInfo();
        long createPts = 0;
        if (debugInfo.get("create_player") != null) {
            String time = debugInfo.get("create_player");
            createPts = (long) Double.parseDouble(time);
            logStrs.add(format.format(new Date(createPts)) + "播放器创建成功");
        }
        if (debugInfo.get("open-url") != null) {
            String time = debugInfo.get("open-url");
            long openPts = (long) Double.parseDouble(time) + createPts;
            logStrs.add(format.format(new Date(openPts)) + "打开成功");
        }
        if (debugInfo.get("find-stream") != null) {
            String time = debugInfo.get("find-stream");
            long findPts = (long) Double.parseDouble(time) + createPts;
            logStrs.add(format.format(new Date(findPts)) + "获取视频流成功");
        }
        if (debugInfo.get("open-stream") != null) {
            String time = debugInfo.get("open-stream");
            long openPts = (long) Double.parseDouble(time) + createPts;
            logStrs.add(format.format(new Date(openPts)) + "开始打开媒体流");
        }
        logStrs.add(format.format(new Date()) + "显示首帧");
    }

    private void showVideoProgressInfo() {
        if (aliyunVodPlayer != null && !inSeek) {
            int curPosition = (int) aliyunVodPlayer.getCurrentPosition();
            currentPositionTv.setText(Formatter.formatTime(curPosition));
            int duration = (int) aliyunVodPlayer.getDuration();
            totalDurationTv.setText(Formatter.formatTime(duration));
            Log.d("sunxj", "duration = " + duration + " , curPosition = " + curPosition);
            int bufferPosition = aliyunVodPlayer.getBufferingPosition();
            seekBar.setMax(duration);
            seekBar.setSecondaryProgress(bufferPosition);
            seekBar.setProgress(curPosition);
        }
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
        progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
    }

    private void stopUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
    }

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            showVideoProgressInfo();
        }
    };

    private long lastSeekTime = -1;
    private void seekTo(int position) {
        if (aliyunVodPlayer == null) {
            return;
        }
        if (isCompleted) {
            inSeek = false;
            return ;
        }
        if (lastSeekTime < 0) {
            lastSeekTime = System.currentTimeMillis();

            inSeek = true;
            aliyunVodPlayer.seekTo(position);
        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastSeekTime > 1000) {//1000ms
                inSeek = true;
                aliyunVodPlayer.seekTo(position);
                lastSeekTime = currentTime;
            }
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
                EventBus.getDefault().post(dataBean);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
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
                EventBus.getDefault().post(dataBean);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
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

    private void finishRefresh(){
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        if(videoInfo != null){
            getPlayVideoEvent();
        }else{
            finishRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.author_layout, R.id.tv_guanzhu,R.id.play_iv,R.id.previous_iv,R.id.next_iv,R.id.hot_list_reflash_ly})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.author_layout:
                    if(playVideoObj == null || playVideoObj.getResource() == null){
                        showToast("没有作者数据");
                        return;
                    }
                    Intent intent = new Intent(mActivity, VideoAuthorHomePageActivity.class);
                    intent.putExtra("author",playVideoObj.getResource().getCreator());
                    startActivity(intent);
                    break;
                case R.id.tv_guanzhu:
                    guanZhuEvent();
                    break;
                case R.id.play_iv:
                    playVideo();
                    break;
                case R.id.previous_iv:
                    seekTo(seekBar.getProgress() - 1000);
                    break;
                case R.id.next_iv:
                    seekTo(seekBar.getProgress() + 1000);
                    break;
                case R.id.hot_list_reflash_ly:
                    getHostlistEvent();
                    break;
            }
        }
    }

    private void guanZhuEvent() {
        if(playVideoObj == null || playVideoObj.getResource() == null || playVideoObj.getResource().getCreator() == null){
            showToast("没有作者数据");
            return;
        }
        final int isFocus = playVideoObj.getResource().getCreator().getAttentioned();
        String uId = UserInfoUtil.getUserInfoBean(mActivity).getUId();
        String attentionId = playVideoObj.getResource().getCreator().getUId();
        if(attentionId == null){
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

    private void getHostlistEvent(){
        if(playVideoObj == null || playVideoObj.getResource() == null){
            showToast("没有更多数据");
            return;
        }
        mDialog = SDDialogUtil.newLoading(mActivity,"正在加载，请稍后");
        mDialog.show();
        page++;
        String scId = playVideoObj.getResource().getScId(); //当前播放视频的scId  获取热门视频列表
        RxHttpUtils.createApi(ApiService.class)
                .getHotList(scId,String.valueOf(10),String.valueOf(page))
                .compose(Transformer.<TwoVideoListBean>switchSchedulers())
                .subscribe(new CommonObserver<TwoVideoListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        if (mDialog.isShowing()){
                            mDialog.cancel();
                        }
                    }

                    @Override
                    protected void onSuccess(TwoVideoListBean videoListBean) {
                        if (mDialog.isShowing()){
                            mDialog.cancel();
                        }
                        if(videoListBean != null && videoListBean.responseState.equals("1")){
                            dataHotArray = videoListBean.data;
                            hotRecommendAdapter.setNewData(dataHotArray);
                            if(videoListBean.data.size() < 1){
                                page = 0;
                            }
                        }
                    }
                });
    }

}
