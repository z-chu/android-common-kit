package com.github.zchu.common.rx

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit


/**
 * [ RxJava retryWhen操作符实现错误重试机制](http://blog.csdn.net/johnny901114/article/details/51539708)
 */

class RetryWithDelay @JvmOverloads constructor(
    private val maxRetries: Int,
    private val retryDelayMillis: Int,
    private val predicate: ((Throwable) -> Boolean)? = null
) : Function<Observable<Throwable>, ObservableSource<*>> {
    private var retryCount: Int = 0

    @Throws(Exception::class)
    override fun apply(throwableObservable: Observable<Throwable>): ObservableSource<*> {
        return throwableObservable
            .flatMap(Function<Throwable, ObservableSource<*>> { throwable ->
                if ((predicate == null || predicate.invoke(throwable)) && ++retryCount <= maxRetries) {
                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                    Log.d(
                        "RetryWithDelay", "get error, it will try after " + retryDelayMillis
                                + " millisecond, retry count " + retryCount
                    )
                    return@Function Observable.timer(
                        retryDelayMillis.toLong(),
                        TimeUnit.MILLISECONDS
                    )
                }
                // Max retries hit. Just pass the error along.
                Observable.error<Any>(throwable)
            })
    }

}
