package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetStudyReocrdBean {

    /**
     * data : [{"buyState":1,"createTime":"2018-03-09 11:07:12","creator":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","jobArea":"","wxId":"","trainAttention":"运营管理,市场营销,销售管理,客户维护","userName":"","attentionCounts":0},"deleteFlag":0,"goldCount":5,"harvest":"获得收货","indexImg":"pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg","rId":"89325d122cb04754999dcb17a7392a88","scId":"1","secondTitle":"钢铁是怎样炼成的","showCounts":2,"state":0,"suitPerson":"适合人群","title":"第一集","totalMinute":120,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-13 16:13:23","videoContent":"视频内容","videoDetail":"视频详细","videoPath":"阿里资源Id"}]
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
         * buyState : 1
         * createTime : 2018-03-09 11:07:12
         * creator : {"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","jobArea":"","wxId":"","trainAttention":"运营管理,市场营销,销售管理,客户维护","userName":"","attentionCounts":0}
         * deleteFlag : 0
         * goldCount : 5
         * harvest : 获得收货
         * indexImg : pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg
         * rId : 89325d122cb04754999dcb17a7392a88
         * scId : 1
         * secondTitle : 钢铁是怎样炼成的
         * showCounts : 2
         * state : 0
         * suitPerson : 适合人群
         * title : 第一集
         * totalMinute : 120
         * uId : f99bda976cde43d18ebf497fb58c4320
         * updateTime : 2018-03-13 16:13:23
         * videoContent : 视频内容
         * videoDetail : 视频详细
         * videoPath : 阿里资源Id
         */

        private int buyState;
        private String createTime;
        private CreatorBean creator;
        private int deleteFlag;
        private int goldCount;
        private String harvest;
        private String indexImg;
        private String rId;
        private String scId;
        private String secondTitle;
        private int showCounts;
        private int state;
        private String suitPerson;
        private String title;
        private int totalMinute;
        private String uId;
        private String updateTime;
        private String videoContent;
        private String videoDetail;
        private String videoPath;

        public int getBuyState() {
            return buyState;
        }

        public void setBuyState(int buyState) {
            this.buyState = buyState;
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

        public int getGoldCount() {
            return goldCount;
        }

        public void setGoldCount(int goldCount) {
            this.goldCount = goldCount;
        }

        public String getHarvest() {
            return harvest;
        }

        public void setHarvest(String harvest) {
            this.harvest = harvest;
        }

        public String getIndexImg() {
            return indexImg;
        }

        public void setIndexImg(String indexImg) {
            this.indexImg = indexImg;
        }

        public String getRId() {
            return rId;
        }

        public void setRId(String rId) {
            this.rId = rId;
        }

        public String getScId() {
            return scId;
        }

        public void setScId(String scId) {
            this.scId = scId;
        }

        public String getSecondTitle() {
            return secondTitle;
        }

        public void setSecondTitle(String secondTitle) {
            this.secondTitle = secondTitle;
        }

        public int getShowCounts() {
            return showCounts;
        }

        public void setShowCounts(int showCounts) {
            this.showCounts = showCounts;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getSuitPerson() {
            return suitPerson;
        }

        public void setSuitPerson(String suitPerson) {
            this.suitPerson = suitPerson;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTotalMinute() {
            return totalMinute;
        }

        public void setTotalMinute(int totalMinute) {
            this.totalMinute = totalMinute;
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

        public String getVideoContent() {
            return videoContent;
        }

        public void setVideoContent(String videoContent) {
            this.videoContent = videoContent;
        }

        public String getVideoDetail() {
            return videoDetail;
        }

        public void setVideoDetail(String videoDetail) {
            this.videoDetail = videoDetail;
        }

        public String getVideoPath() {
            return videoPath;
        }

        public void setVideoPath(String videoPath) {
            this.videoPath = videoPath;
        }

        public static class CreatorBean {
            /**
             * uId : f99bda976cde43d18ebf497fb58c4320
             * headImg :
             * createTime : 2018-03-08 13:38:58
             * userMobile : 15140088201
             * sex : 0
             * userLeval : 小白
             * jobArea :
             * wxId :
             * trainAttention : 运营管理,市场营销,销售管理,客户维护
             * userName :
             * attentionCounts : 0
             */

            private String uId;
            private String headImg;
            private String createTime;
            private String userMobile;
            private int sex;
            private String userLeval;
            private String jobArea;
            private String wxId;
            private String trainAttention;
            private String userName;
            private int attentionCounts;

            public String getUId() {
                return uId;
            }

            public void setUId(String uId) {
                this.uId = uId;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
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

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getUserLeval() {
                return userLeval;
            }

            public void setUserLeval(String userLeval) {
                this.userLeval = userLeval;
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

            public int getAttentionCounts() {
                return attentionCounts;
            }

            public void setAttentionCounts(int attentionCounts) {
                this.attentionCounts = attentionCounts;
            }
        }
    }
}
