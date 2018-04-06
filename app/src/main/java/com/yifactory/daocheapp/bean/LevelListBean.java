package com.yifactory.daocheapp.bean;

import java.util.List;

public class LevelListBean {

    /**
     * data : [{"createTime":"2018-03-12 11:20:46","levelName":"小白","levelTime":0,"sulId":"1","updateTime":"2018-03-12 11:20:59"},{"createTime":"2018-03-12 11:20:46","levelName":"小学","levelTime":3000,"sulId":"2","updateTime":"2018-03-12 11:20:59"},{"createTime":"2018-03-12 11:20:46","levelName":"中学","levelTime":6000,"sulId":"3","updateTime":"2018-03-12 11:21:00"},{"createTime":"2018-03-12 11:20:46","levelName":"高中","levelTime":12000,"sulId":"56","updateTime":"2018-03-12 11:21:01"},{"createTime":"2018-03-12 11:20:46","levelName":"学士","levelTime":21000,"sulId":"6","updateTime":"2018-03-12 11:21:05"},{"createTime":"2018-03-12 11:20:46","levelName":"硕士","levelTime":36000,"sulId":"7","updateTime":"2018-03-12 11:21:02"},{"createTime":"2018-03-12 11:20:46","levelName":"博士","levelTime":60000,"sulId":"8","updateTime":"2018-03-12 11:21:02"},{"createTime":"2018-03-12 11:20:46","levelName":"王者","levelTime":108000,"sulId":"9","updateTime":"2018-03-12 11:21:01"}]
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
         * createTime : 2018-03-12 11:20:46
         * levelName : 小白
         * levelTime : 0
         * sulId : 1
         * updateTime : 2018-03-12 11:20:59
         */

        private String createTime;
        private String levelName;
        private int levelTime;
        private String sulId;
        private String updateTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getLevelTime() {
            return levelTime;
        }

        public void setLevelTime(int levelTime) {
            this.levelTime = levelTime;
        }

        public String getSulId() {
            return sulId;
        }

        public void setSulId(String sulId) {
            this.sulId = sulId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
