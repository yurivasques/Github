package com.yurivasques.github.api_client.data.repository

import android.util.Log
import com.yurivasques.github.api_client.data.di.providers.NetworkChecker
import com.yurivasques.github.api_client.data.mapper.RepoMapper
import com.yurivasques.github.api_client.data.net.GitHubApi
import com.yurivasques.github.api_client.data.persistence.processor.RepoProcessor
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.toObservable

class RepoDataRepository (
    private val gitHubApi: GitHubApi,
    private val repoMapper: RepoMapper,
    private val repoProcessor: RepoProcessor,
    private val networkChecker: NetworkChecker
) : RepoRepository {

    override val isConnected: Boolean
        get() = networkChecker.isConnected

    override fun getListRepo(userName: String): Single<List<Repo>> =
        gitHubApi.getListRepos(userName)
            .map { repoMapper.transform(it, userName) }

    override fun getCacheListRepo(userName: String): Single<List<Repo>> =
        repoProcessor.getAll(userName)
            .map { repoMapper.transform(it) }

    override fun saveListRepo(repoList: List<Repo>): Completable =
        repoList.toObservable().flatMapCompletable { saveRepo(it) }

    override fun saveRepo(repo: Repo): Completable =
            repoProcessor.get(repo.id)
                    .defaultIfEmpty(repoMapper.transformToEntity(repo))
                    .flatMapCompletable {
                        repoProcessor.insert(
                                repoMapper.transformToEntity(
                                        repo.copy()
                                )
                        )
                    }
}