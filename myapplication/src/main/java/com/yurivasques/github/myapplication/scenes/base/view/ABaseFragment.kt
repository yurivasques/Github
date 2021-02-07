package com.yurivasques.github.myapplication.scenes.base.view

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.yurivasques.github.myapplication.AndroidApplication
import com.yurivasques.github.myapplication.di.components.ActivityComponent
import com.yurivasques.github.myapplication.di.components.ApplicationComponent
import com.yurivasques.github.myapplication.scenes.base.view.ABaseActivity

abstract class ABaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected val appComponent: ApplicationComponent by lazy { (requireActivity().application as AndroidApplication).appComponent }

    protected val activityComponent: ActivityComponent by lazy { (activity as ABaseActivity).activityComponent }

    override fun getContext(): Context = requireContext()
}