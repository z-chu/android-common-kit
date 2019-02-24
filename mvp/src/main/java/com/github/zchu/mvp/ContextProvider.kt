package com.github.zchu.mvp

import android.content.Context

interface ContextProvider {

    fun provideContext(): Context

}


/*
fun ContextProvider.getString(resId: Int): String {
    return provideContext().getString(resId)
}

fun ContextProvider.getString(resId: Int, vararg formatArgs: Any): String {
    return provideContext().getString(resId, *formatArgs)
}

fun ContextProvider.getResources(): Resources {
    return provideContext().resources
}*/
