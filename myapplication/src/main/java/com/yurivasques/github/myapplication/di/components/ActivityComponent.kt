package com.yurivasques.github.myapplication.di.components

import android.app.Activity
import com.yurivasques.github.myapplication.di.PerActivity
import com.yurivasques.github.myapplication.di.modules.ActivityModule
import com.yurivasques.github.myapplication.scenes.repoList.RepoListFragment
import com.yurivasques.github.myapplication.scenes.repoTagList.RepoTagListFragment
import com.yurivasques.github.myapplication.scenes.search.SearchFragment
import dagger.BindsInstance
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: Activity): ActivityComponent
    }

    //region Inject
    fun inject(fragment: SearchFragment)

    fun inject(fragment: RepoListFragment)

    fun inject(fragment: RepoTagListFragment)
    //endregion

}