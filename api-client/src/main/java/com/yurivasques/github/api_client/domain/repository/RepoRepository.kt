package com.yurivasques.github.api_client.domain.repository

import com.yurivasques.github.api_client.domain.model.Repo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface RepoRepository {

    val isConnected: Boolean

    fun getListRepo(userName: String): Single<List<Repo>>

    fun getCacheListRepo(userName: String): Single<List<Repo>>

    fun saveListRepo(repoList: List<Repo>): Completable

    fun saveRepo(repo: Repo): Completable
}