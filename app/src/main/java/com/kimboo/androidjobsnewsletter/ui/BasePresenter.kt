package com.kimboo.androidjobsnewsletter.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.StringRes
import java.lang.ref.WeakReference

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
abstract class BasePresenter<V : BaseView>(val context: Context) : Presenter<V> {

    private var weakReference: WeakReference<V>? = null

    @CallSuper
    override fun onViewCreated(view: V, args: Bundle) {
        if (!isViewAttached) {
            weakReference = WeakReference(view)
        }
    }

    fun getString(@StringRes id: Int): String = context?.getString(id);

    override fun onDestroy() {
        weakReference?.clear()
        weakReference = null
    }

    val view: V?
        get() = weakReference?.get()

    private val isViewAttached: Boolean
        get() = weakReference != null && weakReference!!.get() != null

}
