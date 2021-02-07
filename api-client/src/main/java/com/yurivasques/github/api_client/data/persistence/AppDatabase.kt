package com.yurivasques.github.api_client.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yurivasques.github.api_client.data.persistence.dao.RepoDao
import com.yurivasques.github.api_client.data.persistence.dao.TagDao
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity

@Database(entities = [(RepoEntity::class), (TagEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    abstract fun tagDao(): TagDao

}