package com.yurivasques.github.myapplication.scenes.search

import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.myapplication.scenes.base.view.ContentState
import com.yurivasques.github.myapplication.scenes.base.view.LoadingState

class SearchViewModel(
    val loadingState: LoadingState = LoadingState.NONE,
    val contentState: ContentState = ContentState.NONE,
    val data: List<Repo>? = null,
    val errorMessage: String? = null,
    val snackMessage: String? = null
) {
    companion object {
        fun createLoading() = SearchViewModel(
            loadingState = LoadingState.LOADING,
            contentState = ContentState.CONTENT
        )

        fun createRetryLoading() =
            SearchViewModel(loadingState = LoadingState.RETRY, contentState = ContentState.ERROR)

        fun createData(data: List<Repo>) =
            SearchViewModel(contentState = ContentState.CONTENT, data = data)

        fun createFavoriteRepo(position: Int, repo: Repo) =
            SearchViewModel(
                contentState = ContentState.CONTENT
            )

        fun createError(error: String) =
            SearchViewModel(contentState = ContentState.ERROR, errorMessage = error)

        fun createSnack(snackMessage: String) =
            SearchViewModel(contentState = ContentState.CONTENT, snackMessage = snackMessage)
    }
}