package com.yifactory.daocheapp.biz.video_function.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.utils.Formatter;

public class VideoAuthorOtherProductionAdapter extends BaseQuickAdapter<PlayVideoBean.DataBean.TeacherOtherBean, BaseViewHolder> {

    public VideoAuthorOtherProductionAdapter() {
        super(R.layout.item_video_author_other_production);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayVideoBean.DataBean.TeacherOtherBean item) {
        TextView oneTitle = helper.getView(R.id.tvTextone);
        TextView secondTitle = helper.getView(R.id.tvTexttwo);
        ImageView imageView = helper.getView(R.id.other_img_iv);
        TextView playCountTv = helper.getView(R.id.tvFrequency);
        TextView tvTime = helper.getView(R.id.tvTime);

        oneTitle.setText(item.getTitle());
        secondTitle.setText(item.getSecondTitle());
        Glide.with(mContext).load(item.getIndexImg()).into(imageView);
        playCountTv.setText(item.getShowCounts() + "次");
        tvTime.setText(Formatter.formatTime(item.getTotalMinute()) + "分钟");
    }
}
