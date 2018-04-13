package com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.bean.DiscoverCommentBean;
import com.yifactory.daocheapp.bean.GetShowMoodCommentBean;

public class DiscoverMoodInnerAdapter extends BaseQuickAdapter<GetShowMoodCommentBean.DataBean, BaseViewHolder> {

    private ReplayCallBack mReplayCallBack;
    private String userId;

    public DiscoverMoodInnerAdapter(ReplayCallBack replayCallBack, String userId) {
        super(R.layout.item_discover_mood_inner);
        mReplayCallBack = replayCallBack;
        this.userId = userId;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetShowMoodCommentBean.DataBean item) {
        TextView mTv_creator = helper.getView(R.id.creator_tv);
        TextView mTv_reply = helper.getView(R.id.reply_tv);
        TextView mTv_reciver = helper.getView(R.id.reciver_tv);
        GetShowMoodCommentBean.DataBean.CreatorBean creator = item.getCreator();
        final String creatorUserName = creator.getUserName();
        mTv_creator.setText(creatorUserName);
        GetShowMoodCommentBean.DataBean.ReciverBean reciver = item.getReciver();
        if (reciver != null) {
            String reciverUserName = reciver.getUserName();
            if (!TextUtils.isEmpty(reciverUserName)) {
                mTv_reciver.setText(reciverUserName);
                mTv_reply.setVisibility(View.VISIBLE);
                mTv_reciver.setVisibility(View.VISIBLE);
            } else {
                mTv_reply.setVisibility(View.GONE);
                mTv_reciver.setVisibility(View.GONE);
            }
        } else {
            mTv_reply.setVisibility(View.GONE);
            mTv_reciver.setVisibility(View.GONE);
        }

        final String uId = creator.getuId();
        ImageView mIv_huiFu = helper.getView(R.id.huifu_iv);
        TextView mTv_huiFu = helper.getView(R.id.huifu_tv);
        if (userId.equals(uId)) {
            mIv_huiFu.setVisibility(View.GONE);
            mTv_huiFu.setVisibility(View.GONE);
        } else {
            mIv_huiFu.setVisibility(View.VISIBLE);
            mTv_huiFu.setVisibility(View.VISIBLE);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    DiscoverCommentBean discoverCommentBean = new DiscoverCommentBean();
                    discoverCommentBean.setrId(uId);
                    discoverCommentBean.setReciverName(creatorUserName);
                    mReplayCallBack.replay(discoverCommentBean);
                }
            }
        };
        mIv_huiFu.setOnClickListener(listener);
        mTv_huiFu.setOnClickListener(listener);

        ImageView mIvShang = helper.getView(R.id.shang_iv);
        TextView mTvShang = helper.getView(R.id.shang_tv);
        mIvShang.setVisibility(View.GONE);
        mTvShang.setVisibility(View.GONE);

        ImageView mIvZan = helper.getView(R.id.zan_iv);
        TextView mTvZan = helper.getView(R.id.zan_tv);
        mIvZan.setVisibility(View.GONE);
        mTvZan.setVisibility(View.GONE);

        TextView mTv_answers = helper.getView(R.id.answerBody_tv);
        mTv_answers.setText(item.getCotentBody());

        helper.getView(R.id.delete_answers_tv).setVisibility(View.GONE);
        helper.setText(R.id.time_tv, item.getCreateTime());
    }

    public interface ReplayCallBack {
        void replay(DiscoverCommentBean commentBean);
    }
}
