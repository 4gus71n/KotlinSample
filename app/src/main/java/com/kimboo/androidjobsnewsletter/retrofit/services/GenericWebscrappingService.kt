package com.kimboo.androidjobsnewsletter.retrofit.services

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface GenericWebscrappingService {

    @GET
    fun getJobsFromAndroidIo(@Url url: String = "https://androidjobs.io"): Observable<Response<ResponseBody>>

    @GET
    fun getJobsFromRemoteIo(@Url url: String = "https://remoteok.io/remote-android-jobs"): Observable<Response<ResponseBody>>

    @GET
    fun getJobsFromRemotelyAwesome(@Url url: String = "https://www.remotelyawesomejobs.com/tags/android"): Observable<Response<ResponseBody>>

}