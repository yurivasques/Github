package com.yurivasques.github.api_client.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.yurivasques.github.api_client.data.di.providers.NetworkChecker
import com.yurivasques.github.api_client.data.mapper.RepoMapper
import com.yurivasques.github.api_client.data.net.GitHubApi
import com.yurivasques.github.api_client.data.net.dto.RepoDTO
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.processor.RepoProcessor
import com.yurivasques.github.api_client.domain.model.Repo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepoDataRepositoryTest {

    @Mock
    private lateinit var api: GitHubApi

    @Mock
    private lateinit var mapper: RepoMapper

    @Mock
    private lateinit var processor: RepoProcessor

    @Mock
    private lateinit var networkChecker: NetworkChecker

    private lateinit var repository: RepoDataRepository

    // Parameters
    private val repoId = 1L
    private val repoName = "repoName"
    private val userName = "userName"

    @Before
    fun setup() {
        repository = RepoDataRepository(api, mapper, processor, networkChecker)
    }

    @Test
    fun getListRepo() {
        val repoList = mock<List<Repo>>()
        val repoListDTO = mock<List<RepoDTO>>()

        whenever(api.getListRepos(userName)).thenReturn(Single.just(repoListDTO))
        whenever(mapper.transform(repoListDTO, userName)).thenReturn(repoList)

        repository.getListRepo(userName).test()
            .assertValueCount(1)
            .assertResult(repoList)
    }

    @Test
    fun getCacheListRepo() {
        val repoList = mock<List<Repo>>()
        val repoListEntities = mock<List<RepoEntity>>()

        whenever(processor.getAll(userName)).thenReturn(Single.just(repoListEntities))
        whenever(mapper.transform(repoListEntities)).thenReturn(repoList)

        repository.getCacheListRepo(userName).test()
            .assertValueCount(1)
            .assertResult(repoList)
    }

    @Test
    fun saveListRepo() {
        val repo = Repo(1, "repoName", "description", "userName")
        val repoEntity = RepoEntity(
            repo.id,
            repo.name,
            repo.description,
            repo.userName
        )
        val repoList = listOf(repo)

        whenever(processor.get(repo.id)).thenReturn(Maybe.just(repoEntity))
        whenever(mapper.transformToEntity(repo)).thenReturn(repoEntity)
        whenever(processor.insert(repoEntity)).thenReturn(Completable.complete())

        repository.saveListRepo(repoList).test()
            .assertResult()
    }
    //endregion

    @Test
    fun saveRepo() {
        val repo = Repo(1, "repoName", "description",  "userName")
        val repoEntity = RepoEntity(
            repo.id,
            repo.name,
            repo.description,
            repo.userName
        )

        whenever(processor.get(repo.id)).thenReturn(Maybe.just(repoEntity))
        whenever(mapper.transformToEntity(repo)).thenReturn(repoEntity)
        whenever(processor.insert(repoEntity)).thenReturn(Completable.complete())

        repository.saveRepo(repo).test()
            .assertResult()
    }
}
