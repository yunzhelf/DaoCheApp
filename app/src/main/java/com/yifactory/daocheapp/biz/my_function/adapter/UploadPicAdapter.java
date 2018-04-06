package com.yifactory.daocheapp.biz.my_function.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.app.activity.BaseActivity;

import java.util.List;

import me.iwf.photopicker.PhotoPicker;

public class UploadPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private BaseActivity context;
    private List<String> urlList;
    public UploadPicAdapter(List<String> picUrlList, BaseActivity context) {
        super(R.layout.item_my_upload_pic_group, picUrlList);
        urlList = picUrlList;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int layoutPosition = helper.getLayoutPosition();
        ImageView picIv = helper.getView(R.id.pic_iv);
        if (layoutPosition == urlList.size() - 1) {
            picIv.setImageResource(R.drawable.shangchuantupian);
            picIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (urlList.size() < 7) {
                        PhotoPicker.builder()
                                .setPhotoCount(7 - urlList.size())
                                .setShowCamera(true)
                                .setShowGif(true)
                                .setPreviewEnabled(false)
                                .start(context, PhotoPicker.REQUEST_CODE);
                    } else {
                        Toast.makeText(context, "图片数量达到上限", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Glide.with(mContext).load(item).into(picIv);
            picIv.setOnClickListener(null);
        }
    }
}
