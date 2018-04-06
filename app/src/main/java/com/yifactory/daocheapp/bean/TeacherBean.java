package com.yifactory.daocheapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by sunxj on 2018/3/30.
 */

public class TeacherBean implements Parcelable {

    /**
     * data : [{"address":"辽宁沈阳/浑南新区白塔河路理想新城","age":23,"createTime":"2018-03-09 09:31:02","doSuccessTime":null,"goodAtArea":"java","headImg":"pictures/teacher/2/20180309093102-0e4ef487f3f2e3531622c5.jpg,pictures/teacher/2/20180309093102-944f79b1f85522b4457527.jpg,pictures/teacher/2/20180309093102-764e4896d856b0fa13def7.jpg","jobTime":"1","jobUbdergo":"专业从事互联网应用开发","name":"王老师专业辅导","nowCompany":"沈阳普易网科技","oldHonor":"省高级教师职称","selfAssessment":"兴趣大于学习","sex":1,"state":0,"telphone":"15140088201","trId":"20d804d2229e4b86af6e3fda155b5ad5","trainUbdergo":"自学","uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-09 09:31:03"}]
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

    public static class DataBean implements Parcelable {

        /**
         * address : 辽宁沈阳/浑南新区白塔河路理想新城
         * age : 23
         * createTime : 2018-03-09 09:31:02
         * doSuccessTime : null
         * goodAtArea : java
         * headImg : pictures/teacher/2/20180309093102-0e4ef487f3f2e3531622c5.jpg,pictures/teacher/2/20180309093102-944f79b1f85522b4457527.jpg,pictures/teacher/2/20180309093102-764e4896d856b0fa13def7.jpg
         * jobTime : 1
         * jobUbdergo : 专业从事互联网应用开发
         * name : 王老师专业辅导
         * nowCompany : 沈阳普易网科技
         * oldHonor : 省高级教师职称
         * selfAssessment : 兴趣大于学习
         * sex : 1
         * state : 0
         * telphone : 15140088201
         * trId : 20d804d2229e4b86af6e3fda155b5ad5
         * trainUbdergo : 自学
         * uId : f99bda976cde43d18ebf497fb58c4320
         * updateTime : 2018-03-09 09:31:03
         */

        private String address;
        private int age;
        private String createTime;
        private String doSuccessTime;
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
        private String updateTime;

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

        public String getDoSuccessTime() {
            return doSuccessTime;
        }

        public void setDoSuccessTime(String doSuccessTime) {
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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.address);
            dest.writeInt(this.age);
            dest.writeString(this.createTime);
            dest.writeString(this.doSuccessTime);
            dest.writeString(this.goodAtArea);
            dest.writeString(this.headImg);
            dest.writeString(this.jobTime);
            dest.writeString(this.jobUbdergo);
            dest.writeString(this.name);
            dest.writeString(this.nowCompany);
            dest.writeString(this.oldHonor);
            dest.writeString(this.selfAssessment);
            dest.writeInt(this.sex);
            dest.writeInt(this.state);
            dest.writeString(this.telphone);
            dest.writeString(this.trId);
            dest.writeString(this.trainUbdergo);
            dest.writeString(this.uId);
            dest.writeString(this.updateTime);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.address = in.readString();
            this.age = in.readInt();
            this.createTime = in.readString();
            this.doSuccessTime = in.readString();
            this.goodAtArea = in.readString();
            this.headImg = in.readString();
            this.jobTime = in.readString();
            this.jobUbdergo = in.readString();
            this.name = in.readString();
            this.nowCompany = in.readString();
            this.oldHonor = in.readString();
            this.selfAssessment = in.readString();
            this.sex = in.readInt();
            this.state = in.readInt();
            this.telphone = in.readString();
            this.trId = in.readString();
            this.trainUbdergo = in.readString();
            this.uId = in.readString();
            this.updateTime = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeString(this.responseState);
        dest.writeTypedList(this.data);
    }

    public TeacherBean() {
    }

    protected TeacherBean(Parcel in) {
        this.msg = in.readString();
        this.responseState = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<TeacherBean> CREATOR = new Parcelable.Creator<TeacherBean>() {
        @Override
        public TeacherBean createFromParcel(Parcel source) {
            return new TeacherBean(source);
        }

        @Override
        public TeacherBean[] newArray(int size) {
            return new TeacherBean[size];
        }
    };
}
