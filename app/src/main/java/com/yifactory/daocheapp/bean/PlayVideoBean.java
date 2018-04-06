package com.yifactory.daocheapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayVideoBean implements Parcelable {


    /**
     * data : [{"resource":{"buyState":1,"createTime":"2018-03-09 11:07:12","creator":{"teacherGoodAtArea":"java","teacherAge":23,"headImg":"","teacherName":"王老师专业辅导","teacherSelfAssessment":"兴趣大于学习","sex":0,"teacherNowCompany":"沈阳普易网科技","jobArea":"","teacherOldHonor":"省高级教师职称","wxId":"","trainAttention":"运营管理,市场营销,销售管理,客户维护","userName":"","teacherHeadImg":"pictures/teacher/2/20180309093102-0e4ef487f3f2e3531622c5.jpg,pictures/teacher/2/20180309093102-944f79b1f85522b4457527.jpg,pictures/teacher/2/20180309093102-764e4896d856b0fa13def7.jpg","attentionCounts":0,"teacherAddress":"辽宁沈阳/浑南新区白塔河路理想新城","uId":"f99bda976cde43d18ebf497fb58c4320","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","userLeval":"小白","teacherJobUbdergo":"专业从事互联网应用开发","attentioned":0},"deleteFlag":0,"goldCount":5,"harvest":"获得收货","indexImg":"pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg","rId":"89325d122cb04754999dcb17a7392a88","scId":"1","secondTitle":"钢铁是怎样炼成的","showCounts":26,"state":0,"suitPerson":"适合人群","title":"第一集","totalMinute":120,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-26 13:40:43","videoContent":"视频内容","videoDetail":"视频详细","videoPath":"94f797b475bc4bf789fd8f5eadc651e2"},"teacherOther":[{"createTime":"2018-03-24 09:43:20","deleteFlag":0,"goldCount":5,"harvest":"","id":"1d410cb2cb754d6a94778055b4c8561e","indexImg":"","rId":"1d410cb2cb754d6a94778055b4c8561e","scId":"1","secondTitle":"aaa","showCounts":3,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-26 10:51:37","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"},{"createTime":"2018-03-24 09:22:40","deleteFlag":0,"goldCount":5,"harvest":"","id":"203404b7f25b4d40930a220ba7cf6cd1","indexImg":"","rId":"203404b7f25b4d40930a220ba7cf6cd1","scId":"1","secondTitle":"aaa","showCounts":0,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-24 09:22:40","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"},{"createTime":"2018-03-24 09:14:21","deleteFlag":0,"goldCount":5,"harvest":"","id":"a03ca88ffe644ea09b79dde14117e5e6","indexImg":"","rId":"a03ca88ffe644ea09b79dde14117e5e6","scId":"1","secondTitle":"aaa","showCounts":1,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-24 21:12:13","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"},{"createTime":"2018-03-23 17:28:00","deleteFlag":0,"goldCount":5,"harvest":"","id":"ded775455a084935af91b3d109aaa353","indexImg":"","rId":"ded775455a084935af91b3d109aaa353","scId":"1","secondTitle":"aaa","showCounts":0,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-23 17:28:01","videoContent":"","videoDetail":"","videoPath":"b282d292161644ed904c1b769576bd06"}],"hot":[{"createTime":"2018-03-09 11:07:12","deleteFlag":0,"goldCount":5,"harvest":"获得收货","id":"89325d122cb04754999dcb17a7392a88","indexImg":"pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg","rId":"89325d122cb04754999dcb17a7392a88","scId":"1","secondTitle":"钢铁是怎样炼成的","showCounts":26,"state":0,"suitPerson":"适合人群","title":"第一集","totalMinute":120,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-26 13:40:43","videoContent":"视频内容","videoDetail":"视频详细","videoPath":"94f797b475bc4bf789fd8f5eadc651e2"},{"createTime":"2018-03-24 12:30:49","deleteFlag":0,"goldCount":1,"harvest":"学习学习学习学习","id":"96c3ca3f5fa447179a481a04d0dbe349","indexImg":"","rId":"96c3ca3f5fa447179a481a04d0dbe349","scId":"1","secondTitle":"学习学习学习学习","showCounts":22,"state":2,"suitPerson":"学习学习学习学习","title":"学习学习学习学习","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-26 14:16:33","videoContent":"学习学习学习学习","videoDetail":"学习学习学习学习","videoPath":"15e75b9ad84f4fafb5c3e2e50bc70db3"},{"createTime":"2018-03-23 21:30:58","deleteFlag":0,"goldCount":5,"harvest":"故事","id":"170508ef83c3411cbac117ab8379f21a","indexImg":"","rId":"170508ef83c3411cbac117ab8379f21a","scId":"1","secondTitle":"学习","showCounts":8,"state":0,"suitPerson":"哦哦哦","title":"测试","totalMinute":5728000,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-26 14:39:15","videoContent":"啦啦啦","videoDetail":"哈哈哈","videoPath":"ae5feee191d743468c76cc4e875943ca"},{"createTime":"2018-03-24 09:43:20","deleteFlag":0,"goldCount":5,"harvest":"","id":"1d410cb2cb754d6a94778055b4c8561e","indexImg":"","rId":"1d410cb2cb754d6a94778055b4c8561e","scId":"1","secondTitle":"aaa","showCounts":3,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-26 10:51:37","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"},{"createTime":"2018-03-24 10:08:22","deleteFlag":0,"goldCount":1,"harvest":"啊啊啊","id":"6a7ed51aff1d44e5b8f67018be4a3ad4","indexImg":"","rId":"6a7ed51aff1d44e5b8f67018be4a3ad4","scId":"1","secondTitle":"测试测试测试","showCounts":2,"state":2,"suitPerson":"啊啊啊","title":"学习学习学习学习","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-24 23:17:40","videoContent":"啊啊啊","videoDetail":"啊啊啊","videoPath":"5a465fe69d4845a0be53555cddc47571"},{"createTime":"2018-03-26 11:17:37","deleteFlag":0,"goldCount":1,"harvest":"露面","id":"0830873b6f3542f7981b921ec98e339a","indexImg":"","rId":"0830873b6f3542f7981b921ec98e339a","scId":"1","secondTitle":"上班","showCounts":1,"state":0,"suitPerson":"协议","title":"继续","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-26 13:40:36","videoContent":"学习","videoDetail":"学习","videoPath":"09ad3c5fdb064972a9620708de192814"},{"createTime":"2018-03-24 09:49:26","deleteFlag":0,"goldCount":6,"harvest":"尽量","id":"1f8560b1121643cba1266424a8eb7459","indexImg":"","rId":"1f8560b1121643cba1266424a8eb7459","scId":"1","secondTitle":"啦啦啦","showCounts":1,"state":0,"suitPerson":"学生","title":"啊啊啊","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-24 21:11:49","videoContent":"论文","videoDetail":"可能","videoPath":"21fb1fae8b124c0399398d46fa534a53"},{"createTime":"2018-03-26 11:24:16","deleteFlag":0,"goldCount":2,"harvest":"学习","id":"2e7b20c757494dba8cc264b0894ea07f","indexImg":"","rId":"2e7b20c757494dba8cc264b0894ea07f","scId":"1","secondTitle":"道车","showCounts":1,"state":0,"suitPerson":"学习","title":"普易","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-26 11:25:22","videoContent":"学习","videoDetail":"学习","videoPath":"a0f2094aaf6d457f83806d49d613943d"},{"createTime":"2018-03-24 10:01:15","deleteFlag":0,"goldCount":6,"harvest":"测试测试","id":"95245d560427463c9dad8acda0c6f6d7","indexImg":"","rId":"95245d560427463c9dad8acda0c6f6d7","scId":"1","secondTitle":"测试测试","showCounts":1,"state":0,"suitPerson":"测试测试","title":"学习学习","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-24 10:01:21","videoContent":"测试测试","videoDetail":"测试测试","videoPath":"467a7a24dc484fc9925c40a7469ae9d2"},{"createTime":"2018-03-24 09:14:21","deleteFlag":0,"goldCount":5,"harvest":"","id":"a03ca88ffe644ea09b79dde14117e5e6","indexImg":"","rId":"a03ca88ffe644ea09b79dde14117e5e6","scId":"1","secondTitle":"aaa","showCounts":1,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-24 21:12:13","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"}]}]
     * msg : 播放成功
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
         * resource : {"buyState":1,"createTime":"2018-03-09 11:07:12","creator":{"teacherGoodAtArea":"java","teacherAge":23,"headImg":"","teacherName":"王老师专业辅导","teacherSelfAssessment":"兴趣大于学习","sex":0,"teacherNowCompany":"沈阳普易网科技","jobArea":"","teacherOldHonor":"省高级教师职称","wxId":"","trainAttention":"运营管理,市场营销,销售管理,客户维护","userName":"","teacherHeadImg":"pictures/teacher/2/20180309093102-0e4ef487f3f2e3531622c5.jpg,pictures/teacher/2/20180309093102-944f79b1f85522b4457527.jpg,pictures/teacher/2/20180309093102-764e4896d856b0fa13def7.jpg","attentionCounts":0,"teacherAddress":"辽宁沈阳/浑南新区白塔河路理想新城","uId":"f99bda976cde43d18ebf497fb58c4320","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","userLeval":"小白","teacherJobUbdergo":"专业从事互联网应用开发","attentioned":0},"deleteFlag":0,"goldCount":5,"harvest":"获得收货","indexImg":"pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg","rId":"89325d122cb04754999dcb17a7392a88","scId":"1","secondTitle":"钢铁是怎样炼成的","showCounts":26,"state":0,"suitPerson":"适合人群","title":"第一集","totalMinute":120,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-26 13:40:43","videoContent":"视频内容","videoDetail":"视频详细","videoPath":"94f797b475bc4bf789fd8f5eadc651e2"}
         * teacherOther : [{"createTime":"2018-03-24 09:43:20","deleteFlag":0,"goldCount":5,"harvest":"","id":"1d410cb2cb754d6a94778055b4c8561e","indexImg":"","rId":"1d410cb2cb754d6a94778055b4c8561e","scId":"1","secondTitle":"aaa","showCounts":3,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-26 10:51:37","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"},{"createTime":"2018-03-24 09:22:40","deleteFlag":0,"goldCount":5,"harvest":"","id":"203404b7f25b4d40930a220ba7cf6cd1","indexImg":"","rId":"203404b7f25b4d40930a220ba7cf6cd1","scId":"1","secondTitle":"aaa","showCounts":0,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-24 09:22:40","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"},{"createTime":"2018-03-24 09:14:21","deleteFlag":0,"goldCount":5,"harvest":"","id":"a03ca88ffe644ea09b79dde14117e5e6","indexImg":"","rId":"a03ca88ffe644ea09b79dde14117e5e6","scId":"1","secondTitle":"aaa","showCounts":1,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-24 21:12:13","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"},{"createTime":"2018-03-23 17:28:00","deleteFlag":0,"goldCount":5,"harvest":"","id":"ded775455a084935af91b3d109aaa353","indexImg":"","rId":"ded775455a084935af91b3d109aaa353","scId":"1","secondTitle":"aaa","showCounts":0,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-23 17:28:01","videoContent":"","videoDetail":"","videoPath":"b282d292161644ed904c1b769576bd06"}]
         * hot : [{"createTime":"2018-03-09 11:07:12","deleteFlag":0,"goldCount":5,"harvest":"获得收货","id":"89325d122cb04754999dcb17a7392a88","indexImg":"pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg","rId":"89325d122cb04754999dcb17a7392a88","scId":"1","secondTitle":"钢铁是怎样炼成的","showCounts":26,"state":0,"suitPerson":"适合人群","title":"第一集","totalMinute":120,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-26 13:40:43","videoContent":"视频内容","videoDetail":"视频详细","videoPath":"94f797b475bc4bf789fd8f5eadc651e2"},{"createTime":"2018-03-24 12:30:49","deleteFlag":0,"goldCount":1,"harvest":"学习学习学习学习","id":"96c3ca3f5fa447179a481a04d0dbe349","indexImg":"","rId":"96c3ca3f5fa447179a481a04d0dbe349","scId":"1","secondTitle":"学习学习学习学习","showCounts":22,"state":2,"suitPerson":"学习学习学习学习","title":"学习学习学习学习","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-26 14:16:33","videoContent":"学习学习学习学习","videoDetail":"学习学习学习学习","videoPath":"15e75b9ad84f4fafb5c3e2e50bc70db3"},{"createTime":"2018-03-23 21:30:58","deleteFlag":0,"goldCount":5,"harvest":"故事","id":"170508ef83c3411cbac117ab8379f21a","indexImg":"","rId":"170508ef83c3411cbac117ab8379f21a","scId":"1","secondTitle":"学习","showCounts":8,"state":0,"suitPerson":"哦哦哦","title":"测试","totalMinute":5728000,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-26 14:39:15","videoContent":"啦啦啦","videoDetail":"哈哈哈","videoPath":"ae5feee191d743468c76cc4e875943ca"},{"createTime":"2018-03-24 09:43:20","deleteFlag":0,"goldCount":5,"harvest":"","id":"1d410cb2cb754d6a94778055b4c8561e","indexImg":"","rId":"1d410cb2cb754d6a94778055b4c8561e","scId":"1","secondTitle":"aaa","showCounts":3,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-26 10:51:37","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"},{"createTime":"2018-03-24 10:08:22","deleteFlag":0,"goldCount":1,"harvest":"啊啊啊","id":"6a7ed51aff1d44e5b8f67018be4a3ad4","indexImg":"","rId":"6a7ed51aff1d44e5b8f67018be4a3ad4","scId":"1","secondTitle":"测试测试测试","showCounts":2,"state":2,"suitPerson":"啊啊啊","title":"学习学习学习学习","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-24 23:17:40","videoContent":"啊啊啊","videoDetail":"啊啊啊","videoPath":"5a465fe69d4845a0be53555cddc47571"},{"createTime":"2018-03-26 11:17:37","deleteFlag":0,"goldCount":1,"harvest":"露面","id":"0830873b6f3542f7981b921ec98e339a","indexImg":"","rId":"0830873b6f3542f7981b921ec98e339a","scId":"1","secondTitle":"上班","showCounts":1,"state":0,"suitPerson":"协议","title":"继续","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-26 13:40:36","videoContent":"学习","videoDetail":"学习","videoPath":"09ad3c5fdb064972a9620708de192814"},{"createTime":"2018-03-24 09:49:26","deleteFlag":0,"goldCount":6,"harvest":"尽量","id":"1f8560b1121643cba1266424a8eb7459","indexImg":"","rId":"1f8560b1121643cba1266424a8eb7459","scId":"1","secondTitle":"啦啦啦","showCounts":1,"state":0,"suitPerson":"学生","title":"啊啊啊","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-24 21:11:49","videoContent":"论文","videoDetail":"可能","videoPath":"21fb1fae8b124c0399398d46fa534a53"},{"createTime":"2018-03-26 11:24:16","deleteFlag":0,"goldCount":2,"harvest":"学习","id":"2e7b20c757494dba8cc264b0894ea07f","indexImg":"","rId":"2e7b20c757494dba8cc264b0894ea07f","scId":"1","secondTitle":"道车","showCounts":1,"state":0,"suitPerson":"学习","title":"普易","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-26 11:25:22","videoContent":"学习","videoDetail":"学习","videoPath":"a0f2094aaf6d457f83806d49d613943d"},{"createTime":"2018-03-24 10:01:15","deleteFlag":0,"goldCount":6,"harvest":"测试测试","id":"95245d560427463c9dad8acda0c6f6d7","indexImg":"","rId":"95245d560427463c9dad8acda0c6f6d7","scId":"1","secondTitle":"测试测试","showCounts":1,"state":0,"suitPerson":"测试测试","title":"学习学习","totalMinute":7,"uId":"398ca3ac7d9e4741b8f94784325bd0fc","updateTime":"2018-03-24 10:01:21","videoContent":"测试测试","videoDetail":"测试测试","videoPath":"467a7a24dc484fc9925c40a7469ae9d2"},{"createTime":"2018-03-24 09:14:21","deleteFlag":0,"goldCount":5,"harvest":"","id":"a03ca88ffe644ea09b79dde14117e5e6","indexImg":"","rId":"a03ca88ffe644ea09b79dde14117e5e6","scId":"1","secondTitle":"aaa","showCounts":1,"state":0,"suitPerson":"","title":"测试","totalMinute":7,"uId":"f99bda976cde43d18ebf497fb58c4320","updateTime":"2018-03-24 21:12:13","videoContent":"","videoDetail":"","videoPath":"27d20ae29e254a5c8dc894e58512f99e"}]
         */

        private ResourceBean resource;
        private List<TeacherOtherBean> teacherOther;
        private List<HotBean> hot;

        public ResourceBean getResource() {
            return resource;
        }

        public void setResource(ResourceBean resource) {
            this.resource = resource;
        }

        public List<TeacherOtherBean> getTeacherOther() {
            return teacherOther;
        }

        public void setTeacherOther(List<TeacherOtherBean> teacherOther) {
            this.teacherOther = teacherOther;
        }

        public List<HotBean> getHot() {
            return hot;
        }

        public void setHot(List<HotBean> hot) {
            this.hot = hot;
        }

        public static class ResourceBean implements Parcelable {
            /**
             * buyState : 1
             * createTime : 2018-03-09 11:07:12
             * creator : {"teacherGoodAtArea":"java","teacherAge":23,"headImg":"","teacherName":"王老师专业辅导","teacherSelfAssessment":"兴趣大于学习","sex":0,"teacherNowCompany":"沈阳普易网科技","jobArea":"","teacherOldHonor":"省高级教师职称","wxId":"","trainAttention":"运营管理,市场营销,销售管理,客户维护","userName":"","teacherHeadImg":"pictures/teacher/2/20180309093102-0e4ef487f3f2e3531622c5.jpg,pictures/teacher/2/20180309093102-944f79b1f85522b4457527.jpg,pictures/teacher/2/20180309093102-764e4896d856b0fa13def7.jpg","attentionCounts":0,"teacherAddress":"辽宁沈阳/浑南新区白塔河路理想新城","uId":"f99bda976cde43d18ebf497fb58c4320","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","userLeval":"小白","teacherJobUbdergo":"专业从事互联网应用开发","attentioned":0}
             * deleteFlag : 0
             * goldCount : 5
             * harvest : 获得收货
             * indexImg : pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg
             * rId : 89325d122cb04754999dcb17a7392a88
             * scId : 1
             * secondTitle : 钢铁是怎样炼成的
             * showCounts : 26
             * state : 0
             * suitPerson : 适合人群
             * title : 第一集
             * totalMinute : 120
             * uId : f99bda976cde43d18ebf497fb58c4320
             * updateTime : 2018-03-26 13:40:43
             * videoContent : 视频内容
             * videoDetail : 视频详细
             * videoPath : 94f797b475bc4bf789fd8f5eadc651e2
             */

            private int buyState;
            private String createTime;
            private CreatorBean creator;
            private int deleteFlag;
            private int goldCount;
            private String harvest;
            private String indexImg;
            private String rId;
            private String scId;
            private String secondTitle;
            private int showCounts;
            private int state;
            private String suitPerson;
            private String title;
            private int totalMinute;
            private String uId;
            private String updateTime;
            private String videoContent;
            private String videoDetail;
            private String videoPath;

            public int getBuyState() {
                return buyState;
            }

            public void setBuyState(int buyState) {
                this.buyState = buyState;
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

            public int getDeleteFlag() {
                return deleteFlag;
            }

            public void setDeleteFlag(int deleteFlag) {
                this.deleteFlag = deleteFlag;
            }

            public int getGoldCount() {
                return goldCount;
            }

            public void setGoldCount(int goldCount) {
                this.goldCount = goldCount;
            }

            public String getHarvest() {
                return harvest;
            }

            public void setHarvest(String harvest) {
                this.harvest = harvest;
            }

            public String getIndexImg() {
                return indexImg;
            }

            public void setIndexImg(String indexImg) {
                this.indexImg = indexImg;
            }

            public String getRId() {
                return rId;
            }

            public void setRId(String rId) {
                this.rId = rId;
            }

            public String getScId() {
                return scId;
            }

            public void setScId(String scId) {
                this.scId = scId;
            }

            public String getSecondTitle() {
                return secondTitle;
            }

            public void setSecondTitle(String secondTitle) {
                this.secondTitle = secondTitle;
            }

            public int getShowCounts() {
                return showCounts;
            }

            public void setShowCounts(int showCounts) {
                this.showCounts = showCounts;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getSuitPerson() {
                return suitPerson;
            }

            public void setSuitPerson(String suitPerson) {
                this.suitPerson = suitPerson;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getTotalMinute() {
                return totalMinute;
            }

            public void setTotalMinute(int totalMinute) {
                this.totalMinute = totalMinute;
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

            public String getVideoContent() {
                return videoContent;
            }

            public void setVideoContent(String videoContent) {
                this.videoContent = videoContent;
            }

            public String getVideoDetail() {
                return videoDetail;
            }

            public void setVideoDetail(String videoDetail) {
                this.videoDetail = videoDetail;
            }

            public String getVideoPath() {
                return videoPath;
            }

            public void setVideoPath(String videoPath) {
                this.videoPath = videoPath;
            }

            public static class CreatorBean implements Parcelable {
                /**
                 * teacherGoodAtArea : java
                 * teacherAge : 23
                 * headImg :
                 * teacherName : 王老师专业辅导
                 * teacherSelfAssessment : 兴趣大于学习
                 * sex : 0
                 * teacherNowCompany : 沈阳普易网科技
                 * jobArea :
                 * teacherOldHonor : 省高级教师职称
                 * wxId :
                 * trainAttention : 运营管理,市场营销,销售管理,客户维护
                 * userName :
                 * teacherHeadImg : pictures/teacher/2/20180309093102-0e4ef487f3f2e3531622c5.jpg,pictures/teacher/2/20180309093102-944f79b1f85522b4457527.jpg,pictures/teacher/2/20180309093102-764e4896d856b0fa13def7.jpg
                 * attentionCounts : 0
                 * teacherAddress : 辽宁沈阳/浑南新区白塔河路理想新城
                 * uId : f99bda976cde43d18ebf497fb58c4320
                 * createTime : 2018-03-08 13:38:58
                 * userMobile : 15140088201
                 * userLeval : 小白
                 * teacherJobUbdergo : 专业从事互联网应用开发
                 * attentioned : 0
                 */

                private String teacherGoodAtArea;
                private int teacherAge;
                private String headImg;
                private String teacherName;
                private String teacherSelfAssessment;
                private int sex;
                private String teacherNowCompany;
                private String jobArea;
                private String teacherOldHonor;
                private String wxId;
                private String trainAttention;
                private String userName;
                private String teacherHeadImg;
                private int attentionCounts;
                private String teacherAddress;
                private String uId;
                private String createTime;
                private String userMobile;
                private String userLeval;
                private String teacherJobUbdergo;
                private int attentioned;

                public String getTeacherGoodAtArea() {
                    return teacherGoodAtArea;
                }

                public void setTeacherGoodAtArea(String teacherGoodAtArea) {
                    this.teacherGoodAtArea = teacherGoodAtArea;
                }

                public int getTeacherAge() {
                    return teacherAge;
                }

                public void setTeacherAge(int teacherAge) {
                    this.teacherAge = teacherAge;
                }

                public String getHeadImg() {
                    return headImg;
                }

                public void setHeadImg(String headImg) {
                    this.headImg = headImg;
                }

                public String getTeacherName() {
                    return teacherName;
                }

                public void setTeacherName(String teacherName) {
                    this.teacherName = teacherName;
                }

                public String getTeacherSelfAssessment() {
                    return teacherSelfAssessment;
                }

                public void setTeacherSelfAssessment(String teacherSelfAssessment) {
                    this.teacherSelfAssessment = teacherSelfAssessment;
                }

                public int getSex() {
                    return sex;
                }

                public void setSex(int sex) {
                    this.sex = sex;
                }

                public String getTeacherNowCompany() {
                    return teacherNowCompany;
                }

                public void setTeacherNowCompany(String teacherNowCompany) {
                    this.teacherNowCompany = teacherNowCompany;
                }

                public String getJobArea() {
                    return jobArea;
                }

                public void setJobArea(String jobArea) {
                    this.jobArea = jobArea;
                }

                public String getTeacherOldHonor() {
                    return teacherOldHonor;
                }

                public void setTeacherOldHonor(String teacherOldHonor) {
                    this.teacherOldHonor = teacherOldHonor;
                }

                public String getWxId() {
                    return wxId;
                }

                public void setWxId(String wxId) {
                    this.wxId = wxId;
                }

                public String getTrainAttention() {
                    return trainAttention;
                }

                public void setTrainAttention(String trainAttention) {
                    this.trainAttention = trainAttention;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getTeacherHeadImg() {
                    return teacherHeadImg;
                }

                public void setTeacherHeadImg(String teacherHeadImg) {
                    this.teacherHeadImg = teacherHeadImg;
                }

                public int getAttentionCounts() {
                    return attentionCounts;
                }

                public void setAttentionCounts(int attentionCounts) {
                    this.attentionCounts = attentionCounts;
                }

                public String getTeacherAddress() {
                    return teacherAddress;
                }

                public void setTeacherAddress(String teacherAddress) {
                    this.teacherAddress = teacherAddress;
                }

                public String getUId() {
                    return uId;
                }

                public void setUId(String uId) {
                    this.uId = uId;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getUserMobile() {
                    return userMobile;
                }

                public void setUserMobile(String userMobile) {
                    this.userMobile = userMobile;
                }

                public String getUserLeval() {
                    return userLeval;
                }

                public void setUserLeval(String userLeval) {
                    this.userLeval = userLeval;
                }

                public String getTeacherJobUbdergo() {
                    return teacherJobUbdergo;
                }

                public void setTeacherJobUbdergo(String teacherJobUbdergo) {
                    this.teacherJobUbdergo = teacherJobUbdergo;
                }

                public int getAttentioned() {
                    return attentioned;
                }

                public void setAttentioned(int attentioned) {
                    this.attentioned = attentioned;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.teacherGoodAtArea);
                    dest.writeInt(this.teacherAge);
                    dest.writeString(this.headImg);
                    dest.writeString(this.teacherName);
                    dest.writeString(this.teacherSelfAssessment);
                    dest.writeInt(this.sex);
                    dest.writeString(this.teacherNowCompany);
                    dest.writeString(this.jobArea);
                    dest.writeString(this.teacherOldHonor);
                    dest.writeString(this.wxId);
                    dest.writeString(this.trainAttention);
                    dest.writeString(this.userName);
                    dest.writeString(this.teacherHeadImg);
                    dest.writeInt(this.attentionCounts);
                    dest.writeString(this.teacherAddress);
                    dest.writeString(this.uId);
                    dest.writeString(this.createTime);
                    dest.writeString(this.userMobile);
                    dest.writeString(this.userLeval);
                    dest.writeString(this.teacherJobUbdergo);
                    dest.writeInt(this.attentioned);
                }

                public CreatorBean() {
                }

                protected CreatorBean(Parcel in) {
                    this.teacherGoodAtArea = in.readString();
                    this.teacherAge = in.readInt();
                    this.headImg = in.readString();
                    this.teacherName = in.readString();
                    this.teacherSelfAssessment = in.readString();
                    this.sex = in.readInt();
                    this.teacherNowCompany = in.readString();
                    this.jobArea = in.readString();
                    this.teacherOldHonor = in.readString();
                    this.wxId = in.readString();
                    this.trainAttention = in.readString();
                    this.userName = in.readString();
                    this.teacherHeadImg = in.readString();
                    this.attentionCounts = in.readInt();
                    this.teacherAddress = in.readString();
                    this.uId = in.readString();
                    this.createTime = in.readString();
                    this.userMobile = in.readString();
                    this.userLeval = in.readString();
                    this.teacherJobUbdergo = in.readString();
                    this.attentioned = in.readInt();
                }

                public static final Creator<CreatorBean> CREATOR = new Creator<CreatorBean>() {
                    @Override
                    public CreatorBean createFromParcel(Parcel source) {
                        return new CreatorBean(source);
                    }

                    @Override
                    public CreatorBean[] newArray(int size) {
                        return new CreatorBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.buyState);
                dest.writeString(this.createTime);
                dest.writeParcelable(this.creator, flags);
                dest.writeInt(this.deleteFlag);
                dest.writeInt(this.goldCount);
                dest.writeString(this.harvest);
                dest.writeString(this.indexImg);
                dest.writeString(this.rId);
                dest.writeString(this.scId);
                dest.writeString(this.secondTitle);
                dest.writeInt(this.showCounts);
                dest.writeInt(this.state);
                dest.writeString(this.suitPerson);
                dest.writeString(this.title);
                dest.writeInt(this.totalMinute);
                dest.writeString(this.uId);
                dest.writeString(this.updateTime);
                dest.writeString(this.videoContent);
                dest.writeString(this.videoDetail);
                dest.writeString(this.videoPath);
            }

            public ResourceBean() {
            }

            protected ResourceBean(Parcel in) {
                this.buyState = in.readInt();
                this.createTime = in.readString();
                this.creator = in.readParcelable(CreatorBean.class.getClassLoader());
                this.deleteFlag = in.readInt();
                this.goldCount = in.readInt();
                this.harvest = in.readString();
                this.indexImg = in.readString();
                this.rId = in.readString();
                this.scId = in.readString();
                this.secondTitle = in.readString();
                this.showCounts = in.readInt();
                this.state = in.readInt();
                this.suitPerson = in.readString();
                this.title = in.readString();
                this.totalMinute = in.readInt();
                this.uId = in.readString();
                this.updateTime = in.readString();
                this.videoContent = in.readString();
                this.videoDetail = in.readString();
                this.videoPath = in.readString();
            }

            public static final Creator<ResourceBean> CREATOR = new Creator<ResourceBean>() {
                @Override
                public ResourceBean createFromParcel(Parcel source) {
                    return new ResourceBean(source);
                }

                @Override
                public ResourceBean[] newArray(int size) {
                    return new ResourceBean[size];
                }
            };
        }

        public static class TeacherOtherBean implements Parcelable {

            /**
             * createTime : 2018-03-21 14:52:57
             * deleteFlag : 0
             * goldCount : 5
             * harvest :
             * id : 1d410cb2cb754d6a94778055b4c8561e
             * indexImg :
             * rId : 1d410cb2cb754d6a94778055b4c8561e
             * scId : 1
             * secondTitle : aaa
             * showCounts : 3
             * state : 0
             * suitPerson :
             * title : 测试
             * totalMinute : 7
             * uId : f99bda976cde43d18ebf497fb58c4320
             * updateTime : 2018-03-26 10:51:37
             * videoContent :
             * videoDetail :
             * videoPath : 27d20ae29e254a5c8dc894e58512f99e
             */

            private String createTime;
            private int deleteFlag;
            private int goldCount;
            private String harvest;
            private String id;
            private String indexImg;
            private String rId;
            private String scId;
            private String secondTitle;
            private int showCounts;
            private int state;
            private String suitPerson;
            private String title;
            private int totalMinute;
            private String uId;
            private String updateTime;
            private String videoContent;
            private String videoDetail;
            private String videoPath;

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

            public int getGoldCount() {
                return goldCount;
            }

            public void setGoldCount(int goldCount) {
                this.goldCount = goldCount;
            }

            public String getHarvest() {
                return harvest;
            }

            public void setHarvest(String harvest) {
                this.harvest = harvest;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIndexImg() {
                return indexImg;
            }

            public void setIndexImg(String indexImg) {
                this.indexImg = indexImg;
            }

            public String getRId() {
                return rId;
            }

            public void setRId(String rId) {
                this.rId = rId;
            }

            public String getScId() {
                return scId;
            }

            public void setScId(String scId) {
                this.scId = scId;
            }

            public String getSecondTitle() {
                return secondTitle;
            }

            public void setSecondTitle(String secondTitle) {
                this.secondTitle = secondTitle;
            }

            public int getShowCounts() {
                return showCounts;
            }

            public void setShowCounts(int showCounts) {
                this.showCounts = showCounts;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getSuitPerson() {
                return suitPerson;
            }

            public void setSuitPerson(String suitPerson) {
                this.suitPerson = suitPerson;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getTotalMinute() {
                return totalMinute;
            }

            public void setTotalMinute(int totalMinute) {
                this.totalMinute = totalMinute;
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

            public String getVideoContent() {
                return videoContent;
            }

            public void setVideoContent(String videoContent) {
                this.videoContent = videoContent;
            }

            public String getVideoDetail() {
                return videoDetail;
            }

            public void setVideoDetail(String videoDetail) {
                this.videoDetail = videoDetail;
            }

            public String getVideoPath() {
                return videoPath;
            }

            public void setVideoPath(String videoPath) {
                this.videoPath = videoPath;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.createTime);
                dest.writeInt(this.deleteFlag);
                dest.writeInt(this.goldCount);
                dest.writeString(this.harvest);
                dest.writeString(this.id);
                dest.writeString(this.indexImg);
                dest.writeString(this.rId);
                dest.writeString(this.scId);
                dest.writeString(this.secondTitle);
                dest.writeInt(this.showCounts);
                dest.writeInt(this.state);
                dest.writeString(this.suitPerson);
                dest.writeString(this.title);
                dest.writeInt(this.totalMinute);
                dest.writeString(this.uId);
                dest.writeString(this.updateTime);
                dest.writeString(this.videoContent);
                dest.writeString(this.videoDetail);
                dest.writeString(this.videoPath);
            }

            public TeacherOtherBean() {
            }

            protected TeacherOtherBean(Parcel in) {
                this.createTime = in.readString();
                this.deleteFlag = in.readInt();
                this.goldCount = in.readInt();
                this.harvest = in.readString();
                this.id = in.readString();
                this.indexImg = in.readString();
                this.rId = in.readString();
                this.scId = in.readString();
                this.secondTitle = in.readString();
                this.showCounts = in.readInt();
                this.state = in.readInt();
                this.suitPerson = in.readString();
                this.title = in.readString();
                this.totalMinute = in.readInt();
                this.uId = in.readString();
                this.updateTime = in.readString();
                this.videoContent = in.readString();
                this.videoDetail = in.readString();
                this.videoPath = in.readString();
            }

            public static final Creator<TeacherOtherBean> CREATOR = new Creator<TeacherOtherBean>() {
                @Override
                public TeacherOtherBean createFromParcel(Parcel source) {
                    return new TeacherOtherBean(source);
                }

                @Override
                public TeacherOtherBean[] newArray(int size) {
                    return new TeacherOtherBean[size];
                }
            };
        }

        public static class HotBean implements Parcelable {

            /**
             * buyCount : 0
             * buyState : 1
             * createTime : 2018-03-09 11:07:12
             * creator : {"uId":"f99bda976cde43d18ebf497fb58c4320","headImg":"","createTime":"2018-03-08 13:38:58","userMobile":"15140088201","sex":0,"userLeval":"小白","jobArea":"","wxId":"","trainAttention":"运营管理,市场营销,销售管理,客户维护","userName":"","attentionCounts":-2}
             * deleteFlag : 0
             * goldCount : 5
             * harvest : 获得收货
             * indexImg : pictures/indexVideo/2/20180309110712-bb4d018b6306de81cd5cb7.jpg
             * rId : 89325d122cb04754999dcb17a7392a88
             * scId : 1
             * secondTitle : 钢铁是怎样炼成的
             * showCounts : 33
             * state : 0
             * suitPerson : 适合人群
             * title : 第一集
             * totalMinute : 120
             * uId : f99bda976cde43d18ebf497fb58c4320
             * updateTime : 2018-03-29 09:23:35
             * videoContent : 视频内容
             * videoDetail : 视频详细
             * videoPath : 94f797b475bc4bf789fd8f5eadc651e2
             */

            private int buyCount;
            private int buyState;
            private String createTime;
            private CreatorBean creator;
            private int deleteFlag;
            private int goldCount;
            private String harvest;
            private String indexImg;
            private String rId;
            private String scId;
            private String secondTitle;
            private int showCounts;
            private int state;
            private String suitPerson;
            private String title;
            private int totalMinute;
            private String uId;
            private String updateTime;
            private String videoContent;
            private String videoDetail;
            private String videoPath;

            public int getBuyCount() {
                return buyCount;
            }

            public void setBuyCount(int buyCount) {
                this.buyCount = buyCount;
            }

            public int getBuyState() {
                return buyState;
            }

            public void setBuyState(int buyState) {
                this.buyState = buyState;
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

            public int getDeleteFlag() {
                return deleteFlag;
            }

            public void setDeleteFlag(int deleteFlag) {
                this.deleteFlag = deleteFlag;
            }

            public int getGoldCount() {
                return goldCount;
            }

            public void setGoldCount(int goldCount) {
                this.goldCount = goldCount;
            }

            public String getHarvest() {
                return harvest;
            }

            public void setHarvest(String harvest) {
                this.harvest = harvest;
            }

            public String getIndexImg() {
                return indexImg;
            }

            public void setIndexImg(String indexImg) {
                this.indexImg = indexImg;
            }

            public String getRId() {
                return rId;
            }

            public void setRId(String rId) {
                this.rId = rId;
            }

            public String getScId() {
                return scId;
            }

            public void setScId(String scId) {
                this.scId = scId;
            }

            public String getSecondTitle() {
                return secondTitle;
            }

            public void setSecondTitle(String secondTitle) {
                this.secondTitle = secondTitle;
            }

            public int getShowCounts() {
                return showCounts;
            }

            public void setShowCounts(int showCounts) {
                this.showCounts = showCounts;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getSuitPerson() {
                return suitPerson;
            }

            public void setSuitPerson(String suitPerson) {
                this.suitPerson = suitPerson;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getTotalMinute() {
                return totalMinute;
            }

            public void setTotalMinute(int totalMinute) {
                this.totalMinute = totalMinute;
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

            public String getVideoContent() {
                return videoContent;
            }

            public void setVideoContent(String videoContent) {
                this.videoContent = videoContent;
            }

            public String getVideoDetail() {
                return videoDetail;
            }

            public void setVideoDetail(String videoDetail) {
                this.videoDetail = videoDetail;
            }

            public String getVideoPath() {
                return videoPath;
            }

            public void setVideoPath(String videoPath) {
                this.videoPath = videoPath;
            }

            public static class CreatorBean implements Parcelable {

                /**
                 * uId : f99bda976cde43d18ebf497fb58c4320
                 * headImg :
                 * createTime : 2018-03-08 13:38:58
                 * userMobile : 15140088201
                 * sex : 0
                 * userLeval : 小白
                 * jobArea :
                 * wxId :
                 * trainAttention : 运营管理,市场营销,销售管理,客户维护
                 * userName :
                 * attentionCounts : -2
                 */

                private String uId;
                private String headImg;
                private String createTime;
                private String userMobile;
                private int sex;
                private String userLeval;
                private String jobArea;
                private String wxId;
                private String trainAttention;
                private String userName;
                private int attentionCounts;

                public String getUId() {
                    return uId;
                }

                public void setUId(String uId) {
                    this.uId = uId;
                }

                public String getHeadImg() {
                    return headImg;
                }

                public void setHeadImg(String headImg) {
                    this.headImg = headImg;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getUserMobile() {
                    return userMobile;
                }

                public void setUserMobile(String userMobile) {
                    this.userMobile = userMobile;
                }

                public int getSex() {
                    return sex;
                }

                public void setSex(int sex) {
                    this.sex = sex;
                }

                public String getUserLeval() {
                    return userLeval;
                }

                public void setUserLeval(String userLeval) {
                    this.userLeval = userLeval;
                }

                public String getJobArea() {
                    return jobArea;
                }

                public void setJobArea(String jobArea) {
                    this.jobArea = jobArea;
                }

                public String getWxId() {
                    return wxId;
                }

                public void setWxId(String wxId) {
                    this.wxId = wxId;
                }

                public String getTrainAttention() {
                    return trainAttention;
                }

                public void setTrainAttention(String trainAttention) {
                    this.trainAttention = trainAttention;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public int getAttentionCounts() {
                    return attentionCounts;
                }

                public void setAttentionCounts(int attentionCounts) {
                    this.attentionCounts = attentionCounts;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.uId);
                    dest.writeString(this.headImg);
                    dest.writeString(this.createTime);
                    dest.writeString(this.userMobile);
                    dest.writeInt(this.sex);
                    dest.writeString(this.userLeval);
                    dest.writeString(this.jobArea);
                    dest.writeString(this.wxId);
                    dest.writeString(this.trainAttention);
                    dest.writeString(this.userName);
                    dest.writeInt(this.attentionCounts);
                }

                public CreatorBean() {
                }

                protected CreatorBean(Parcel in) {
                    this.uId = in.readString();
                    this.headImg = in.readString();
                    this.createTime = in.readString();
                    this.userMobile = in.readString();
                    this.sex = in.readInt();
                    this.userLeval = in.readString();
                    this.jobArea = in.readString();
                    this.wxId = in.readString();
                    this.trainAttention = in.readString();
                    this.userName = in.readString();
                    this.attentionCounts = in.readInt();
                }

                public static final Creator<CreatorBean> CREATOR = new Creator<CreatorBean>() {
                    @Override
                    public CreatorBean createFromParcel(Parcel source) {
                        return new CreatorBean(source);
                    }

                    @Override
                    public CreatorBean[] newArray(int size) {
                        return new CreatorBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.buyCount);
                dest.writeInt(this.buyState);
                dest.writeString(this.createTime);
                dest.writeParcelable(this.creator, flags);
                dest.writeInt(this.deleteFlag);
                dest.writeInt(this.goldCount);
                dest.writeString(this.harvest);
                dest.writeString(this.indexImg);
                dest.writeString(this.rId);
                dest.writeString(this.scId);
                dest.writeString(this.secondTitle);
                dest.writeInt(this.showCounts);
                dest.writeInt(this.state);
                dest.writeString(this.suitPerson);
                dest.writeString(this.title);
                dest.writeInt(this.totalMinute);
                dest.writeString(this.uId);
                dest.writeString(this.updateTime);
                dest.writeString(this.videoContent);
                dest.writeString(this.videoDetail);
                dest.writeString(this.videoPath);
            }

            public HotBean() {
            }

            protected HotBean(Parcel in) {
                this.buyCount = in.readInt();
                this.buyState = in.readInt();
                this.createTime = in.readString();
                this.creator = in.readParcelable(CreatorBean.class.getClassLoader());
                this.deleteFlag = in.readInt();
                this.goldCount = in.readInt();
                this.harvest = in.readString();
                this.indexImg = in.readString();
                this.rId = in.readString();
                this.scId = in.readString();
                this.secondTitle = in.readString();
                this.showCounts = in.readInt();
                this.state = in.readInt();
                this.suitPerson = in.readString();
                this.title = in.readString();
                this.totalMinute = in.readInt();
                this.uId = in.readString();
                this.updateTime = in.readString();
                this.videoContent = in.readString();
                this.videoDetail = in.readString();
                this.videoPath = in.readString();
            }

            public static final Creator<HotBean> CREATOR = new Creator<HotBean>() {
                @Override
                public HotBean createFromParcel(Parcel source) {
                    return new HotBean(source);
                }

                @Override
                public HotBean[] newArray(int size) {
                    return new HotBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.resource, flags);
            dest.writeList(this.teacherOther);
            dest.writeList(this.hot);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.resource = in.readParcelable(ResourceBean.class.getClassLoader());
            this.teacherOther = new ArrayList<TeacherOtherBean>();
            in.readList(this.teacherOther, TeacherOtherBean.class.getClassLoader());
            this.hot = new ArrayList<HotBean>();
            in.readList(this.hot, HotBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
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
        dest.writeList(this.data);
    }

    public PlayVideoBean() {
    }

    protected PlayVideoBean(Parcel in) {
        this.msg = in.readString();
        this.responseState = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PlayVideoBean> CREATOR = new Parcelable.Creator<PlayVideoBean>() {
        @Override
        public PlayVideoBean createFromParcel(Parcel source) {
            return new PlayVideoBean(source);
        }

        @Override
        public PlayVideoBean[] newArray(int size) {
            return new PlayVideoBean[size];
        }
    };
}
