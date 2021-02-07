package com.yurivasques.github.api_client.data.net

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission

import com.google.gson.Gson

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    private const val MAIN_URL = "https://api.github.com/"

    /**
     * Get [Retrofit] instance.
     * @return instances of [Retrofit]
     */
    @RequiresPermission(value = Manifest.permission.INTERNET)
    fun getRetrofit(
        context: Context,
        gson: Gson,
        okHttpClientFactory: OkHttpClientFactory
    ): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(MAIN_URL)
            .client(okHttpClientFactory.createOkHttpClient(context))
            .build()
}