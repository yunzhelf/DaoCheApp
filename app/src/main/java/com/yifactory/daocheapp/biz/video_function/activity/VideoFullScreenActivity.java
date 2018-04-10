package com.yifactory.daocheapp.biz.video_function.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
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
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.AddStudyRecordBean;
import com.yifactory.daocheapp.utils.AliyunPlayerUtils;
import com.yifactory.daocheapp.utils.Formatter;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.TitleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Completed;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Idle;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Paused;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Prepared;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Started;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Stopped;

public class VideoFullScreenActivity extends BaseActivity {

    @BindView(R.id.full_surfaceview)
    SurfaceView surfaceView;
    @BindView(R.id.full_pause_iv)
    ImageView playIv;
    @BindView(R.id.full_curTime)
    TextView curTimeTv;
    @BindView(R.id.full_totalTime)
    TextView totalTimeTv;
    @BindView(R.id.full_seekbar)
    SeekBar seekBar;
    private IAliyunVodPlayer.PlayerState mPlayerState;
    private AliyunVidSts mVidSts;
    private boolean inSeek = false;
    private NetWatchdog netWatchDog;
    private AlertDialog netChangeDialog;
    private long studyTime;
    private String rId;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
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
        return false;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().init();
        AliyunPlayerUtils.handler = handler;
        IAliyunVodPlayer.PlayerState state = AliyunPlayerUtils.aliyunVodPlayer.getPlayerState();
        if(state == Idle || state == Completed || state == Stopped || state == Prepared){
            playIv.setImageResource(R.drawable.bofanganniu);
        }
        rId = getIntent().getStringExtra("rId");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getVidsEventBus(AliyunVidSts mVidSts){
        this.mVidSts = mVidSts;
    }

    @Override
    protected void initView() {
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
                Log.d("Full", "surfaceDestroyed");
            }
        });
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playIv.getVisibility() == View.VISIBLE){
                    playIv.setVisibility(View.GONE);
                }else{
                    playIv.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playIv.setVisibility(View.GONE);

                        }
                    },2000);
                }
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
                    if (AliyunPlayerUtils.isCompleted) {
                        //播放完成了，不能seek了
                        AliyunPlayerUtils.inSeek = false;
                    } else {
                        AliyunPlayerUtils.inSeek = true;
                    }
                }
            }
        });
        studyTime = System.currentTimeMillis();
    }
    @Override
    protected void addListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(rId != null && UserInfoUtil.getUserInfoBean(this) != null){
            long curtime = System.currentTimeMillis();
            addStudyReocrd(curtime - studyTime);
        }
    }

    private void addStudyReocrd(long lastTime){
        String uId = UserInfoUtil.getUserInfoBean(this).getUId();
        String time = String.valueOf(lastTime/1000);

        RxHttpUtils.createApi(ApiService.class)
                .addStudyRecord(rId,uId,time)
                .compose(Transformer.<AddStudyRecordBean>switchSchedulers())
                .subscribe(new CommonObserver<AddStudyRecordBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(AddStudyRecordBean addStudyRecordBean) {
                        Log.e("videoFragment",addStudyRecordBean.getMsg());
                    }
                });

    }

    private void showVideoProgressInfo() {
        if (AliyunPlayerUtils.aliyunVodPlayer != null && !inSeek) {
            int curPosition = (int) AliyunPlayerUtils.aliyunVodPlayer.getCurrentPosition();
            Log.e("Full","curPosition = " + curPosition);
            curTimeTv.setText(Formatter.formatTime(curPosition));
            int duration = (int) AliyunPlayerUtils.aliyunVodPlayer.getDuration();
            totalTimeTv.setText(Formatter.formatTime(duration));
            int bufferPosition = AliyunPlayerUtils.aliyunVodPlayer.getBufferingPosition();
            seekBar.setMax(duration);
            seekBar.setSecondaryProgress(bufferPosition);
            seekBar.setProgress(curPosition);
        }
    }

    private void playVideo(){
        AliyunPlayerUtils.isCompleted = false;
        mPlayerState = AliyunPlayerUtils.aliyunVodPlayer.getPlayerState();
        if(mPlayerState == Idle || mPlayerState == Stopped || mPlayerState == Completed){
            if(surfaceView != null){
                surfaceView.setVisibility(View.GONE);
                surfaceView.setVisibility(View.VISIBLE);
            }
            AliyunPlayerUtils.aliyunVodPlayer.stop();
            AliyunPlayerUtils.aliyunVodPlayer.prepareAsync(mVidSts);
        }else if (mPlayerState == Prepared){
            AliyunPlayerUtils.aliyunVodPlayer.start();
            playIv.setImageResource(R.drawable.bofangzanting);
        }else if (mPlayerState == Started){
            AliyunPlayerUtils.aliyunVodPlayer.pause();
            playIv.setImageResource(R.drawable.bofanganniu);
        }else if (mPlayerState == Paused){
            AliyunPlayerUtils.aliyunVodPlayer.resume();
            playIv.setImageResource(R.drawable.bofangzanting);
        }

    }

    private void startNetWatch(){
        netWatchDog = new NetWatchdog(this);
        netWatchDog.setNetChangeListener(new NetWatchdog.NetChangeListener() {
            @Override
            public void onWifiTo4G() {
                if (AliyunPlayerUtils.aliyunVodPlayer.isPlaying()) {
                    AliyunPlayerUtils.aliyunVodPlayer.pause();
                }
                if (netChangeDialog == null || !netChangeDialog.isShowing()) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(VideoFullScreenActivity.this);
                    alertDialog.setTitle("切换到4G网络");
                    alertDialog.setMessage("是否继续播放");
                    alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            IAliyunVodPlayer.PlayerState playerState = AliyunPlayerUtils.aliyunVodPlayer.getPlayerState();
                            if(playerState == IAliyunVodPlayer.PlayerState.Idle ||
                                    playerState == IAliyunVodPlayer.PlayerState.Completed ||
                                    playerState == IAliyunVodPlayer.PlayerState.Stopped){
//                                mAutoPlay = true;
                                AliyunPlayerUtils.seekTo(seekBar.getProgress());
                                if (mVidSts != null) {
                                    AliyunPlayerUtils.aliyunVodPlayer.prepareAsync(mVidSts);
                                }
                            }
                            else if (playerState == AliyunVodPlayer.PlayerState.Paused) {
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

                Toast.makeText(getApplicationContext(), "切换到4G网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void on4GToWifi() {
                Toast.makeText(getApplicationContext(), "wifi网络已连接", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNetDisconnected() {
                Toast.makeText(getApplicationContext(), "网络已断开", Toast.LENGTH_SHORT).show();
            }
        });
        netWatchDog.startWatch();
    }

    @OnClick({R.id.full_back, R.id.full_pause})
    public void onClickEvent(View view){
        if(view != null){
            switch (view.getId()){
                case R.id.full_back:
                    onBackPressed();
                    break;
                case R.id.full_pause:
                    playVideo();
                    break;
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_full_screen;
    }
}
