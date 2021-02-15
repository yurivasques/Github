package com.github.yurivasques.github_library.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.yurivasques.github_library.data.persistence.entity.RepoEntity.Companion.REPO_TABLE

@Entity(tableName = REPO_TABLE)
class RepoEntity(
    @PrimaryKey @ColumnInfo(name = REPO_ID) val id: Long,
    @ColumnInfo(name = REPO_NAME) val name: String,
    @ColumnInfo(name = REPO_DESCRIPTION) val description: String?,
    @ColumnInfo(name = REPO_USER_NAME) val userName: String
) {

    companion object {
        const val REPO_TABLE = "repo"
        const val REPO_ID = "id"
        const val REPO_NAME = "name"
        const val REPO_DESCRIPTION = "description"
        const val REPO_USER_NAME = "user_name"
    }

}