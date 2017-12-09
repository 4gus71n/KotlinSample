package com.kimboo.androidjobsnewsletter.repository

import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.retrofit.mappers.HtmlToJson
import com.kimboo.androidjobsnewsletter.retrofit.mappers.JobsMapper
import com.kimboo.androidjobsnewsletter.retrofit.services.GenericWebscrappingService
import com.kimboo.androidjobsnewsletter.utils.rx.DataSource
import com.kimboo.androidjobsnewsletter.utils.rx.transformEntity
import com.kimboo.androidjobsnewsletter.utils.rx.transformHtmlResponseIntoJson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class JobsNetworkRepositoryImp(val service: GenericWebscrappingService) : JobsNetworkRepository {

    override fun getJobsFromAndroidIo(): Observable<DataSource<List<JobDetail>>> {
        return service.getJobsFromAndroidIo()
                .subscribeOn(Schedulers.io())
                .transformHtmlResponseIntoJson(HtmlToJson()::mapAndroidJobsIo)
                .transformEntity(JobsMapper()::fromServerToModel)
    }

    override fun getJobsFromRemoteIo(): Observable<DataSource<List<JobDetail>>> {
        return service.getJobsFromRemoteIo()
                .subscribeOn(Schedulers.io())
                .transformHtmlResponseIntoJson(HtmlToJson()::mapRemoteIo)
                .transformEntity(JobsMapper()::fromServerToModel)
    }

    override fun getJobsFromRemotelyAwesome(): Observable<DataSource<List<JobDetail>>> {
        return service.getJobsFromRemotelyAwesome()
                .subscribeOn(Schedulers.io())
                .transformHtmlResponseIntoJson(HtmlToJson()::mapRemotelyAwesome)
                .transformEntity(JobsMapper()::fromServerToModel)
    }
}
