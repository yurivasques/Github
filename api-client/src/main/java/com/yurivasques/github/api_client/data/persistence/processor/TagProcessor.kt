package com.yurivasques.github.api_client.data.persistence.processor

import com.yurivasques.github.api_client.data.persistence.dao.RepoDao
import com.yurivasques.github.api_client.data.persistence.dao.TagDao
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.data.persistence.processor.base.BaseProcessor
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagProcessor
@Inject internal constructor(private val dao: TagDao) : BaseProcessor<TagEntity>(dao) {

    fun get(id: Long): Maybe<TagEntity> =
        Maybe.fromCallable { dao.get(id) }

    fun getAll(userName: String, repoName: String): Single<List<TagEntity>> =
        Single.fromCallable { dao.getAll(userName, repoName) }

}