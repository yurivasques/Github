package com.yurivasques.github.api_client.data.mapper

import com.yurivasques.github.api_client.data.net.dto.RepoDTO
import com.yurivasques.github.api_client.data.net.dto.TagDTO
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import javax.inject.Inject

/**
 * Mapper class used to transform [TagDTO] or [TagEntity] (in the data layer) to [Tag]
 * in the domain layer and vice versa.
 */
class TagMapper
@Inject constructor(){
    //region DTO to MODEL
    /**
     * Transform a [TagDTO] into an [Tag].
     * @param dto Object to be transformed.
     * @param repoId Foreign key
     * @return [Tag] if valid [TagDTO]
     */
    fun transform(dto: TagDTO, repoId: Long): Tag =
        Tag(dto.id, dto.name, repoId)

    /**
     * Transform a Collection of [TagDTO] into a List of [Tag].
     * @param dtoCollection Object Collection to be transformed.
     * @param repoId Foreign key
     * @return list of [Tag]
     */
    fun transform(dtoCollection: Collection<TagDTO>, repoId: Long): List<Tag> =
        dtoCollection.map { transform(it, repoId) }
    //endregion

    //region ENTITY to MODEL
    /**
     * Transform a [TagEntity] into an [Tag].
     * @param entity Object to be transformed.
     * @return [Tag] if valid [TagEntity] otherwise null.
     */
    fun transform(entity: TagEntity): Tag =
        Tag(
            entity.id,
            entity.name,
            entity.repoId
        )

    /**
     * Transform a Collection of [TagEntity] into a List of [Tag].
     * @param entityCollection Object Collection to be transformed.
     * @return list of [Tag]
     */
    fun transform(entityCollection: Collection<TagEntity>): List<Tag> =
        entityCollection.map { transform(it) }
    //endregion

    //region MODEL to ENTITY
    /**
     * Transform a [Tag] into an [TagEntity].
     * @param model Object to be transformed.
     * @return [Tag] if valid [TagEntity] otherwise null.
     */
    fun transformToEntity(model: Tag): TagEntity {
        return TagEntity(
            model.id,
            model.name,
            model.repoId
        )
    }

    /**
     * Transform a Collection of [Tag] into a List of [TagEntity].
     * @param modelCollection Object Collection to be transformed.
     * @return list of [TagEntity]
     */
    fun transformToEntity(modelCollection: Collection<Tag>): List<TagEntity> =
        modelCollection.map { transformToEntity(it) }
    //endregion
}