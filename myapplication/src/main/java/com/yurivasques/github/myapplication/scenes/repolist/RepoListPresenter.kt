package com.yurivasques.github.myapplication.scenes.repolist

import android.util.Log
import com.yurivasques.github.api_client.data.extensions.shareReplay
import com.yurivasques.github.api_client.data.extensions.startWithSingle
import com.yurivasques.github.api_client.domain.functions.DelayFunction
import com.yurivasques.github.api_client.domain.usecases.GetListRepo
import com.yurivasques.github.api_client.domain.usecases.RefreshListRepo
import com.yurivasques.github.myapplication.scenes.base.view.APresenter
import com.yurivasques.github.myapplication.exception.ErrorMessageFactory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class RepoListPresenter
@Inject constructor(
    private val getListRepo: GetListRepo,
    private val refreshListRepo: RefreshListRepo,
    private val router: RepoListRouter,
    private val scheduler: Scheduler,
    errorMessageFactory: ErrorMessageFactory
) : APresenter<RepoListView, RepoListViewModel>(errorMessageFactory) {

    override fun attach(view: RepoListView) {
        val loadRepo = view.intentLoadData().flatMap { loadRepo(it) }.shareReplay()
        val refreshRepo = view.intentRefreshData().flatMap { refreshData(it) }
        val retryRepo = view.intentErrorRetry().flatMap { retryRepo(it) }

        subscribeViewModel(view, loadRepo, refreshRepo, retryRepo)

        view.openRepo()
            .subscribe { (repo, userName) -> router.routeToTags(repo.id, repo.name!!, repo.description, userName) }
            .addTo(composite)
    }

    //region USE CASES TO VIEW MODEL
    private fun loadRepo(userName: String): Observable<RepoListViewModel> =
        getListRepo.execute(userName).toObservable()
            .map {
                Log.d("loadRepo", "$it")
                RepoListViewModel.createData(
                    it
                )
            }
            .startWithSingle(RepoListViewModel.createLoading())
            .onErrorReturn { onError(it) }

    private fun refreshData(userName: String): Observable<RepoListViewModel> =
        refreshListRepo.execute(userName).toObservable()
            .map {
                RepoListViewModel.createData(
                    it
                )
            }
            .onErrorReturn {
                RepoListViewModel.createSnack(
                    getErrorMessage(it)
                )
            }

    private fun retryRepo(userName: String): Observable<RepoListViewModel> =
        getListRepo.execute(userName).toObservable()
            .map {
                RepoListViewModel.createData(
                    it
                )
            }
            .startWithSingle(RepoListViewModel.createRetryLoading())
            .onErrorResumeNext(DelayFunction<RepoListViewModel>(scheduler))
            .onErrorReturn { onError(it) }
    //endregion

    private fun onError(error: Throwable): RepoListViewModel =
        RepoListViewModel.createError(
            getErrorMessage(error)
        )

}
