package com.github.zchu.common.rx

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class RxViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun Disposable.disposeWhenCleared() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}