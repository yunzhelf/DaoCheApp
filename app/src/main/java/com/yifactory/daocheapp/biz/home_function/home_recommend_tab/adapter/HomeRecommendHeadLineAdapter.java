package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;

/**
 * Created by sunxj on 2018/4/13.
 */

public class HomeRecommendHeadLineAdapter extends BaseQuickAdapter<GetSystemInfoBean.DataBean.SysIndexNewsBean, BaseViewHolder> {

    public HomeRecommendHeadLineAdapter(){
        super(R.layout.item_home_recommend_head_line);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetSystemInfoBean.DataBean.SysIndexNewsBean item) {
        TextView title = helper.getView(R.id.item_head_title);
        TextView date = helper.getView(R.id.item_head_time);

        title.setText(item.getTitle());
        date.setText(item.getCreateTime());
    }
}
