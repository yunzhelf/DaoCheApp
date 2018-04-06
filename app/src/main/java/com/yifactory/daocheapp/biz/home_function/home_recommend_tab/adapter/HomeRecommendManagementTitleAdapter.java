package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;

public class HomeRecommendManagementTitleAdapter extends BaseQuickAdapter<GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean, BaseViewHolder> {

    public HomeRecommendManagementTitleAdapter() {
        super(R.layout.item_home_recommend_management_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetSystemInfoBean.DataBean.IndexBtnsBean.CategorySecondListBean item) {
        helper.setText(R.id.autofit_tv, item.getSecondContent());
        if (item.isSelected != null && item.isSelected)
            helper.setTextColor(R.id.autofit_tv, mContext.getResources().getColor(R.color.__picker_item_photo_border_selected));
        else
            helper.setTextColor(R.id.autofit_tv, mContext.getResources().getColor(R.color.home_title_color));
    }
}
