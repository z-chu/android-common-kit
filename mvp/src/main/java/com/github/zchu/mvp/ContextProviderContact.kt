package com.github.zchu.mvp

interface ContextProviderContact {

    val contextProvider: ContextProvider

}

fun ContextProviderContact.context() {
    contextProvider.provideContext()
}