package com.yifactory.daocheapp.biz.my_function.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.event.DiscoverAnswersDaShangMsg;

import org.greenrobot.eventbus.EventBus;

public class MyQuestionInnerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MyQuestionInnerAdapter() {
        super(R.layout.item_my_answer_inner);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView mIvShang = helper.getView(R.id.shang_iv);
        mIvShang.setVisibility(View.VISIBLE);
        TextView mTvShang = helper.getView(R.id.shang_tv);
        mTvShang.setVisibility(View.VISIBLE);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {

                }
            }
        };
        mIvShang.setOnClickListener(listener);
        mTvShang.setOnClickListener(listener);
    }
}
