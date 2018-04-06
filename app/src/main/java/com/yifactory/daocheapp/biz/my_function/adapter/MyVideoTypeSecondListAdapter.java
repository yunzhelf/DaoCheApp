package com.yifactory.daocheapp.biz.my_function.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.GetCategoryListBean;

public class MyVideoTypeSecondListAdapter extends BaseQuickAdapter<GetCategoryListBean.DataBean.CategorySecondListBean, BaseViewHolder> {

    public MyVideoTypeSecondListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, GetCategoryListBean.DataBean.CategorySecondListBean item) {
        TextView secondTv = helper.getView(R.id.type_name_tv);
        ImageView secondIv = helper.getView(R.id.type_right_Iv);
        secondIv.setVisibility(View.GONE);
        secondTv.setText(item.getSecondContent());
    }
}
