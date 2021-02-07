package com.yurivasques.github.myapplication.scenes.base.view

import android.app.Application
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.yurivasques.github.myapplication.AndroidApplication
import com.yurivasques.github.myapplication.di.components.ActivityComponent
import com.yurivasques.github.myapplication.di.components.ApplicationComponent

abstract class ABaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    private val applicationComponent: ApplicationComponent by lazy {
        (application as AndroidApplication).appComponent
    }

    val activityComponent: ActivityComponent by lazy {
        applicationComponent.activityComponent().create(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle toolbar back arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}