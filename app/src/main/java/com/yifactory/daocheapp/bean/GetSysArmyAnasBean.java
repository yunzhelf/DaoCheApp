package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetSysArmyAnasBean {

    /**
     * data : [{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"10","title":"军师语录 第九期","updateTime":"2018-03-08 15:22:24","voicePath":"123213这个是阿里云点播服务资源Id"},{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"11","title":"军师语录 第十一期","updateTime":"2018-03-08 15:22:24","voicePath":"123213这个是阿里云点播服务资源Id"},{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"5","title":"军师语录 第wu期","updateTime":"2018-03-08 15:22:23","voicePath":"123213这个是阿里云点播服务资源Id"},{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"6","title":"军师语录 第七期","updateTime":"2018-03-08 15:22:23","voicePath":"123213这个是阿里云点播服务资源Id"},{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"7","title":"军师语录 第六期","updateTime":"2018-03-08 15:22:23","voicePath":"123213这个是阿里云点播服务资源Id"},{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"8","title":"军师语录 第七期","updateTime":"2018-03-08 15:22:23","voicePath":"123213这个是阿里云点播服务资源Id"},{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"9","title":"军师语录 第八期","updateTime":"2018-03-08 15:22:23","voicePath":"123213这个是阿里云点播服务资源Id"},{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"4","title":"军师语录 第八期","updateTime":"2018-03-08 15:22:18","voicePath":"123213这个是阿里云点播服务资源Id"},{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"12","title":"军师语录 第十二期","updateTime":"2018-03-08 15:22:18","voicePath":"123213这个是阿里云点播服务资源Id"},{"createTime":"2018-03-08 15:17:13","deleteFlag":0,"saaId":"2","title":"军师语录 第一期","updateTime":"2018-03-08 15:22:17","voicePath":"123213这个是阿里云点播服务资源Id"}]
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
         * createTime : 2018-03-08 15:17:13
         * deleteFlag : 0
         * saaId : 10
         * title : 军师语录 第九期
         * updateTime : 2018-03-08 15:22:24
         * voicePath : 123213这个是阿里云点播服务资源Id
         */

        private String createTime;
        private int deleteFlag;
        private String saaId;
        private String title;
        private String updateTime;
        private String voicePath;

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

        public String getSaaId() {
            return saaId;
        }

        public void setSaaId(String saaId) {
            this.saaId = saaId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getVoicePath() {
            return voicePath;
        }

        public void setVoicePath(String voicePath) {
            this.voicePath = voicePath;
        }
    }
}
