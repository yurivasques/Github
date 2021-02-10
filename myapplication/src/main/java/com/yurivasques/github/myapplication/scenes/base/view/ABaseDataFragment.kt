package com.yurivasques.github.myapplication.scenes.base.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.yurivasques.github.myapplication.di.PerApplication


abstract class ABaseDataFragment(@LayoutRes contentLayoutId: Int) : ABaseFragment(contentLayoutId) {

    @PerApplication
    lateinit var progressLayout: View
    @PerApplication
    lateinit var btnErrorRetry: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressLayout = view.findViewById(com.yurivasques.github.myapplication.R.id.progress_layout)!!
        btnErrorRetry = view.findViewById(com.yurivasques.github.myapplication.R.id.btnErrorRetry)!!
        super.onViewCreated(view, savedInstanceState)
    }

    //region RENDER
    protected fun showLoading(visible: Boolean) {
        progressLayout.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected fun showRefreshingLoading(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.isRefreshing = false
    }

    protected fun showRetryLoading(visible: Boolean) {
        btnErrorRetry.isClickable = !visible
        view?.findViewById<View>(com.yurivasques.github.myapplication.R.id.errorProgress)?.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    protected fun showContent(content: View, visible: Boolean) {
        content.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected fun showError(visible: Boolean) {
        view?.findViewById<View>(com.yurivasques.github.myapplication.R.id.viewError)?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected fun renderError(messageError: String?) {
        val errorProgress = view?.findViewById<View>(com.yurivasques.github.myapplication.R.id.errorProgress)
        messageError?.also { errorProgress?.findViewById<TextView>(com.yurivasques.github.myapplication.R.id.textErrorDescription)?.text = it }
    }

    protected fun renderSnack(message: String?) {
        message?.also {
            activity?.also { activity ->
                Snackbar.make(
                    activity.findViewById(android.R.id.content),
                    it, Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
    //endregion

}