package com.yifactory.daocheapp.biz.home_function.adapter;

import android.app.Dialog;
import android.view.View;

import com.allen.retrofit.RxHttpUtils;
import com.allen.retrofit.interceptor.Transformer;
import com.allen.retrofit.observer.CommonObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.api.ApiService;
import com.yifactory.daocheapp.bean.BaseBean;
import com.yifactory.daocheapp.bean.MsgBean;
import com.yifactory.daocheapp.utils.SDDialogUtil;
import com.yifactory.daocheapp.utils.UserInfoUtil;

import static com.allen.retrofit.utils.ToastUtils.showToast;

public class HomeMsgAdapter extends BaseQuickAdapter<MsgBean.DataBean, BaseViewHolder> {
    private Dialog mDialog;

    public HomeMsgAdapter() {
        super(R.layout.item_home_msg);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MsgBean.DataBean item) {
        helper.setText(R.id.item_home_time, item.createTime);
        switch (item.type) {
            case 0:
                helper.setImageResource(R.id.item_home_type_img, R.drawable.zan_sel);
                helper.setText(R.id.item_home_type_text, "点赞");
                helper.setText(R.id.content_tv, item.content);
                helper.setText(R.id.small_content, item.creatorName + "在" + item.createTime + "赞了你");
                break;
            case 1:
                helper.setImageResource(R.id.item_home_type_img, R.drawable.zan_sel);
                helper.setText(R.id.item_home_type_text, "回复");
                helper.setText(R.id.content_tv, item.content);
                helper.setText(R.id.small_content, item.creatorName + "在" + item.createTime + "回复了你");
                break;
            case 2:
                helper.setImageResource(R.id.item_home_type_img, R.drawable.zan_sel);
                helper.setText(R.id.item_home_type_text, "系统消息");
                helper.setText(R.id.content_tv, "系统公告");
                helper.setText(R.id.small_content, item.content);
                break;
        }
        helper.getView(R.id.item_home_type_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog == null) {
                    mDialog = SDDialogUtil.newLoading(mContext, "请求中");
                }
                removeMsg(item);
            }
        });
    }

    private void removeMsg(final MsgBean.DataBean item) {
        mDialog.show();
        String uId = UserInfoUtil.getUserInfoBean(mContext).getUId();
        RxHttpUtils.createApi(ApiService.class)
                .removeMsg(item.sumId, null)
                .compose(Transformer.<BaseBean>switchSchedulers())
                .subscribe(new CommonObserver<BaseBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showToast(errorMsg);
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    protected void onSuccess(BaseBean getSystemInfoBean) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        if (getSystemInfoBean.responseState.equals("1")) {
                            getData().remove(item);
                            notifyDataSetChanged();
                        } else {
                            showToast(getSystemInfoBean.msg);
                        }
                    }
                });
    }
}
