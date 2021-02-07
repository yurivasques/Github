package com.yurivasques.github.api_client.data.di.components

import android.content.Context
import com.yurivasques.github.api_client.data.di.modules.NetModule
import com.yurivasques.github.api_client.data.di.modules.PersistenceModule
import com.yurivasques.github.api_client.data.di.modules.RepositoryModule
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetModule::class), (PersistenceModule::class), (RepositoryModule::class)])
interface DataComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    // Exposed to sub-graphs
    fun provideRepoRepository(): RepoRepository

}