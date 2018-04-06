/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.aliyun.demo.recorder;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.aliyun.demo.R;
import com.aliyun.quview.CircularImageView;
import com.aliyun.struct.form.PreviewPasterForm;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;


public class PasterAdapter extends RecyclerView.Adapter<PasterAdapter.AssetInfoViewHolder> {
    private Context context;
    private List<PreviewPasterForm> data;
    private int itemWidth;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public PasterAdapter(Context context, List<PreviewPasterForm> data, int itemWidth) {
        this.context = context;
        this.data = data;
        this.itemWidth = itemWidth;
    }

    public void setOnitemClickListener(OnItemClickListener l) {
        this.listener = l;
    }

    @Override
    public AssetInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.aliyun_svideo_item_asset, parent, false);
        item.setLayoutParams(new FrameLayout.LayoutParams(itemWidth, itemWidth));
        AssetInfoViewHolder holder = new AssetInfoViewHolder(item, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AssetInfoViewHolder holder, int position) {
        if (data.get(position).getIcon() == null || data.get(position).getIcon().isEmpty()) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setTag(null);
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
            Glide.with(context.getApplicationContext())
                    .setDefaultRequestOptions(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .load(data.get(position).getIcon())
                    .into(new ViewTarget<CircularImageView, Drawable>(holder.imageView) {
                        @Override
                        public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                            holder.imageView.setImageBitmap(((BitmapDrawable) drawable).getBitmap());
                        }
                    });
            holder.itemView.setTag(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AssetInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CircularImageView imageView;
        public OnItemClickListener listener;

        public AssetInfoViewHolder(View itemView, OnItemClickListener l) {
            super(itemView);
            this.listener = l;
            imageView = (CircularImageView) itemView.findViewById(R.id.aliyun_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
