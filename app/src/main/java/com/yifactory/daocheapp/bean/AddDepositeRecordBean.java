package com.yifactory.daocheapp.bean;

import java.util.List;

public class AddDepositeRecordBean {

    /**
     * data : []
     * msg : 用户信息异常
     * responseState : 0
     */

    private String msg;
    private String responseState;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
