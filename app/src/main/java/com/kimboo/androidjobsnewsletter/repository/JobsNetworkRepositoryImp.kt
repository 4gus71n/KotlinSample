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

    //TODO https://www.simplyhired.com/search?q=remote+android+developer&job=gGroTQrR_EPUDG-v-LU9gMjVwhUBOEjr8bzyjpe2nlBDCuj5seQAZQ
    //https://stackoverflow.com/jobs?r=true&rs=1&sort=p
    //https://angel.co/job-collections/remote

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

    override fun getJobsFromGoRemote(): Observable<DataSource<List<JobDetail>>> {
        return service.getJobsFromGoRemote()
                .subscribeOn(Schedulers.io())
                .transformHtmlResponseIntoJson(HtmlToJson()::mapGoRemote)
                .transformEntity(JobsMapper()::fromServerToModel)
    }
}
