package com.github.yurivasques.github_library.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.github.yurivasques.github_library.data.persistence.dao.base.BaseDao
import com.github.yurivasques.github_library.data.persistence.entity.TagEntity
import com.github.yurivasques.github_library.data.persistence.entity.TagEntity.Companion.TAG_ID
import com.github.yurivasques.github_library.data.persistence.entity.TagEntity.Companion.TAG_REPO_ID
import com.github.yurivasques.github_library.data.persistence.entity.TagEntity.Companion.TAG_TABLE

/**
 * Tag data access object
 */
@Dao
abstract class TagDao : BaseDao<TagEntity> {

    /**
     * Select a tag by the id
     * @param id The tag id
     * @return instance of [TagEntity]
     */
    @Query("SELECT * FROM $TAG_TABLE WHERE $TAG_ID = :id")
    abstract fun get(id: String): TagEntity?

    /**
     * Select all tags by the repository id
     * @param repoId repository id
     * @return A list of [TagEntity] of all the tags in the table for a repository
     */
    @Query("SELECT * FROM $TAG_TABLE WHERE $TAG_REPO_ID = :repoId")
    abstract fun getAll(repoId: Long): List<TagEntity>

    @Transaction
    open fun insertAndDeleteInTransaction(newTag: TagEntity, oldTag: TagEntity) {
        insert(newTag)
        delete(oldTag)
    }

}