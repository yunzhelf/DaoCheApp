package com.yifactory.daocheapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BankCardBean {

    /**
     * data : [{"bankAddress":"沈阳市","bankName":"中国农业银行","bankNum":"6210810730016925887","createTime":"2018-03-09 09:28:57","deleteFlag":0,"subId":"58716be7f66d41b38a1f0bf9cc67f5cb","uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-09 09:28:58","userMobile":"15140088201","userName":"王振读"},{"bankAddress":"沈阳市","bankName":"中国农业银行","bankNum":"6210810730016925887","createTime":"2018-03-09 09:31:02","deleteFlag":0,"subId":"a77b25a9ec0f483f8633b93482bf211a","uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-09 09:31:03","userMobile":"15140088201","userName":"王振读"}]
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
         * bankAddress : 沈阳市
         * bankName : 中国农业银行
         * bankNum : 6210810730016925887
         * createTime : 2018-03-09 09:28:57
         * deleteFlag : 0
         * subId : 58716be7f66d41b38a1f0bf9cc67f5cb
         * uId : f99bda976cde43d18ebf497fb58c4320
         * updateTime : 2018-03-09 09:28:58
         * userMobile : 15140088201
         * userName : 王振读
         */

        private String bankAddress;
        private String bankName;
        private String bankNum;
        private String createTime;
        private int deleteFlag;
        private String subId;
        private String uId;
        private String updateTime;
        private String userMobile;
        private String userName;


        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankNum() {
            return bankNum;
        }

        public void setBankNum(String bankNum) {
            this.bankNum = bankNum;
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

        public String getSubId() {
            return subId;
        }

        public void setSubId(String subId) {
            this.subId = subId;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.bankAddress);
            dest.writeString(this.bankName);
            dest.writeString(this.bankNum);
            dest.writeString(this.createTime);
            dest.writeInt(this.deleteFlag);
            dest.writeString(this.subId);
            dest.writeString(this.uId);
            dest.writeString(this.updateTime);
            dest.writeString(this.userMobile);
            dest.writeString(this.userName);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.bankAddress = in.readString();
            this.bankName = in.readString();
            this.bankNum = in.readString();
            this.createTime = in.readString();
            this.deleteFlag = in.readInt();
            this.subId = in.readString();
            this.uId = in.readString();
            this.updateTime = in.readString();
            this.userMobile = in.readString();
            this.userName = in.readString();
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
}
