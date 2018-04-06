package com.yifactory.daocheapp.bean;

import java.util.List;

public class RegisterBean {

    /**
     * data : [{"answerCounts":0,"attentionCounts":0,"buyCounts":0,"companyName":"宏达科技","createTime":"2018-03-19 13:34:17","depositePercent":0,"discountCouponCounts":0,"goldBalance":0,"headImg":"http://wzdimg.oss-cn-beijing.aliyuncs.com/2213db17-da51-4ed4-b931-736cf3ff2a16.jpg","id":"916e60bbe84b4913a5f2b4a28300fe2a","inviteCode":"e9627b1","jobArea":"4S店","jobName":"技术员","jobTime":2,"lastStudyTime":0,"learnDays":0,"learnTime":0,"levalTime":0,"nowArea":"辽宁省 沈阳市 浑南新区(东陵区)","parentInviteCode":"","password":"c4ca4238a0b923820dcc509a6f75849b","questionCounts":0,"reciveBalance":0,"role":0,"sex":1,"shareState":0,"teachState":0,"trainAttention":"销售管理","uId":"916e60bbe84b4913a5f2b4a28300fe2a","updateTime":"2018-03-19 13:34:17","userLeval":"","userMobile":"13109876143","userName":"哈哈","workExperience":"","wxId":""}]
     * msg : 注册成功
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
         * answerCounts : 0
         * attentionCounts : 0
         * buyCounts : 0
         * companyName : 宏达科技
         * createTime : 2018-03-19 13:34:17
         * depositePercent : 0
         * discountCouponCounts : 0
         * goldBalance : 0
         * headImg : http://wzdimg.oss-cn-beijing.aliyuncs.com/2213db17-da51-4ed4-b931-736cf3ff2a16.jpg
         * id : 916e60bbe84b4913a5f2b4a28300fe2a
         * inviteCode : e9627b1
         * jobArea : 4S店
         * jobName : 技术员
         * jobTime : 2
         * lastStudyTime : 0
         * learnDays : 0
         * learnTime : 0
         * levalTime : 0
         * nowArea : 辽宁省 沈阳市 浑南新区(东陵区)
         * parentInviteCode :
         * password : c4ca4238a0b923820dcc509a6f75849b
         * questionCounts : 0
         * reciveBalance : 0
         * role : 0
         * sex : 1
         * shareState : 0
         * teachState : 0
         * trainAttention : 销售管理
         * uId : 916e60bbe84b4913a5f2b4a28300fe2a
         * updateTime : 2018-03-19 13:34:17
         * userLeval :
         * userMobile : 13109876143
         * userName : 哈哈
         * workExperience :
         * wxId :
         */

        private int answerCounts;
        private int attentionCounts;
        private int buyCounts;
        private String companyName;
        private String createTime;
        private int depositePercent;
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

        public int getDepositePercent() {
            return depositePercent;
        }

        public void setDepositePercent(int depositePercent) {
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
