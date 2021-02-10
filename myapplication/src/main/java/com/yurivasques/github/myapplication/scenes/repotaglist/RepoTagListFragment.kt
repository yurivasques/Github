package com.yurivasques.github.myapplication.scenes.repotaglist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.yurivasques.github.api_client.domain.model.Tag
import com.yurivasques.github.api_client.domain.usecases.GetListTag
import com.yurivasques.github.api_client.domain.usecases.RefreshListTag
import com.yurivasques.github.myapplication.R
import com.yurivasques.github.myapplication.di.PerActivity
import com.yurivasques.github.myapplication.extensions.build
import com.yurivasques.github.myapplication.extensions.getLongArg
import com.yurivasques.github.myapplication.extensions.getNullableStringArg
import com.yurivasques.github.myapplication.extensions.getStringArg
import com.yurivasques.github.myapplication.scenes.base.view.ABaseDataFragment
import com.yurivasques.github.myapplication.scenes.base.view.ContentState
import com.yurivasques.github.myapplication.scenes.base.view.LoadingState
import com.yurivasques.github.myapplication.scenes.repolist.*
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RepoTagListFragment : ABaseDataFragment(R.layout.repo_tag_list_fragment), RepoTagListView {

    companion object {
        private const val ARGS_REPO_NAME = "args_repo_name"
        private const val ARGS_REPO_DESCRIPTION = "args_repo_description"
        private const val ARGS_USER_NAME = "args_user_name"
        private const val ARGS_REPO_ID = "args_repo_id"

        @SuppressLint("LongLogTag")
        fun newInstance(repoId: Long, repoName: String, repoDescription: String?, userName: String): RepoTagListFragment =
            RepoTagListFragment().build {
                Log.d("RepoTagListFragment:newInstance", "$repoId")
                putLong(ARGS_REPO_ID, repoId)
                putString(ARGS_REPO_NAME, repoName)
                putString(ARGS_REPO_DESCRIPTION, repoDescription)
                putString(ARGS_USER_NAME, userName)
            }
    }

    @PerActivity
    lateinit var recyclerView: RecyclerView
    @PerActivity
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @PerActivity
    lateinit var content: FrameLayout
    @Inject
    lateinit var presenter: RepoTagListPresenter

    private val repoId: Long by lazy { getLongArg(ARGS_REPO_ID) }
    private val repoName: String by lazy { getStringArg(ARGS_REPO_NAME) }
    private val repoDescription: String? by lazy { getNullableStringArg(ARGS_REPO_DESCRIPTION) }
    private val userName: String by lazy { getStringArg(ARGS_USER_NAME) }

    private val repoTagAdapter =
        RepoTagsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view?.findViewById(R.id.tagListRecyclerView)!!
        swipeRefreshLayout = view?.findViewById(R.id.tagListSwipeRefreshLayout)!!
        content = view?.findViewById(R.id.tagListContent)!!
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
        recyclerView?.adapter = repoTagAdapter
    }

    //region INTENTS
    override fun intentLoadData(): Observable<GetListTag.Param> {
        Log.d("intentLoadData", "busca")
        Log.d("intentLoadData:id", "$repoId")
        return Observable.just(GetListTag.Param(userName, repoName, repoId))
    }

    override fun intentRefreshData(): Observable<RefreshListTag.Param> =
        swipeRefreshLayout.refreshes().map { RefreshListTag.Param(userName, repoName, repoId) }

    override fun intentErrorRetry(): Observable<GetListTag.Param> =
        view?.findViewById<TextView>(R.id.btnErrorRetry)?.clicks()?.map { GetListTag.Param(userName, repoName, repoId) }!!
    //endregion

    //region RENDER
    override fun render(viewModel: RepoTagListViewModel) {
        TimberWrapper.d { "render: $viewModel" }
        Log.d("render", "RepoTagListFragment")

        showLoading(viewModel.loadingState == LoadingState.LOADING)
        if (swipeRefreshLayout != null) {
            Log.d("render", "swipeRefreshLayout")
            showRefreshingLoading(swipeRefreshLayout, false)
        }
        showRetryLoading(viewModel.loadingState == LoadingState.RETRY)
        if (content != null) {
            Log.d("render", "content")
            showContent(content, viewModel.contentState == ContentState.CONTENT)
        }
        showError(viewModel.contentState == ContentState.ERROR)

        Log.d("render:viewModel.data", "$viewModel.data")

        renderData(viewModel.data)
        renderError(viewModel.errorMessage)
        renderSnack(viewModel.snackMessage)
    }

    private fun renderData(repoTagList: List<Tag>?) {
        Log.d("tag:renderData", "$repoTagList")
        view?.findViewById<TextView>(R.id.textRepoTagName)?.text = repoName
        view?.findViewById<TextView>(R.id.textRepoTagDescription)?.text = repoDescription

        repoTagList?.also {
            repoTagAdapter.data = it.toMutableList()
            recyclerView?.scrollToPosition(0)
        }
    }
    //endregion

}