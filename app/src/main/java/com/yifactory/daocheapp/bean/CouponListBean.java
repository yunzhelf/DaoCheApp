package com.yifactory.daocheapp.bean;

import java.io.Serializable;
import java.util.List;

public class CouponListBean {

    /**
     * data : [{"cId":"250","coupon":{"cId":"250","createTime":"2018-03-30 00:02:46","deleteFlag":0,"detail":"马自达钟情的情人-转子发动机","endTime":"2018-03-30 00:00:00","fcId":"1","fcInfo":[{"backgroundImg":"http://test-ze-bj.oss-cn-beijing.aliyuncs.com/fbaf44a54215458092b908b470f08fa5","btnIndex":0,"categoryId":"","categoryLeval":1,"categorySecondList":[],"fcId":"1","firstCategoryContent":"运营管理","isHot":1,"isIndex":1,"updateTime":"2018-04-11 16:09:56"}],"id":"250","minPrice":5,"percent":0.8,"scId":"1","scInfo":[],"startTime":"2018-03-30 00:00:00","title":"让我们荡起双桨","updateTime":"2018-03-30 00:02:46"},"createTime":"2018-03-09 13:55:51","deleteFlag":0,"state":0,"uId":"f99bda976cde43d18ebf497fb58c4320","ucId":"fb78b466ede24ee0b15645963c3d4248","updateTime":"2018-04-09 10:25:47"},{"cId":"222","coupon":{"cId":"222","createTime":"2018-03-09 12:46:48","deleteFlag":1,"detail":"加油","endTime":"2018-04-08 12:46:41","fcId":"1","fcInfo":[{"backgroundImg":"http://test-ze-bj.oss-cn-beijing.aliyuncs.com/fbaf44a54215458092b908b470f08fa5","btnIndex":0,"categoryId":"","categoryLeval":1,"categorySecondList":[],"fcId":"1","firstCategoryContent":"运营管理","isHot":1,"isIndex":1,"updateTime":"2018-04-11 16:09:56"}],"id":"222","minPrice":10,"percent":0.8,"scId":"1","scInfo":[],"startTime":"2018-03-09 12:46:38","title":"精神的食粮不能停!","updateTime":"2018-03-30 00:02:34"},"createTime":"2018-03-09 13:55:51","deleteFlag":0,"state":0,"uId":"f99bda976cde43d18ebf497fb58c4320","ucId":"sdfadfadsf","updateTime":"2018-03-09 13:55:57"}]
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
         * cId : 250
         * coupon : {"cId":"250","createTime":"2018-03-30 00:02:46","deleteFlag":0,"detail":"马自达钟情的情人-转子发动机","endTime":"2018-03-30 00:00:00","fcId":"1","fcInfo":[{"backgroundImg":"http://test-ze-bj.oss-cn-beijing.aliyuncs.com/fbaf44a54215458092b908b470f08fa5","btnIndex":0,"categoryId":"","categoryLeval":1,"categorySecondList":[],"fcId":"1","firstCategoryContent":"运营管理","isHot":1,"isIndex":1,"updateTime":"2018-04-11 16:09:56"}],"id":"250","minPrice":5,"percent":0.8,"scId":"1","scInfo":[],"startTime":"2018-03-30 00:00:00","title":"让我们荡起双桨","updateTime":"2018-03-30 00:02:46"}
         * createTime : 2018-03-09 13:55:51
         * deleteFlag : 0
         * state : 0
         * uId : f99bda976cde43d18ebf497fb58c4320
         * ucId : fb78b466ede24ee0b15645963c3d4248
         * updateTime : 2018-04-09 10:25:47
         */

        private String cId;
        private CouponBean coupon;
        private String createTime;
        private int deleteFlag;
        private int state;
        private String uId;
        private String ucId;
        private String updateTime;

        public String getCId() {
            return cId;
        }

        public void setCId(String cId) {
            this.cId = cId;
        }

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getUId() {
            return uId;
        }

        public void setUId(String uId) {
            this.uId = uId;
        }

        public String getUcId() {
            return ucId;
        }

        public void setUcId(String ucId) {
            this.ucId = ucId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public static class CouponBean {
            /**
             * cId : 250
             * createTime : 2018-03-30 00:02:46
             * deleteFlag : 0
             * detail : 马自达钟情的情人-转子发动机
             * endTime : 2018-03-30 00:00:00
             * fcId : 1
             * fcInfo : [{"backgroundImg":"http://test-ze-bj.oss-cn-beijing.aliyuncs.com/fbaf44a54215458092b908b470f08fa5","btnIndex":0,"categoryId":"","categoryLeval":1,"categorySecondList":[],"fcId":"1","firstCategoryContent":"运营管理","isHot":1,"isIndex":1,"updateTime":"2018-04-11 16:09:56"}]
             * id : 250
             * minPrice : 5
             * percent : 0.8
             * scId : 1
             * scInfo : []
             * startTime : 2018-03-30 00:00:00
             * title : 让我们荡起双桨
             * updateTime : 2018-03-30 00:02:46
             */

            private String cId;
            private String createTime;
            private int deleteFlag;
            private String detail;
            private String endTime;
            private String fcId;
            private String id;
            private int minPrice;
            private double percent;
            private String scId;
            private String startTime;
            private String title;
            private String updateTime;
            private List<FcInfoBean> fcInfo;
            private List<?> scInfo;

            public String getCId() {
                return cId;
            }

            public void setCId(String cId) {
                this.cId = cId;
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

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getFcId() {
                return fcId;
            }

            public void setFcId(String fcId) {
                this.fcId = fcId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(int minPrice) {
                this.minPrice = minPrice;
            }

            public double getPercent() {
                return percent;
            }

            public void setPercent(double percent) {
                this.percent = percent;
            }

            public String getScId() {
                return scId;
            }

            public void setScId(String scId) {
                this.scId = scId;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public List<FcInfoBean> getFcInfo() {
                return fcInfo;
            }

            public void setFcInfo(List<FcInfoBean> fcInfo) {
                this.fcInfo = fcInfo;
            }

            public List<?> getScInfo() {
                return scInfo;
            }

            public void setScInfo(List<?> scInfo) {
                this.scInfo = scInfo;
            }

            public static class FcInfoBean {
                /**
                 * backgroundImg : http://test-ze-bj.oss-cn-beijing.aliyuncs.com/fbaf44a54215458092b908b470f08fa5
                 * btnIndex : 0
                 * categoryId :
                 * categoryLeval : 1
                 * categorySecondList : []
                 * fcId : 1
                 * firstCategoryContent : 运营管理
                 * isHot : 1
                 * isIndex : 1
                 * updateTime : 2018-04-11 16:09:56
                 */

                private String backgroundImg;
                private int btnIndex;
                private String categoryId;
                private int categoryLeval;
                private String fcId;
                private String firstCategoryContent;
                private int isHot;
                private int isIndex;
                private String updateTime;
                private List<?> categorySecondList;

                public String getBackgroundImg() {
                    return backgroundImg;
                }

                public void setBackgroundImg(String backgroundImg) {
                    this.backgroundImg = backgroundImg;
                }

                public int getBtnIndex() {
                    return btnIndex;
                }

                public void setBtnIndex(int btnIndex) {
                    this.btnIndex = btnIndex;
                }

                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

                public int getCategoryLeval() {
                    return categoryLeval;
                }

                public void setCategoryLeval(int categoryLeval) {
                    this.categoryLeval = categoryLeval;
                }

                public String getFcId() {
                    return fcId;
                }

                public void setFcId(String fcId) {
                    this.fcId = fcId;
                }

                public String getFirstCategoryContent() {
                    return firstCategoryContent;
                }

                public void setFirstCategoryContent(String firstCategoryContent) {
                    this.firstCategoryContent = firstCategoryContent;
                }

                public int getIsHot() {
                    return isHot;
                }

                public void setIsHot(int isHot) {
                    this.isHot = isHot;
                }

                public int getIsIndex() {
                    return isIndex;
                }

                public void setIsIndex(int isIndex) {
                    this.isIndex = isIndex;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public List<?> getCategorySecondList() {
                    return categorySecondList;
                }

                public void setCategorySecondList(List<?> categorySecondList) {
                    this.categorySecondList = categorySecondList;
                }
            }
        }
    }
}
