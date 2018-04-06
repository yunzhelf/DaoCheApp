package com.yifactory.daocheapp.bean;

import java.io.Serializable;
import java.util.List;

public class GetShowMoodListBean {

    /**
     * data : [{"createTime":"2018-03-09 16:27:49","creator":{"jobName":"前台经理","nowArea":"沈阳","jobTime":2,"learnTime":0,"headImg":"pictures/user/headImg/2/20180308134429-454e8e98f540d26dd321b4.jpg","sex":0,"companyName":"wingfac","jobArea":"销售","wxId":"","trainAttention":"运营管理,市场营销","userName":"恒宇少年","uId":"f99bda976cde43d18ebf497fb58c4320","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","userLeval":"小白"},"deleteFlag":0,"file":"pictures/showMood/2/20180309161707-8a411b8706ef363bdfccae.jpg","moodBody":"你看好转子发动机吗","praiseCount":0,"praised":0,"smId":"8fc5fc88cbfe4656be043be4034fc873","uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-09 16:27:53"},{"createTime":"2018-03-09 16:18:55","creator":{"jobName":"前台经理","nowArea":"沈阳","jobTime":2,"learnTime":0,"headImg":"pictures/user/headImg/2/20180308134429-454e8e98f540d26dd321b4.jpg","sex":0,"companyName":"wingfac","jobArea":"销售","wxId":"","trainAttention":"运营管理,市场营销","userName":"恒宇少年","uId":"f99bda976cde43d18ebf497fb58c4320","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","userLeval":"小白"},"deleteFlag":0,"file":"pictures/showMood/2/20180309161855-664718ad3a8cbb3b62a967.jpg","moodBody":"你看好转子发动机吗","praiseCount":0,"praised":0,"smId":"c7104e76eff24eaaa96454d300d83de0","uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-09 16:18:57"}]
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

    public static class DataBean implements Serializable{
        /**
         * createTime : 2018-03-09 16:27:49
         * creator : {"jobName":"前台经理","nowArea":"沈阳","jobTime":2,"learnTime":0,"headImg":"pictures/user/headImg/2/20180308134429-454e8e98f540d26dd321b4.jpg","sex":0,"companyName":"wingfac","jobArea":"销售","wxId":"","trainAttention":"运营管理,市场营销","userName":"恒宇少年","uId":"f99bda976cde43d18ebf497fb58c4320","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","userLeval":"小白"}
         * deleteFlag : 0
         * file : pictures/showMood/2/20180309161707-8a411b8706ef363bdfccae.jpg
         * moodBody : 你看好转子发动机吗
         * praiseCount : 0
         * praised : 0
         * smId : 8fc5fc88cbfe4656be043be4034fc873
         * uId : f99bda976cde43d18ebf497fb58c4320
         * updateTime : 2018-03-09 16:27:53
         */

        private int commentCount;
        private String createTime;
        private CreatorBean creator;
        private int deleteFlag;
        private String file;
        private String moodBody;
        private int praiseCount;
        private int praised;
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

        public CreatorBean getCreator() {
            return creator;
        }

        public void setCreator(CreatorBean creator) {
            this.creator = creator;
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

        public int getPraised() {
            return praised;
        }

        public void setPraised(int praised) {
            this.praised = praised;
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

        public static class CreatorBean implements Serializable{
            /**
             * jobName : 前台经理
             * nowArea : 沈阳
             * jobTime : 2
             * learnTime : 0
             * headImg : pictures/user/headImg/2/20180308134429-454e8e98f540d26dd321b4.jpg
             * sex : 0
             * companyName : wingfac
             * jobArea : 销售
             * wxId :
             * trainAttention : 运营管理,市场营销
             * userName : 恒宇少年
             * uId : f99bda976cde43d18ebf497fb58c4320
             * createTime : 2018-03-08 13:38:58
             * userMobile : 15140088201
             * userLeval : 小白
             */

            private String jobName;
            private String nowArea;
            private int jobTime;
            private int learnTime;
            private String headImg;
            private int sex;
            private String companyName;
            private String jobArea;
            private String wxId;
            private String trainAttention;
            private String userName;
            private String uId;
            private String createTime;
            private String userMobile;
            private String userLeval;

            public String getJobName() {
                return jobName;
            }

            public void setJobName(String jobName) {
                this.jobName = jobName;
            }

            public String getNowArea() {
                return nowArea;
            }

            public void setNowArea(String nowArea) {
                this.nowArea = nowArea;
            }

            public int getJobTime() {
                return jobTime;
            }

            public void setJobTime(int jobTime) {
                this.jobTime = jobTime;
            }

            public int getLearnTime() {
                return learnTime;
            }

            public void setLearnTime(int learnTime) {
                this.learnTime = learnTime;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getJobArea() {
                return jobArea;
            }

            public void setJobArea(String jobArea) {
                this.jobArea = jobArea;
            }

            public String getWxId() {
                return wxId;
            }

            public void setWxId(String wxId) {
                this.wxId = wxId;
            }

            public String getTrainAttention() {
                return trainAttention;
            }

            public void setTrainAttention(String trainAttention) {
                this.trainAttention = trainAttention;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUId() {
                return uId;
            }

            public void setUId(String uId) {
                this.uId = uId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUserMobile() {
                return userMobile;
            }

            public void setUserMobile(String userMobile) {
                this.userMobile = userMobile;
            }

            public String getUserLeval() {
                return userLeval;
            }

            public void setUserLeval(String userLeval) {
                this.userLeval = userLeval;
            }
        }
    }
}
