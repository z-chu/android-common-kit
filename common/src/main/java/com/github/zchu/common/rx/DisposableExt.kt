package com.github.zchu.common.rx

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

fun Disposable.bindLifecycle(lifecycleOwner: LifecycleOwner) {
    bindLifecycle(lifecycleOwner.lifecycle)
}

fun Disposable.bindLifecycle(lifecycleOwner: LifecycleOwner, untilEvent: Lifecycle.Event) {
    bindLifecycle(lifecycleOwner.lifecycle, untilEvent)
}


fun Disposable.bindLifecycle(lifecycle: Lifecycle) {
    bindLifecycle(lifecycle, Lifecycle.Event.ON_DESTROY)
}

fun Disposable.bindLifecycle(lifecycle: Lifecycle, untilEvent: Lifecycle.Event) {
    if (untilEvent <= Lifecycle.Event.ON_RESUME || untilEvent == Lifecycle.Event.ON_ANY) {
        throw IllegalArgumentException("The parameter untilEvent($untilEvent) cannot be a positive event")
    }
    if (this.isDisposed) {
        return
    }
    val currentState = lifecycle.currentState
    if (currentState == Lifecycle.State.DESTROYED) {
        this.dispose()
    } else {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
            fun onAny(source: LifecycleOwner, event: Lifecycle.Event) {
                if (isDisposed) {
                    source.lifecycle.removeObserver(this)
                } else {
                    if (event >= untilEvent) {
                        this@bindLifecycle.dispose()
                        source.lifecycle.removeObserver(this)
                    }
                }
            }
        })
    }

}


fun Disposable.addTo(disposableContainer: DisposableContainer) {
    disposableContainer.add(this)
}