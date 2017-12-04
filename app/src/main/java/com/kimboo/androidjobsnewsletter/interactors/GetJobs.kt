package com.kimboo.androidjobsnewsletter.interactors

import com.kimboo.androidjobsnewsletter.model.JobDetail

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface GetJobs {

    interface Callback {
        fun onJobsFetched(jobs: List<JobDetail>)
        fun onErrorFetchingJobs()
    }

    fun executeFromApi(callback: Callback)

}
