package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.activity.BaseActivity;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;
import com.yifactory.daocheapp.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRecommendBannerDetailActivity extends BaseActivity {
    @BindView(R.id.head_detail_wv)
    WebView webView;
    GetSystemInfoBean.DataBean.BannersBean bannersBean;
    TitleBar titleBar;
    @Override
    protected boolean buildTitle(TitleBar bar) {
        titleBar = bar;
        titleBar.setTitleText("详情");
        titleBar.setLeftImageResource(R.drawable.fanhui);
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        bannersBean = (GetSystemInfoBean.DataBean.BannersBean) getIntent().getSerializableExtra("banner");
    }

    @Override
    protected void initView() {
        if(bannersBean != null && bannersBean.getTId() != null){
            webView.loadUrl(bannersBean.getTId());
        }
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
        webSettings.setJavaScriptEnabled(true);//设置能够解析Javascript
        webSettings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(webView != null){
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(webView != null){
            webView.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(webView != null){
            webView.destroy();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webView != null){
            webView.destroy();
        }
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_recommend_head_line_detail;
    }

    @OnClick({R.id.naviButtonLeft})
    public void onClickEvent(View v){
        if(v != null){
            switch (v.getId()){
                case R.id.naviButtonLeft:
                    finish();
                    break;
            }
        }
    }

    private WebViewClient webViewClient=new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
//            progressBar.setVisibility(View.GONE);
            Log.e("sunxj","加载完成");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
//            progressBar.setVisibility(View.VISIBLE);
            Log.e("sunxj","开始加载");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen","拦截url:"+url);
            if(url.equals("http://www.google.com/")){
                Toast.makeText(HomeRecommendBannerDetailActivity.this,"国内不能访问google,拦截该url",Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient=new WebChromeClient(){
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定",null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen","网页标题:"+title);
            titleBar.setTitleText(title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            progressBar.setProgress(newProgress);
            Log.e("sunxj","加载进度：" + newProgress);
        }
    };
}
