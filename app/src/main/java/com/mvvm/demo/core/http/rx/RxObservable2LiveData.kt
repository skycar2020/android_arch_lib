package com.mvvm.demo.core.http.rx

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.*


fun <T> Flowable<T>.toLiveData() : LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}

fun <T> Observable<T>.toLiveData(backPressureStrategy: BackpressureStrategy) :  LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable(backPressureStrategy))
}

fun <T> Single<T>.toLiveData() :  LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}
