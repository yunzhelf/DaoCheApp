package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetLevalDataBean {

    /**
     * data : [{"weekData":[{"createTime":"2018-02-25","showTime":10760},{"createTime":"2018-02-26","showTime":10760},{"createTime":"2018-02-27","showTime":10760},{"createTime":"2018-02-28","showTime":10760},{"createTime":"2018-03-01","showTime":14320},{"createTime":"2018-03-02","showTime":14320},{"createTime":"2018-03-03","showTime":14320}],"dayLevalData":[{"createTime":"2018-03-01","showTime":3560}],"monthData":[{"createTime":"2018-03-02","showTime":10760},{"createTime":"2018-03-03","showTime":10760},{"createTime":"2018-03-04","showTime":10760},{"createTime":"2018-03-05","showTime":10760},{"createTime":"2018-03-06","showTime":10760},{"createTime":"2018-03-07","showTime":10760},{"createTime":"2018-03-08","showTime":10760},{"createTime":"2018-03-09","showTime":10760},{"createTime":"2018-03-10","showTime":10760},{"createTime":"2018-03-11","showTime":10760},{"createTime":"2018-03-12","showTime":10760},{"createTime":"2018-03-13","showTime":10760},{"createTime":"2018-03-14","showTime":10760},{"createTime":"2018-03-15","showTime":10760},{"createTime":"2018-03-16","showTime":10760},{"createTime":"2018-03-17","showTime":10760},{"createTime":"2018-03-18","showTime":10760},{"createTime":"2018-03-19","showTime":10760},{"createTime":"2018-03-20","showTime":10760},{"createTime":"2018-03-21","showTime":10760},{"createTime":"2018-03-22","showTime":10760},{"createTime":"2018-03-23","showTime":10760},{"createTime":"2018-03-24","showTime":10760},{"createTime":"2018-03-25","showTime":10760},{"createTime":"2018-03-26","showTime":10760},{"createTime":"2018-03-27","showTime":10760},{"createTime":"2018-03-28","showTime":10760},{"createTime":"2018-03-29","showTime":10760},{"createTime":"2018-03-30","showTime":10760},{"createTime":"2018-03-31","showTime":10760},{"createTime":"2018-04-01","showTime":10760}]}]
     * msg : 查询成功
     * responseState : 1
     */

    public String msg;
    public String responseState;
    public List<DataBean> data;

    public static class DataBean {
        public List<WeekDataBean> weekData;
        public List<DayLevalDataBean> dayLevalData;
        public List<MonthDataBean> monthData;

        public static class WeekDataBean {
            /**
             * createTime : 2018-02-25
             * showTime : 10760
             */

            public String createTime;
            public int showTime;
        }

        public static class DayLevalDataBean {
            /**
             * createTime : 2018-03-01
             * showTime : 3560
             */

            public String createTime;
            public int showTime;
        }

        public static class MonthDataBean {
            /**
             * createTime : 2018-03-02
             * showTime : 10760
             */

            public String createTime;
            public int showTime;
        }
    }
}
