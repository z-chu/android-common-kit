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

/**
 * 当状态不相等时才进行下发value
 */
fun MutableLiveData<WorkState>.setValueOnStatusNotEqual(networkState: WorkState) {
    val value = value
    if (value != null && value.status != networkState.status) {
        setValue(networkState)
    }

}