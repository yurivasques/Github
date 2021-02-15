package com.yurivasques.github.myapplication.scenes.repoList

import androidx.appcompat.app.AppCompatActivity
import com.yurivasques.github.myapplication.scenes.repoTagList.RepoTagListActivity
import javax.inject.Inject

class RepoListRouter
@Inject internal constructor(private val activity: AppCompatActivity) {

    fun routeToTags(repoId: Long, repoName: String, repoDescription: String?, userName: String) {
        activity.startActivity(
            RepoTagListActivity.newIntent(
                activity.applicationContext,
                repoId,
                repoName,
                repoDescription,
                userName
            )
        )
    }

}