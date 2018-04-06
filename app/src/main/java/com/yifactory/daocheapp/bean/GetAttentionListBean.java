package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetAttentionListBean {

    /**
     * data : [{"answerCounts":3,"attentionCounts":0,"buyCounts":0,"companyName":"","createTime":"2018-03-08 13:38:58","depositePercent":0.8,"discountCouponCounts":0,"goldBalance":0,"headImg":"","id":"f99bda976cde43d18ebf497fb58c4320","inviteCode":"b06e83a","jobArea":"","jobName":"","jobTime":6,"lastStudyTime":0,"learnDays":0,"learnTime":3600,"levalTime":0,"nowArea":"","parentInviteCode":"","password":"e10adc3949ba59abbe56e057f20f883e","questionCounts":2,"reciveBalance":0,"role":0,"sex":0,"shareState":0,"teachState":0,"trainAttention":"运营管理,市场营销,销售管理,客户维护","uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-23 01:32:52","userLeval":"","userMobile":"15140088201","userName":"","workExperience":"0","wxId":""}]
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
         * answerCounts : 3
         * attentionCounts : 0
         * buyCounts : 0
         * companyName :
         * createTime : 2018-03-08 13:38:58
         * depositePercent : 0.8
         * discountCouponCounts : 0
         * goldBalance : 0
         * headImg :
         * id : f99bda976cde43d18ebf497fb58c4320
         * inviteCode : b06e83a
         * jobArea :
         * jobName :
         * jobTime : 6
         * lastStudyTime : 0
         * learnDays : 0
         * learnTime : 3600
         * levalTime : 0
         * nowArea :
         * parentInviteCode :
         * password : e10adc3949ba59abbe56e057f20f883e
         * questionCounts : 2
         * reciveBalance : 0
         * role : 0
         * sex : 0
         * shareState : 0
         * teachState : 0
         * trainAttention : 运营管理,市场营销,销售管理,客户维护
         * uId : f99bda976cde43d18ebf497fb58c4320
         * updateTime : 2018-03-23 01:32:52
         * userLeval :
         * userMobile : 15140088201
         * userName :
         * workExperience : 0
         * wxId :
         */

        private int answerCounts;
        private int attentionCounts;
        private int buyCounts;
        private String companyName;
        private String createTime;
        private double depositePercent;
        private int discountCouponCounts;
        private int goldBalance;
        private String headImg;
        private String id;
        private String inviteCode;
        private String jobArea;
        private String jobName;
        private int jobTime;
        private int lastStudyTime;
        private int learnDays;
        private int learnTime;
        private int levalTime;
        private String nowArea;
        private String parentInviteCode;
        private String password;
        private int questionCounts;
        private int reciveBalance;
        private int role;
        private int sex;
        private int shareState;
        private int teachState;
        private String trainAttention;
        private String uId;
        private String updateTime;
        private String userLeval;
        private String userMobile;
        private String userName;
        private String workExperience;
        private String wxId;

        public int getAnswerCounts() {
            return answerCounts;
        }

        public void setAnswerCounts(int answerCounts) {
            this.answerCounts = answerCounts;
        }

        public int getAttentionCounts() {
            return attentionCounts;
        }

        public void setAttentionCounts(int attentionCounts) {
            this.attentionCounts = attentionCounts;
        }

        public int getBuyCounts() {
            return buyCounts;
        }

        public void setBuyCounts(int buyCounts) {
            this.buyCounts = buyCounts;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public double getDepositePercent() {
            return depositePercent;
        }

        public void setDepositePercent(double depositePercent) {
            this.depositePercent = depositePercent;
        }

        public int getDiscountCouponCounts() {
            return discountCouponCounts;
        }

        public void setDiscountCouponCounts(int discountCouponCounts) {
            this.discountCouponCounts = discountCouponCounts;
        }

        public int getGoldBalance() {
            return goldBalance;
        }

        public void setGoldBalance(int goldBalance) {
            this.goldBalance = goldBalance;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getJobArea() {
            return jobArea;
        }

        public void setJobArea(String jobArea) {
            this.jobArea = jobArea;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public int getJobTime() {
            return jobTime;
        }

        public void setJobTime(int jobTime) {
            this.jobTime = jobTime;
        }

        public int getLastStudyTime() {
            return lastStudyTime;
        }

        public void setLastStudyTime(int lastStudyTime) {
            this.lastStudyTime = lastStudyTime;
        }

        public int getLearnDays() {
            return learnDays;
        }

        public void setLearnDays(int learnDays) {
            this.learnDays = learnDays;
        }

        public int getLearnTime() {
            return learnTime;
        }

        public void setLearnTime(int learnTime) {
            this.learnTime = learnTime;
        }

        public int getLevalTime() {
            return levalTime;
        }

        public void setLevalTime(int levalTime) {
            this.levalTime = levalTime;
        }

        public String getNowArea() {
            return nowArea;
        }

        public void setNowArea(String nowArea) {
            this.nowArea = nowArea;
        }

        public String getParentInviteCode() {
            return parentInviteCode;
        }

        public void setParentInviteCode(String parentInviteCode) {
            this.parentInviteCode = parentInviteCode;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getQuestionCounts() {
            return questionCounts;
        }

        public void setQuestionCounts(int questionCounts) {
            this.questionCounts = questionCounts;
        }

        public int getReciveBalance() {
            return reciveBalance;
        }

        public void setReciveBalance(int reciveBalance) {
            this.reciveBalance = reciveBalance;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getShareState() {
            return shareState;
        }

        public void setShareState(int shareState) {
            this.shareState = shareState;
        }

        public int getTeachState() {
            return teachState;
        }

        public void setTeachState(int teachState) {
            this.teachState = teachState;
        }

        public String getTrainAttention() {
            return trainAttention;
        }

        public void setTrainAttention(String trainAttention) {
            this.trainAttention = trainAttention;
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

        public String getUserLeval() {
            return userLeval;
        }

        public void setUserLeval(String userLeval) {
            this.userLeval = userLeval;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getWorkExperience() {
            return workExperience;
        }

        public void setWorkExperience(String workExperience) {
            this.workExperience = workExperience;
        }

        public String getWxId() {
            return wxId;
        }

        public void setWxId(String wxId) {
            this.wxId = wxId;
        }
    }
}
