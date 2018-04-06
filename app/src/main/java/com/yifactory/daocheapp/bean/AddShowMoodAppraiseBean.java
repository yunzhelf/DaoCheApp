package com.yifactory.daocheapp.bean;

import java.util.List;

public class AddShowMoodAppraiseBean {

    /**
     * data : [{"createTime":"2018-03-25 12:53:25","smId":"c13bf3396e71451680c92d1782bc6612","smaId":"","uId":"916e60bbe84b4913a5f2b4a28300fe2a","updateTime":"2018-03-25 12:53:25"}]
     * msg : 取消成功
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
         * createTime : 2018-03-25 12:53:25
         * smId : c13bf3396e71451680c92d1782bc6612
         * smaId :
         * uId : 916e60bbe84b4913a5f2b4a28300fe2a
         * updateTime : 2018-03-25 12:53:25
         */

        private String createTime;
        private String smId;
        private String smaId;
        private String uId;
        private String updateTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSmId() {
            return smId;
        }

        public void setSmId(String smId) {
            this.smId = smId;
        }

        public String getSmaId() {
            return smaId;
        }

        public void setSmaId(String smaId) {
            this.smaId = smaId;
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
}
