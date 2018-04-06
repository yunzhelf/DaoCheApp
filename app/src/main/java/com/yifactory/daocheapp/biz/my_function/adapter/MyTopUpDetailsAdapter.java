package com.yifactory.daocheapp.biz.my_function.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.GetUserBalanceRecordBean;

public class MyTopUpDetailsAdapter extends BaseQuickAdapter<GetUserBalanceRecordBean.DataBean, BaseViewHolder> {

    public MyTopUpDetailsAdapter() {
        super(R.layout.item_top_up_details);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetUserBalanceRecordBean.DataBean item) {
        int recordType = item.getRecordType();
        TextView mTv_type = helper.getView(R.id.type_tv);
        String createTime = item.getCreateTime();
        int goldCounts = item.getGoldCounts();
        if (recordType == 0) {
            mTv_type.setText("充值");
        } else if (recordType == 1) {
            mTv_type.setText("提现");
        } else if (recordType == 2) {
            mTv_type.setText("消费");
        }
        helper.setText(R.id.time_tv, createTime);
        helper.setText(R.id.money_tv, String.valueOf(goldCounts));
    }
}
