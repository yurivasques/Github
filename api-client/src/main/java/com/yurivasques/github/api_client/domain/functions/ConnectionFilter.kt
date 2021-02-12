package com.yurivasques.github.api_client.domain.functions

import com.yurivasques.github.api_client.domain.exception.NoConnectionException
import io.reactivex.rxjava3.functions.Predicate
import kotlin.jvm.Throws

/**
 * [Predicate] that returns true if there's an available internet connection
 * @throws [NoConnectionException]
 */
class ConnectionFilter : Predicate<Boolean> {

    @Throws(Exception::class)
    override fun test(isConnected: Boolean): Boolean {
        if (!isConnected) {
            throw NoConnectionException
        }
        return true
    }

}