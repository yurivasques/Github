package com.yurivasques.github.api_library

import android.content.Context
import androidx.room.Room
import com.github.yurivasques.github_library.data.persistence.AppDatabase

object DatabaseFactoryTest {

    fun getDatabase(context: Context): AppDatabase =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

}