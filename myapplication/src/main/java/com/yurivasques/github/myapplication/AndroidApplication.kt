package com.yurivasques.github.myapplication

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.yurivasques.github.api_client.data.di.components.DataComponent
import com.yurivasques.github.myapplication.di.components.ApplicationComponent
import com.yurivasques.github.api_client.data.di.components.DaggerDataComponent
import com.yurivasques.github.myapplication.di.components.DaggerApplicationComponent

class AndroidApplication : Application() {

    @set:VisibleForTesting
    lateinit var appComponent: ApplicationComponent

    @VisibleForTesting
    val dataComponent: DataComponent by lazy { DaggerDataComponent.factory().create(baseContext) }

    override fun onCreate() {
        super.onCreate()

        // Create App Component
        appComponent = createAppComponent()
    }

    @VisibleForTesting
    fun createAppComponent(): ApplicationComponent =
        DaggerApplicationComponent.factory()
            .create(this, dataComponent)

}