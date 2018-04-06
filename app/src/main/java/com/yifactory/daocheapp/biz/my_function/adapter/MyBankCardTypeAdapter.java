package com.yifactory.daocheapp.biz.my_function.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.BankCardBean;


public class MyBankCardTypeAdapter extends BaseQuickAdapter<BankCardBean.DataBean, BaseViewHolder> {

    public MyBankCardTypeAdapter() {
        super(R.layout.item_my_bank_card_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCardBean.DataBean item) {
        final TextView bankNameTv = helper.getView(R.id.bank_card_name_tv);
        final TextView bankLastNumTv = helper.getView(R.id.bank_card_lastnum_tv);
        String lastNum = item.getBankNum();

        bankNameTv.setText(item.getBankName());
        bankLastNumTv.setText("尾号" + lastNum.substring(lastNum.length()-4) + "  储蓄卡");
    }
}
