package com.yurivasques.github.api_client.data.persistence.dao.base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BaseDao<T> {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T): Long

   @Delete
    fun delete(entity: T): Int

}