package com.yifactory.daocheapp.bean;

import java.util.ArrayList;
import java.util.List;

public class DiscoverBean {

    private List<String> dataList1 = new ArrayList<>();

    private List<String> dataList2 = new ArrayList<>();

    private boolean isShowMore = false;

    public DiscoverBean() {
        for (int i = 0; i < 10; i++) {
            dataList2.add("" + i);
            if (i < 2) {
                dataList1.add("" + i);
            }
        }
    }

    public List<String> getDataList1() {
        return dataList1;
    }

    public void setDataList1(List<String> dataList1) {
        this.dataList1 = dataList1;
    }

    public List<String> getDataList2() {
        return dataList2;
    }

    public void setDataList2(List<String> dataList2) {
        this.dataList2 = dataList2;
    }

    public boolean isShowMore() {
        return isShowMore;
    }

    public void setShowMore(boolean showMore) {
        isShowMore = showMore;
    }
}
