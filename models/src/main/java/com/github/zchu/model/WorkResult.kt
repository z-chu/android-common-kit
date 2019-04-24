package com.github.zchu.model

import androidx.lifecycle.LiveData

sealed class WorkResult<T> {
    var tag: Any? = null
}

class Loading<T>(var canceler: (() -> Unit)? = null) : WorkResult<T>()

class Success<T>(val value: T) : WorkResult<T>()

class Failure<T>(val throwable: Throwable) : WorkResult<T>() {
    constructor() : this(RuntimeException())
    constructor(message: String) : this(RuntimeException(message))
}

inline fun <T> WorkResult<T>.switch(
    onLoading: (Loading<T>) -> Unit = { _ -> },
    onSuccess: (Success<T>) -> Unit = { _ -> },
    onFailure: (Failure<T>) -> Unit = { _ -> }
) {
    when (this) {
        is Loading<T> -> onLoading.invoke(this)
        is Success<T> -> onSuccess.invoke(this)
        is Failure<T> -> onFailure.invoke(this)
    }
}

inline fun <T> WorkResult<T>.doOnLoading(
    onLoading: (Loading<T>) -> Unit = { _ -> }
) {
    if (this is Loading<T>) {
        onLoading.invoke(this)
    }
}

inline fun <T> WorkResult<T>.doOnSuccess(
    onSuccess: (Success<T>) -> Unit = { _ -> }
) {
    if (this is Success<T>) {
        onSuccess.invoke(this)
    }
}

inline fun <T> WorkResult<T>.doOnFailure(
    onFailure: (Failure<T>) -> Unit = { _ -> }
) {
    if (this is Failure<T>) {
        onFailure.invoke(this)
    }
}

typealias LiveResult<T> = LiveData<WorkResult<T>>