/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.aliyun.demo.importer.media;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import com.aliyun.demo.importer.MyGlideModule;
import com.aliyun.demo.importer.R;
import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2016/5/18.
 */
public class GalleryItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView thumbImage;
    private TextView duration;
    private View durationLayoput;
    private ThumbnailGenerator thumbnailGenerator;

    public GalleryItemViewHolder(View itemView, ThumbnailGenerator thumbnailGenerator) {
        super(itemView);

        this.thumbnailGenerator = thumbnailGenerator;
        thumbImage = (ImageView) itemView.findViewById(R.id.draft_thumbnail);
        duration = (TextView) itemView.findViewById(R.id.draft_duration);
        durationLayoput = itemView.findViewById(R.id.duration_layoput);

        itemView.setTag(this);
    }

    public void setData(final MediaInfo info){
        if(info == null){
            return;
        }
        if(info.thumbnailPath != null
                && onCheckFileExsitence(info.thumbnailPath)) {
            String uri = "file://" + info.thumbnailPath;
            Log.e("sunxj" , "====================="+thumbImage.getContext().toString() );
            Glide.with(thumbImage.getContext()).load(uri).into(thumbImage);
        }else {
            thumbImage.setImageDrawable(new ColorDrawable(Color.GRAY));
            thumbnailGenerator.generateThumbnail(info.type, info.id,0,
                    new ThumbnailGenerator.OnThumbnailGenerateListener() {
                        @Override
                        public void onThumbnailGenerate(int key, Bitmap thumbnail) {
                            int currentKey = ThumbnailGenerator.generateKey(info.type, info.id);
                            if(key == currentKey){
                                thumbImage.setImageBitmap(thumbnail);
                            }
                        }
                    });
        }

        int du = info.duration;
        if(du == 0){
            durationLayoput.setVisibility(View.GONE);
        }else{
            durationLayoput.setVisibility(View.VISIBLE);
            onMetaDataUpdate(duration, du);
        }

    }

    public void onBind(MediaInfo info, boolean actived){
        setData(info);
        itemView.setActivated(actived);
    }

    private boolean onCheckFileExsitence(String path) {
        Boolean res = false;
        if(path == null) {
            return res;
        }

        File file = new File(path);
        if(file.exists()) {
            res = true;
        }

        return res;
    }


    private void onMetaDataUpdate(TextView view, int duration) {
        if (duration == 0) {
            return;
        }

        int sec = Math.round((float) duration / 1000);
        int min = sec / 60;
        sec %= 60;

        view.setText(String.format(String.format("%d:%02d", min, sec)));
    }

}
