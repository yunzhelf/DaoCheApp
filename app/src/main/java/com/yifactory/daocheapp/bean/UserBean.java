package com.yifactory.daocheapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunxj on 2018/3/15.
 */

public class UserBean {
    private List<DataBean> data;
    private String msg;
    private String responseState;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

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

    public static class DataBean implements Serializable {
        /*
            answerCounts: 答案数量
            attentionCounts: 关注数量
            buyCounts: 够买数量
            companyName: 公司名称
            createTime: 创建时间
            depositePercent: 提现比例
            discountCouponCounts:优惠券数量
            goldBalance:我的金币
            headImg:头像
            inviteCode:邀请码:
            jobArea:岗位属性
            jobName:岗位名称
            jobTime:工作年限
            lastStudyTime:最后学习时间
            learnDays:学习天数
            learnTime: 学习时间
            nowArea:所在区域
            parentInviteCode: 父邀请码
            questionCounts:我的提问数量
            reciveBalance:我的收益
            role:用户角色:04S店1二手车2汽车超市3维修店4职业老师5媒体人
            sex:性别0未知1男2女
            shareState:分享认证0未发起1审核中2已驳回3已通过
            teachState:讲师认证状态0未发起1审核中2已驳回3已通过
            trainAttention:关注领域
            uId: 用户Id
            userMobile:用户手机号
            userName:用户姓名
            工作经历:workExperience
            wxId:微信ID
         */
        private int answerCounts;
        private int attentionCounts;
        private int buyCounts;
        private String companyName;
        private String createTime;
        private double depositePercent;
        private int discountCouponCounts;
        private double goldBalance;
        private String headImg;
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
        private double reciveBalance;
        private int role;
        private int sex;
        private int shareState;
        private int teachState;
        private String trainAttention;
        private String uId;
        private String userMobile;
        private String userName;
        private String workExperience;
        private String wxId;

        public int getLevalTime() {
            return levalTime;
        }

        public void setLevalTime(int levalTime) {
            this.levalTime = levalTime;
        }

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

        public double getGoldBalance() {
            return goldBalance;
        }

        public void setGoldBalance(double goldBalance) {
            this.goldBalance = goldBalance;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
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

        public double getReciveBalance() {
            return reciveBalance;
        }

        public void setReciveBalance(double reciveBalance) {
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

        public String getuId() {
            return uId;
        }

        public void setuId(String uId) {
            this.uId = uId;
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
