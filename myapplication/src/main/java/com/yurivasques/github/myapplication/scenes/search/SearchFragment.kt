package com.yurivasques.github.myapplication.scenes.search

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import com.yurivasques.github.myapplication.R
import com.yurivasques.github.myapplication.di.PerActivity
import com.yurivasques.github.myapplication.scenes.base.view.ABaseDataFragment
import com.yurivasques.github.myapplication.scenes.repolist.RepoListActivity
import com.yurivasques.github.myapplication.scenes.repolist.RepoListPresenter
import com.yurivasques.github.myapplication.scenes.repolist.RepoListRouter
import javax.inject.Inject

class SearchFragment : ABaseDataFragment(R.layout.search_fragment) {

    @PerActivity
    lateinit var content: FrameLayout
    @Inject
    lateinit var router: SearchRouter

    companion object {
        fun newInstance(): SearchFragment = SearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        content = view.findViewById(R.id.searchContent)!!
        view.findViewById<Button>(R.id.searchButton)?.setOnClickListener { search(view) }
    }

    private fun search(view: View) {
        val userName = view.findViewById<EditText>(R.id.editTextTextUserName)?.text.toString()

        router.routeToRepoList(userName)
    }

}