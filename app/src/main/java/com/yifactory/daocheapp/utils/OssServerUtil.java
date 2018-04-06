package com.yifactory.daocheapp.utils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.yifactory.daocheapp.app.AppCtx;

public class OssServerUtil {

    public OSS oss;

    private OssServerUtil() {
        initOssClient();
    }

    private void initOssClient() {

        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OssUploadImgUtil.ACCESS_KEY_ID, OssUploadImgUtil.ACCESS_KEY_SECRET);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(AppCtx.getC(), OssUploadImgUtil.ENDPOINT, credentialProvider, conf);
    }

    public static OssServerUtil getSingleTon() {
        return OssServerUtilSingleTon.sOssServerUtil;
    }

    private static class OssServerUtilSingleTon {
        private static OssServerUtil sOssServerUtil = new OssServerUtil();
    }
}
