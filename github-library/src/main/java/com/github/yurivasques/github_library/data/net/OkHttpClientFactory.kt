package com.github.yurivasques.github_library.data.net

import android.content.Context
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

open class OkHttpClientFactory {
    open fun createOkHttpClient(context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .apply { updateTimeout() }
            .build()

    private fun OkHttpClient.Builder.updateTimeout(read: Long = 60, write: Long = 60) {
        readTimeout(read, TimeUnit.SECONDS)
        writeTimeout(write, TimeUnit.SECONDS)
    }
}