package com.yurivasques.github.myapplication.scenes.repolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.yurivasques.github.myapplication.R
import com.yurivasques.github.myapplication.scenes.base.view.ABaseActivity
import com.yurivasques.github.myapplication.exception.addFragment
import com.yurivasques.github.myapplication.exception.enableToolbar

class RepoListActivity : ABaseActivity(R.layout.activity_main) {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, RepoListActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.Theme_Github)
        super.onCreate(savedInstanceState)
        initializeActivity(savedInstanceState)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val view = findViewById<View>(R.id.coordinatorLayout)
        //enableToolbar(false, null, view)
    }

    private fun initializeActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(R.id.container,
                RepoListFragment.newInstance()
            )
        }
    }
}
