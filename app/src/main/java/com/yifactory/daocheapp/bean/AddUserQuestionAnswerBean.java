package com.yifactory.daocheapp.bean;

import java.util.List;

public class AddUserQuestionAnswerBean {

    /**
     * data : {"aId":"5e26c0875ec34e9bb5512e153b7d0b41","answerBody":"吉利博越,12向可调节座椅,平民级的价格老板级的享受","createTime":"2018-03-12 16:31:37","deleteFlag":0,"dmireCounts":0,"praiseCounts":0,"qId":"f99bda976cde43d18ebf497fb58c4320","rId":"","uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-12 16:31:37"}
     * msg : 回答成功
     * responseState : 1
     */

    private List<GetUserQuestionListBean.DataBean.AnswersBean> data;
    private String msg;
    private String responseState;

    public List<GetUserQuestionListBean.DataBean.AnswersBean> getData() {
        return data;
    }

    public void setData(List<GetUserQuestionListBean.DataBean.AnswersBean> data) {
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
}
