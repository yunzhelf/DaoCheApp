package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetShowMoodCommentBean {

    /**
     * data : [{"cotentBody":"不错不错","createTime":"2018-03-12 14:34:50","creator":{"jobName":"前台经理","nowArea":"沈阳","jobTime":2,"learnTime":0,"headImg":"pictures/user/headImg/2/20180308134429-454e8e98f540d26dd321b4.jpg","sex":0,"companyName":"wingfac","jobArea":"销售","wxId":"","trainAttention":"运营管理,市场营销","userName":"恒宇少年","uId":"f99bda976cde43d18ebf497fb58c4320","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","userLeval":"小白"},"reciver":{},"smId":"8fc5fc88cbfe4656be043be4034fc873","smcId":"b0a76ee347c14de389382f538c6b1cbf","updateTime":"2018-03-12 14:34:50"},{"cotentBody":"不错不错","createTime":"2018-03-12 14:35:43","creator":{"jobName":"","nowArea":"","jobTime":0,"learnTime":0,"headImg":"","sex":0,"companyName":"","jobArea":"","workExperience":"","wxId":"","trainAttention":"","userName":"匿名","uId":"sfadfadfasf","userMobile":"","userLeval":"小白"},"reciver":{"$ref":"$.data[0].creator"},"smId":"8fc5fc88cbfe4656be043be4034fc873","smcId":"e5e7cef6460047c5a1dd3c102c539ab3","updateTime":"2018-03-12 14:35:43"}]
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
         * cotentBody : 不错不错
         * createTime : 2018-03-12 14:34:50
         * creator : {"jobName":"前台经理","nowArea":"沈阳","jobTime":2,"learnTime":0,"headImg":"pictures/user/headImg/2/20180308134429-454e8e98f540d26dd321b4.jpg","sex":0,"companyName":"wingfac","jobArea":"销售","wxId":"","trainAttention":"运营管理,市场营销","userName":"恒宇少年","uId":"f99bda976cde43d18ebf497fb58c4320","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","userLeval":"小白"}
         * reciver : {}
         * smId : 8fc5fc88cbfe4656be043be4034fc873
         * smcId : b0a76ee347c14de389382f538c6b1cbf
         * updateTime : 2018-03-12 14:34:50
         */

        private String cotentBody;
        private String createTime;
        private CreatorBean creator;
        private ReciverBean reciver;
        private String smId;
        private String smcId;
        private String updateTime;

        public String getCotentBody() {
            return cotentBody;
        }

        public void setCotentBody(String cotentBody) {
            this.cotentBody = cotentBody;
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

        public ReciverBean getReciver() {
            return reciver;
        }

        public void setReciver(ReciverBean reciver) {
            this.reciver = reciver;
        }

        public String getSmId() {
            return smId;
        }

        public void setSmId(String smId) {
            this.smId = smId;
        }

        public String getSmcId() {
            return smcId;
        }

        public void setSmcId(String smcId) {
            this.smcId = smcId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public static class CreatorBean {
            /**
             * jobName : 前台经理
             * nowArea : 沈阳
             * jobTime : 2
             * learnTime : 0
             * headImg : pictures/user/headImg/2/20180308134429-454e8e98f540d26dd321b4.jpg
             * sex : 0
             * companyName : wingfac
             * jobArea : 销售
             * wxId :
             * trainAttention : 运营管理,市场营销
             * userName : 恒宇少年
             * uId : f99bda976cde43d18ebf497fb58c4320
             * createTime : 2018-03-08 13:38:58
             * userMobile : 15140088201
             * userLeval : 小白
             */

            private String uId;
            private String userName;
            private String jobName;

            public String getuId() {
                return uId;
            }

            public void setuId(String uId) {
                this.uId = uId;
            }

            public String getJobName() {
                return jobName;
            }

            public void setJobName(String jobName) {
                this.jobName = jobName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class ReciverBean {
            private String userName;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
