package com.yurivasques.github.api_client.data.mapper

import com.yurivasques.github.api_client.data.net.dto.RepoDTO
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.domain.model.Repo
import javax.inject.Inject

class RepoMapper
@Inject constructor(){
    fun transform(dto: RepoDTO, userName: String): Repo =
        Repo(dto.id, dto.name, dto.description, userName)

    fun transform(dtoCollection: Collection<RepoDTO>, userName: String): List<Repo> =
        dtoCollection.map { transform(it, userName) }

    fun transform(entity: RepoEntity): Repo =
        Repo(
            entity.id,
            entity.name,
            entity.description,
            entity.userName
        )
    fun transform(entityCollection: Collection<RepoEntity>): List<Repo> =
        entityCollection.map { transform(it) }

    fun transformToEntity(model: Repo): RepoEntity =
        RepoEntity(
            model.id,
            model.name,
            model.description,
            model.userName
        )

    fun transformToEntity(modelCollection: Collection<Repo>): List<RepoEntity> =
        modelCollection.map { transformToEntity(it) }
    //endregion
}