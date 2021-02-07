package com.yurivasques.github.api_client.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yurivasques.github.api_client.data.persistence.dao.RepoDao
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity

@Database(entities = [(RepoEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

}