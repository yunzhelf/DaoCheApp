package com.yifactory.daocheapp.biz.discover_function.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.activity.BaseActivity;

import java.util.List;

import me.iwf.photopicker.PhotoPicker;

public class DiscoverPublishAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private boolean isFinish = false;
    private List<String> data;
    private BaseActivity context;

    public DiscoverPublishAdapter(List<String> data, BaseActivity context) {
        super(R.layout.item_discover_publish_pic, data);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView picIv = helper.getView(R.id.upload_effect_pic_iv);
        if (!isFinish) {
            picIv.setImageResource(R.drawable.shangchuantupian);
        } else {
            Glide.with(mContext).load(data.get(helper.getLayoutPosition())).into(picIv);
        }
        picIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFinish) {
                    PhotoPicker.builder()
                            .setPhotoCount(9)
                            .setShowCamera(true)
                            .setShowGif(false)
                            .setPreviewEnabled(false)
                            .start(context, PhotoPicker.REQUEST_CODE);
                }
            }
        });
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
