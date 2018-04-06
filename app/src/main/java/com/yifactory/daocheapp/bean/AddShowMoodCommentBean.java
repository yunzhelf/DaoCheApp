package com.yifactory.daocheapp.bean;

import java.util.List;

public class AddShowMoodCommentBean {
    /**
     * data : [{"cotentBody":"呵呵","createTime":"2018-03-28 14:29:31","rId":"916e60bbe84b4913a5f2b4a28300fe2a","smId":"0d41c449ced349239f22eba83b29cd8e","smcId":"c1ed7cef41a8494384499b6d46492544","uId":"df87dcb705c5481bb954f2e5b102ecce","updateTime":"2018-03-28 14:29:31"}]
     * msg : 评论成功
     * responseState : 1
     */

    private String msg;
    private String responseState;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cotentBody : 呵呵
         * createTime : 2018-03-28 14:29:31
         * rId : 916e60bbe84b4913a5f2b4a28300fe2a
         * smId : 0d41c449ced349239f22eba83b29cd8e
         * smcId : c1ed7cef41a8494384499b6d46492544
         * uId : df87dcb705c5481bb954f2e5b102ecce
         * updateTime : 2018-03-28 14:29:31
         */

        private String cotentBody;
        private String createTime;
        private String rId;
        private String smId;
        private String smcId;
        private String uId;
        private String updateTime;

        public String getCotentBody() {
            return cotentBody;
        }

        public void setCotentBody(String cotentBody) {
            this.cotentBody = cotentBody;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getRId() {
            return rId;
        }

        public void setRId(String rId) {
            this.rId = rId;
        }

        public String getSmId() {
            return smId;
        }

        public void setSmId(String smId) {
            this.smId = smId;
        }

        public String getSmcId() {
            return smcId;
        }

        public void setSmcId(String smcId) {
            this.smcId = smcId;
        }

        public String getUId() {
            return uId;
        }

        public void setUId(String uId) {
            this.uId = uId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    /**
     * data : {"cotentBody":"恩上排面了昂","createTime":"2018-03-12 14:57:51","rId":"f99bda976cde43d18ebf497fb58c4320","smId":"8fc5fc88cbfe4656be043be4034fc873","smcId":"fb6bbbb01000464ebc67a2de30c151d9","uId":"sfadfadfasf","updateTime":"2018-03-12 14:57:51"}
     * msg : 评论成功
     * responseState : 1
     *//*

    private GetShowMoodCommentBean.DataBean data;
    private String msg;
    private String responseState;

    public GetShowMoodCommentBean.DataBean getData() {
        return data;
    }

    public void setData(GetShowMoodCommentBean.DataBean data) {
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
    }*/


}
