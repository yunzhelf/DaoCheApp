package com.yifactory.daocheapp.utils;

import android.content.Context;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class PictureCompressUtil {

    public static PictureCompressUtil getInstance() {
        return PictureCompressHolder.INSTANCE;
    }

    private static class PictureCompressHolder {
        private static final PictureCompressUtil INSTANCE = new PictureCompressUtil();
    }

    public void startCompress(final Context context, final List<String> photos, final CompressedPicResultCallBack compressedPicResultCallBack) {
        Flowable.just(photos)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        return Luban.with(context).load(list).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) throws Exception {
                        compressedPicResultCallBack.showResult(photos, list);
                    }
                });

    }

    public interface CompressedPicResultCallBack {
        void showResult(List<String> photos, List<File> list);
    }
}
