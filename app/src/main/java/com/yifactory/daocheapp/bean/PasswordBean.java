package com.yifactory.daocheapp.bean;

import java.util.List;

/**
 * Created by sunxj on 2018/3/17.
 */

public class PasswordBean {

    /**
     * data : {}
     * msg : 修改成功
     * responseState : 1
     */

    private List<DataBean> data;
    private String msg;
    private String responseState;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

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

    public static class DataBean {
    }
}
