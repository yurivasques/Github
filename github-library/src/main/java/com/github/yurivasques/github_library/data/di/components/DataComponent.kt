package com.github.yurivasques.github_library.data.di.components

import android.content.Context
import com.github.yurivasques.github_library.data.di.modules.NetModule
import com.github.yurivasques.github_library.data.di.modules.PersistenceModule
import com.github.yurivasques.github_library.data.di.modules.RepositoryModule
import com.github.yurivasques.github_library.domain.repository.RepoRepository
import com.github.yurivasques.github_library.domain.repository.TagRepository
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

    fun provideTagRepository(): TagRepository

}