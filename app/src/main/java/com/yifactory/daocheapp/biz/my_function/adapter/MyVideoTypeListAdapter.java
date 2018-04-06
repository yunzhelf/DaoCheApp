package com.yifactory.daocheapp.biz.my_function.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.DiscoverBean;
import com.yifactory.daocheapp.bean.GetCategoryListBean;

import java.util.ArrayList;

public class MyVideoTypeListAdapter extends BaseQuickAdapter<GetCategoryListBean.DataBean, BaseViewHolder> {

    public MyVideoTypeListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, GetCategoryListBean.DataBean item) {
        TextView typeNameTv = helper.getView(R.id.type_name_tv);
        typeNameTv.setText(item.getFirstCategoryContent());

    }
}
