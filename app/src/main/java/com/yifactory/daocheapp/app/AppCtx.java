package com.yifactory.daocheapp.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.alivc.player.AliVcMediaPlayer;
import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.allen.retrofit.RxHttpUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.ninegrid.NineGridView;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiConstant;
import com.yifactory.daocheapp.utils.AliyunPlayerUtils;
import com.zhy.autolayout.config.AutoLayoutConifg;

public class AppCtx extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context = null;

    public static boolean isHomeTabPlayed = false;

    public static boolean isHomeRecommendTabPlayed = false;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        initAliyunVideo();
        setDefaultFont();

        AliyunPlayerUtils.context = context;
        AliyunPlayerUtils.initAliyunVideo();

        AutoLayoutConifg.getInstance().useDeviceSize();

        initRxHttpUtils();

        NineGridView.setImageLoader(new GlideImageLoader());
    }

    private static class GlideImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).setDefaultRequestOptions(new RequestOptions()
                    .placeholder(R.drawable.ic_default_color)
                    .error(R.drawable.ic_default_color)
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .load(url)
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    public static Context getC() {
        return context;
    }

    private void initRxHttpUtils() {

        RxHttpUtils.init(this);

        RxHttpUtils
                .getInstance()
                //开启全局配置
                .config()
                //全局的BaseUrl
                .setBaseUrl(ApiConstant.BASE_URL)
                //全局的请求头信息
                //.setHeaders(headerMaps)
                //全局持久话cookie,保存本地每次都会携带在header中
                .setCookie(false)
                //全局ssl证书认证
                //信任所有证书,不安全有风险
                .setSslSocketFactory()
                //使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.cer"))
                //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.bks"), "123456", getAssets().open("your.cer"))
                //全局超时配置
                .setReadTimeout(10)
                //全局超时配置
                .setWriteTimeout(10)
                //全局超时配置
                .setConnectTimeout(10)
                //全局是否打开请求log日志
                .setLog(true);
    }

    private void setDefaultFont() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    private void initAliyunVideo() {
        System.loadLibrary("live-openh264");
        System.loadLibrary("QuCore-ThirdParty");
        System.loadLibrary("QuCore");
        QupaiHttpFinal.getInstance().initOkHttpFinal();
        com.aliyun.vod.common.httpfinal.QupaiHttpFinal.getInstance().initOkHttpFinal();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
