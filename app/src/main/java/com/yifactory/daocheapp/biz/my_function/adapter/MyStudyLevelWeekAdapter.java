package com.yifactory.daocheapp.biz.my_function.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.StudyDateBean;

public class MyStudyLevelWeekAdapter extends BaseQuickAdapter<StudyDateBean.DataBean.WeekDataBean, BaseViewHolder> {

    public MyStudyLevelWeekAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final StudyDateBean.DataBean.WeekDataBean item) {
        final TextView dayTv = helper.getView(R.id.level_day_tv);
        final TextView minTv = helper.getView(R.id.level_min_tv);
        dayTv.setText(String.valueOf(item.getSsDay()));
        minTv.setText(String.valueOf(item.getDaySum()/60));
    }

}
