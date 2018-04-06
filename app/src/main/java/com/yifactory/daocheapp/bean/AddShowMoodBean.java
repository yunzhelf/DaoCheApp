package com.yifactory.daocheapp.bean;

import java.util.List;

public class AddShowMoodBean {

    /**
     * data : [{"commentCount":0,"createTime":"2018-03-28 11:21:53","deleteFlag":0,"file":"\"\"","moodBody":"哈哈哈哈哈哈哈哈哈哈哈","praiseCount":0,"smId":"119c4800163e43439892b8880eab0b4a","uId":"916e60bbe84b4913a5f2b4a28300fe2a","updateTime":"2018-03-28 11:21:53"}]
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
         * commentCount : 0
         * createTime : 2018-03-28 11:21:53
         * deleteFlag : 0
         * file : ""
         * moodBody : 哈哈哈哈哈哈哈哈哈哈哈
         * praiseCount : 0
         * smId : 119c4800163e43439892b8880eab0b4a
         * uId : 916e60bbe84b4913a5f2b4a28300fe2a
         * updateTime : 2018-03-28 11:21:53
         */

        private int commentCount;
        private String createTime;
        private int deleteFlag;
        private String file;
        private String moodBody;
        private int praiseCount;
        private String smId;
        private String uId;
        private String updateTime;

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

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

        public String getMoodBody() {
            return moodBody;
        }

        public void setMoodBody(String moodBody) {
            this.moodBody = moodBody;
        }

        public int getPraiseCount() {
            return praiseCount;
        }

        public void setPraiseCount(int praiseCount) {
            this.praiseCount = praiseCount;
        }

        public String getSmId() {
            return smId;
        }

        public void setSmId(String smId) {
            this.smId = smId;
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
