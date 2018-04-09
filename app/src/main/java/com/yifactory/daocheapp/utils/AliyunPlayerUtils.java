package com.yifactory.daocheapp.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.alivc.player.AliVcMediaPlayer;
import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AliyunPlayerUtils {
    public static int PREPARE = 0;
    public static int SHOW_PROGRESS = 1;
    public static int COMPLETE = 2;
    public static int COMPLETE_SEEK = 3;
    public static Context context;
    public static AliyunVodPlayer aliyunVodPlayer;
    public static AliyunVidSts mVidSts;
    public static boolean inSeek = false;
    public static boolean isCompleted = false;
    public static boolean mAutoPlay = false;
    public static List<String> logStrs = new ArrayList<>();
    public static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
    public static Handler handler = new Handler();
    public static void initAliyunVideo() {
        System.loadLibrary("live-openh264");
        System.loadLibrary("QuCore-ThirdParty");
        System.loadLibrary("QuCore");
        QupaiHttpFinal.getInstance().initOkHttpFinal();
        com.aliyun.vod.common.httpfinal.QupaiHttpFinal.getInstance().initOkHttpFinal();
        AliVcMediaPlayer.init(context);
        aliyunVodPlayer = new AliyunVodPlayer(context);
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        aliyunVodPlayer.setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        aliyunVodPlayer.setVideoScalingMode(IAliyunVodPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        aliyunVodPlayer.setVolume(100);
        aliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                //准备完成触发
                handler.sendEmptyMessage(PREPARE);
                if(mAutoPlay && mVidSts != null){
                    aliyunVodPlayer.start();
                }
            }
        });
        AliyunPlayerUtils.aliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {
                //首帧显示触发
                    firstFrameStart();
            }
        });
        AliyunPlayerUtils.aliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int arg0, int arg1, String msg) {
                //出错时处理，查看接口文档中的错误码和错误消息
            }
        });
        AliyunPlayerUtils.aliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                //播放正常完成时触发
                    AliyunPlayerUtils.isCompleted = true;
                    AliyunPlayerUtils.inSeek = false;
                    AliyunPlayerUtils.aliyunVodPlayer.prepareAsync(mVidSts);
                    handler.sendEmptyMessage(COMPLETE);
                    showVideoProgressInfo();
                stopUpdateTimer();
            }
        });
        AliyunPlayerUtils.aliyunVodPlayer.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                //seek完成时触发
                AliyunPlayerUtils.inSeek = false;
            }
        });
        AliyunPlayerUtils.aliyunVodPlayer.setOnStoppedListner(new IAliyunVodPlayer.OnStoppedListener() {
            @Override
            public void onStopped() {
                //使用stop功能时触发
            }
        });
        AliyunPlayerUtils.aliyunVodPlayer.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
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

    public static void firstFrameStart() {

        //首帧显示之后再开始更新进度信息。
        AliyunPlayerUtils.inSeek = false;
        showVideoProgressInfo();
        Map<String, String> debugInfo = AliyunPlayerUtils.aliyunVodPlayer.getAllDebugInfo();
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

    public static void showVideoProgressInfo() {
        handler.sendEmptyMessage(SHOW_PROGRESS);
        startUpdateTimer();
    }

    public static void startUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
        progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
    }

    public static void stopUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
    }

    public static Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            showVideoProgressInfo();
        }
    };

    public static void relese(){
        aliyunVodPlayer.release();
    }

    public static long lastSeekTime = -1;
    public static void seekTo(int position) {
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
}
