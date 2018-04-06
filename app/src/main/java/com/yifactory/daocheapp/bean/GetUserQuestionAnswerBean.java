package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetUserQuestionAnswerBean {


    /**
     * data : [{"aId":"71b09a4dbda243ba983ae0a3fdc4ccb5","answerBody":"吉利博越,12向可调节座椅,平民级的价格老板级的享受","bePraised":0,"createTime":"2018-03-12 16:32:50","creator":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"pictures/user/headImg/2/20180308134429-454e8e98f540d26dd321b4.jpg","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":"恒宇少年"},"deleteFlag":0,"dmireCounts":0,"praiseCounts":0,"praised":0,"qId":"1c556d89d9864dd784d6b23d7d4ac002","reciver":{},"updateTime":"2018-03-12 16:32:50"},{"aId":"9acf4a0bb51c42bc814629a0a4d43ba0","answerBody":"吉利博越,12向可调节座椅,平民级的价格老板级的享受","bePraised":0,"createTime":"2018-03-12 16:35:27","creator":{"uId":"sfadfadfasf","headImg":"","userMobile":"","sex":0,"userLeval":"小白","wxId":"","userName":"匿名"},"deleteFlag":0,"dmireCounts":0,"praiseCounts":0,"praised":0,"qId":"1c556d89d9864dd784d6b23d7d4ac002","reciver":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"pictures/user/headImg/2/20180308134429-454e8e98f540d26dd321b4.jpg","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":"恒宇少年"},"updateTime":"2018-03-12 16:35:28"}]
     * msg : 查询成功
     * responseState : 1
     */

    private String msg;
    private String responseState;
    private List<GetUserQuestionListBean.DataBean.AnswersBean> data;

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

    public List<GetUserQuestionListBean.DataBean.AnswersBean> getData() {
        return data;
    }

    public void setData(List<GetUserQuestionListBean.DataBean.AnswersBean> data) {
        this.data = data;
    }
}
