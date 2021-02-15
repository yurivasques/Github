package com.yurivasques.github.api_client.domain.functions

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.plugins.RxJavaPlugins

/**
 * Statements expressed as Single operators.
 */
object StatementSingle {

    /**
     * Return an Single that can return two different Single emissions
     * based on a condition evaluation
     *
     * @param <R> the result value type
     * @param condition the condition that decides which Observable to emit the emissions from
     * @param then the Single sequence to emit to if `condition` is `true`
     * @param orElse the Single sequence to emit to if `condition` is `false`
     * @return an Single that mimics either the `then` or `orElse` Single depending on a condition function
     */
    fun <R> ifThen(
        condition: Boolean,
        then: SingleSource<out R>,
        orElse: Single<out R>
    ): Single<R> =
        RxJavaPlugins.onAssembly(SingleIfThen(condition, then, orElse))

}
