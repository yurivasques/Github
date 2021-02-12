package com.yurivasques.github.api_client.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.yurivasques.github.api_client.data.persistence.dao.base.BaseDao
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity.Companion.REPO_ID
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity.Companion.REPO_TABLE
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity.Companion.REPO_USER_NAME

/**
 * Repository data access object
 */
@Dao
abstract class RepoDao : BaseDao<RepoEntity> {

    /**
     * Select a repository by the id
     * @param id The repo id
     * @return instance of [RepoEntity]
     */
    @Query("SELECT * FROM $REPO_TABLE WHERE $REPO_ID = :id")
    abstract fun get(id: Long): RepoEntity?

    /**
     * Select all repositories by the userName
     * @param userName repositories owner username
     * @return A list of [RepoEntity] of all the repos in the table for a user
     */
    @Query("SELECT * FROM $REPO_TABLE WHERE $REPO_USER_NAME = :userName")
    abstract fun getAll(userName: String): List<RepoEntity>

    @Transaction
    open fun insertAndDeleteInTransaction(newRepo: RepoEntity, oldRepo: RepoEntity) {
        insert(newRepo)
        delete(oldRepo)
    }

}