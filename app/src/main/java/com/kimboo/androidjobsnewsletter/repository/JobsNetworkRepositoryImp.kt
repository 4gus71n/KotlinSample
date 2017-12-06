package com.kimboo.androidjobsnewsletter.repository

import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.retrofit.responses.ApiJobDetailResponse
import com.kimboo.androidjobsnewsletter.retrofit.services.AndroidJobsService
import com.kimboo.androidjobsnewsletter.utils.rx.DataSource
import com.kimboo.androidjobsnewsletter.utils.rx.EntityMapper
import com.kimboo.androidjobsnewsletter.utils.rx.transformEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Response

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class JobsNetworkRepositoryImp(val service: AndroidJobsService) : JobsNetworkRepository {

    override fun getJobs(): Observable<DataSource<List<JobDetail>>> {
        return service.getJobs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { r -> mapAndroidJobsIo(r) }
                .transformEntity(mapper)
    }

    val mapper = object: EntityMapper<List<ApiJobDetailResponse>, List<JobDetail>> {
        override fun transformServerToModel(serverResponses:List<ApiJobDetailResponse>): List<JobDetail> {
            var result: MutableList<JobDetail> = ArrayList()

            for (serverResponse in serverResponses) {
                var jobDetail: JobDetail = JobDetail()
                jobDetail.description = serverResponse.description

                result.add(jobDetail)
            }

            return result.toList()
        }
    }

    private fun mapAndroidJobsIo(response: Response<ResponseBody>): Response<List<ApiJobDetailResponse>> {
        var result: MutableList<ApiJobDetailResponse> = ArrayList()

        try {
            if (response.isSuccessful!!) {
                val htmlNode = Jsoup.parse(response.body()?.string()!!)

                for (liNode in htmlNode.getElementsByClass("jobs").get(0).getElementsByTag("li")) {
                    var apiResponse = ApiJobDetailResponse()
                    apiResponse.description = liNode.text()
                    result.add(apiResponse)
                }

            }
        } catch (e: Exception) {
            return Response.error(response.code(), response.errorBody())
        } finally {
            return Response.success(result.toList())
        }
    }

}
