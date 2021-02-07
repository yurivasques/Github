package com.yurivasques.github.api_client.data.di.modules

import android.content.Context
import com.google.gson.Gson
import com.yurivasques.github.api_client.data.di.providers.NetworkChecker
import com.yurivasques.github.api_client.data.net.OkHttpClientFactory
import com.yurivasques.github.api_client.data.net.RetrofitFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    internal fun provideNetworkChecker(context: Context): NetworkChecker = NetworkChecker(context)

    @Provides
    @Singleton
    internal fun provideOkHttpClientFactory(): OkHttpClientFactory = OkHttpClientFactory()

    @Provides
    @Singleton
    internal fun provideRetrofit(
        context: Context,
        gson: Gson,
        okHttpClientFactory: OkHttpClientFactory
    ): Retrofit =
        RetrofitFactory.getRetrofit(context, gson, okHttpClientFactory)

}