package com.yifactory.daocheapp.biz.my_function.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.CouponListBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCouponAdapter extends BaseQuickAdapter<CouponListBean.DataBean, BaseViewHolder> {

    public MyCouponAdapter() {
        super(R.layout.item_my_coupon);
    }

    @Override
    protected void convert(final BaseViewHolder helper, CouponListBean.DataBean item) {
        final ImageView expandIv = helper.getView(R.id.coupon_expand_iv);
        final TextView expandContentTv = helper.getView(R.id.coupon_expand_content_tv);
        final TextView percentTv = helper.getView(R.id.coupon_tv_zhekou);
        final TextView minPriceTv = helper.getView(R.id.coupon_tv_manjian);
        final TextView sDateTv = helper.getView(R.id.coupon_sdata_tv);
        final TextView eDateTv = helper.getView(R.id.coupon_edata_tv);
        final TextView detailTv = helper.getView(R.id.coupon_expand_content_tv);
        final TextView typeTv = helper.getView(R.id.coupon_type_tv);
        final TextView subTypeTv = helper.getView(R.id.coupon_subtype_tv);
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf1.parse(item.getCoupon().getStartTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            sDateTv.setText(sdf.format(d));
            d = sdf1.parse(item.getCoupon().getEndTime());
            eDateTv.setText(sdf.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int percent = (int)(item.getCoupon().getPercent()*10);
        percentTv.setText(String.valueOf(percent) + "折");
        minPriceTv.setText("满" + item.getCoupon().getMinPrice() + "元可用");
        if(item.getCoupon().getTitle() != null){
            typeTv.setText(item.getCoupon().getTitle());
        }
        if(item.getCoupon().getFcInfo() != null && item.getCoupon().getFcInfo().get(0) != null){
            subTypeTv.setText(item.getCoupon().getFcInfo().get(0).getFirstCategoryContent());
        }
        if(item.getCoupon().getDetail() != null){
            detailTv.setText(item.getCoupon().getDetail());
        }

        expandIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandContentTv.getVisibility() == View.VISIBLE) {
                    expandContentTv.setVisibility(View.GONE);
                } else if (expandContentTv.getVisibility() == View.GONE) {
                    expandContentTv.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}
