package com.yifactory.daocheapp.biz.home_function.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.GetCategoryListBean;

import java.util.List;

public class HomeTypeManagementChildAdapter extends BaseQuickAdapter<GetCategoryListBean.DataBean.CategorySecondListBean, BaseViewHolder> {

    public HomeTypeManagementChildAdapter(@Nullable List<GetCategoryListBean.DataBean.CategorySecondListBean> data) {
        super(R.layout.item_home_type_second_hand_car_oper_man, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GetCategoryListBean.DataBean.CategorySecondListBean item) {
        helper.setText(R.id.title_tv, item.getSecondContent());
    }
}
