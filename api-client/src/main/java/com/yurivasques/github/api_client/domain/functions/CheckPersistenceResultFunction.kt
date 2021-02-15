package com.yurivasques.github.api_client.domain.functions

import com.yurivasques.github.api_client.domain.exception.PersistenceException
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import kotlin.jvm.Throws

/**
 * [Function] that adds a delay to receiving the onComplete when a [Single] return an error
 * Used to check if the persistence process was successful
 */
class CheckPersistenceResultFunction : Function<Boolean, Completable> {

    @Throws(Exception::class)
    override fun apply(result: Boolean): Completable =
            if (result) Completable.complete()
            else Completable.error(PersistenceException)

}