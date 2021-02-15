package com.yurivasques.github.myapplication.scenes.repoTagList

import com.yurivasques.github.api_client.data.extensions.shareReplay
import com.yurivasques.github.api_client.data.extensions.startWithSingle
import com.yurivasques.github.api_client.domain.functions.DelayFunction
import com.yurivasques.github.api_client.domain.usecases.GetListTag
import com.yurivasques.github.api_client.domain.usecases.RefreshListTag
import com.yurivasques.github.myapplication.exception.ErrorMessageFactory
import com.yurivasques.github.myapplication.scenes.base.view.APresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class RepoTagListPresenter
@Inject constructor(
    private val getListTag: GetListTag,
    private val refreshListTag: RefreshListTag,
    private val scheduler: Scheduler,
    errorMessageFactory: ErrorMessageFactory
) : APresenter<RepoTagListView, RepoTagListViewModel>(errorMessageFactory) {

    override fun attach(view: RepoTagListView) {
        val loadTag = view.intentLoadData().flatMap { getListTag(it) }.shareReplay()
        val refreshListTag = view.intentRefreshData().flatMap { refreshListTag(it) }
        val retryTag = view.intentErrorRetry().flatMap { retryTag(it) }

        subscribeViewModel(view, loadTag, refreshListTag, retryTag)
    }

    //region USE CASES TO VIEW MODEL
    private fun getListTag(getListTagParam: GetListTag.Param): Observable<RepoTagListViewModel> =
        getListTag.execute(getListTagParam).toObservable()
            .map { RepoTagListViewModel.createData(it) }
            .startWithSingle(RepoTagListViewModel.createLoading())
            .onErrorReturn { onError(it) }

    private fun refreshListTag(refreshListTagParam: RefreshListTag.Param): Observable<RepoTagListViewModel> =
        refreshListTag.execute(refreshListTagParam).toObservable()
            .map { RepoTagListViewModel.createData(it) }
            .onErrorReturn { RepoTagListViewModel.createSnack(getErrorMessage(it)) }

    private fun retryTag(getListTagParam: GetListTag.Param): Observable<RepoTagListViewModel> =
        getListTag.execute(getListTagParam).toObservable()
            .map { RepoTagListViewModel.createData(it) }
            .startWithSingle(RepoTagListViewModel.createRetryLoading())
            .onErrorResumeNext(DelayFunction<RepoTagListViewModel>(scheduler))
            .onErrorReturn { onError(it) }
    //endregion

    private fun onError(error: Throwable): RepoTagListViewModel =
        RepoTagListViewModel.createError(getErrorMessage(error))

}