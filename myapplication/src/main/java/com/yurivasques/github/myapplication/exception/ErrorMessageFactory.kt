package com.yurivasques.github.myapplication.exception

import android.content.Context
import com.github.yurivasques.github_library.domain.exception.NoConnectionException
import com.github.yurivasques.github_library.domain.exception.PersistenceException
import com.yurivasques.github.myapplication.R
import timber.log.Timber
import javax.inject.Inject

open class ErrorMessageFactory
@Inject internal constructor(private val context: Context) {

    /**
     * Creates a String representing an error message.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return [String] an error message.
     */
    open fun getError(exception: Throwable?): String =
        exception?.let {
            when (it) {
                is NoConnectionException -> context.getString(R.string.error_no_connection)
                is PersistenceException -> context.getString(R.string.error_persistence)
                else -> context.getString(R.string.error_generic)
            }.apply { Timber.e(it) }
        } ?: getGenericError()

    private fun getGenericError() = context.getString(R.string.error_generic)
}
