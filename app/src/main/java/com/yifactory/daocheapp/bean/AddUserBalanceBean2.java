package com.yifactory.daocheapp.bean;

import java.util.List;

public class AddUserBalanceBean2 {


    /**
     * data : ["alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018020802162154&biz_content=%7B%22body%22%3A%22%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%222bd243e8eb9448009d7d36989552820a%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2230.0%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F192.168.1.103%3A8080%2Faskcar%2Fpay%2Fsync%2FALUserBalancePayOrderSyncAddress.vnb&sign=tJ%2Fm6o9J1m3OnKWu3CJhFsgezBV9LUHkmYHdCh98qzZgLZj9n8nizs%2FDUAeNcKW1x5K3JXXEMWcztggEGgIDFvPJ9lD7qiV3oSoePOCxshBgRjgIgQlCTASxna%2BpTjTgFKvbd6jThtN7FK76tGks8%2FyWtjj8NH7y8py4QucywyV5MV1kX5MvIW434WV83IzESWPiiUQXrb1wREppPTxlJv7OesljAmAv1Bi3LT1YHl9BB0qRBWKYeJRviZDPm60lM4z%2FcNmTmXmo5y4EtIh%2F8xptI9BrRNE08CXdMOi%2FVvYySB1HOr52U0fvxsJ64A7BOjnE8sXMrgOqhFV%2BXI3ceA%3D%3D&sign_type=RSA2&timestamp=2018-03-23+13%3A46%3A59&version=1.0"]
     * msg : 发起成功
     * responseState : 1
     */

    private String msg;
    private String responseState;
    private List<String> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponseState() {
        return responseState;
    }

    public void setResponseState(String responseState) {
        this.responseState = responseState;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
