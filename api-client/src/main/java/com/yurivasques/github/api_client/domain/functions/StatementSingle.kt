package com.yurivasques.github.api_client.domain.functions

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.plugins.RxJavaPlugins

object StatementSingle {

    fun <R> ifThen(
        condition: Boolean,
        then: SingleSource<out R>,
        orElse: Single<out R>
    ): Single<R> =
        RxJavaPlugins.onAssembly(SingleIfThen(condition, then, orElse))

}
