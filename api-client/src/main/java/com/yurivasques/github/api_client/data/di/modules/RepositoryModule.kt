package com.yurivasques.github.api_client.data.di.modules

import com.yurivasques.github.api_client.data.di.providers.NetworkChecker
import com.yurivasques.github.api_client.data.extensions.api
import com.yurivasques.github.api_client.data.mapper.RepoMapper
import com.yurivasques.github.api_client.data.persistence.processor.RepoProcessor
import com.yurivasques.github.api_client.data.repository.RepoDataRepository
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

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

}