package com.yurivasques.github.api_client.data.persistence.processor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.yurivasques.github.api_client.data.persistence.dao.RepoDao
import com.yurivasques.github.api_client.data.persistence.dao.TagDao
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.domain.exception.PersistenceException
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TagProcessorTest {

    @Mock
    private lateinit var dao: TagDao

    private lateinit var processor: TagProcessor

    @Before
    fun setup() {
        processor = TagProcessor(dao)
    }

    @Test
    fun insertTag() {
        val rowID = 1L
        val entity = mock<TagEntity>()

        whenever(dao.insert(entity)).thenReturn(rowID)

        processor.insert(entity).test()
            .assertResult()
    }

    @Test
    fun insertTagFail() {
        val entity = mock<TagEntity>()

        whenever(dao.insert(entity)).thenReturn(0L)

        processor.insert(entity).test()
            .assertError(PersistenceException::class.java)
    }

    @Test
    fun deleteTag() {
        val nbEntityDeleted = 1
        val entity = mock<TagEntity>()

        whenever(dao.delete(entity)).thenReturn(nbEntityDeleted)

        processor.delete(entity).test()
            .assertResult()
    }

    @Test
    fun deleteTagFail() {
        val entity = mock<TagEntity>()

        whenever(dao.delete(entity)).thenReturn(0)

        processor.delete(entity).test()
            .assertError(PersistenceException::class.java)
    }

    @Test
    fun getTag() {
        val id = 1L
        val entity = mock<TagEntity>()

        whenever(dao.get(id)).thenReturn(entity)

        processor.get(id).test()
            .assertValueCount(1)
            .assertResult(entity)
    }

    @Test
    fun getTagEmpty() {
        val id = 1L

        whenever(dao.get(id)).thenReturn(null)

        processor.get(id).test()
            .assertResult()
    }

    @Test
    fun getListTag() {
        val userName = "userName"
        val repoName = "repoName"
        val tagList = mock<List<TagEntity>>()

        whenever(dao.getAll(userName, repoName)).thenReturn(tagList)

        processor.getAll(userName, repoName).test()
            .assertValueCount(1)
            .assertResult(tagList)
    }

}