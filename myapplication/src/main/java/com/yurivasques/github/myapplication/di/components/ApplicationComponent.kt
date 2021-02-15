package com.yurivasques.github.myapplication.di.components

import android.app.Application
import com.yurivasques.github.api_client.data.di.components.DataComponent
import com.yurivasques.github.myapplication.di.PerApplication
import com.yurivasques.github.myapplication.di.modules.ApplicationModule
import com.yurivasques.github.myapplication.di.modules.UseCaseModule
import dagger.BindsInstance
import dagger.Component

@PerApplication // Constraints this component to one-per-application or unscoped bindings.
@Component(
    dependencies = [(DataComponent::class)],
    modules = [(ApplicationModule::class), (UseCaseModule::class)]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            dataComponent: DataComponent
        ): ApplicationComponent
    }

    fun activityComponent(): ActivityComponent.Factory

}