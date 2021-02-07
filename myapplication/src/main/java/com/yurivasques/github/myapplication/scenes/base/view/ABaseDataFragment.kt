package com.yurivasques.github.myapplication.scenes.base.view

import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text


abstract class ABaseDataFragment(@LayoutRes contentLayoutId: Int) : ABaseFragment(contentLayoutId) {

    protected val progressLayout = view?.findViewById<View>(com.yurivasques.github.myapplication.R.id.progress_layout)
    protected val btnErrorRetry = view?.findViewById<TextView>(com.yurivasques.github.myapplication.R.id.btnErrorRetry)
    protected val errorProgress = view?.findViewById<View>(com.yurivasques.github.myapplication.R.id.errorProgress)
    protected val textErrorDescription = errorProgress?.findViewById<TextView>(com.yurivasques.github.myapplication.R.id.textErrorDescription)

    //region RENDER
    protected fun showLoading(visible: Boolean) {
        progressLayout?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected fun showRefreshingLoading(swipeRefreshLayout: SwipeRefreshLayout, visible: Boolean) {
        swipeRefreshLayout.isRefreshing = visible
    }

    protected fun showRetryLoading(visible: Boolean) {
        btnErrorRetry?.isClickable = !visible
        errorProgress?.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    protected fun showContent(content: View, visible: Boolean) {
        content.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected fun showError(visible: Boolean) {
        view?.findViewById<View>(com.yurivasques.github.myapplication.R.id.viewError)?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected fun renderError(messageError: String?) {
        messageError?.also { textErrorDescription?.text = it }
    }

    protected fun renderSnack(message: String?) {
        message?.also {
            activity?.also { activity ->
                Snackbar.make(
                    activity.findViewById(com.yurivasques.github.myapplication.R.id.content),
                    it, Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
    //endregion

}