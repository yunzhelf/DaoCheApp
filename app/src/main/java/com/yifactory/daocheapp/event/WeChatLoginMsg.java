package com.yifactory.daocheapp.event;

public class WeChatLoginMsg {

    private String code;

    public WeChatLoginMsg(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
