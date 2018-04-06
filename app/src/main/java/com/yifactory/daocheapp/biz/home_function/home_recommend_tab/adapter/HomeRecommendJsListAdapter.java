package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.GetSysArmyAnasBean;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;

public class HomeRecommendJsListAdapter extends BaseQuickAdapter<GetSysArmyAnasBean.DataBean, BaseViewHolder> {

    public HomeRecommendJsListAdapter() {
        super(R.layout.item_home_recommend_js);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetSysArmyAnasBean.DataBean item) {
        helper.setText(R.id.title_tv, item.getTitle());
        helper.setText(R.id.time_tv, item.getCreateTime());
//        helper.setText(R.id.time_tv, item.getUpdateTime());
    }
}
