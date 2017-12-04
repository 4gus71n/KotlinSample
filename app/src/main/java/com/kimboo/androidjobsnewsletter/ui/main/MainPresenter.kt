package com.kimboo.androidjobsnewsletter.ui.main

import com.kimboo.androidjobsnewsletter.ui.Presenter

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface MainPresenter : Presenter<MainView> {

    fun getJobs()

    fun fetchJobs()

}
