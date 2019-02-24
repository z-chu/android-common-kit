package com.github.zchu.common.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

fun <T, R> LiveData<T>.map(mapper: (T?) -> R?): LiveData<R> = Transformations.map(this, mapper)

fun <T, R> LiveData<T>.switchMap(mapper: (T?) -> LiveData<R>?): LiveData<R> = Transformations.switchMap(this, mapper)

fun <T> LiveData<T>.skip(count: Long): LiveData<T> {
    val mediatorLiveData = MediatorLiveData<T>()
    mediatorLiveData.addSource(this, object : Observer<T> {
        var skip: Int = 1
        override fun onChanged(t: T) {
            if (skip > count) {
                mediatorLiveData.value = t
            }
            skip++

        }

    })
    return mediatorLiveData
}

