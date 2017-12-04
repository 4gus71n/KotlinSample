package com.kimboo.androidjobsnewsletter.ui

import android.os.Bundle

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface Presenter<V : BaseView> {

    fun onViewCreated(view: V, args: Bundle)
    fun onSaveInstance(args: Bundle?)
    fun onDestroy()

}
