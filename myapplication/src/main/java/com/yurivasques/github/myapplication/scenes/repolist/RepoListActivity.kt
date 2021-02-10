package com.yurivasques.github.myapplication.scenes.repoList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yurivasques.github.myapplication.R
import com.yurivasques.github.myapplication.extensions.addFragment
import com.yurivasques.github.myapplication.extensions.getStringExtra
import com.yurivasques.github.myapplication.scenes.base.view.ABaseActivity
import io.reactivex.rxjava3.subjects.PublishSubject

class RepoListActivity : ABaseActivity(R.layout.activity_main) {

    companion object {
        private const val EXTRA_USER_NAME = "extra_user_name"

        fun newIntent(context: Context, userName: String): Intent =
            Intent(context, RepoListActivity::class.java).apply {
                putExtra(EXTRA_USER_NAME, userName)
            }
    }

    private val userName: String by lazy { getStringExtra(EXTRA_USER_NAME) }

    internal val intentActionLink = PublishSubject.create<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Github)
        super.onCreate(savedInstanceState)
        initializeActivity(savedInstanceState)
    }

    private fun initializeActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(R.id.container, RepoListFragment.newInstance(userName))
        }
    }
}
