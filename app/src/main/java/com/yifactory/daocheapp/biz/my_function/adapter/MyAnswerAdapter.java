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
import com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter.DiscoverAnswersInnerAdapter;
import com.yifactory.daocheapp.utils.SoftInputUtils;

import java.util.ArrayList;

public class MyAnswerAdapter extends BaseQuickAdapter<DiscoverBean, BaseViewHolder> {

    private RecyclerVScrollEventCallBack scrollEventCallBack;

    public MyAnswerAdapter(int layoutResId, RecyclerVScrollEventCallBack scrollEventCallBack) {
        super(layoutResId);
        this.scrollEventCallBack = scrollEventCallBack;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DiscoverBean item) {
        final int layoutPosition = helper.getLayoutPosition();
        NineGridView nineGridView = helper.getView(R.id.nineGrid);
        final TextView moreCommentTv = helper.getView(R.id.more_comment_tv);

        ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
        //  2018/3/6 此处需要构建缩略图和大图数据
        for (int i = 0; i < 10; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setImageViewHeight(170);
            imageInfo.setImageViewWidth(170);
//            imageInfo.setBigImageUrl();
//            imageInfo.setThumbnailUrl();
        }
        nineGridView.setAdapter(new NineGridViewClickAdapter(mContext, imageInfoList));

        TextView giveHimAdviceTv = helper.getView(R.id.give_him_advice_tv);
        giveHimAdviceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editView = LayoutInflater.from(mContext).inflate(R.layout.include_comment_layout, null);
                PopupWindow editWindow = new PopupWindow(editView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                editWindow.setOutsideTouchable(true);
                editWindow.setFocusable(true);
                editWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                final EditText replyEdit = (EditText) editView.findViewById(R.id.comment_et);
                replyEdit.setFocusable(true);
                replyEdit.requestFocus();
                editWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                editWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                editWindow.showAtLocation(helper.itemView, Gravity.BOTTOM, 0, 0);
//                SoftInputUtils.showSoftInput(replyEdit.getContext());

                editWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Log.i("521", "onDismiss: popup消失了...");
//                        if (imm.isActive()) imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
//                        SoftInputUtils.hideSoftInput(replyEdit.getContext(), replyEdit);
                    }
                });
            }
        });

        final RecyclerView mRv_inner = helper.getView(R.id.inner_rv);
        final MyQuestionInnerAdapter innerAdapter = new MyQuestionInnerAdapter();
        mRv_inner.setFocusableInTouchMode(false);
        mRv_inner.requestFocus();
        mRv_inner.setLayoutManager(new LinearLayoutManager(mContext));
        mRv_inner.setAdapter(innerAdapter);
        innerAdapter.setNewData(item.getDataList1());
        moreCommentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isShowMore()) {
                    innerAdapter.setNewData(item.getDataList1());
                    moreCommentTv.setText("更多评论");
                    item.setShowMore(false);
                    scrollEventCallBack.scrollEvent(layoutPosition);
                } else {
                    innerAdapter.setNewData(item.getDataList2());
                    moreCommentTv.setText("收起");
                    item.setShowMore(true);
                }
            }
        });
    }

    public interface RecyclerVScrollEventCallBack {
        void scrollEvent(int position);
    }

    public interface CommentEditLayoutShowCallBack {
        void toShowEvent(int visibility);
    }
}
