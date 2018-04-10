package com.yifactory.daocheapp.biz.video_function.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.utils.Formatter;

public class VideoHotRecommendAdapter extends BaseQuickAdapter<PlayVideoBean.DataBean.HotBean, BaseViewHolder> {

    public VideoHotRecommendAdapter() {
        super(R.layout.item_home_recommend_video);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayVideoBean.DataBean.HotBean item) {
        TextView oneTv = helper.getView(R.id.title_tv);
        ImageView imageView = helper.getView(R.id.btn_iv);
        TextView secondTv = helper.getView(R.id.content_tv);
        TextView playCountTv = helper.getView(R.id.play_count_tv);
        TextView timeTv = helper.getView(R.id.duration_tv);
        TextView goldTv = helper.getView(R.id.free_tv);
        TextView autherTv = helper.getView(R.id.name_tv);

        oneTv.setText(item.getTitle());
        secondTv.setText(item.getSecondTitle());
        Glide.with(mContext).load(item.getIndexImg()).into(imageView);
        playCountTv.setText(item.getShowCounts() + "次");
        timeTv.setText("时长：" + Formatter.formatTime(item.getTotalMinute()));
        if(item.getGoldCount() == 0){
            goldTv.setText("免费");
        }else{
            goldTv.setText(item.getGoldCount() + "金币");
        }
        if(item.getCreator() != null && item.getCreator().getUserName() != null){
            autherTv.setVisibility(View.VISIBLE);
            autherTv.setText(item.getCreator().getUserName());
        }
    }
}
