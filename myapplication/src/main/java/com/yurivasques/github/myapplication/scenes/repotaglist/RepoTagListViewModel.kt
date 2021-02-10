package com.yurivasques.github.myapplication.scenes.repotaglist

import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import com.yurivasques.github.myapplication.scenes.base.view.ContentState
import com.yurivasques.github.myapplication.scenes.base.view.LoadingState
import com.yurivasques.github.myapplication.scenes.repolist.RepoListViewModel

class RepoTagListViewModel(
    val loadingState: LoadingState = LoadingState.NONE,
    val contentState: ContentState = ContentState.NONE,
    val data: List<Tag>? = null,
    val errorMessage: String? = null,
    val snackMessage: String? = null
) {

    companion object {
        fun createLoading() =
            RepoTagListViewModel(
                loadingState = LoadingState.LOADING,
                contentState = ContentState.CONTENT
            )

        fun createRetryLoading() =
            RepoTagListViewModel(
                loadingState = LoadingState.RETRY,
                contentState = ContentState.ERROR
            )

        fun createData(data: List<Tag>) =
            RepoTagListViewModel(
                contentState = ContentState.CONTENT,
                data = data
            )

        fun createError(error: String) =
            RepoTagListViewModel(
                contentState = ContentState.ERROR,
                errorMessage = error
            )

        fun createSnack(snackMessage: String) =
            RepoTagListViewModel(
                contentState = ContentState.CONTENT,
                snackMessage = snackMessage
            )
    }

}