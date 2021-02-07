package com.yurivasques.github.myapplication.exception

import android.content.Context
import com.yurivasques.github.api_client.domain.exception.NoConnectedException

class ErrorMessageFactoryTest(context: Context) : ErrorMessageFactory(context) {

    override fun getError(exception: Throwable?): String =
        if (exception is NoConnectedException) "No connection"
        else "An error has occurred"

}