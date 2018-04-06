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
import com.yifactory.daocheapp.bean.AddShowMoodAppraiseBean;
import com.yifactory.daocheapp.bean.DeleteShowMoodBean;
import com.yifactory.daocheapp.bean.GetShowMoodListBean;
import com.yifactory.daocheapp.biz.discover_function.activity.DiscoverMoodDetailsActivity;
import com.yifactory.daocheapp.utils.SPreferenceUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;
import com.yifactory.daocheapp.widget.CircleImageView;

import java.util.ArrayList;

import io.reactivex.Observable;

public class DiscoverMoodOuterAdapter extends BaseQuickAdapter<GetShowMoodListBean.DataBean, BaseViewHolder> {

    private DiscoverMoodOuterAdapter mOuterAdapter;
    private String userId;

    public DiscoverMoodOuterAdapter(String userId) {
        super(R.layout.item_discover_answers_outer);
        this.mOuterAdapter = this;
        this.userId = userId;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetShowMoodListBean.DataBean item) {

        final int layoutPosition = helper.getLayoutPosition();

        GetShowMoodListBean.DataBean.CreatorBean creator = item.getCreator();
        String uId = creator.getUId();
        String headImg = creator.getHeadImg();
        CircleImageView headIv = helper.getView(R.id.head_iv);
        Glide.with(mContext).setDefaultRequestOptions(new RequestOptions().dontAnimate().centerCrop()).load(headImg).into(headIv);

        String creatorUserName = creator.getUserName();
        helper.setText(R.id.creator_tv, creatorUserName);

        String jobArea = creator.getJobArea();
        helper.setText(R.id.job_attr_tv, jobArea);

        String userLeval = creator.getUserLeval();
        TextView userLevelTv = helper.getView(R.id.grade_tv);
        if (userLeval.equals("小白") || userLeval.equals("小学") || userLeval.equals("中学") || userLeval.equals("高中")) {
            userLevelTv.setVisibility(View.GONE);
        } else {
            userLevelTv.setVisibility(View.VISIBLE);
            userLevelTv.setText(userLeval);
        }

        String moodBody = item.getMoodBody();
        helper.setText(R.id.question_tv, moodBody);

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

        final String smId = item.getSmId();
        TextView deleteTv = helper.getView(R.id.delete_question_tv);
        deleteTv.setVisibility(userId.equals(uId) ? View.VISIBLE : View.GONE);
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(mContext, smId, layoutPosition);
            }
        });

        ImageView commentIv = helper.getView(R.id.comment_iv);
        TextView commentTv = helper.getView(R.id.comment_tv);
        int commentCount = item.getCommentCount();
        commentTv.setText(String.valueOf(commentCount));
        View.OnClickListener commentListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    if (new SPreferenceUtil(mContext, "config.sp").getIsLine()) {
                        Intent intent = new Intent(mContext, DiscoverMoodDetailsActivity.class);
                        intent.putExtra("dataBean", item);
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        commentIv.setOnClickListener(commentListener);
        commentTv.setOnClickListener(commentListener);

        final int praised = item.getPraised();
        int praiseCount = item.getPraiseCount();
        ImageView zanIv = helper.getView(R.id.zan_iv);
        if (praised == 0) {
            zanIv.setImageResource(R.drawable.zan);
        } else {
            zanIv.setImageResource(R.drawable.zan_sel);
        }
        TextView zanTv = helper.getView(R.id.zan_tv);
        zanTv.setText(String.valueOf(praiseCount));
        View.OnClickListener zanListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    if (new SPreferenceUtil(mContext, "config.sp").getIsLine()) {
                        zanItem(mContext, praised, layoutPosition, smId);
                    } else {
                        Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        zanIv.setOnClickListener(zanListener);
        zanTv.setOnClickListener(zanListener);
    }

    private void zanItem(final Context context, final int praised, final int itemPosition, String smId) {
        String uId = UserInfoUtil.getUserInfoBean(context).getUId();
        ApiService api = RxHttpUtils
                .createApi(ApiService.class);
        Observable<AddShowMoodAppraiseBean> observable;
        if (praised == 0) {
            observable = api
                    .addShowMoodAppraise(smId, uId);
        } else {
            observable = api
                    .deleteShowMoodAppraise(smId, uId);
        }
        observable
                .compose(Transformer.<AddShowMoodAppraiseBean>switchSchedulers())
                .subscribe(new CommonObserver<AddShowMoodAppraiseBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onSuccess(AddShowMoodAppraiseBean addShowMoodAppraiseBean) {
                        if (addShowMoodAppraiseBean.getResponseState().equals("1")) {
                            GetShowMoodListBean.DataBean dataBean = mOuterAdapter.getData().get(itemPosition);
                            int praiseCount = dataBean.getPraiseCount();
                            if (praised == 0) {
                                dataBean.setPraised(1);
                                dataBean.setPraiseCount(praiseCount + 1);
                            } else {
                                dataBean.setPraised(0);
                                dataBean.setPraiseCount(praiseCount - 1);
                            }
                            mOuterAdapter.notifyItemChanged(itemPosition);
                        } else {
                            Toast.makeText(context, addShowMoodAppraiseBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteEvent(final Context context, String smId, final int itemPosition) {
        RxHttpUtils
                .createApi(ApiService.class)
                .deleteShowMood(smId)
                .compose(Transformer.<DeleteShowMoodBean>switchSchedulers())
                .subscribe(new CommonObserver<DeleteShowMoodBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onSuccess(DeleteShowMoodBean deleteShowMoodBean) {
                        if (deleteShowMoodBean.getResponseState().equals("1")) {
                            mOuterAdapter.getData().remove(itemPosition);
                            mOuterAdapter.notifyItemRemoved(itemPosition);
                        } else {
                            Toast.makeText(context, deleteShowMoodBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
