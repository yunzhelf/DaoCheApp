package com.yifactory.daocheapp.biz.home_function.home_tab.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.HomeShareTypeBean;

import java.util.List;

public class HomeShareTypeSelectAdapter extends BaseQuickAdapter<HomeShareTypeBean, BaseViewHolder> {

    public HomeShareTypeSelectAdapter(@Nullable List<HomeShareTypeBean> data) {
        super(R.layout.item_home_share_type_select, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeShareTypeBean item) {
        int layoutPosition = helper.getLayoutPosition();
        TextView titleTv = helper.getView(R.id.title_tv);
        titleTv.setText(item.getTitle());
        if (item.isSelected()) {
            titleTv.setTextColor(Color.parseColor("#FFFFFF"));
            titleTv.setBackgroundResource(R.drawable.shape_blue_corner);
        } else {
            titleTv.setTextColor(Color.parseColor("#000000"));
            titleTv.setBackgroundResource(R.drawable.shape_gray_corner);
        }
    }
}
