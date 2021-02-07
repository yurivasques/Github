package com.yurivasques.github.api_client.data.mapper

import com.yurivasques.github.api_client.data.net.dto.RepoDTO
import com.yurivasques.github.api_client.data.net.dto.TagDTO
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TagMapperTest {

    private lateinit var mapper: TagMapper

    @Before
    fun setup() {
        mapper = TagMapper()
    }

    //region DTO to MODEL
    @Test
    fun dtoToModel() {
        val userName = "userName"
        val repoName = "repoName"
        val dto = TagDTO(1, "tagName")
        val model = mapper.transform(dto, userName, repoName)

        model.compareTo(dto)
    }

    @Test
    fun dtoCollectionToModelList() {
        val userName = "userName"
        val repoName = "repoName"
        val dtos = listOf(TagDTO(1, "tagName"))
        val models = mapper.transform(dtos, userName, repoName)

        assertNotNull(models)
        assertTrue(models.size == 1)

        val dto = dtos[0]
        val model = models[0]

        model.compareTo(dto)
    }

    private fun Tag.compareTo(dto: TagDTO) {
        assertNotNull(this)
        assertEquals(dto.id, id)
        assertEquals(dto.name, name)
        assertEquals(userName, userName)
        assertEquals(repoName, repoName)
    }
    //endregion

    //region ENTITY to MODEL
    @Test
    fun entityToModel() {
        val entity =
            TagEntity(1, "tagName", "repoName", "userName")
        val model = mapper.transform(entity)

        model.compareTo(entity)
    }

    @Test
    fun entityCollectionToModelList() {
        val entities =
            listOf(TagEntity(1, "tagName", "repoName", "userName"))
        val models = mapper.transform(entities)

        assertNotNull(models)
        assertTrue(models.size == 1)

        val entity = entities[0]
        val model = models[0]

        model.compareTo(entity)
    }

    private fun Tag.compareTo(entity: TagEntity) {
        assertNotNull(this)
        assertEquals(entity.id, id)
        assertEquals(entity.name, name)
        assertEquals(entity.repoName, repoName)
        assertEquals(entity.userName, userName)
    }
    //endregion

    //region MODEL to ENTITY
    @Test
    fun modelToEntity() {
        val model = Tag(1, "tagName", "repoName", "userName")
        val entity = mapper.transformToEntity(model)

        entity.compareTo(model)
    }

    @Test
    fun modelCollectionToEntityList() {
        val models =
            listOf(Tag(1, "repoName", "description", "http://myrepo.com"))
        val entities = mapper.transformToEntity(models)

        assertNotNull(entities)
        assertTrue(entities.size == 1)

        val model = models[0]
        val entity = entities[0]

        entity.compareTo(model)
    }

    private fun TagEntity.compareTo(model: Tag) {
        assertNotNull(this)
        assertEquals(model.id, id)
        assertEquals(model.name, name)
        assertEquals(model.repoName, repoName)
        assertEquals(model.userName, userName)
    }
    //endregion

}