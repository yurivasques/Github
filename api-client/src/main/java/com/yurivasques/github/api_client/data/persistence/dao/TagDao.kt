package com.yurivasques.github.api_client.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.yurivasques.github.api_client.data.persistence.dao.base.BaseDao
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity.Companion.TAG_ID
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity.Companion.TAG_REPO_ID
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity.Companion.TAG_TABLE

@Dao
abstract class TagDao : BaseDao<TagEntity> {

    @Query("SELECT * FROM $TAG_TABLE WHERE $TAG_ID = :id")
    abstract fun get(id: String): TagEntity?

    @Query("SELECT * FROM $TAG_TABLE WHERE $TAG_REPO_ID = :repoId")
    abstract fun getAll(repoId: Long): List<TagEntity>

    @Transaction
    open fun insertAndDeleteInTransaction(newTag: TagEntity, oldTag: TagEntity) {
        insert(newTag)
        delete(oldTag)
    }

}