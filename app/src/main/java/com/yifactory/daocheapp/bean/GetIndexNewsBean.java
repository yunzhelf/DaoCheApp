package com.yifactory.daocheapp.bean;

import java.util.List;

/**
 * Created by sunxj on 2018/4/13.
 */

public class GetIndexNewsBean {
    private String msg;
    private String responseState;
    private List<GetSystemInfoBean.DataBean.SysIndexNewsBean> data;

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

    public List<GetSystemInfoBean.DataBean.SysIndexNewsBean> getData() {
        return data;
    }

    public void setData(List<GetSystemInfoBean.DataBean.SysIndexNewsBean> data) {
        this.data = data;
    }
}
