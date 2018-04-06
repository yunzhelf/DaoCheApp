package com.allen.retrofit.base;

import com.allen.retrofit.bean.BaseData;
import com.allen.retrofit.exception.ApiException;
import com.allen.retrofit.interfaces.IDataSubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基类BaseObserver使用BaseData
 */

public abstract class BaseDataObserver<T> implements Observer<BaseData<T>>, IDataSubscriber<T> {

    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(BaseData<T> baseData) {
        doOnNext(baseData);
    }

    @Override
    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }


    private void setError(String errorMsg) {
        doOnError(errorMsg);
    }

}
