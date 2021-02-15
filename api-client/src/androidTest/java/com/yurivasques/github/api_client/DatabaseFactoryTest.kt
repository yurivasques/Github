package com.yurivasques.github.api_client

import android.content.Context
import androidx.room.Room
import com.yurivasques.github.api_client.data.persistence.AppDatabase

object DatabaseFactoryTest {

    fun getDatabase(context: Context): AppDatabase =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

}