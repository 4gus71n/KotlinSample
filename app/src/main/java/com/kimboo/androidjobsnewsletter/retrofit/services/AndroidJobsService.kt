package com.kimboo.androidjobsnewsletter.retrofit.services

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface AndroidJobsService {

    @GET("/")
    fun getJobs(): Observable<Response<ResponseBody>>

}