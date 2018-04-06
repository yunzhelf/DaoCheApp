package com.yifactory.daocheapp.bean;

import java.io.Serializable;
import java.util.List;

public class GetCategoryListBean {

    /**
     * data : [{"backgroundImg":"pictures/app/btn/da@2x.png","categorySecondList":[{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"1","secondContent":"集团管理","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"2","secondContent":"运营思路","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"3","secondContent":"组织架构","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"4","secondContent":"过程管理","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"5","secondContent":"岗位细则","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"6","secondContent":"会议管理","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"7","secondContent":"KPI考核","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"8","secondContent":"奖励与处罚","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"9","secondContent":"其他","updateTime":"2018-03-13 15:30:34"}],"fcId":"1","firstCategoryContent":"运营管理","isHot":0,"isIndex":1,"updateTime":"2018-03-16 11:43:18"},{"backgroundImg":"pictures/app/btn/kai@2x.png","categorySecondList":[{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"10","secondContent":"展厅管理","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"11","secondContent":"二网管理","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"12","secondContent":"IDCC管理","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"13","secondContent":"流程管理","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"14","secondContent":"潜客管理","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"15","secondContent":"保有客户管理","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"16","secondContent":"员工管理","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"17","secondContent":"数据分析","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"18","secondContent":"客户邀约","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"19","secondContent":"接待技巧","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"20","secondContent":"谈判技巧","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"21","secondContent":"试乘试驾","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"22","secondContent":"产品讲解","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"23","secondContent":"进度管理","updateTime":"2018-03-13 15:30:36"},{"createTime":"2018-03-05 11:22:30","deleteFlag":0,"fcId":"2","scId":"24","secondContent":"其他","updateTime":"2018-03-13 15:30:36"}],"fcId":"2","firstCategoryContent":"销售管理","isHot":0,"isIndex":1,"updateTime":"2018-03-16 11:43:18"},{"backgroundImg":"pictures/app/btn/si@2x.png","categorySecondList":[{"createTime":"2018-03-05 11:24:04","deleteFlag":0,"fcId":"3","scId":"25","secondContent":"外展集客","updateTime":"2018-03-13 15:30:39"},{"createTime":"2018-03-05 11:24:04","deleteFlag":0,"fcId":"3","scId":"26","secondContent":"活动策划","updateTime":"2018-03-13 15:30:39"},{"createTime":"2018-03-05 11:24:04","deleteFlag":0,"fcId":"3","scId":"27","secondContent":"展厅布置","updateTime":"2018-03-13 15:30:39"},{"createTime":"2018-03-05 11:24:04","deleteFlag":0,"fcId":"3","scId":"28","secondContent":"竞品调研","updateTime":"2018-03-13 15:30:39"},{"createTime":"2018-03-05 11:24:04","deleteFlag":0,"fcId":"3","scId":"29","secondContent":"数据分析","updateTime":"2018-03-13 15:30:39"},{"createTime":"2018-03-05 11:24:04","deleteFlag":0,"fcId":"3","scId":"30","secondContent":"车展团购","updateTime":"2018-03-13 15:30:39"},{"createTime":"2018-03-05 11:24:04","deleteFlag":0,"fcId":"3","scId":"31","secondContent":"异业联盟","updateTime":"2018-03-13 15:30:39"},{"createTime":"2018-03-05 11:24:04","deleteFlag":0,"fcId":"3","scId":"32","secondContent":"文案设计","updateTime":"2018-03-13 15:30:39"},{"createTime":"2018-03-05 11:24:04","deleteFlag":0,"fcId":"3","scId":"33","secondContent":"其他","updateTime":"2018-03-13 15:30:39"}],"fcId":"3","firstCategoryContent":"市场营销","isHot":1,"isIndex":1,"updateTime":"2018-03-16 11:43:18"},{"backgroundImg":"pictures/app/btn/wei@2x.png","categorySecondList":[{"createTime":"2018-03-05 11:25:13","deleteFlag":0,"fcId":"4","scId":"34","secondContent":"潜客满意度","updateTime":"2018-03-13 15:30:42"},{"createTime":"2018-03-05 11:25:13","deleteFlag":0,"fcId":"4","scId":"35","secondContent":"保有客户满意度","updateTime":"2018-03-13 15:30:42"},{"createTime":"2018-03-05 11:25:13","deleteFlag":0,"fcId":"4","scId":"36","secondContent":"爱车讲堂","updateTime":"2018-03-13 15:30:42"},{"createTime":"2018-03-05 11:25:13","deleteFlag":0,"fcId":"4","scId":"37","secondContent":"回访话术","updateTime":"2018-03-13 15:30:42"},{"createTime":"2018-03-05 11:25:13","deleteFlag":0,"fcId":"4","scId":"38","secondContent":"自驾游","updateTime":"2018-03-13 15:30:42"},{"createTime":"2018-03-05 11:25:13","deleteFlag":0,"fcId":"4","scId":"39","secondContent":"维护活动","updateTime":"2018-03-13 15:30:42"},{"createTime":"2018-03-05 11:25:13","deleteFlag":0,"fcId":"4","scId":"40","secondContent":"客户整理","updateTime":"2018-03-13 15:30:42"},{"createTime":"2018-03-05 11:25:13","deleteFlag":0,"fcId":"4","scId":"41","secondContent":"其他","updateTime":"2018-03-13 15:30:42"}],"fcId":"4","firstCategoryContent":"衍生业务","isHot":0,"isIndex":1,"updateTime":"2018-03-16 11:43:19"},{"backgroundImg":"pictures/app/btn/ti@2x.png","categorySecondList":[{"createTime":"2018-03-05 11:26:14","deleteFlag":0,"fcId":"5","scId":"42","secondContent":"金融分期","updateTime":"2018-03-13 15:30:44"},{"createTime":"2018-03-05 11:26:14","deleteFlag":0,"fcId":"5","scId":"43","secondContent":"保险续保","updateTime":"2018-03-13 15:30:44"},{"createTime":"2018-03-05 11:26:14","deleteFlag":0,"fcId":"5","scId":"44","secondContent":"精品加装","updateTime":"2018-03-13 15:30:44"},{"createTime":"2018-03-05 11:26:14","deleteFlag":0,"fcId":"5","scId":"45","secondContent":"二手车","updateTime":"2018-03-13 15:30:44"},{"createTime":"2018-03-05 11:26:14","deleteFlag":0,"fcId":"5","scId":"46","secondContent":"车辆延保","updateTime":"2018-03-13 15:30:44"},{"createTime":"2018-03-05 11:26:14","deleteFlag":0,"fcId":"5","scId":"47","secondContent":"其他","updateTime":"2018-03-13 15:30:44"}],"fcId":"5","firstCategoryContent":"客户维护","isHot":0,"isIndex":1,"updateTime":"2018-03-16 11:43:19"},{"backgroundImg":"pictures/app/btn/li@2x.png","categorySecondList":[{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"48","secondContent":"服务顾问","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"49","secondContent":"增值业务","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"50","secondContent":"流程管理","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"51","secondContent":"绩效管理","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"52","secondContent":"车间管理","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"53","secondContent":"投诉处理","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"54","secondContent":"维修钣喷","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"55","secondContent":"数据分析","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"56","secondContent":"配件管理","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"57","secondContent":"三包索赔","updateTime":"2018-03-13 15:30:47"},{"createTime":"2018-03-05 11:28:01","deleteFlag":0,"fcId":"6","scId":"58","secondContent":"其他","updateTime":"2018-03-13 15:30:47"}],"fcId":"6","firstCategoryContent":"售后管理","isHot":0,"isIndex":1,"updateTime":"2018-03-16 11:43:19"},{"backgroundImg":"pictures/app/btn/sheng@2x.png","categorySecondList":[{"createTime":"2018-03-05 11:29:14","deleteFlag":0,"fcId":"7","scId":"59","secondContent":"招聘入职","updateTime":"2018-03-13 15:30:49"},{"createTime":"2018-03-05 11:29:14","deleteFlag":0,"fcId":"7","scId":"60","secondContent":"规章制度","updateTime":"2018-03-13 15:30:49"},{"createTime":"2018-03-05 11:29:14","deleteFlag":0,"fcId":"7","scId":"61","secondContent":"企业文化","updateTime":"2018-03-13 15:30:49"},{"createTime":"2018-03-05 11:29:14","deleteFlag":0,"fcId":"7","scId":"62","secondContent":"资产管理","updateTime":"2018-03-13 15:30:49"},{"createTime":"2018-03-05 11:29:14","deleteFlag":0,"fcId":"7","scId":"63","secondContent":"员工培训","updateTime":"2018-03-13 15:30:49"},{"createTime":"2018-03-05 11:29:14","deleteFlag":0,"fcId":"7","scId":"64","secondContent":"会议管理","updateTime":"2018-03-13 15:30:49"},{"createTime":"2018-03-05 11:29:14","deleteFlag":0,"fcId":"7","scId":"65","secondContent":"团队建设","updateTime":"2018-03-13 15:30:49"},{"createTime":"2018-03-05 11:29:14","deleteFlag":0,"fcId":"7","scId":"66","secondContent":"其他","updateTime":"2018-03-13 15:30:49"}],"fcId":"7","firstCategoryContent":"人事行政","isHot":0,"isIndex":1,"updateTime":"2018-03-16 11:43:19"},{"backgroundImg":"pictures/app/btn/neng@2x.png","categorySecondList":[{"createTime":"2018-03-05 11:30:01","deleteFlag":0,"fcId":"8","scId":"67","secondContent":"日常报销","updateTime":"2018-03-13 15:30:52"},{"createTime":"2018-03-05 11:30:01","deleteFlag":0,"fcId":"8","scId":"68","secondContent":"资金管理","updateTime":"2018-03-13 15:30:52"},{"createTime":"2018-03-05 11:30:01","deleteFlag":0,"fcId":"8","scId":"69","secondContent":"工资核算","updateTime":"2018-03-13 15:30:52"},{"createTime":"2018-03-05 11:30:01","deleteFlag":0,"fcId":"8","scId":"70","secondContent":"财务报表","updateTime":"2018-03-13 15:30:52"},{"createTime":"2018-03-05 11:30:01","deleteFlag":0,"fcId":"8","scId":"71","secondContent":"厂家核算","updateTime":"2018-03-13 15:30:52"},{"createTime":"2018-03-05 11:30:01","deleteFlag":0,"fcId":"8","scId":"72","secondContent":"其他","updateTime":"2018-03-13 15:30:52"}],"fcId":"8","firstCategoryContent":"财务管理","isHot":0,"isIndex":1,"updateTime":"2018-03-16 11:43:19"},{"backgroundImg":"categoryFirst/2/20180314163144-9c4a90be63ef3a73202fe9.jpg","categorySecondList":[{"createTime":"2018-03-06 11:05:05","deleteFlag":0,"fcId":"9","scId":"73","secondContent":"汽车超市","updateTime":"2018-03-13 15:31:48"},{"createTime":"2018-03-06 11:05:31","deleteFlag":0,"fcId":"9","scId":"74","secondContent":"二手车","updateTime":"2018-03-13 15:31:48"},{"createTime":"2018-03-06 11:05:36","deleteFlag":0,"fcId":"9","scId":"75","secondContent":"后市场","updateTime":"2018-03-13 15:31:48"},{"createTime":"2018-03-06 11:06:14","deleteFlag":0,"fcId":"9","scId":"76","secondContent":"新能源","updateTime":"2018-03-13 15:31:48"},{"createTime":"2018-03-06 11:06:01","deleteFlag":0,"fcId":"9","scId":"77","secondContent":"行业分析","updateTime":"2018-03-13 15:31:48"},{"createTime":"2018-03-06 11:07:43","deleteFlag":0,"fcId":"9","scId":"78","secondContent":"人物专访","updateTime":"2018-03-13 15:31:48"}],"fcId":"9","firstCategoryContent":"其他","isHot":0,"isIndex":0,"updateTime":"2018-03-14 22:45:34"}]
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
         * backgroundImg : pictures/app/btn/da@2x.png
         * categorySecondList : [{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"1","secondContent":"集团管理","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"2","secondContent":"运营思路","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"3","secondContent":"组织架构","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"4","secondContent":"过程管理","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"5","secondContent":"岗位细则","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"6","secondContent":"会议管理","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"7","secondContent":"KPI考核","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"8","secondContent":"奖励与处罚","updateTime":"2018-03-13 15:30:34"},{"createTime":"2018-03-05 11:20:29","deleteFlag":0,"fcId":"1","scId":"9","secondContent":"其他","updateTime":"2018-03-13 15:30:34"}]
         * fcId : 1
         * firstCategoryContent : 运营管理
         * isHot : 0
         * isIndex : 1
         * updateTime : 2018-03-16 11:43:18
         */

        private String backgroundImg;
        private String fcId;
        private String firstCategoryContent;
        private int isHot;
        private int isIndex;
        private String updateTime;
        private List<CategorySecondListBean> categorySecondList;

        public String getBackgroundImg() {
            return backgroundImg;
        }

        public void setBackgroundImg(String backgroundImg) {
            this.backgroundImg = backgroundImg;
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

        public List<CategorySecondListBean> getCategorySecondList() {
            return categorySecondList;
        }

        public void setCategorySecondList(List<CategorySecondListBean> categorySecondList) {
            this.categorySecondList = categorySecondList;
        }

        public static class CategorySecondListBean implements Serializable{
            /**
             * createTime : 2018-03-05 11:20:29
             * deleteFlag : 0
             * fcId : 1
             * scId : 1
             * secondContent : 集团管理
             * updateTime : 2018-03-13 15:30:34
             */

            private String createTime;
            private int deleteFlag;
            private String fcId;
            private String scId;
            private String secondContent;
            private String updateTime;

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

            public String getFcId() {
                return fcId;
            }

            public void setFcId(String fcId) {
                this.fcId = fcId;
            }

            public String getScId() {
                return scId;
            }

            public void setScId(String scId) {
                this.scId = scId;
            }

            public String getSecondContent() {
                return secondContent;
            }

            public void setSecondContent(String secondContent) {
                this.secondContent = secondContent;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
