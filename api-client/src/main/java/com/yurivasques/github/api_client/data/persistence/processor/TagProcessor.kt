package com.yurivasques.github.api_client.data.persistence.processor

import com.yurivasques.github.api_client.data.persistence.dao.TagDao
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.data.persistence.processor.base.BaseProcessor
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Tag processor for data access object.
 * This is the access class to TagDAO operations
 */
@Singleton
class TagProcessor
@Inject internal constructor(private val dao: TagDao) : BaseProcessor<TagEntity>(dao) {

    fun get(id: String): Maybe<TagEntity> =
        Maybe.fromCallable { dao.get(id) }

    fun getAll(repoId: Long): Single<List<TagEntity>> =
        Single.fromCallable { dao.getAll(repoId) }

}