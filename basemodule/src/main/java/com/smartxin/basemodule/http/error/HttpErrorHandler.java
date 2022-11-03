package com.smartxin.basemodule.http.error;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/19 16:48
 * Description: 网络请求异常处理
 */
public class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable t) {
        return Observable.error(ExceptionHandle.handleException(t));
    }
}
