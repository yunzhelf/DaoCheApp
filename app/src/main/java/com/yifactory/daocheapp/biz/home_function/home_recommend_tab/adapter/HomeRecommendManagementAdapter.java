package com.yifactory.daocheapp.biz.home_function.home_recommend_tab.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.AppCtx;
import com.yifactory.daocheapp.bean.GetSystemInfoBean;

public class HomeRecommendManagementAdapter extends BaseQuickAdapter<GetSystemInfoBean.DataBean.IndexBtnsBean, BaseViewHolder> {

    public HomeRecommendManagementAdapter() {
        super(R.layout.item_home_recommend_management);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetSystemInfoBean.DataBean.IndexBtnsBean item) {
        int layoutPosition = helper.getLayoutPosition();
        TextView titleTv = helper.getView(R.id.title_tv);
        titleTv.setText(item.getFirstCategoryContent());
        ImageView iconIv = helper.getView(R.id.icon_iv);
        switch (helper.getLayoutPosition()) {
            case 0:
                iconIv.setImageResource(R.drawable.da);
                break;
            case 1:
                iconIv.setImageResource(R.drawable.kai);
                break;
            case 2:
                iconIv.setImageResource(R.drawable.si);
                break;
            case 3:
                iconIv.setImageResource(R.drawable.wei);
                break;
            case 4:
                iconIv.setImageResource(R.drawable.ti);
                break;
            case 5:
                iconIv.setImageResource(R.drawable.sheng);
                break;
            case 6:
                iconIv.setImageResource(R.drawable.neng);
                break;
            case 7:
                iconIv.setImageResource(R.drawable.li);
                break;
        }
        if (!AppCtx.isHomeRecommendTabPlayed) {
            startAnim(iconIv);
            startAnim(titleTv);
        }
        if (layoutPosition == getData().size() - 1) {
            AppCtx.isHomeRecommendTabPlayed = true;
        }
    }

    private void startAnim(View view) {
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -300f, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotationY, translationX);
        animatorSet.setDuration(800);
        animatorSet.start();
    }
}
