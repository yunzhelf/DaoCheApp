package com.yifactory.daocheapp.bean;

import java.util.List;

public class AddRegisterShareBean {

    /**
     * data : [{"address":"辽宁省 沈阳市 浑南新区(东陵区)哦婆婆","age":12,"createTime":"2018-03-28 16:45:16","doSuccessTime":null,"goodAtArea":"哦poor您","headImg":"","jobTime":"2","jobUbdergo":"我婆婆哦哦","name":"哈哈","nowCompany":"哦婆婆","oldHonor":"","selfAssessment":"哦嗖嗖嗖送","sex":0,"state":0,"telphone":"18944544","trId":"5041e20f9b3f48c98db9e5d8613c5674","trainUbdergo":"磨破送","uId":"916e60bbe84b4913a5f2b4a28300fe2a","updateTime":null}]
     * msg : 申请成功,请耐心等待审核
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
         * address : 辽宁省 沈阳市 浑南新区(东陵区)哦婆婆
         * age : 12
         * createTime : 2018-03-28 16:45:16
         * doSuccessTime : null
         * goodAtArea : 哦poor您
         * headImg :
         * jobTime : 2
         * jobUbdergo : 我婆婆哦哦
         * name : 哈哈
         * nowCompany : 哦婆婆
         * oldHonor :
         * selfAssessment : 哦嗖嗖嗖送
         * sex : 0
         * state : 0
         * telphone : 18944544
         * trId : 5041e20f9b3f48c98db9e5d8613c5674
         * trainUbdergo : 磨破送
         * uId : 916e60bbe84b4913a5f2b4a28300fe2a
         * updateTime : null
         */

        private String address;
        private int age;
        private String createTime;
        private Object doSuccessTime;
        private String goodAtArea;
        private String headImg;
        private String jobTime;
        private String jobUbdergo;
        private String name;
        private String nowCompany;
        private String oldHonor;
        private String selfAssessment;
        private int sex;
        private int state;
        private String telphone;
        private String trId;
        private String trainUbdergo;
        private String uId;
        private Object updateTime;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getDoSuccessTime() {
            return doSuccessTime;
        }

        public void setDoSuccessTime(Object doSuccessTime) {
            this.doSuccessTime = doSuccessTime;
        }

        public String getGoodAtArea() {
            return goodAtArea;
        }

        public void setGoodAtArea(String goodAtArea) {
            this.goodAtArea = goodAtArea;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getJobTime() {
            return jobTime;
        }

        public void setJobTime(String jobTime) {
            this.jobTime = jobTime;
        }

        public String getJobUbdergo() {
            return jobUbdergo;
        }

        public void setJobUbdergo(String jobUbdergo) {
            this.jobUbdergo = jobUbdergo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNowCompany() {
            return nowCompany;
        }

        public void setNowCompany(String nowCompany) {
            this.nowCompany = nowCompany;
        }

        public String getOldHonor() {
            return oldHonor;
        }

        public void setOldHonor(String oldHonor) {
            this.oldHonor = oldHonor;
        }

        public String getSelfAssessment() {
            return selfAssessment;
        }

        public void setSelfAssessment(String selfAssessment) {
            this.selfAssessment = selfAssessment;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getTrId() {
            return trId;
        }

        public void setTrId(String trId) {
            this.trId = trId;
        }

        public String getTrainUbdergo() {
            return trainUbdergo;
        }

        public void setTrainUbdergo(String trainUbdergo) {
            this.trainUbdergo = trainUbdergo;
        }

        public String getUId() {
            return uId;
        }

        public void setUId(String uId) {
            this.uId = uId;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }
}
