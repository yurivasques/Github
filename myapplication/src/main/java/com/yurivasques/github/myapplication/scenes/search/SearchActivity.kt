package com.yurivasques.github.myapplication.scenes.search

import android.os.Bundle
import com.yurivasques.github.myapplication.R
import com.yurivasques.github.myapplication.extensions.addFragment
import com.yurivasques.github.myapplication.scenes.base.view.ABaseActivity

class SearchActivity : ABaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Github)
        super.onCreate(savedInstanceState)
        initializeActivity(savedInstanceState)
    }

    private fun initializeActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(R.id.container,
                SearchFragment.newInstance()
            )
        }
    }
}