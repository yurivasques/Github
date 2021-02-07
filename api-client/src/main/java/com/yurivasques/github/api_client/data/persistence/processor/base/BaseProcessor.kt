package com.yurivasques.github.api_client.data.persistence.processor.base

import com.yurivasques.github.api_client.data.persistence.dao.base.BaseDao
import com.yurivasques.github.api_client.domain.functions.CheckPersistenceResultFunction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

abstract class BaseProcessor<T>(private val baseDao: BaseDao<T>) {

    fun insert(entity: T): Completable =
        Single.fromCallable { baseDao.insert(entity) > 0 }
            .flatMapCompletable(CheckPersistenceResultFunction())

    fun delete(entity: T): Completable =
        Single.fromCallable { baseDao.delete(entity) > 0 }
            .flatMapCompletable(CheckPersistenceResultFunction())

}