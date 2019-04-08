package com.github.zchu.model

sealed class WorkResult<T> {
    var tag: Any? = null
}

class Loading<T>() : WorkResult<T>()

class Success<T>(val value: T) : WorkResult<T>()

class Failure<T>(val throwable: Throwable) : WorkResult<T>() {
    constructor() : this(RuntimeException())
    constructor(message: String) : this(RuntimeException(message))
}


fun <T> WorkResult<T>.switch(block: WorkResultBridge<T>.() -> Unit) {
    val bridge = WorkResultBridge<T>().apply(block)
    when (this) {
        is Loading<T> -> bridge.onLoading(this)
        is Success<T> -> bridge.onSuccess(this)
        is Failure<T> -> bridge.onError(this)
    }
}

class WorkResultBridge<T> {

    private var _onLoading: ((Loading<T>) -> Unit)? = null

    private var _onSuccess: ((Success<T>) -> Unit)? = null

    private var _onError: ((Failure<T>) -> Unit)? = null

    fun onLoading(block: ((Loading<T>) -> Unit)) {
        _onLoading = block
    }

    fun onSuccess(block: ((Success<T>) -> Unit)) {
        _onSuccess = block
    }

    fun onError(block: ((Failure<T>) -> Unit)) {
        _onError = block
    }

    internal fun onLoading(value: Loading<T>) {
        _onLoading?.invoke(value)
    }

    internal fun onSuccess(value: Success<T>) {
        _onSuccess?.invoke(value)
    }

    internal fun onError(value: Failure<T>) {
        _onError?.invoke(value)
    }

}