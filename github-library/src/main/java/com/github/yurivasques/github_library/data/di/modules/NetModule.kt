package com.github.yurivasques.github_library.data.di.modules

import android.content.Context
import com.google.gson.Gson
import com.github.yurivasques.github_library.data.di.providers.NetworkChecker
import com.github.yurivasques.github_library.data.net.OkHttpClientFactory
import com.github.yurivasques.github_library.data.net.RetrofitFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Dagger module for Net class.
 */
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