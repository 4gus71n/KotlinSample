package com.kimboo.androidjobsnewsletter.ui.main

import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.ui.BaseView

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface MainView : BaseView {

    fun onJobsFetched(jobs: List<JobDetail>)
    fun onHideLoader()
    fun onShowLoader()

}
