package com.yurivasques.github.api_client.data.mapper

import com.yurivasques.github.api_client.data.net.dto.TagDTO
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.domain.model.Tag
import javax.inject.Inject

class TagMapper
@Inject constructor(){
    fun transform(dto: TagDTO, repoId: Long): Tag =
        Tag(dto.id, dto.name, repoId)

    fun transform(dtoCollection: Collection<TagDTO>, repoId: Long): List<Tag> =
        dtoCollection.map { transform(it, repoId) }

    fun transform(entity: TagEntity): Tag =
        Tag(
            entity.id,
            entity.name,
            entity.repoId
        )
    fun transform(entityCollection: Collection<TagEntity>): List<Tag> =
        entityCollection.map { transform(it) }

    fun transformToEntity(model: Tag): TagEntity {
        return TagEntity(
            model.id,
            model.name,
            model.repoId
        )
    }

    fun transformToEntity(modelCollection: Collection<Tag>): List<TagEntity> =
        modelCollection.map { transformToEntity(it) }
}