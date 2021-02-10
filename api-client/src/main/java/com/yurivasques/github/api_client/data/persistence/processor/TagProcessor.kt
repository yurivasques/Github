package com.yurivasques.github.api_client.data.persistence.processor

import com.yurivasques.github.api_client.data.persistence.dao.TagDao
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.data.persistence.processor.base.BaseProcessor
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagProcessor
@Inject internal constructor(private val dao: TagDao) : BaseProcessor<TagEntity>(dao) {

    fun get(id: String): Maybe<TagEntity> =
        Maybe.fromCallable { dao.get(id) }

    fun getAll(repoId: Long): Single<List<TagEntity>> {
        return Single.fromCallable { dao.getAll(repoId) }
    }

    fun getAll(): Single<List<TagEntity>> =
        Single.fromCallable { dao.getAll() }

}