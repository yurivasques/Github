package com.github.yurivasques.github_library.domain.repository

import com.github.yurivasques.github_library.domain.model.Tag
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface TagRepository {

    val isConnected: Boolean

    fun getListTag(userName: String, repoName: String, repoId: Long): Single<List<Tag>>

    fun getCacheListTag(repoId: Long): Single<List<Tag>>

    fun saveListTag(tagList: List<Tag>): Completable

    fun saveTag(tag: Tag): Completable
}