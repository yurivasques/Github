package com.github.yurivasques.github_library.data.persistence.processor.base

import com.github.yurivasques.github_library.data.persistence.dao.base.BaseDao
import com.github.yurivasques.github_library.domain.functions.CheckPersistenceResultFunction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Base processor for data access object
 */
abstract class BaseProcessor<T>(private val baseDao: BaseDao<T>) {

    fun insert(entity: T): Completable =
        Single.fromCallable { baseDao.insert(entity) > 0 }
            .flatMapCompletable(CheckPersistenceResultFunction())

    fun delete(entity: T): Completable =
        Single.fromCallable { baseDao.delete(entity) > 0 }
            .flatMapCompletable(CheckPersistenceResultFunction())

}