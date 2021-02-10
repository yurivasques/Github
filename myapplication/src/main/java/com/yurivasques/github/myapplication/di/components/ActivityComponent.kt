package com.yurivasques.github.myapplication.di.components

import android.app.Activity
import com.yurivasques.github.myapplication.di.PerActivity
import com.yurivasques.github.myapplication.di.modules.ActivityModule
import com.yurivasques.github.myapplication.scenes.repolist.RepoListFragment
import com.yurivasques.github.myapplication.scenes.repotaglist.RepoTagListFragment
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