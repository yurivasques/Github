package com.yurivasques.github.myapplication.scenes.search

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.myapplication.R
import com.yurivasques.github.myapplication.di.PerActivity
import com.yurivasques.github.myapplication.scenes.base.view.ABaseDataFragment
import com.yurivasques.github.myapplication.scenes.repolist.RepoListActivity
import com.yurivasques.github.myapplication.scenes.repolist.RepoListFragment
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SearchFragment : ABaseDataFragment(R.layout.search_fragment) {

    @PerActivity
    lateinit var content: FrameLayout

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

        val intent = RepoListActivity.newIntent(activity!!.applicationContext, userName)
        startActivity(intent)
    }

}