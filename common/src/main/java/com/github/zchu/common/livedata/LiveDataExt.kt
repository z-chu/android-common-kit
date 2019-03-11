package com.github.zchu.common.livedata

import android.os.Looper
import androidx.lifecycle.*

fun <T> MutableLiveData<T>.safeSetValue(value: T) {
    if (Looper.getMainLooper().thread === Thread.currentThread()) {
        setValue(value)
    } else {
        postValue(value)
    }
}

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

fun <X, Y, Z> LiveData<X>.zip(stream: LiveData<Y>, func: (source1: X?, source2: Y?) -> Z): LiveData<Z> {
    val result = MediatorLiveData<Z>()
    result.addSource(this) { x -> result.setValue(func.invoke(x, stream.value)) }
    result.addSource(stream) { y -> result.setValue(func.invoke(this.value, y)) }
    return result
}

fun <X> LiveData<X>.merge(stream: LiveData<X>): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x -> result.setValue(x) }
    result.addSource(stream) { x -> result.setValue(x) }
    return result
}

fun <X, Y> LiveData<X>.mapNotNull(func: (source: X?) -> Y?): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this) { x ->
        func.invoke(x)?.let { result.value = it }
    }
    return result
}

fun <X> LiveData<X>.filter(func: (source: X?) -> Boolean): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x -> if (func.invoke(x)) result.value = x }
    return result
}

fun <X> LiveData<X>.filterNotNull(func: (source: X) -> Boolean): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x ->
        if (x != null && func.invoke(x)) result.value = x
    }
    return result
}

fun <X> LiveData<X?>.filterOutNull(): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x ->
        if (x != null) result.value = x
    }
    return result
}

fun <M, S> MediatorLiveData<M>.observeOnce(source: LiveData<S>, func: (data: S?) -> Unit) {
    this.removeSource(source)
    this.addSource(source) {
        func.invoke(it)
        this@observeOnce.removeSource(source)
    }
}

