package com.yurivasques.github.myapplication.scenes.base.view

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.yurivasques.github.myapplication.exception.ErrorMessageFactory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class APresenter<in View : LoadDataView<ViewModel>, ViewModel>(private val errorMessageFactory: ErrorMessageFactory) {

    protected val composite = CompositeDisposable()

    @set:VisibleForTesting
    var testMode: Boolean = false

    abstract fun attach(view: View)

    protected fun subscribeViewModel(view: View, vararg observables: Observable<ViewModel>) {
        if (!testMode) {
            composite.add(Observable.mergeArray(*observables).subscribe { view.render(it) })
        }
    }

    protected fun getErrorMessage(error: Throwable): String {
        return errorMessageFactory.getError(error)
    }

    fun detach() {
        composite.clear()
    }

}
