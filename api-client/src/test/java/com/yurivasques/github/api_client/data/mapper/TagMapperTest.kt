package com.yurivasques.github.api_client.data.mapper

import com.yurivasques.github.api_client.data.net.dto.TagDTO
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.domain.model.Tag
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
        val repoId = 1L
        val dto = TagDTO("id1","tagName")
        val model = mapper.transform(dto, repoId)

        model.compareTo(dto, repoId)
    }

    @Test
    fun dtoCollectionToModelList() {
        val repoId = 1L
        val dtos = listOf(TagDTO("id1","tagName1"), TagDTO("id2","tagName2"))
        val models = mapper.transform(dtos, repoId)

        assertNotNull(models)
        assertTrue(models.size == 2)

        var dto = dtos[0]
        var model = models[0]

        model.compareTo(dto, repoId)

        dto = dtos[1]
        model = models[1]

        model.compareTo(dto, repoId)
    }

    private fun Tag.compareTo(dto: TagDTO, repoId: Long) {
        assertNotNull(this)
        assertEquals(dto.name, name)
        assertEquals(dto.id, id)
        assertEquals(repoId, this.repoId)
    }
    //endregion

    //region ENTITY to MODEL
    @Test
    fun entityToModel() {
        val entity =
            TagEntity("id1", "tagName", 1L)
        val model = mapper.transform(entity)

        model.compareTo(entity)
    }

    @Test
    fun entityCollectionToModelList() {
        val entities =
            listOf(TagEntity("id1", "tagName", 1L), TagEntity("id2", "tagName2", 2L))
        val models = mapper.transform(entities)

        assertNotNull(models)
        assertTrue(models.size == 2)

        var entity = entities[0]
        var model = models[0]

        model.compareTo(entity)

        entity = entities[0]
        model = models[0]

        model.compareTo(entity)
    }

    private fun Tag.compareTo(entity: TagEntity) {
        assertNotNull(this)
        assertEquals(entity.id, id)
        assertEquals(entity.name, name)
        assertEquals(entity.repoId, repoId)
    }
    //endregion

    //region MODEL to ENTITY
    @Test
    fun modelToEntity() {
        val model = Tag("id1", "tagName", 1L)
        val entity = mapper.transformToEntity(model)

        entity.compareTo(model)
    }

    @Test
    fun modelCollectionToEntityList() {
        val models =
            listOf(Tag("id1", "tagName", 1L), Tag("id2", "tagName2", 2L))
        val entities = mapper.transformToEntity(models)

        assertNotNull(entities)
        assertTrue(entities.size == 2)

        var model = models[0]
        var entity = entities[0]

        entity.compareTo(model)

        model = models[0]
        entity = entities[0]

        entity.compareTo(model)
    }

    private fun TagEntity.compareTo(model: Tag) {
        assertNotNull(this)
        assertEquals(model.id, id)
        assertEquals(model.name, name)
        assertEquals(model.repoId, repoId)
    }
    //endregion

}