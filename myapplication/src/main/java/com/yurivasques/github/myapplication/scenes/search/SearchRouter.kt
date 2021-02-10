package com.yurivasques.github.myapplication.scenes.search

import androidx.appcompat.app.AppCompatActivity
import com.yurivasques.github.myapplication.scenes.repoList.RepoListActivity
import javax.inject.Inject

class SearchRouter
@Inject internal constructor(private val activity: AppCompatActivity) {
    fun routeToRepoList(userName: String) {
        activity.startActivity(
            RepoListActivity.newIntent(
                activity.applicationContext,
                userName
            )
        )
    }
}