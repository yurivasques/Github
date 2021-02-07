package com.yurivasques.github.api_client.domain.repository

import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface TagRepository {

    val isConnected: Boolean

    fun getListTag(userName: String, repoName: String): Single<List<Tag>>

    fun getCacheListTag(userName: String, repoName: String): Single<List<Tag>>

    fun saveListTag(tagList: List<Tag>): Completable

    fun saveTag(tag: Tag): Completable
}