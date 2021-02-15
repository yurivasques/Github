package com.github.yurivasques.github_library.data.mapper

import com.github.yurivasques.github_library.data.net.dto.RepoDTO
import com.github.yurivasques.github_library.data.persistence.entity.RepoEntity
import com.github.yurivasques.github_library.domain.model.Repo
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class RepoMapperTest {

    private lateinit var mapper: RepoMapper

    @Before
    fun setup() {
        mapper = RepoMapper()
    }

    //region DTO to MODEL
    @Test
    fun dtoToModel() {
        val userName = "userName"
        val dto = RepoDTO(1, "repoName", "description")
        val model = mapper.transform(dto, userName)

        model.compareTo(dto)
    }

    @Test
    fun dtoCollectionToModelList() {
        val userName = "userName"
        val dtos = listOf(RepoDTO(1, "repoName", "description"))
        val models = mapper.transform(dtos, userName)

        Assert.assertNotNull(models)
        Assert.assertTrue(models.size == 1)

        val dto = dtos[0]
        val model = models[0]

        model.compareTo(dto)
    }

    private fun Repo.compareTo(dto: RepoDTO) {
        assertNotNull(this)
        assertEquals(dto.id, id)
        assertEquals(dto.name, name)
        assertEquals(dto.description, description)
        assertEquals(userName, userName)
    }
    //endregion

    //region ENTITY to MODEL
    @Test
    fun entityToModel() {
        val entity =
            RepoEntity(1, "repoName", "description", "userName")
        val model = mapper.transform(entity)

        model.compareTo(entity)
    }

    @Test
    fun entityCollectionToModelList() {
        val entities =
            listOf(RepoEntity(1, "repoName", "description", "userName"))
        val models = mapper.transform(entities)

        Assert.assertNotNull(models)
        Assert.assertTrue(models.size == 1)

        val entity = entities[0]
        val model = models[0]

        model.compareTo(entity)
    }

    private fun Repo.compareTo(entity: RepoEntity) {
        assertNotNull(this)
        assertEquals(entity.id, id)
        assertEquals(entity.name, name)
        assertEquals(entity.description, description)
        assertEquals(entity.userName, userName)
    }
    //endregion

    //region MODEL to ENTITY
    @Test
    fun modelToEntity() {
        val model = Repo(1, "repoName", "description", "http://myrepo.com")
        val entity = mapper.transformToEntity(model)

        entity.compareTo(model)
    }

    @Test
    fun modelCollectionToEntityList() {
        val models =
            listOf(Repo(1, "repoName", "description", "http://myrepo.com"))
        val entities = mapper.transformToEntity(models)

        Assert.assertNotNull(entities)
        Assert.assertTrue(entities.size == 1)

        val model = models[0]
        val entity = entities[0]

        entity.compareTo(model)
    }

    private fun RepoEntity.compareTo(model: Repo) {
        assertNotNull(this)
        assertEquals(model.id, id)
        assertEquals(model.name, name)
        assertEquals(model.description, description)
        assertEquals(model.userName, userName)
    }
    //endregion

}