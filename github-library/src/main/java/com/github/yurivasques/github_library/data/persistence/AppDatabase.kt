package com.github.yurivasques.github_library.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.yurivasques.github_library.data.persistence.dao.RepoDao
import com.github.yurivasques.github_library.data.persistence.dao.TagDao
import com.github.yurivasques.github_library.data.persistence.entity.RepoEntity
import com.github.yurivasques.github_library.data.persistence.entity.TagEntity

@Database(entities = [(RepoEntity::class), (TagEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    abstract fun tagDao(): TagDao

}