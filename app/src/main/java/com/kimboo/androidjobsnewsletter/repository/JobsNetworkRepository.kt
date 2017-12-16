package com.kimboo.androidjobsnewsletter.repository

import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.utils.rx.DataSource
import io.reactivex.Observable
import io.reactivex.ObservableSource


/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface JobsNetworkRepository {

    fun getJobsFromAndroidIo() : Observable<DataSource<List<JobDetail>>>

    fun getJobsFromRemoteIo() : Observable<DataSource<List<JobDetail>>>

    fun getJobsFromRemotelyAwesome() : Observable<DataSource<List<JobDetail>>>

    fun getJobsFromGoRemote(): ObservableSource<out DataSource<List<JobDetail>>>

}
