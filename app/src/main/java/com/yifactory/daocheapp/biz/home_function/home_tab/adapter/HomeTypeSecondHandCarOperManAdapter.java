package com.yifactory.daocheapp.biz.home_function.home_tab.adapter;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.GetCategoryListBean;
import com.yifactory.daocheapp.biz.home_function.adapter.HomeTypeManagementChildAdapter;
import com.yifactory.daocheapp.biz.home_function.home_recommend_tab.activity.HomeRecommendOperateManagerActivity;

import java.util.List;

public class HomeTypeSecondHandCarOperManAdapter extends BaseQuickAdapter<GetCategoryListBean.DataBean, BaseViewHolder> {

    public HomeTypeSecondHandCarOperManAdapter() {
        super(R.layout.item_home_type_out_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetCategoryListBean.DataBean item) {
        helper.setText(R.id.title_tv, item.getFirstCategoryContent());
        RecyclerView recyclerView = helper.getView(R.id.child_management_rv);
        List<GetCategoryListBean.DataBean.CategorySecondListBean> categorySecondList = item.getCategorySecondList();
        HomeTypeManagementChildAdapter operManAdapter = new HomeTypeManagementChildAdapter(categorySecondList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(operManAdapter);
        operManAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, HomeRecommendOperateManagerActivity.class);
                intent.putExtra("mark", "type");
                intent.putExtra("position",position);
                intent.putExtra("dataBean", item);
                mContext.startActivity(intent);
            }
        });
    }
}
