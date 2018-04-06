package com.yifactory.daocheapp.biz.my_function.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.bean.DeleteUserAttentionBean;
import com.yifactory.daocheapp.bean.GetAttentionListBean;
import com.yifactory.daocheapp.widget.CircleImageView;

import io.reactivex.Observable;

public class MyFocusAdapter extends BaseQuickAdapter<GetAttentionListBean.DataBean, BaseViewHolder> {

    private MyFocusAdapter mMyFocusAdapter;
    private String userId;

    public MyFocusAdapter(String userId) {
        super(R.layout.item_my_focus);
        this.userId = userId;
        this.mMyFocusAdapter = this;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetAttentionListBean.DataBean item) {
        final int layoutPosition = helper.getLayoutPosition();
        CircleImageView authorHeadIv = helper.getView(R.id.author_head_iv);
        TextView authorNameTv = helper.getView(R.id.author_name_tv);
        TextView authorJobTv = helper.getView(R.id.author_job_tv);
        TextView focusTv = helper.getView(R.id.focus_tv);
        String headImg = item.getHeadImg();
        if (!TextUtils.isEmpty(headImg)) {
            Glide.with(mContext).load(headImg).into(authorHeadIv);
        }
        authorNameTv.setText(item.getUserName());
        authorJobTv.setText(item.getJobName());

        final String uId = item.getUId();
        focusTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guanZhuEvent(mContext, uId, layoutPosition);
            }
        });
    }

    private void guanZhuEvent(final Context context, String attentionId, final int itemPosition) {
        ApiService api = RxHttpUtils.createApi(ApiService.class);
        Observable<DeleteUserAttentionBean> observable = api.deleteUserAttention2(userId, attentionId);
        observable.compose(Transformer.<DeleteUserAttentionBean>switchSchedulers())
                .subscribe(new CommonObserver<DeleteUserAttentionBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onSuccess(DeleteUserAttentionBean deleteUserAttentionBean) {
                        if (deleteUserAttentionBean.getResponseState().equals("1")) {
                            mMyFocusAdapter.getData().remove(itemPosition);
                            mMyFocusAdapter.notifyItemRemoved(itemPosition);
                        }
                        Toast.makeText(context, deleteUserAttentionBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
