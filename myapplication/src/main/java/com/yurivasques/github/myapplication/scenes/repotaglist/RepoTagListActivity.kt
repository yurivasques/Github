package com.yurivasques.github.myapplication.scenes.repotaglist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.yurivasques.github.myapplication.R
import com.yurivasques.github.myapplication.scenes.base.view.ABaseActivity
import com.yurivasques.github.myapplication.extensions.addFragment
import com.yurivasques.github.myapplication.extensions.getLongExtra
import com.yurivasques.github.myapplication.extensions.getNullableStringExtra
import com.yurivasques.github.myapplication.extensions.getStringExtra
import com.yurivasques.github.myapplication.scenes.repolist.RepoListActivity
import io.reactivex.rxjava3.subjects.PublishSubject

class RepoTagListActivity : ABaseActivity(R.layout.activity_main) {

    companion object {
        private const val EXTRA_REPO_ID = "extra_repo_id"
        private const val EXTRA_REPO_NAME = "extra_repo_name"
        private const val EXTRA_REPO_DESCRIPTION = "extra_repo_description"
        private const val EXTRA_USER_NAME = "extra_user_name"

        @SuppressLint("LongLogTag")
        fun newIntent(context: Context, repoId: Long, repoName: String, repoDescription: String?, userName: String): Intent =
            Intent(context, RepoTagListActivity::class.java).apply {
                Log.d("RepoTagListActivity:newIntent", "$repoId")
                putExtra(EXTRA_REPO_ID, repoId)
                putExtra(EXTRA_REPO_NAME, repoName)

                putExtra(EXTRA_REPO_DESCRIPTION, repoDescription)
                putExtra(EXTRA_USER_NAME, userName)
            }
    }

    private val repoId: Long by lazy { getLongExtra(EXTRA_REPO_ID) }
    private val repoName: String by lazy { getStringExtra(EXTRA_REPO_NAME) }
    private val repoDescription: String? by lazy { getNullableStringExtra(EXTRA_REPO_DESCRIPTION) }
    private val userName: String by lazy { getStringExtra(EXTRA_USER_NAME) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Github)
        super.onCreate(savedInstanceState)
        initializeActivity(savedInstanceState)
    }

    private fun initializeActivity(savedInstanceState: Bundle?) {
        Log.d("initializeActivity", "$repoId")
        if (savedInstanceState == null) {
            addFragment(R.id.container, RepoTagListFragment.newInstance(repoId, repoName, repoDescription, userName))
        }
    }
}
