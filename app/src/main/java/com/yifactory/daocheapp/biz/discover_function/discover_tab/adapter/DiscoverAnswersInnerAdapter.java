package com.yifactory.daocheapp.biz.discover_function.discover_tab.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.bean.AddQuestionAnswerAppraiseBean;
import com.yifactory.daocheapp.bean.DiscoverCommentBean;
import com.yifactory.daocheapp.bean.GetUserQuestionListBean;
import com.yifactory.daocheapp.utils.UserInfoUtil;

import io.reactivex.Observable;

public class DiscoverAnswersInnerAdapter extends BaseQuickAdapter<GetUserQuestionListBean.DataBean.AnswersBean, BaseViewHolder> {

    private ReplayCallBack mReplayCallBack;
    private DaShangCallBack mDaShangCallBack;
    private DiscoverAnswersInnerAdapter mInnerAdapter;
    private String userId;

    public DiscoverAnswersInnerAdapter(ReplayCallBack replayCallBack, String userId, DaShangCallBack daShangCallBack) {
        super(R.layout.item_discover_mood_inner);
        mInnerAdapter = this;
        mReplayCallBack = replayCallBack;
        this.userId = userId;
        mDaShangCallBack = daShangCallBack;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetUserQuestionListBean.DataBean.AnswersBean item) {
        final int layoutPosition = helper.getLayoutPosition();
        TextView mTv_creator = helper.getView(R.id.creator_tv);
        TextView mTv_reply = helper.getView(R.id.reply_tv);
        TextView mTv_reciver = helper.getView(R.id.reciver_tv);
        GetUserQuestionListBean.DataBean.AnswersBean.CreatorBean creator = item.getCreator();
        final String creatorUserName = creator.getUserName();
        mTv_creator.setText(creatorUserName);
        GetUserQuestionListBean.DataBean.AnswersBean.ReciverBean reciver = item.getReciver();
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
        ImageView mIvShang = helper.getView(R.id.shang_iv);
        TextView mTvShang = helper.getView(R.id.shang_tv);

        if (userId.equals(uId)) {
            mIv_huiFu.setVisibility(View.GONE);
            mTv_huiFu.setVisibility(View.GONE);
            mIvShang.setVisibility(View.GONE);
            mTvShang.setVisibility(View.GONE);
        } else {
            mIv_huiFu.setVisibility(View.VISIBLE);
            mTv_huiFu.setVisibility(View.VISIBLE);
            mIvShang.setVisibility(View.VISIBLE);
            mTvShang.setVisibility(View.VISIBLE);
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

        final String aId = item.getAId();
        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    mDaShangCallBack.shang(aId);
                }
            }
        };
        mIvShang.setOnClickListener(listener2);
        mTvShang.setOnClickListener(listener2);

        ImageView mIvZan = helper.getView(R.id.zan_iv);
        final int praised = item.getPraised();
        if (praised == 0) {
            mIvZan.setImageResource(R.drawable.zan);
        } else {
            mIvZan.setImageResource(R.drawable.zan_sel);
        }
        TextView mTvZan = helper.getView(R.id.zan_tv);
        int praiseCounts = item.getPraiseCounts();
        mTvZan.setText(String.valueOf(praiseCounts));
        View.OnClickListener listener3 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    zanEvent(mContext, aId, layoutPosition, praised);
                }
            }
        };
        mIvZan.setOnClickListener(listener3);
        mTvZan.setOnClickListener(listener3);

        TextView mTv_answers = helper.getView(R.id.answerBody_tv);
        mTv_answers.setText(item.getAnswerBody());
    }

    //点赞请求
    private void zanEvent(final Context context, String aId, final int itemPosition, final int praised) {
        ApiService api = RxHttpUtils
                .createApi(ApiService.class);
        Observable<AddQuestionAnswerAppraiseBean> addQuestionAnswerAppraiseBeanObservable;
        if (praised == 0) {
            addQuestionAnswerAppraiseBeanObservable = api
                    .addQuestionAnswerAppraise(UserInfoUtil.getUserInfoBean(context).getUId(), aId);
        } else {
            addQuestionAnswerAppraiseBeanObservable = api
                    .deleteQuestionAnswerAppraise(UserInfoUtil.getUserInfoBean(context).getUId(), aId);
        }
        addQuestionAnswerAppraiseBeanObservable
                .compose(Transformer.<AddQuestionAnswerAppraiseBean>switchSchedulers())
                .subscribe(new CommonObserver<AddQuestionAnswerAppraiseBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onSuccess(AddQuestionAnswerAppraiseBean addQuestionAnswerAppraiseBean) {
                        if (addQuestionAnswerAppraiseBean.getResponseState().equals("1")) {
                            GetUserQuestionListBean.DataBean.AnswersBean answersBean = mInnerAdapter.getItem(itemPosition - 1);
                            if (answersBean != null) {
                                int praiseCounts = answersBean.getPraiseCounts();
                                if (praised == 0) {
                                    answersBean.setPraised(1);
                                    answersBean.setPraiseCounts(praiseCounts + 1);
                                } else {
                                    answersBean.setPraised(0);
                                    answersBean.setPraiseCounts(praiseCounts-1);
                                }
                                mInnerAdapter.notifyItemChanged(itemPosition);
                            }
                        } else {
                            Toast.makeText(context, addQuestionAnswerAppraiseBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public interface ReplayCallBack{
        void replay(DiscoverCommentBean commentBean);
    }

    public interface DaShangCallBack{
        void shang(String aId);
    }
}
