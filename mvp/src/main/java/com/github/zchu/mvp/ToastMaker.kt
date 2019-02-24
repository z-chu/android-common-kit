package com.github.zchu.mvp

import androidx.annotation.StringRes

interface ToastMaker {

    fun showToast(message: String)

    fun showToast(@StringRes resId: Int)

}