package com.android.ge.network.response;

import com.android.ge.network.error.ExceptionEngine;

import rx.Observable;
import rx.functions.Func1;

public class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable throwable) {
            //ExceptionEngine为处理异常的驱动器
//            return Observable.error(ExceptionEngine.handleException(throwable));
            return Observable.error(throwable);
        }
    }