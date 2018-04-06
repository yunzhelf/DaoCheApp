package com.yifactory.daocheapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddUserBalanceBean {

    /**
     * data : [{"package":"Sign=WXPay","appid":"wx29599880807e694c","sign":"4AC38E90482128233873F86B0ED57949","partnerid":"1499007532","prepayid":"wx201803231345333b063ab0cc0776906794","noncestr":"66f4d5d324e9449bb6ab67298c201112","timestamp":"1521783932"}]
     * msg : 发起成功
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
         * package : Sign=WXPay
         * appid : wx29599880807e694c
         * sign : 4AC38E90482128233873F86B0ED57949
         * partnerid : 1499007532
         * prepayid : wx201803231345333b063ab0cc0776906794
         * noncestr : 66f4d5d324e9449bb6ab67298c201112
         * timestamp : 1521783932
         */

        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
