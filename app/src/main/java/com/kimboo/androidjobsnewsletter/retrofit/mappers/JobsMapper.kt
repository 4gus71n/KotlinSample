package com.kimboo.androidjobsnewsletter.retrofit.mappers

import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.retrofit.responses.ApiJobDetailResponse

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class JobsMapper {

    fun fromServerToModel(serverResponses: List<ApiJobDetailResponse>): List<JobDetail> {
        var result: MutableList<JobDetail> = ArrayList()

        for (serverResponse in serverResponses) {
            var jobDetail = JobDetail()
            jobDetail.description = serverResponse.description
            jobDetail.title = serverResponse.title
            jobDetail.url = serverResponse.url
            result.add(jobDetail)
        }

        return result.toList()
    }

}
