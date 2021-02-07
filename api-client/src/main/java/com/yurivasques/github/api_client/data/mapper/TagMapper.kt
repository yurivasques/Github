package com.yurivasques.github.api_client.data.mapper

import com.yurivasques.github.api_client.data.net.dto.RepoDTO
import com.yurivasques.github.api_client.data.net.dto.TagDTO
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import javax.inject.Inject

class TagMapper
@Inject constructor(){
    fun transform(dto: TagDTO, userName: String, repoName: String): Tag =
        Tag(dto.id, dto.name, userName, repoName)

    fun transform(dtoCollection: Collection<TagDTO>, userName: String, repoName: String): List<Tag> =
        dtoCollection.map { transform(it, userName, repoName) }

    fun transform(entity: TagEntity): Tag =
        Tag(
            entity.id,
            entity.name,
            entity.repoName,
            entity.userName
        )
    fun transform(entityCollection: Collection<TagEntity>): List<Tag> =
        entityCollection.map { transform(it) }

    fun transformToEntity(model: Tag): TagEntity =
        TagEntity(
            model.id,
            model.name,
            model.repoName,
            model.userName
        )

    fun transformToEntity(modelCollection: Collection<Tag>): List<TagEntity> =
        modelCollection.map { transformToEntity(it) }
    //endregion
}