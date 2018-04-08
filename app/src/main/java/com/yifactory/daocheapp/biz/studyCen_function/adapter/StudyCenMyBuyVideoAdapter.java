package com.yifactory.daocheapp.biz.studyCen_function.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.GetUserBuyRecordBean;
import com.yifactory.daocheapp.bean.GetUserBuyedBean;
import com.yifactory.daocheapp.bean.PlayVideoBean;
import com.yifactory.daocheapp.utils.Formatter;

public class StudyCenMyBuyVideoAdapter extends BaseQuickAdapter<PlayVideoBean.DataBean.HotBean, BaseViewHolder> {

    public StudyCenMyBuyVideoAdapter() {
        super(R.layout.item_home_recommend_video);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayVideoBean.DataBean.HotBean item) {
        ImageView iconVideoIv = helper.getView(R.id.video_iv);
        TextView titleTv = helper.getView(R.id.title_tv);
        TextView contentTv = helper.getView(R.id.content_tv);
        TextView playCountTv = helper.getView(R.id.play_count_tv);
        TextView timeTv = helper.getView(R.id.duration_tv);
        TextView moneyTv = helper.getView(R.id.free_tv);

        Glide.with(mContext).load(item.getIndexImg()).into(iconVideoIv);
        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
        String secondTitle = item.getSecondTitle();
        if (!TextUtils.isEmpty(secondTitle)) {
            contentTv.setText(secondTitle);
        }
        int showCounts = item.getShowCounts();
        playCountTv.setText(showCounts + "次");
        int totalMinute = item.getTotalMinute();
        timeTv.setText(Formatter.formatTime(totalMinute) + "分钟");
        int goldCount = item.getGoldCount();
        moneyTv.setText(goldCount+"金币");
    }
}
