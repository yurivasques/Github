package com.github.yurivasques.github_library.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.github.yurivasques.github_library.data.di.providers.NetworkChecker
import com.github.yurivasques.github_library.data.mapper.TagMapper
import com.github.yurivasques.github_library.data.net.GitHubApi
import com.github.yurivasques.github_library.data.net.dto.TagDTO
import com.github.yurivasques.github_library.data.persistence.entity.TagEntity
import com.github.yurivasques.github_library.data.persistence.processor.TagProcessor
import com.github.yurivasques.github_library.domain.model.Tag
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TagDataRepositoryTest {

    @Mock
    private lateinit var api: GitHubApi

    @Mock
    private lateinit var mapper: TagMapper

    @Mock
    private lateinit var processor: TagProcessor

    @Mock
    private lateinit var networkChecker: NetworkChecker

    private lateinit var repository: TagDataRepository

    // Parameters
    private val tagId = "id1"
    private val tagName = "tagName"
    private val repoName = "repoName"
    private val userName = "userName"
    private val repoId = 1L

    @Before
    fun setup() {
        repository = TagDataRepository(api, mapper, processor, networkChecker)
    }

    @Test
    fun getListTag() {
        val tagList = mock<List<Tag>>()
        val tagListDTO = mock<List<TagDTO>>()

        whenever(api.getListTags(userName, repoName)).thenReturn(Single.just(tagListDTO))
        whenever(mapper.transform(tagListDTO, repoId)).thenReturn(tagList)

        repository.getListTag(userName, repoName, repoId).test()
            .assertValueCount(1)
            .assertResult(tagList)
    }

    @Test
    fun getCacheListTag() {
        val tagList = mock<List<Tag>>()
        val tagListEntities = mock<List<TagEntity>>()

        whenever(processor.getAll(repoId)).thenReturn(Single.just(tagListEntities))
        whenever(mapper.transform(tagListEntities)).thenReturn(tagList)

        repository.getCacheListTag(repoId).test()
            .assertValueCount(1)
            .assertResult(tagList)
    }

    @Test
    fun saveListTag() {
        val tag = Tag(tagId, tagName, repoId)
        val tagEntity = TagEntity(
            tag.id,
            tag.name,
            tag.repoId
        )
        val tagList = listOf(tag)

        whenever(processor.get(tag.id)).thenReturn(Maybe.just(tagEntity))
        whenever(mapper.transformToEntity(tag)).thenReturn(tagEntity)
        whenever(processor.insert(tagEntity)).thenReturn(Completable.complete())

        repository.saveListTag(tagList).test()
            .assertResult()
    }
    //endregion

    @Test
    fun saveTag() {
        val tag = Tag(tagId, tagName, repoId)
        val tagEntity = TagEntity(
            tag.id,
            tag.name,
            tag.repoId
        )

        whenever(processor.get(tag.id)).thenReturn(Maybe.just(tagEntity))
        whenever(mapper.transformToEntity(tag)).thenReturn(tagEntity)
        whenever(processor.insert(tagEntity)).thenReturn(Completable.complete())

        repository.saveTag(tag).test()
            .assertResult()
    }
}
