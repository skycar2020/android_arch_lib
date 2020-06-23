package com.mvvm.demo.core.http.rx

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.mvvm.demo.core.http.ApiResponse
import com.mvvm.demo.core.log.XLogger
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<ApiResponse<T>>.toLiveDataCatchError() : LiveData<ApiResponse<T>> {
    val flowable = this.toFlowable().doOnNext {
        if (it.error != null ){
            XLogger.i("not null--")
        } else {
            XLogger.e("null---")
        }

        XLogger.i(it.status ?: "status null")
    }
    return LiveDataReactiveStreams.fromPublisher(flowable)
}

fun <T> Single<ApiResponse<T>>.proccessRequest() : LiveData<ApiResponse<T>> {
    val flowable = this.toFlowable()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        //.subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))
        .doOnNext {
            if (it.error != null ){
                XLogger.i("not null--")
            } else {
                XLogger.e("null---")
            }

            XLogger.i(it.status ?: "status null")
        }
    return LiveDataReactiveStreams.fromPublisher(flowable)
}

fun <T> Single<ApiResponse<T>>.toLiveDataResponse() :  LiveData<ApiResponse<T>> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

