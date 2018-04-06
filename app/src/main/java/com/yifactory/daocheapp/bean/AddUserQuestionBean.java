package com.yifactory.daocheapp.bean;

import java.util.List;

public class AddUserQuestionBean {


    /**
     * data : [{"createTime":"2018-03-22 14:10:49","deleteFlag":0,"file":"\"\"","qId":"00756afcae444d4c83021aac7bb6f7b6","questionBody":"哈哈哈哈哈哈哈哈哈哈哈","rewardCounts":0,"uId":"916e60bbe84b4913a5f2b4a28300fe2a","updateTime":"2018-03-22 14:10:50"}]
     * msg : 发布成功
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
         * createTime : 2018-03-22 14:10:49
         * deleteFlag : 0
         * file : ""
         * qId : 00756afcae444d4c83021aac7bb6f7b6
         * questionBody : 哈哈哈哈哈哈哈哈哈哈哈
         * rewardCounts : 0
         * uId : 916e60bbe84b4913a5f2b4a28300fe2a
         * updateTime : 2018-03-22 14:10:50
         */

        private String createTime;
        private int deleteFlag;
        private String file;
        private String qId;
        private String questionBody;
        private int rewardCounts;
        private String uId;
        private String updateTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(int deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getQId() {
            return qId;
        }

        public void setQId(String qId) {
            this.qId = qId;
        }

        public String getQuestionBody() {
            return questionBody;
        }

        public void setQuestionBody(String questionBody) {
            this.questionBody = questionBody;
        }

        public int getRewardCounts() {
            return rewardCounts;
        }

        public void setRewardCounts(int rewardCounts) {
            this.rewardCounts = rewardCounts;
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
