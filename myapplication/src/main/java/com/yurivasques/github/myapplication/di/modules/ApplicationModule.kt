package com.yurivasques.github.myapplication.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.yurivasques.github.myapplication.di.PerApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    @PerApplication
    internal fun provideContext(application: Application): Context = application.baseContext

    @Provides
    @PerApplication
    internal fun providesSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

}