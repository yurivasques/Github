package com.yurivasques.github.api_client.domain.functions

import com.yurivasques.github.api_client.domain.exception.PersistenceException
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.functions.Function

class CheckPersistenceResultFunction : Function<Boolean, Completable> {

    @Throws(Exception::class)
    override fun apply(result: Boolean): Completable =
            if (result) Completable.complete()
            else Completable.error(PersistenceException)

}