package com.github.yurivasques.github_library.data.persistence.processor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.github.yurivasques.github_library.data.persistence.dao.RepoDao
import com.github.yurivasques.github_library.data.persistence.entity.RepoEntity
import com.github.yurivasques.github_library.domain.exception.PersistenceException
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepoProcessorTest {

    @Mock
    private lateinit var dao: RepoDao

    private lateinit var processor: RepoProcessor

    @Before
    fun setup() {
        processor = RepoProcessor(dao)
    }

    @Test
    fun insertRepo() {
        val rowID = 1L
        val entity = mock<RepoEntity>()

        whenever(dao.insert(entity)).thenReturn(rowID)

        processor.insert(entity).test()
            .assertResult()
    }

    @Test
    fun insertRepoFail() {
        val entity = mock<RepoEntity>()

        whenever(dao.insert(entity)).thenReturn(0L)

        processor.insert(entity).test()
            .assertError(PersistenceException::class.java)
    }

    @Test
    fun deleteRepo() {
        val nbEntityDeleted = 1
        val entity = mock<RepoEntity>()

        whenever(dao.delete(entity)).thenReturn(nbEntityDeleted)

        processor.delete(entity).test()
            .assertResult()
    }

    @Test
    fun deleteRepoFail() {
        val entity = mock<RepoEntity>()

        whenever(dao.delete(entity)).thenReturn(0)

        processor.delete(entity).test()
            .assertError(PersistenceException::class.java)
    }

    @Test
    fun getRepo() {
        val id = 1L
        val entity = mock<RepoEntity>()

        whenever(dao.get(id)).thenReturn(entity)

        processor.get(id).test()
            .assertValueCount(1)
            .assertResult(entity)
    }

    @Test
    fun getRepoEmpty() {
        val id = 1L

        whenever(dao.get(id)).thenReturn(null)

        processor.get(id).test()
            .assertResult()
    }

    @Test
    fun getListRepo() {
        val userName = "userName"
        val repoList = mock<List<RepoEntity>>()

        whenever(dao.getAll(userName)).thenReturn(repoList)

        processor.getAll(userName).test()
            .assertValueCount(1)
            .assertResult(repoList)
    }

}