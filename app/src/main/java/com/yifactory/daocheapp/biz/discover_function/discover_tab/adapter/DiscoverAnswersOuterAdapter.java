package com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.bean.DeleteUserQuestionBean;
import com.yifactory.daocheapp.bean.GetUserQuestionListBean;
import com.yifactory.daocheapp.biz.discover_function.activity.DiscoverAnswersDetailsActivity;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.widget.CircleImageView;

import java.util.ArrayList;

public class DiscoverAnswersOuterAdapter extends BaseQuickAdapter<GetUserQuestionListBean.DataBean, BaseViewHolder> {

    private String userId;
    private DiscoverAnswersOuterAdapter mOuterAdapter;

    public DiscoverAnswersOuterAdapter(String userId) {
        super(R.layout.item_discover_answers_outer);
        mOuterAdapter = this;
        this.userId = userId;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GetUserQuestionListBean.DataBean item) {
        final int layoutPosition = helper.getLayoutPosition();
        GetUserQuestionListBean.DataBean.CreatorBean creator = item.getCreator();
        String uId = creator.getuId();
        String headImg = creator.getHeadImg();
        CircleImageView circleImageView = helper.getView(R.id.head_iv);
        Glide.with(mContext).setDefaultRequestOptions(new RequestOptions().dontAnimate().centerCrop()).load(headImg).into(circleImageView);
        final String creatorUserName = creator.getUserName();
        helper.setText(R.id.creator_tv, creatorUserName);
        helper.setText(R.id.job_attr_tv, creator.getJobArea());
        String userLeval = creator.getUserLeval();
        TextView userLevelTv = helper.getView(R.id.grade_tv);
        if (userLeval.equals("小白") || userLeval.equals("小学") || userLeval.equals("中学") || userLeval.equals("高中")) {
            userLevelTv.setVisibility(View.GONE);
        } else {
            userLevelTv.setVisibility(View.VISIBLE);
            userLevelTv.setText(userLeval);
        }
        helper.setText(R.id.question_tv, item.getQuestionBody());
        NineGridView nineGridView = helper.getView(R.id.nineGrid);
        String fileStr = item.getFile();
        if (!TextUtils.isEmpty(fileStr)) {
            String[] fileUrlArray = fileStr.split(",");
            ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
            for (String aFileUrlArray : fileUrlArray) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setImageViewHeight(150);
                imageInfo.setImageViewWidth(150);
                imageInfo.setBigImageUrl(aFileUrlArray);
                imageInfo.setThumbnailUrl(aFileUrlArray);
                imageInfoList.add(imageInfo);
            }
            nineGridView.setAdapter(new NineGridViewClickAdapter(mContext, imageInfoList));
            nineGridView.setVisibility(View.VISIBLE);
        } else {
            nineGridView.setVisibility(View.GONE);
        }

        final String qId = item.getQId();
        TextView deleteTv = helper.getView(R.id.delete_question_tv);
        deleteTv.setVisibility(userId.equals(uId) ? View.VISIBLE : View.GONE);
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(mContext, qId, layoutPosition);
            }
        });

        int commentCount = item.getCommentCount();
        TextView commentTv = helper.getView(R.id.comment_tv);
        ImageView commentIv = helper.getView(R.id.comment_iv);
        commentTv.setText(String.valueOf(commentCount));
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    if (new SPreferenceUtil(mContext, "config.sp").getIsLine()) {
                        Intent intent = new Intent(mContext, DiscoverAnswersDetailsActivity.class);
                        intent.putExtra("dataBean", item);
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        commentIv.setOnClickListener(listener);
        commentTv.setOnClickListener(listener);
        TextView zanTv = helper.getView(R.id.zan_tv);
        ImageView zanIv = helper.getView(R.id.zan_iv);
        View view = helper.getView(R.id.view_1);
        zanIv.setVisibility(View.GONE);
        zanTv.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
    }

    private void deleteEvent(final Context context, String qId, final int itemPosition) {
        RxHttpUtils
                .createApi(ApiService.class)
                .deleteUserQuestion(qId)
                .compose(Transformer.<DeleteUserQuestionBean>switchSchedulers())
                .subscribe(new CommonObserver<DeleteUserQuestionBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onSuccess(DeleteUserQuestionBean getUserQuestionListBean) {
                        if (getUserQuestionListBean.getResponseState().equals("1")) {
                            mOuterAdapter.remove(itemPosition);
                        } else {
                            Toast.makeText(context, getUserQuestionListBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
