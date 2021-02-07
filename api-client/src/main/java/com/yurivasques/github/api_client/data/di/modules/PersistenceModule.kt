package com.yurivasques.github.api_client.data.di.modules

import android.content.Context
import com.yurivasques.github.api_client.data.persistence.AppDatabase
import com.yurivasques.github.api_client.data.persistence.DatabaseFactory
import com.yurivasques.github.api_client.data.persistence.dao.RepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
        DatabaseFactory.getDatabase(context)

    @Provides
    @Singleton
    internal fun provideRepoDao(appDatabase: AppDatabase): RepoDao = appDatabase.repoDao()

}