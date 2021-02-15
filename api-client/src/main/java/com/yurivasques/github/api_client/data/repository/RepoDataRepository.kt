package com.yurivasques.github.api_client.data.repository

import android.annotation.SuppressLint
import com.yurivasques.github.api_client.data.di.providers.NetworkChecker
import com.yurivasques.github.api_client.data.mapper.RepoMapper
import com.yurivasques.github.api_client.data.net.GitHubApi
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.processor.RepoProcessor
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.toObservable

/**
 * [RepoRepository] for retrieving repo data.
 * This the class for external access through UseCases
 */
class RepoDataRepository (
    private val gitHubApi: GitHubApi,
    private val repoMapper: RepoMapper,
    private val repoProcessor: RepoProcessor,
    private val networkChecker: NetworkChecker
) : RepoRepository {

    /**
     * Get network connection status
     * @return network connection status
     */
    override val isConnected: Boolean
        get() = networkChecker.isConnected

    //region LIST REPO
    /**
     * Request user's repositories from GitHub API using username as parameter
     * @param userName repositories owner's username to search
     * @return list of [Repo]
     */
    override fun getListRepo(userName: String): Single<List<Repo>> =
        gitHubApi.getListRepos(userName)
            .map { repoMapper.transform(it, userName) }

    /**
     * Get user's repositories from local database using username as parameter
     * @param userName repositories owner's username to search
     * @return list of [Repo]
     */
    override fun getCacheListRepo(userName: String): Single<List<Repo>> =
        repoProcessor.getAll(userName)
            .map { repoMapper.transform(it) }

    /**
     * Save user's repositories to local database
     * @param repoList list of [Repo]
     * @return Completable of the save process
     */
    override fun saveListRepo(repoList: List<Repo>): Completable =
        repoList.toObservable().flatMapCompletable { saveRepo(it) }
    //endregion

    //region REPO
    /**
     * Save user's repository to local database
     * @param repo instance of [Repo]
     * @return Completable of the save process
     */
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
    //endregion

}