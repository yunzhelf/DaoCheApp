package com.yifactory.daocheapp.biz.my_function.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClient;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.SvideoInfo;
import com.alibaba.sdk.android.vod.upload.session.VodHttpClientConfig;
import com.alibaba.sdk.android.vod.upload.session.VodSessionCreateInfo;
import com.aliyun.demo.importer.MediaActivity;
import com.aliyun.demo.recorder.AliyunVideoRecorder;
import com.aliyun.struct.common.CropKey;
import com.aliyun.struct.common.ScaleMode;
import com.aliyun.struct.common.VideoQuality;
import com.aliyun.struct.encoder.VideoCodecs;
import com.aliyun.struct.recorder.CameraType;
import com.aliyun.struct.recorder.FlashType;
import com.aliyun.struct.snap.AliyunSnapVideoParam;
import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.GetCategoryListBean;
import com.yifactory.daocheapp.bean.StsToken;
import com.yifactory.daocheapp.bean.TwoVideoListBean;
import com.yifactory.daocheapp.bean.VideoListBean;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.CustomPopWindow;
import com.yifactory.daocheapp.widget.TitleBar;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MyLecturerUploadVideoActivity extends BaseActivity {
    @BindView(R.id.rootLayout)
    AutoLinearLayout mRootViewLayout;
    @BindView(R.id.upload_video_choose_iv)
    ImageView videoThumbnailIv;
    @BindView(R.id.upload_video_title_ev)
    EditText videoTitleEt;
    @BindView(R.id.upload_video_second_title_ev)
    EditText videoSecondEt;
    @BindView(R.id.upload_video_gold_et)
    EditText videoGoldEt;
    @BindView(R.id.upload_video_introduction_et)
    EditText videoIntroductionEt;
    @BindView(R.id.upload_video_content_et)
    EditText videoContentEt;
    @BindView(R.id.upload_video_suitPerson_et)
    EditText videoSuitPersonEt;
    @BindView(R.id.upload_video_harvest_et)
    EditText videoHarvestEt;
    @BindView(R.id.etSex)
    TextView videoTypeTv;
    @BindView(R.id.upload_video_processbar)
    ProgressBar progressBar;

    private CustomPopWindow mCustomPopWindow_SelectedVideo;
    private String videoPath;
    private String videoThumbnail;
    private long videoDuration;
    private VODSVideoUploadClient vClient; //上传video客户端
    private String TAG = "uploadVideoAct";
    private String[] mEffDirs;
    private Dialog mDialog;

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setLeftImageResource(R.drawable.fanhui);
        bar.setTitleText("讲师");
        return true;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Intent i = getIntent();
        if( !TextUtils.isEmpty(i.getStringExtra("vpath"))){
            videoPath = i.getStringExtra("vpath");
            videoThumbnail = i.getStringExtra("vthum");
            videoDuration = i.getLongExtra("videoDuration",0);
        }
        if(videoThumbnail != null){
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true);

            Glide.with(this)
                    .load(videoThumbnail)
                    .apply(options)
                    .into(videoThumbnailIv);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void videoTypeEvent(GetCategoryListBean.DataBean.CategorySecondListBean secondListBean){
        if(secondListBean != null){
            videoTypeTv.setText(secondListBean.getSecondContent());
            videoTypeTv.setTag(secondListBean.getScId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vClient.release();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        vClient = new VODSVideoUploadClientImpl(this);
        vClient.init();
        EventBus.getDefault().register(this);
        initAssetPath();
    }

    private void selectVideoPopupWindow() {
        if (mCustomPopWindow_SelectedVideo == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.popup_window_select_pic, null);
            TextView localTv = (TextView) contentView.findViewById(R.id.local_tv);
            TextView takePhotoTv = (TextView) contentView.findViewById(R.id.take_photo_tv);
            TextView cancelTv = (TextView) contentView.findViewById(R.id.cancel_tv);

            mCustomPopWindow_SelectedVideo = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.5f)
                    .create()
                    .showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCustomPopWindow_SelectedVideo != null) {
                        mCustomPopWindow_SelectedVideo.dissmiss();
                    }
                    switch (v.getId()) {
                        case R.id.take_photo_tv:
                                takeCamera();
                            break;
                        case R.id.local_tv:
                                takeVideo();
                            break;
                    }
                }
            };
            localTv.setOnClickListener(listener);
            takePhotoTv.setOnClickListener(listener);
            cancelTv.setOnClickListener(listener);
        } else {
            mCustomPopWindow_SelectedVideo.showAtLocation(mRootViewLayout, Gravity.BOTTOM, 0, 0);
        }
    }

    private void takeVideo() {
        Intent edit = new Intent(this,MediaActivity.class);
        edit.putExtra(CropKey.VIDEO_RATIO, CropKey.RATIO_MODE_3_4);
        edit.putExtra(CropKey.VIDEO_SCALE,CropKey.SCALE_CROP);
        edit.putExtra(CropKey.VIDEO_QUALITY , VideoQuality.HD);
        edit.putExtra(CropKey.VIDEO_FRAMERATE,25); //默认25  建议20-30
        edit.putExtra(CropKey.VIDEO_GOP,125); //建议1-300 默认125
        edit.putExtra(CropKey.VIDEO_BITRATE, 0);  //
        startActivity(edit);
    }

    private void takeCamera() {
        AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
                .setResolutionMode(AliyunSnapVideoParam.RESOLUTION_540P)
                .setRatioMode(AliyunSnapVideoParam.RATIO_MODE_3_4)
                .setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)
                .setSortMode(AliyunSnapVideoParam.SORT_MODE_VIDEO)
                .setFilterList(mEffDirs)
                .setBeautyLevel(80)
                .setBeautyStatus(true)
                .setCameraType(CameraType.BACK)
                .setFlashType(FlashType.OFF)
                .setNeedClip(true)
                .setMaxDuration(30*60*1000)
                .setMinDuration(2000)
                .setVideoQuality(VideoQuality.HD)
                .setGop(5)
                .setVideoBitrate(0)
                .setVideoCodec(VideoCodecs.H264_HARDWARE)
                /**
                 * 裁剪参数
                 */
                .setMinVideoDuration(4000)
                .setMaxVideoDuration(29 * 1000)
                .setMinCropDuration(3000)
                .setFrameRate(25)
                .setCropMode(ScaleMode.PS)
                .build();
        AliyunVideoRecorder.startRecord(this,recordParam);

    }

    private void getSTStoken(){
        String uId = UserInfoUtil.getUserInfoBean(this).getUId();
        if(uId == null){
            showToast("没有用户信息");
            return;
        }
        String title = videoTitleEt.getText().toString().trim();
        if(TextUtils.isEmpty(title)){
            showToast("请输入视频标题");
            return;
        }
        String secondTitle = videoSecondEt.getText().toString().trim();
        if(TextUtils.isEmpty(secondTitle)){
            showToast("请输入视频副标题");
            return;
        }
        if(videoTypeTv.getTag() == null){
            showToast("请选择视频类型");
            return;
        }
        mDialog = SDDialogUtil.newLoading(MyLecturerUploadVideoActivity.this,"正在上传视频,请稍后");
        mDialog.show();
        RxHttpUtils.createApi(ApiService.class)
                .getSTSToken()
                .compose(Transformer.<StsToken>switchSchedulers())
                .subscribe(new CommonObserver<StsToken>() {
                    @Override
                    protected void onError(String errorMsg) {
                        if(mDialog.isShowing()){
                            mDialog.cancel();
                        }
                        Log.e("sunxj","sts error");
                    }

                    @Override
                    protected void onSuccess(StsToken stsToken) {
                        if(stsToken != null){
                            StsToken.DataBean token = stsToken.getData().get(0);
                            Log.e(TAG,"accessKeyId="+ token.getAccessKeyId() + ",accesskeySecrty = " + token.getAccessKeySecret()
                                                    + ",Expiration=" + token.getExpiration() + ",token=" + token.getSecurityToken());
                            upLoadVideoEvent(token);
                        }
                    }
                });
    }

    private void upLoadVideoEvent(final StsToken.DataBean token){
        if(vClient == null){
            vClient = new VODSVideoUploadClientImpl(MyLecturerUploadVideoActivity.this);
            vClient.init();
        }
        if(videoPath != null && videoThumbnail != null){
            progressBar.setVisibility(View.VISIBLE);
            VodHttpClientConfig vodHttpClientConfig = new VodHttpClientConfig.Builder()
                    .setMaxRetryCount(2)//重试次数
                    .setConnectionTimeout(20 * 1000)//连接超时
                    .setSocketTimeout(20 * 1000)//socket超时
                    .build();

            SvideoInfo svideoInfo = new SvideoInfo();
            svideoInfo.setTitle(new File(videoPath).getName());
            svideoInfo.setDesc("");
            svideoInfo.setCateId(1);

            VodSessionCreateInfo vodSessionCreateInfo = new VodSessionCreateInfo.Builder()
                    .setImagePath(videoThumbnail)//图片地址
                    .setVideoPath(videoPath)//视频地址
                    .setAccessKeyId(token.getAccessKeyId())//临时accessKeyId
                    .setAccessKeySecret(token.getAccessKeySecret())//临时accessKeySecret
//                    .setIsTranscode(true)//是否转码.如开启转码请AppSever务必监听服务端转码成功的通知
                    .setSecurityToken(token.getSecurityToken())
                    .setExpriedTime(token.getExpiration())
                    .setSvideoInfo(svideoInfo)//短视频视频信息
                    .setVodHttpClientConfig(vodHttpClientConfig)//网络参数
                    .build();
            vClient.uploadWithVideoAndImg(vodSessionCreateInfo, new VODSVideoUploadCallback() {
                @Override
                public void onUploadSucceed(String videoId, String imageUrl) {
                    Log.e("sunxj", "videoId = " + videoId + ", imageUrl = " + imageUrl);
                    videoThumbnail = null;
                    videoPath = null;
                    upLoadTeachReason(videoId,imageUrl);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onUploadFailed(String code, String message) {
                    Log.e("sunxj", "code = " + code + ", message = " + message);
                    if(mDialog.isShowing()){
                        mDialog.cancel();
                    }
                    Log.e("sunxj","upload failed");
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onUploadProgress(long uploadedSize, long totalSize) {
                    int progress = (int)(uploadedSize*1.0*100/totalSize);
                    Log.e("sunxj", "uploadedSize = " + uploadedSize + ", totalSize = " + totalSize +", progress:" + progress);
                    progressBar.setProgress(progress);
                }

                @Override
                public void onSTSTokenExpried() {
                    refreshTokenEvent();
                    Log.e("sunxj","ststoken expried");
                }

                @Override
                public void onUploadRetry(String code, String message) {
                    Log.e(TAG, "====code = " + code + ", message = " + message);
                    Log.e("sunxj", "重试");
                }

                @Override
                public void onUploadRetryResume() {
                    Log.e("sunxj", "重试resume");

                }
            });
        }else{
            showToast("上传失败");
            if(mDialog.isShowing()){
                mDialog.dismiss();
            }
        }
    }

    private void upLoadTeachReason(String videoId, final String imageUrl){
        try{
            String uId = UserInfoUtil.getUserInfoBean(this).getUId();
            Map<String,RequestBody> param = new HashMap<>();
            RequestBody scIdBody = RequestBody.create(MediaType.parse("text/plain"),videoTypeTv.getTag().toString()); //scid
            RequestBody uIdBody = RequestBody.create(MediaType.parse("text/plain"),uId);
            RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"),videoTitleEt.getText().toString().trim());
            RequestBody secondBody = RequestBody.create(MediaType.parse("text/plain"),videoSecondEt.getText().toString().trim());
            RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"),videoContentEt.getText().toString());
            RequestBody introductionBody = RequestBody.create(MediaType.parse("text/plain"),videoIntroductionEt.getText().toString());
            RequestBody harvestBody = RequestBody.create(MediaType.parse("text/plain"),videoHarvestEt.getText().toString());
            RequestBody suitPersonBody = RequestBody.create(MediaType.parse("text/plain"),videoSuitPersonEt.getText().toString());
            RequestBody videoIdBody = RequestBody.create(MediaType.parse("text/plain"),videoId);
            RequestBody videoDurationBody = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(videoDuration)); //
            RequestBody goldBody = RequestBody.create(MediaType.parse("text/plain"),videoGoldEt.getText().toString());
            RequestBody imageUrlBody = RequestBody.create(MediaType.parse("text/plain"),imageUrl);
            param.put("scId",scIdBody);
            param.put("uId",uIdBody);
            param.put("title",titleBody);
            param.put("secondTitle",secondBody);
            param.put("videoContent",introductionBody);
            param.put("videoDetail",contentBody);
            param.put("harvest",harvestBody);
            param.put("suitPerson",suitPersonBody);
            param.put("videoPath",videoIdBody);
            param.put("indexImg",imageUrlBody);
            param.put("totalMinute",videoDurationBody);
            param.put("goldCount",goldBody);
            RxHttpUtils.createApi(ApiService.class)
                    .addTeachReason(param)
                    .compose(Transformer.<TwoVideoListBean>switchSchedulers())
                    .subscribe(new CommonObserver<TwoVideoListBean>() {
                        @Override
                        protected void onError(String errorMsg) {
                            Log.e(TAG,errorMsg);
                            if(mDialog.isShowing()){
                                mDialog.cancel();
                            }
                            Log.e("sunxj","add teacher error");
                        }

                        @Override
                        protected void onSuccess(TwoVideoListBean videoListBean) {
                            if(mDialog.isShowing()){
                                mDialog.cancel();
                            }
                            if(videoListBean != null && videoListBean.responseState.equals("1")){
                                showToast("上传成功");
                                EventBus.getDefault().post(videoListBean.data.get(0));
                                Message msg = Message.obtain();
                                msg.what = 1;
                                msg.obj = imageUrl;
                                mHandler.sendMessage(msg);
                            }
                        }
                    });
        }catch (Exception e){
            if(mDialog.isShowing()){
                mDialog.cancel();
            }
        }

    }

    private void refreshTokenEvent(){
        RxHttpUtils.createApi(ApiService.class)
                .getSTSToken()
                .compose(Transformer.<StsToken>switchSchedulers())
                .subscribe(new CommonObserver<StsToken>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(StsToken stsToken) {
                        if(stsToken != null){
                            StsToken.DataBean token = stsToken.getData().get(0);
                            vClient.refreshSTSToken(token.getAccessKeyId(),token.getAccessKeySecret(),token.getSecurityToken(),token.getExpiration());
                        }
                    }
                });
    }

    private void initAssetPath(){
//        String path = StorageUtils.getCacheDirectory(this).getAbsolutePath() + File.separator+ Common.QU_NAME + File.separator;
        File videoDir = null;
        String filePath = Environment.getExternalStorageDirectory().toString() + "/daocheapp/thumbnail";
        videoDir = new File(filePath);
        if( !videoDir.exists()){
            videoDir.mkdir();
        }
        String path = filePath + File.separator;
        File filter = new File(new File(path), "filter");

        String[] list = filter.list();
        if(list == null || list.length == 0){
            return ;
        }
        mEffDirs = new String[list.length + 1];
        mEffDirs[0] = null;
        for(int i = 0; i < list.length; i++){
            mEffDirs[i + 1] = filter.getPath() + "/" + list[i];
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_lecturer_upload_video;
    }

    @OnClick({R.id.naviFrameLeft,R.id.upload_video_btn,R.id.upload_video_choose_iv,R.id.upload_video_type_ly})
    public void onClickEvent(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.naviFrameLeft:
                    finish();
                    break;
                case R.id.upload_video_btn:
                    getSTStoken();
                    break;
                case R.id.upload_video_choose_iv:
                    MyLecturerUploadVideoActivityPermissionsDispatcher.readAndWriteNeedsPermissionWithCheck(MyLecturerUploadVideoActivity.this);
                    break;
                case R.id.upload_video_type_ly:
                    Intent intent = new Intent(this,MyVideoTypeListActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO})
    void readAndWriteNeedsPermission() {
        selectVideoPopupWindow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyLecturerUploadVideoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    })
    void readAndWriteOnShowRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("提示：应用必要权限，没有此权限，您将无法上传头像！")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("去申请", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO})
    void readAndWriteOnPermissionDenied() {
        showLongToast("您拒绝了我们必要的权限，没有此权限，您将无法上传头像！");
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO})
    void readAndWriteOnNeverAskAgain() {
        showLongToast("您拒绝了我们必要的权限，如有需要请前往设置中打开“存储”权限！");
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    File file = new File((String)msg.obj);
                    if(file.exists()){
                        file.delete();
                    }
                    onBackPressed();
                    break;
            }
        }
    };
}
