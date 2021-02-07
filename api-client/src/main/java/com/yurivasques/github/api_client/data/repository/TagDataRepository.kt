package com.yurivasques.github.api_client.data.repository

import android.util.Log
import com.yurivasques.github.api_client.data.di.providers.NetworkChecker
import com.yurivasques.github.api_client.data.mapper.RepoMapper
import com.yurivasques.github.api_client.data.mapper.TagMapper
import com.yurivasques.github.api_client.data.net.GitHubApi
import com.yurivasques.github.api_client.data.persistence.processor.RepoProcessor
import com.yurivasques.github.api_client.data.persistence.processor.TagProcessor
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import com.yurivasques.github.api_client.domain.repository.TagRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.toObservable

class TagDataRepository (
    private val gitHubApi: GitHubApi,
    private val tagMapper: TagMapper,
    private val tagProcessor: TagProcessor,
    private val networkChecker: NetworkChecker
) : TagRepository {

    override val isConnected: Boolean
        get() = networkChecker.isConnected

    override fun getListTag(userName: String, repoName: String): Single<List<Tag>> =
        gitHubApi.getListTags(userName, repoName)
            .map { tagMapper.transform(it, userName, repoName) }

    override fun getCacheListTag(userName: String, repoName: String): Single<List<Tag>> =
        tagProcessor.getAll(userName, repoName)
            .map { tagMapper.transform(it) }

    override fun saveListTag(tagList: List<Tag>): Completable =
        tagList.toObservable().flatMapCompletable { saveTag(it) }

    override fun saveTag(tag: Tag): Completable =
            tagProcessor.get(tag.id)
                    .defaultIfEmpty(tagMapper.transformToEntity(tag))
                    .flatMapCompletable {
                        tagProcessor.insert(
                                tagMapper.transformToEntity(
                                        tag.copy()
                                )
                        )
                    }
}