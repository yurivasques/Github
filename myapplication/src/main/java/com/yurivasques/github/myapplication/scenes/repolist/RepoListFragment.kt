package com.yurivasques.github.myapplication.scenes.repolist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.view.clicks
import com.yurivasques.github.api_client.data.helper.TimberWrapper
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.myapplication.R
import com.yurivasques.github.myapplication.di.PerActivity
import com.yurivasques.github.myapplication.extensions.build
import com.yurivasques.github.myapplication.extensions.getStringArg
import com.yurivasques.github.myapplication.scenes.base.view.ABaseDataFragment
import com.yurivasques.github.myapplication.scenes.base.view.ContentState
import com.yurivasques.github.myapplication.scenes.base.view.LoadingState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class RepoListFragment : ABaseDataFragment(R.layout.repo_list_fragment), RepoListView {

    @PerActivity
    lateinit var recyclerView: RecyclerView
    @PerActivity
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @PerActivity
    lateinit var content: FrameLayout

    companion object {
        private const val ARGS_USER_NAME = "args_user_name"

        fun newInstance(userName: String): RepoListFragment =
            RepoListFragment().build{
                putString(ARGS_USER_NAME, userName)
            }
    }

    @Inject
    lateinit var presenter: RepoListPresenter

    private val userName: String by lazy { getStringArg(ARGS_USER_NAME) }

    private fun getParam(): String = userName

    private val repoAdapter =
        ReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view?.findViewById(R.id.recyclerView)!!
        swipeRefreshLayout = view?.findViewById(R.id.swipeRefreshLayout)!!
        content = view?.findViewById(R.id.repoListContent)!!
        initView()
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }

    private fun initView() {
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = repoAdapter
    }

    //region INTENTS
    override fun intentLoadData(): Observable<String> = Observable.just(getParam())

    override fun intentRefreshData(): Observable<String> =
        swipeRefreshLayout.refreshes().map { getParam() }

    override fun intentErrorRetry(): Observable<String> =
        view?.findViewById<TextView>(R.id.btnErrorRetry)?.clicks()?.map { getParam() }!!

    override fun openRepo(): Observable<Pair<Repo, String>> =
        repoAdapter.repoClickIntent.map { it to getParam() }
    //endregion

    //region RENDER
    override fun render(viewModel: RepoListViewModel) {
        TimberWrapper.d { "render: $viewModel" }

        showLoading(viewModel.loadingState == LoadingState.LOADING)
        if (swipeRefreshLayout != null) {
            showRefreshingLoading(swipeRefreshLayout, false)
        }
        showRetryLoading(viewModel.loadingState == LoadingState.RETRY)
        if (content != null) {
            showContent(content, viewModel.contentState == ContentState.CONTENT)
        }
        showError(viewModel.contentState == ContentState.ERROR)

        renderData(viewModel.data)
        renderError(viewModel.errorMessage)
        renderSnack(viewModel.snackMessage)
    }

    private fun renderData(repoList: List<Repo>?) {
        Log.d("repo:renderData", "$repoList")
        repoList?.also {
            repoAdapter.data = it.toMutableList()
            recyclerView?.scrollToPosition(0)
        }
    }
    //endregion

}