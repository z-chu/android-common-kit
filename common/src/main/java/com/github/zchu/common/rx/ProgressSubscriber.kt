package com.github.zchu.common.rx

import android.app.Dialog
import androidx.annotation.CallSuper
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by zchu on 17-2-23.
 */

abstract class ProgressSubscriber<T> : Observer<T> {

    private lateinit var progressDialogHandler: ProgressDialogHandler

    protected var disposable: Disposable? = null


    @CallSuper
    override fun onSubscribe(disposable: Disposable) {
        this.disposable = disposable
        progressDialogHandler = ProgressDialogHandler(createProgressDialog(disposable))
        progressDialogHandler.showDialog()
    }

    override fun onComplete() {
        progressDialogHandler.dismissDialog()
    }


    override fun onError(e: Throwable) {
        progressDialogHandler.dismissDialog()

    }


    abstract fun createProgressDialog(disposable: Disposable): Dialog

}
