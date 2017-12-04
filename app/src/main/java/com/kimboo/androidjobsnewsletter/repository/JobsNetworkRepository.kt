package com.kimboo.androidjobsnewsletter.repository

import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.utils.rx.DataSource
import io.reactivex.Observable


/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface JobsNetworkRepository {

    fun getJobs() : Observable<DataSource<List<JobDetail>>>

}
