package com.yifactory.daocheapp.bean;

import java.util.List;

public class GetStudyDateBean {

    /**
     * data : [{"weekData":[{"ssDay":"2018-03-18","weekTotal":8878,"daySum":1235},{"ssDay":"2018-03-20","weekTotal":8878,"daySum":1833},{"ssDay":"2018-03-21","weekTotal":8878,"daySum":2100},{"ssDay":"2018-03-22","weekTotal":8878,"daySum":1910},{"ssDay":"2018-03-23","weekTotal":8878,"daySum":1800}],"monthData":[{"monthTotal":39875,"ssDay":"2018-03-01","daySum":7200},{"monthTotal":39875,"ssDay":"2018-03-04","daySum":6592},{"monthTotal":39875,"ssDay":"2018-03-05","daySum":1234},{"monthTotal":39875,"ssDay":"2018-03-09","daySum":6600},{"monthTotal":39875,"ssDay":"2018-03-10","daySum":3560},{"monthTotal":39875,"ssDay":"2018-03-11","daySum":1899},{"monthTotal":39875,"ssDay":"2018-03-17","daySum":3692},{"monthTotal":39875,"ssDay":"2018-03-18","daySum":1235},{"monthTotal":39875,"ssDay":"2018-03-20","daySum":1833},{"monthTotal":39875,"ssDay":"2018-03-21","daySum":2100},{"monthTotal":39875,"ssDay":"2018-03-22","daySum":1910},{"monthTotal":39875,"ssDay":"2018-03-23","daySum":1800},{"monthTotal":39875,"ssDay":"2018-03-25","daySum":220}],"dayData":{"ssDay":"2018-03-25","daySum":220}}]
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
         * weekData : [{"ssDay":"2018-03-18","weekTotal":8878,"daySum":1235},{"ssDay":"2018-03-20","weekTotal":8878,"daySum":1833},{"ssDay":"2018-03-21","weekTotal":8878,"daySum":2100},{"ssDay":"2018-03-22","weekTotal":8878,"daySum":1910},{"ssDay":"2018-03-23","weekTotal":8878,"daySum":1800}]
         * monthData : [{"monthTotal":39875,"ssDay":"2018-03-01","daySum":7200},{"monthTotal":39875,"ssDay":"2018-03-04","daySum":6592},{"monthTotal":39875,"ssDay":"2018-03-05","daySum":1234},{"monthTotal":39875,"ssDay":"2018-03-09","daySum":6600},{"monthTotal":39875,"ssDay":"2018-03-10","daySum":3560},{"monthTotal":39875,"ssDay":"2018-03-11","daySum":1899},{"monthTotal":39875,"ssDay":"2018-03-17","daySum":3692},{"monthTotal":39875,"ssDay":"2018-03-18","daySum":1235},{"monthTotal":39875,"ssDay":"2018-03-20","daySum":1833},{"monthTotal":39875,"ssDay":"2018-03-21","daySum":2100},{"monthTotal":39875,"ssDay":"2018-03-22","daySum":1910},{"monthTotal":39875,"ssDay":"2018-03-23","daySum":1800},{"monthTotal":39875,"ssDay":"2018-03-25","daySum":220}]
         * dayData : {"ssDay":"2018-03-25","daySum":220}
         */

        private DayDataBean dayData;
        private List<WeekDataBean> weekData;
        private List<MonthDataBean> monthData;

        public DayDataBean getDayData() {
            return dayData;
        }

        public void setDayData(DayDataBean dayData) {
            this.dayData = dayData;
        }

        public List<WeekDataBean> getWeekData() {
            return weekData;
        }

        public void setWeekData(List<WeekDataBean> weekData) {
            this.weekData = weekData;
        }

        public List<MonthDataBean> getMonthData() {
            return monthData;
        }

        public void setMonthData(List<MonthDataBean> monthData) {
            this.monthData = monthData;
        }

        public static class DayDataBean {
            /**
             * ssDay : 2018-03-25
             * daySum : 220
             */

            private String ssDay;
            private int daySum;

            public String getSsDay() {
                return ssDay;
            }

            public void setSsDay(String ssDay) {
                this.ssDay = ssDay;
            }

            public int getDaySum() {
                return daySum;
            }

            public void setDaySum(int daySum) {
                this.daySum = daySum;
            }
        }

        public static class WeekDataBean {
            /**
             * ssDay : 2018-03-18
             * weekTotal : 8878
             * daySum : 1235
             */

            private String ssDay;
            private int weekTotal;
            private int daySum;

            public String getSsDay() {
                return ssDay;
            }

            public void setSsDay(String ssDay) {
                this.ssDay = ssDay;
            }

            public int getWeekTotal() {
                return weekTotal;
            }

            public void setWeekTotal(int weekTotal) {
                this.weekTotal = weekTotal;
            }

            public int getDaySum() {
                return daySum;
            }

            public void setDaySum(int daySum) {
                this.daySum = daySum;
            }
        }

        public static class MonthDataBean {
            /**
             * monthTotal : 39875
             * ssDay : 2018-03-01
             * daySum : 7200
             */

            private int monthTotal;
            private String ssDay;
            private int daySum;

            public int getMonthTotal() {
                return monthTotal;
            }

            public void setMonthTotal(int monthTotal) {
                this.monthTotal = monthTotal;
            }

            public String getSsDay() {
                return ssDay;
            }

            public void setSsDay(String ssDay) {
                this.ssDay = ssDay;
            }

            public int getDaySum() {
                return daySum;
            }

            public void setDaySum(int daySum) {
                this.daySum = daySum;
            }
        }
    }
}
