package com.yifactory.daocheapp.bean;

import java.util.List;

public class AddQuestionAnswerAppraiseBean {

    /**
     * data : [{"createtime":"2018-03-25 13:08:24","uId":"916e60bbe84b4913a5f2b4a28300fe2a","uaId":"2a6cf88fc9954cf0b38de57ca2e994ed","uapId":"a666b75a5a964083b3ff3a933849f5ca","updateTime":"2018-03-25 13:08:24"}]
     * msg : 点赞成功
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
         * createtime : 2018-03-25 13:08:24
         * uId : 916e60bbe84b4913a5f2b4a28300fe2a
         * uaId : 2a6cf88fc9954cf0b38de57ca2e994ed
         * uapId : a666b75a5a964083b3ff3a933849f5ca
         * updateTime : 2018-03-25 13:08:24
         */

        private String createtime;
        private String uId;
        private String uaId;
        private String uapId;
        private String updateTime;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUId() {
            return uId;
        }

        public void setUId(String uId) {
            this.uId = uId;
        }

        public String getUaId() {
            return uaId;
        }

        public void setUaId(String uaId) {
            this.uaId = uaId;
        }

        public String getUapId() {
            return uapId;
        }

        public void setUapId(String uapId) {
            this.uapId = uapId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
