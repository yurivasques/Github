package com.yurivasques.github.myapplication.exception

import android.content.Context
import com.github.yurivasques.github_library.domain.exception.NoConnectionException

class ErrorMessageFactoryTest(context: Context) : ErrorMessageFactory(context) {

    override fun getError(exception: Throwable?): String =
        if (exception is NoConnectionException) "No connection"
        else "An error has occurred"

}