package com.yurivasques.github.myapplication.scenes.repoTagList

import com.github.yurivasques.github_library.domain.usecases.GetListTag
import com.github.yurivasques.github_library.domain.usecases.RefreshListTag
import com.yurivasques.github.myapplication.scenes.base.view.LoadDataView
import io.reactivex.rxjava3.core.Observable

interface RepoTagListView :
    LoadDataView<RepoTagListViewModel> {

    fun intentLoadData(): Observable<GetListTag.Param>

    fun intentRefreshData(): Observable<RefreshListTag.Param>

    fun intentErrorRetry(): Observable<GetListTag.Param>

}