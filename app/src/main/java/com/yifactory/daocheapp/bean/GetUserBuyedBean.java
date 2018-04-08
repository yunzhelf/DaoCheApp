package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetUserBuyedBean {

    /**
     * data : [{"buyState":1,"createTime":"2018-03-09 11:07:12","creator":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":""},"deleteFlag":0,"goldCount":5,"harvest":"获得收货","indexImg":"pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg","rId":"89325d122cb04754999dcb17a7392a88","scId":"1","secondTitle":"钢铁是怎样炼成的","showCounts":2,"state":0,"suitPerson":"适合人群","title":"第一集","totalMinute":120,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-13 16:13:23","videoContent":"视频内容","videoDetail":"视频详细","videoPath":"阿里资源Id"}]
     * msg : 查询成功
     * responseState : 1
     */

    private String msg;
    private String responseState;
    private List<PlayVideoBean.DataBean.HotBean> data;

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

    public List<PlayVideoBean.DataBean.HotBean> getData() {
        return data;
    }

    public void setData(List<PlayVideoBean.DataBean.HotBean> data) {
        this.data = data;
    }

}
