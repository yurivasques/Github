package com.yurivasques.github.myapplication.scenes.repoList

import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.myapplication.scenes.base.view.LoadDataView
import io.reactivex.rxjava3.core.Observable

interface RepoListView :
    LoadDataView<RepoListViewModel> {

    fun intentLoadData(): Observable<String>

    fun intentRefreshData(): Observable<String>

    fun intentErrorRetry(): Observable<String>

    fun openRepo(): Observable<Pair<Repo, String>>

}