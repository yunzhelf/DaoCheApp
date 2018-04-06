package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetUserBalanceRecordBean {

    /**
     * data : [{"createTime":"2018-03-22 17:10:02","recordType":1,"goldCounts":1},{"createTime":"2018-03-21 15:04:40","recordType":2,"goldCounts":4}]
     * msg : 查询成功
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
         * createTime : 2018-03-22 17:10:02
         * recordType : 1
         * goldCounts : 1
         */

        private String createTime;
        private int recordType;
        private int goldCounts;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getRecordType() {
            return recordType;
        }

        public void setRecordType(int recordType) {
            this.recordType = recordType;
        }

        public int getGoldCounts() {
            return goldCounts;
        }

        public void setGoldCounts(int goldCounts) {
            this.goldCounts = goldCounts;
        }
    }
}
