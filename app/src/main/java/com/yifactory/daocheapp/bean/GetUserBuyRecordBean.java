package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetUserBuyRecordBean {

    /**
     * data : [{"cwglCount":0,"kfglCount":0,"rsxzCount":0,"scyxCount":0,"shglCount":0,"xsglCount":0,"ysywCount":0,"yyglCount":1}]
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
         * cwglCount : 0
         * kfglCount : 0
         * rsxzCount : 0
         * scyxCount : 0
         * shglCount : 0
         * xsglCount : 0
         * ysywCount : 0
         * yyglCount : 1
         */

        private int cwglCount;
        private int kfglCount;
        private int rsxzCount;
        private int scyxCount;
        private int shglCount;
        private int xsglCount;
        private int ysywCount;
        private int yyglCount;

        public int getCwglCount() {
            return cwglCount;
        }

        public void setCwglCount(int cwglCount) {
            this.cwglCount = cwglCount;
        }

        public int getKfglCount() {
            return kfglCount;
        }

        public void setKfglCount(int kfglCount) {
            this.kfglCount = kfglCount;
        }

        public int getRsxzCount() {
            return rsxzCount;
        }

        public void setRsxzCount(int rsxzCount) {
            this.rsxzCount = rsxzCount;
        }

        public int getScyxCount() {
            return scyxCount;
        }

        public void setScyxCount(int scyxCount) {
            this.scyxCount = scyxCount;
        }

        public int getShglCount() {
            return shglCount;
        }

        public void setShglCount(int shglCount) {
            this.shglCount = shglCount;
        }

        public int getXsglCount() {
            return xsglCount;
        }

        public void setXsglCount(int xsglCount) {
            this.xsglCount = xsglCount;
        }

        public int getYsywCount() {
            return ysywCount;
        }

        public void setYsywCount(int ysywCount) {
            this.ysywCount = ysywCount;
        }

        public int getYyglCount() {
            return yyglCount;
        }

        public void setYyglCount(int yyglCount) {
            this.yyglCount = yyglCount;
        }
    }
}
