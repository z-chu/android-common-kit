package com.github.zchu.model

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * 如果最后一次结果是Failure，则在一个新的Observer订阅后，不会收到之前的Failure结果
 */
class SingleFailureLiveResult<T> : LiveResult<T>() {


    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in WorkResult<T>>) {
        val value = value
        if (value != null && value is Failure) {
            super.observe(owner, object : Observer<WorkResult<T>> {
                var isFirst: Boolean = true
                override fun onChanged(t: WorkResult<T>?) {
                    if (!isFirst || t !is Failure) {
                        observer.onChanged(t)
                    }
                    isFirst = false
                }

            })
        } else {
            super.observe(owner, observer)
        }
    }


}