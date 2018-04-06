package com.yifactory.daocheapp.bean;

import java.io.Serializable;
import java.util.List;

public class GetUserQuestionListBean {

    /**
     * data : [{"answers":[{"aId":"71b09a4dbda243ba983ae0a3fdc4ccb5","answerBody":"吉利博越,12向可调节座椅,平民级的价格老板级的享受","bePraised":0,"createTime":"2018-03-12 16:32:50","creator":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":""},"deleteFlag":0,"dmireCounts":0,"praiseCounts":0,"praised":0,"qId":"1c556d89d9864dd784d6b23d7d4ac002","reciver":{},"updateTime":"2018-03-12 16:32:50"},{"aId":"9acf4a0bb51c42bc814629a0a4d43ba0","answerBody":"吉利博越,12向可调节座椅,平民级的价格老板级的享受","bePraised":0,"createTime":"2018-03-12 16:35:27","creator":null,"deleteFlag":0,"dmireCounts":1,"praiseCounts":0,"praised":0,"qId":"1c556d89d9864dd784d6b23d7d4ac002","reciver":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":""},"updateTime":"2018-03-12 16:35:28"}],"createTime":"2018-03-12 16:27:23","deleteFlag":0,"file":"","qId":"1c556d89d9864dd784d6b23d7d4ac002","questionBody":"大家给推荐一辆10万左右的家用suv,主要上下班用","rewardCounts":0,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-12 16:27:24"},{"answers":[{"aId":"5e26c0875ec34e9bb5512e153b7d0b41","answerBody":"吉利博越,12向可调节座椅,平民级的价格老板级的享受","bePraised":0,"createTime":"2018-03-12 16:31:37","creator":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":""},"deleteFlag":0,"dmireCounts":0,"praiseCounts":0,"praised":0,"qId":"aa7082cb102c4be493723a6f85992286","reciver":{},"updateTime":"2018-03-12 16:34:24"}],"createTime":"2018-03-09 16:22:33","deleteFlag":0,"file":"pictures/showMood/2/20180309162233-994e32be8faee5534247aa.jpg","qId":"aa7082cb102c4be493723a6f85992286","questionBody":"你了解转子发动机,活塞发动机,的区别吗","rewardCounts":0,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-09 16:22:35"},{"answers":[],"createTime":"2018-03-09 16:22:18","deleteFlag":0,"file":"pictures/showMood/2/20180309162218-f847df8227b4e9db5c4a09.jpg","qId":"1b890c4deb4b49029b13db1325e864e2","questionBody":"你了解转子发动机,活塞发动机,的区别吗","rewardCounts":0,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-09 16:22:20"}]
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
         * answers : [{"aId":"71b09a4dbda243ba983ae0a3fdc4ccb5","answerBody":"吉利博越,12向可调节座椅,平民级的价格老板级的享受","bePraised":0,"createTime":"2018-03-12 16:32:50","creator":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":""},"deleteFlag":0,"dmireCounts":0,"praiseCounts":0,"praised":0,"qId":"1c556d89d9864dd784d6b23d7d4ac002","reciver":{},"updateTime":"2018-03-12 16:32:50"},{"aId":"9acf4a0bb51c42bc814629a0a4d43ba0","answerBody":"吉利博越,12向可调节座椅,平民级的价格老板级的享受","bePraised":0,"createTime":"2018-03-12 16:35:27","creator":null,"deleteFlag":0,"dmireCounts":1,"praiseCounts":0,"praised":0,"qId":"1c556d89d9864dd784d6b23d7d4ac002","reciver":{"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":""},"updateTime":"2018-03-12 16:35:28"}]
         * createTime : 2018-03-12 16:27:23
         * deleteFlag : 0
         * file :
         * qId : 1c556d89d9864dd784d6b23d7d4ac002
         * questionBody : 大家给推荐一辆10万左右的家用suv,主要上下班用
         * rewardCounts : 0
         * uId : f99bda976cde43d18ebf497fb58c4320
         * updateTime : 2018-03-12 16:27:24
         */

        private int commentCount;
        private String createTime;
        private int deleteFlag;
        private String file;
        private String qId;
        private String questionBody;
        private int rewardCounts;
        private String uId;
        private String updateTime;
        private List<AnswersBean> answers;
        private CreatorBean creator;

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public CreatorBean getCreator() {
            return creator;
        }

        public void setCreator(CreatorBean creator) {
            this.creator = creator;
        }

        public static class CreatorBean implements Serializable{
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

            public String getJobArea() {
                return jobArea;
            }

            public void setJobArea(String jobArea) {
                this.jobArea = jobArea;
            }

            public String getTrainAttention() {
                return trainAttention;
            }

            public void setTrainAttention(String trainAttention) {
                this.trainAttention = trainAttention;
            }

            public String getuId() {
                return uId;
            }

            public void setuId(String uId) {
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

            public String getWxId() {
                return wxId;
            }

            public void setWxId(String wxId) {
                this.wxId = wxId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        private boolean isShowMore = false;

        public boolean isShowMore() {
            return isShowMore;
        }

        public void setShowMore(boolean showMore) {
            isShowMore = showMore;
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

        public List<AnswersBean> getAnswers() {
            return answers;
        }

        public void setAnswers(List<AnswersBean> answers) {
            this.answers = answers;
        }

        public static class AnswersBean implements Serializable{
            /**
             * aId : 71b09a4dbda243ba983ae0a3fdc4ccb5
             * answerBody : 吉利博越,12向可调节座椅,平民级的价格老板级的享受
             * bePraised : 0
             * createTime : 2018-03-12 16:32:50
             * creator : {"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","wxId":"","userName":""}
             * deleteFlag : 0
             * dmireCounts : 0
             * praiseCounts : 0
             * praised : 0
             * qId : 1c556d89d9864dd784d6b23d7d4ac002
             * reciver : {}
             * updateTime : 2018-03-12 16:32:50
             */

            private String aId;
            private String answerBody;
            private int bePraised;
            private String createTime;
            private CreatorBean creator;
            private int deleteFlag;
            private int dmireCounts;
            private int praiseCounts;
            private int praised;
            private String qId;
            private ReciverBean reciver;
            private String updateTime;

            public String getAId() {
                return aId;
            }

            public void setAId(String aId) {
                this.aId = aId;
            }

            public String getAnswerBody() {
                return answerBody;
            }

            public void setAnswerBody(String answerBody) {
                this.answerBody = answerBody;
            }

            public int getBePraised() {
                return bePraised;
            }

            public void setBePraised(int bePraised) {
                this.bePraised = bePraised;
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

            public int getDmireCounts() {
                return dmireCounts;
            }

            public void setDmireCounts(int dmireCounts) {
                this.dmireCounts = dmireCounts;
            }

            public int getPraiseCounts() {
                return praiseCounts;
            }

            public void setPraiseCounts(int praiseCounts) {
                this.praiseCounts = praiseCounts;
            }

            public int getPraised() {
                return praised;
            }

            public void setPraised(int praised) {
                this.praised = praised;
            }

            public String getQId() {
                return qId;
            }

            public void setQId(String qId) {
                this.qId = qId;
            }

            public ReciverBean getReciver() {
                return reciver;
            }

            public void setReciver(ReciverBean reciver) {
                this.reciver = reciver;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public static class CreatorBean implements Serializable{
                private String uId;

                private String headImg;

                private String createTime;

                private String userMobile;

                private int sex;

                private String userLeval;

                private String wxId;

                private String userName;

                public String getuId() {
                    return uId;
                }

                public void setuId(String uId) {
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

                public String getWxId() {
                    return wxId;
                }

                public void setWxId(String wxId) {
                    this.wxId = wxId;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }
            }

            public static class ReciverBean implements Serializable{

                private String uId;

                private String headImg;

                private String createTime;

                private String userMobile;

                private int sex;

                private String userLeval;

                private String wxId;

                private String userName;

                public String getuId() {
                    return uId;
                }

                public void setuId(String uId) {
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

                public String getWxId() {
                    return wxId;
                }

                public void setWxId(String wxId) {
                    this.wxId = wxId;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }
            }
        }
    }
}
