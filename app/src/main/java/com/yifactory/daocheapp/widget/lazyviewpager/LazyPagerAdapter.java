package com.yifactory.daocheapp.widget.lazyviewpager;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public abstract class LazyPagerAdapter<T> extends PagerAdapter {

    protected SparseArray<T> mLazyItems = new SparseArray<T>();
    private T mCurrentItem;

    protected abstract T addLazyItem(ViewGroup container, int position);

    protected abstract T getItem(ViewGroup container, int position);

    public boolean isLazyItem(int position) {
        return mLazyItems.get(position) != null;
    }

    public T getCurrentItem() {
        return mCurrentItem;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentItem = addLazyItem(container, position);
    }

}
