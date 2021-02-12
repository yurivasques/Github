package com.yurivasques.github.api_client.data.persistence.processor

import com.yurivasques.github.api_client.data.persistence.dao.RepoDao
import com.yurivasques.github.api_client.data.persistence.entity.RepoEntity
import com.yurivasques.github.api_client.data.persistence.processor.base.BaseProcessor
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository processor for data access object
 * This is the access class to RepoDao operations
 */
@Singleton
class RepoProcessor
@Inject internal constructor(private val dao: RepoDao) : BaseProcessor<RepoEntity>(dao) {

    fun get(id: Long): Maybe<RepoEntity> =
        Maybe.fromCallable { dao.get(id) }

    fun getAll(userName: String): Single<List<RepoEntity>> =
        Single.fromCallable { dao.getAll(userName) }

}