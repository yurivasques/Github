package com.yurivasques.github.api_client.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.yurivasques.github.api_client.data.di.providers.NetworkChecker
import com.yurivasques.github.api_client.data.mapper.RepoMapper
import com.yurivasques.github.api_client.data.mapper.TagMapper
import com.yurivasques.github.api_client.data.net.GitHubApi
import com.yurivasques.github.api_client.data.net.dto.RepoDTO
import com.yurivasques.github.api_client.data.net.dto.TagDTO
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.data.persistence.processor.RepoProcessor
import com.yurivasques.github.api_client.data.persistence.processor.TagProcessor
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
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
    private val tagId = 1L
    private val tagName = "tagName"
    private val repoName = "repoName"
    private val userName = "userName"

    @Before
    fun setup() {
        repository = TagDataRepository(api, mapper, processor, networkChecker)
    }

    @Test
    fun getListTag() {
        val tagList = mock<List<Tag>>()
        val tagListDTO = mock<List<TagDTO>>()

        whenever(api.getListTags(userName, repoName)).thenReturn(Single.just(tagListDTO))
        whenever(mapper.transform(tagListDTO, userName, repoName)).thenReturn(tagList)

        repository.getListTag(userName, repoName).test()
            .assertValueCount(1)
            .assertResult(tagList)
    }

    @Test
    fun getCacheListTag() {
        val tagList = mock<List<Tag>>()
        val tagListEntities = mock<List<TagEntity>>()

        whenever(processor.getAll(userName, repoName)).thenReturn(Single.just(tagListEntities))
        whenever(mapper.transform(tagListEntities)).thenReturn(tagList)

        repository.getCacheListTag(userName, repoName).test()
            .assertValueCount(1)
            .assertResult(tagList)
    }

    @Test
    fun saveListTag() {
        val tag = Tag(1, "tagName", "repoName", "userName")
        val tagEntity = TagEntity(
            tag.id,
            tag.name,
            tag.repoName,
            tag.userName
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
        val tag = Tag(1, "tagName", "repoName",  "userName")
        val tagEntity = TagEntity(
            tag.id,
            tag.name,
            tag.repoName,
            tag.userName
        )

        whenever(processor.get(tag.id)).thenReturn(Maybe.just(tagEntity))
        whenever(mapper.transformToEntity(tag)).thenReturn(tagEntity)
        whenever(processor.insert(tagEntity)).thenReturn(Completable.complete())

        repository.saveTag(tag).test()
            .assertResult()
    }
}
