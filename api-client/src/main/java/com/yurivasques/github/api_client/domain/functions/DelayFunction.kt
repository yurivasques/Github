package com.yurivasques.github.api_client.domain.functions

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.functions.Function
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

class DelayFunction<T>(private val postScheduler: Scheduler) :
    Function<Throwable, ObservableSource<out T>> {
    private val delay: Int = 1
    private val timeUnit: TimeUnit = TimeUnit.SECONDS

    @Throws(Exception::class)
    override fun apply(throwable: Throwable): ObservableSource<out T> =
        Observable.just(throwable)
            .delay(delay.toLong(), timeUnit, postScheduler)
            .flatMap { Observable.error<T>(it) }

}
