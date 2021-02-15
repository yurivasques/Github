package com.github.yurivasques.github_library.data.di.modules

import com.github.yurivasques.github_library.data.di.providers.NetworkChecker
import com.github.yurivasques.github_library.data.extensions.api
import com.github.yurivasques.github_library.data.mapper.RepoMapper
import com.github.yurivasques.github_library.data.mapper.TagMapper
import com.github.yurivasques.github_library.data.persistence.processor.RepoProcessor
import com.github.yurivasques.github_library.data.persistence.processor.TagProcessor
import com.github.yurivasques.github_library.data.repository.RepoDataRepository
import com.github.yurivasques.github_library.data.repository.TagDataRepository
import com.github.yurivasques.github_library.domain.repository.RepoRepository
import com.github.yurivasques.github_library.domain.repository.TagRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Dagger module for Repository class.
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepoDataRepository(
        retrofit: Retrofit,
        repoMapper: RepoMapper,
        repoProcessor: RepoProcessor,
        networkChecker: NetworkChecker
    ): RepoRepository =
        RepoDataRepository(retrofit.api(), repoMapper, repoProcessor, networkChecker)

    @Provides
    @Singleton
    internal fun provideTagDataRepository(
        retrofit: Retrofit,
        tagMapper: TagMapper,
        TagProcessor: TagProcessor,
        networkChecker: NetworkChecker
    ): TagRepository =
        TagDataRepository(retrofit.api(), tagMapper, TagProcessor, networkChecker)

}