package com.yurivasques.github.api_client.data.di.modules

import android.content.Context
import com.yurivasques.github.api_client.data.persistence.AppDatabase
import com.yurivasques.github.api_client.data.persistence.DatabaseFactory
import com.yurivasques.github.api_client.data.persistence.dao.RepoDao
import com.yurivasques.github.api_client.data.persistence.dao.TagDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module for Persistence class
 */
@Module
class PersistenceModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
        DatabaseFactory.getDatabase(context)

    @Provides
    @Singleton
    internal fun provideRepoDao(appDatabase: AppDatabase): RepoDao = appDatabase.repoDao()

    @Provides
    @Singleton
    internal fun provideTagDao(appDatabase: AppDatabase): TagDao = appDatabase.tagDao()

}