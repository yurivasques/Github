package com.yurivasques.github.myapplication.scenes.repoList

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.yurivasques.github.api_client.domain.exception.NoConnectedException
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.usecases.GetListRepo
import com.yurivasques.github.api_client.domain.usecases.RefreshListRepo
import com.yurivasques.github.myapplication.exception.ErrorMessageFactoryTest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class RepoListPresenterTest {

    // View
    private lateinit var view: RepoListView

    // Presenter
    private val getListRepo = mock<GetListRepo>()
    private val refreshListRepo = mock<RefreshListRepo>()
    private val router = mock<RepoListRouter>()
    private val errorMessageFactoryTest = ErrorMessageFactoryTest(mock())
    private val presenter by lazy {
        RepoListPresenter(
            getListRepo,
            refreshListRepo,
            router,
            TestScheduler(),
            errorMessageFactoryTest
        )
    }

    // Properties
    private val userName = "yurivasques"

    @Before
    fun setup() {
        view = mock {
            on { intentLoadData() } doReturn Observable.never()
            on { intentRefreshData() } doReturn Observable.never()
            on { intentErrorRetry() } doReturn Observable.never()
            on { openRepo() } doReturn Observable.never()
        }
    }

    @Test
    fun loadData() {
        val repoList = mock<List<Repo>>()

        whenever(getListRepo.execute(userName)).thenReturn(Single.just(repoList))
        whenever(view.intentLoadData()).thenReturn(Observable.just(userName))

        // Attach
        presenter.attach(view)

        verify(view, times(2)).render(any())
        verify(view, times(1)).render(RepoListViewModel.createLoading())
        verify(view, times(1)).render(RepoListViewModel.createData(repoList))

        // Detach
        presenter.detach()
    }

    @Test
    fun loadDataWhenUseCaseReturnException() {
        val noConnectedException = NoConnectedException

        whenever(getListRepo.execute(userName)).thenReturn(Single.error(noConnectedException))
        whenever(view.intentLoadData()).thenReturn(Observable.just(userName))

        // Attach
        presenter.attach(view)

        verify(view, times(2)).render(any())
        verify(view, times(1)).render(RepoListViewModel.createLoading())
        verify(
            view,
            times(1)
        ).render(RepoListViewModel.createError(errorMessageFactoryTest.getError(noConnectedException)))

        // Detach
        presenter.detach()
    }

    @Test
    fun refresh() {
        val repoList = mock<List<Repo>>()

        whenever(refreshListRepo.execute(userName)).thenReturn(Single.just(repoList))
        whenever(view.intentRefreshData()).thenReturn(Observable.just(userName))

        // Attach
        presenter.attach(view)

        verify(view, times(1)).render(any())
        verify(view, times(1)).render(RepoListViewModel.createData(repoList))

        // Detach
        presenter.detach()
    }

    @Test
    fun retry() {
        val repoList = mock<List<Repo>>()

        whenever(getListRepo.execute(userName)).thenReturn(Single.just(repoList))
        whenever(view.intentErrorRetry()).thenReturn(Observable.just(userName))

        // Attach
        presenter.attach(view)

        verify(view, times(2)).render(any())
        verify(view, times(1)).render(RepoListViewModel.createRetryLoading())
        verify(view, times(1)).render(RepoListViewModel.createData(repoList))

        // Detach
        presenter.detach()
    }

}