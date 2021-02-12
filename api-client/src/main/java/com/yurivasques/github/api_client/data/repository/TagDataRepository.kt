package com.yurivasques.github.api_client.data.repository

import android.annotation.SuppressLint
import com.yurivasques.github.api_client.data.di.providers.NetworkChecker
import com.yurivasques.github.api_client.data.mapper.TagMapper
import com.yurivasques.github.api_client.data.net.GitHubApi
import com.yurivasques.github.api_client.data.persistence.processor.TagProcessor
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import com.yurivasques.github.api_client.domain.repository.TagRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.toObservable

/**
 * [TagRepository] for retrieving repo data.
 * This the class for external access through UseCases
 */
class TagDataRepository (
    private val gitHubApi: GitHubApi,
    private val tagMapper: TagMapper,
    private val tagProcessor: TagProcessor,
    private val networkChecker: NetworkChecker
) : TagRepository {

    /**
     * Get network connection status
     * @return network connection status
     */
    override val isConnected: Boolean
        get() = networkChecker.isConnected

    //region LIST REPO
    /**
     * Request repository's tags from GitHub API using username and repository name as parameter
     * @param userName repository owner's username to search
     * @param repoName repository name to search
     * @param repoId repository id persist into the [Tag] object
     * @return list of [Tag]
     */
    override fun getListTag(userName: String, repoName: String, repoId: Long): Single<List<Tag>> =
        gitHubApi.getListTags(userName, repoName)
            .map { tagMapper.transform(it, repoId) }

    /**
     * Get repository's tags from GitHub API using repository id as parameter
     * @param repoId repository id to search
     * @return list of [Tag]
     */
    override fun getCacheListTag(repoId: Long): Single<List<Tag>> {
        return tagProcessor.getAll(repoId)
            .map { tagMapper.transform(it) }
    }

    /**
     * Save repository's tags to local database
     * @param tagList list of [Tag]
     * @return Completable of the save process
     */
    override fun saveListTag(tagList: List<Tag>): Completable =
        tagList.toObservable().flatMapCompletable { saveTag(it) }
    //endregion

    //region REPO
    /**
     * Save user's repository to local database
     * @param tag instance of [Tag]
     * @return Completable of the save process
     */
    override fun saveTag(tag: Tag): Completable {
        return tagProcessor.get(tag.id)
            .defaultIfEmpty(tagMapper.transformToEntity(tag))
            .flatMapCompletable {
                tagProcessor.insert(
                    tagMapper.transformToEntity(
                        tag.copy()
                    )
                )
            }
    }
    //endregion

}