package com.yurivasques.github.api_client.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity.Companion.TAG_TABLE

@Entity(tableName = TAG_TABLE)
class TagEntity(
    @PrimaryKey @ColumnInfo(name = TAG_ID) val id: Long,
    @ColumnInfo(name = TAG_NAME) val name: String,
    @ColumnInfo(name = TAG_REPO_NAME) val repoName: String,
    @ColumnInfo(name = TAG_USER_NAME) val userName: String
) {

    companion object {
        const val TAG_TABLE = "tag"
        const val TAG_ID = "id"
        const val TAG_NAME = "name"
        const val TAG_REPO_NAME = "repo_name"
        const val TAG_USER_NAME = "user_name"
    }

}