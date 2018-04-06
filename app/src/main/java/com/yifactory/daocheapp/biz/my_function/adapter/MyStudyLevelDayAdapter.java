package com.yifactory.daocheapp.biz.my_function.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.DiscoverBean;
import com.yifactory.daocheapp.bean.StudyDateBean;
import com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter.DiscoverMoodInnerAdapter;

import java.util.ArrayList;

public class MyStudyLevelDayAdapter extends BaseQuickAdapter<StudyDateBean.DataBean.DayDataBean, BaseViewHolder> {

    public MyStudyLevelDayAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final StudyDateBean.DataBean.DayDataBean item) {
        final TextView dayTv = helper.getView(R.id.level_day_tv);
        final TextView minTv = helper.getView(R.id.level_min_tv);
        dayTv.setText(String.valueOf(item.getSsDay()));
        minTv.setText(String.valueOf(item.getDaySum()));
    }

}
