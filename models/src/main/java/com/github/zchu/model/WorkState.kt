package com.github.zchu.model

import androidx.lifecycle.MutableLiveData


class WorkState private constructor(
    val status: Status,
    val throwable: Throwable? = null
) {
    companion object {

        val LOADING = WorkState(Status.RUNNING)

        val LOADED = WorkState(Status.SUCCEEDED)

        fun error(throwable: Throwable?) = WorkState(Status.FAILED, throwable)
    }
}


inline fun WorkState.switch(
    onLoading: () -> Unit = { },
    onSuccess: () -> Unit = { },
    onFailure: () -> Unit = { }
) {
    when (status) {
        Status.RUNNING -> onLoading.invoke()
        Status.SUCCEEDED -> onSuccess.invoke()
        Status.FAILED -> onFailure.invoke()
    }
}

inline fun WorkState.doOnLoading(
    onLoading: () -> Unit = { }
) {
    if (status == Status.RUNNING) {
        onLoading.invoke()
    }
}

inline fun WorkState.doOnSuccess(
    onSuccess: () -> Unit = { }
) {
    if (status == Status.SUCCEEDED) {
        onSuccess.invoke()
    }
}

inline fun WorkState.doOnFailure(
    onFailure: () -> Unit = { }
) {
    if (status == Status.FAILED) {
        onFailure.invoke()
    }
}


/**
 * 当状态不相等时才进行下发value
 */
fun MutableLiveData<WorkState>.setValueOnStatusNotEqual(networkState: WorkState) {
    val value = value
    if (value != null && value.status != networkState.status) {
        setValue(networkState)
    }

}