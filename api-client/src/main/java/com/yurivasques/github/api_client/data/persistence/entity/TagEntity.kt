package com.yurivasques.github.api_client.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity.Companion.TAG_TABLE

@Entity(tableName = TAG_TABLE)
class TagEntity(
    @PrimaryKey @ColumnInfo(name = TAG_ID) val id: String,
    @ColumnInfo(name = TAG_NAME) val name: String,
    @ColumnInfo(name = TAG_REPO_ID) val repoId: Long
) {

    companion object {
        const val TAG_TABLE = "repoTag"
        const val TAG_ID = "id"
        const val TAG_REPO_ID = "repo_id"
        const val TAG_NAME = "name"
    }

}