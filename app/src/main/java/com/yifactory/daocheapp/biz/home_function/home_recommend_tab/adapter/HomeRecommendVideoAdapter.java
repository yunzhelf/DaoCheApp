package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.bean.VideoListBean;
import com.yifactory.daocheapp.utils.Formatter;

public class HomeRecommendVideoAdapter extends BaseQuickAdapter<PlayVideoBean.DataBean.HotBean, BaseViewHolder> {

    public HomeRecommendVideoAdapter() {
        super(R.layout.item_home_recommend_video);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayVideoBean.DataBean.HotBean item) {
        ImageView imageView = helper.getView(R.id.btn_iv);
        TextView titleTv = helper.getView(R.id.title_tv);
        TextView contentTv = helper.getView(R.id.content_tv);
        TextView playCountTv = helper.getView(R.id.play_count_tv);
        TextView durationTv = helper.getView(R.id.duration_tv);
        TextView freeTv = helper.getView(R.id.free_tv);

        Glide.with(mContext).load(item.getIndexImg()).into(imageView);
        titleTv.setText(item.getTitle());
        contentTv.setText(item.getSecondTitle());
        playCountTv.setText(String.valueOf(item.getShowCounts())+"次");
        durationTv.setText(Formatter.formatTime(item.getTotalMinute()) + "分钟");
        if(item.getGoldCount() == 0){
            freeTv.setText("免费");
        }else{
            freeTv.setText(String.valueOf(item.getGoldCount())+"金币");
        }
    }
}
