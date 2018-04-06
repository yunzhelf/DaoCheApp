package com.yifactory.daocheapp.bean;


import java.util.List;

public class MsgBean {

    /**
     * data : [{"content":"系统公告: 新一波红包雨即将来临,请各位同学做好准备","createTime":"2018-03-13 11:51:03","creatorName":"","sumId":"dfasdfa","type":2,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-13 11:56:21"},{"content":"有人回复了你的评论","createTime":"2018-03-13 11:51:00","creatorName":"","sumId":"asdfasd","type":1,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-13 11:51:03"},{"content":"有人赞了你的评论","createTime":"2018-03-13 11:50:56","creatorName":"","sumId":"sdasd","type":0,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-13 11:50:59"}]
     * msg : 查询成功
     * responseState : 1
     */

    public String msg;
    public String responseState;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * content : 系统公告: 新一波红包雨即将来临,请各位同学做好准备
         * createTime : 2018-03-13 11:51:03
         * creatorName :
         * sumId : dfasdfa
         * type : 2
         * uId : f99bda976cde43d18ebf497fb58c4320
         * updateTime : 2018-03-13 11:56:21
         */

        public String content;
        public String createTime;
        public String creatorName;
        public String sumId;
        public int type;
        public String uId;
        public String updateTime;
    }
}
