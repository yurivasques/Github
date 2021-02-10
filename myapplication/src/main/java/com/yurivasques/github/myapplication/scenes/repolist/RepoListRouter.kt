package com.yurivasques.github.myapplication.scenes.repolist

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yurivasques.github.myapplication.scenes.repotaglist.RepoTagListActivity
import javax.inject.Inject

class RepoListRouter
@Inject internal constructor(private val activity: AppCompatActivity) {

    fun routeToTags(repoId: Long, repoName: String, repoDescription: String?, userName: String) {
        Log.d("routeToTags", "$repoId")
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