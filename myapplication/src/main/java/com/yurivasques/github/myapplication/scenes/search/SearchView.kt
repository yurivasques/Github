package com.yurivasques.github.myapplication.scenes.search

import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.myapplication.scenes.base.view.LoadDataView
import com.yurivasques.github.myapplication.scenes.repolist.RepoListViewModel
import io.reactivex.rxjava3.core.Observable

interface SearchView :
    LoadDataView<SearchViewModel> {

    fun searchRepos(): Observable<Pair<List<Repo>, String>>

}