package com.kimboo.androidjobsnewsletter.repository

import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.retrofit.mappers.HtmlToJson
import com.kimboo.androidjobsnewsletter.retrofit.mappers.JobsMapper
import com.kimboo.androidjobsnewsletter.retrofit.services.AndroidJobsService
import com.kimboo.androidjobsnewsletter.utils.rx.DataSource
import com.kimboo.androidjobsnewsletter.utils.rx.transformEntity
import com.kimboo.androidjobsnewsletter.utils.rx.transformHtmlResponseIntoJson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class JobsNetworkRepositoryImp(val service: AndroidJobsService) : JobsNetworkRepository {

    override fun getJobs(): Observable<DataSource<List<JobDetail>>> {
        return service.getJobs()
                .subscribeOn(Schedulers.io())
                .transformHtmlResponseIntoJson(HtmlToJson()::mapAndroidJobsIo)
                .transformEntity(JobsMapper()::fromServerToModel)
    }

}
