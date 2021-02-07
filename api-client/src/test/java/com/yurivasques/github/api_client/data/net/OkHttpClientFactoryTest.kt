package com.yurivasques.github.api_client.data.net

import android.content.Context
import okhttp3.OkHttpClient

class OkHttpClientFactoryTest : OkHttpClientFactory() {

    override fun createOkHttpClient(context: Context): OkHttpClient =
        OkHttpClient.Builder().build()

}