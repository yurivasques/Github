package com.yurivasques.github.myapplication.di.modules

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.yurivasques.github.myapplication.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @Provides
    @PerActivity
    internal fun provideActivity(activity: Activity) = activity as AppCompatActivity

}