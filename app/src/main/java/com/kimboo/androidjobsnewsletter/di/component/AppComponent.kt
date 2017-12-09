package com.kimboo.androidjobsnewsletter.di.component

import android.content.Context
import com.google.gson.Gson
import com.kimboo.androidjobsnewsletter.NewsletterApplication
import com.kimboo.androidjobsnewsletter.di.module.AppModule
import com.kimboo.androidjobsnewsletter.di.module.NetworkModule
import com.kimboo.androidjobsnewsletter.di.module.RepositoryModule
import com.kimboo.androidjobsnewsletter.di.module.RetrofitServiceModule
import com.kimboo.androidjobsnewsletter.repository.JobsNetworkRepository
import com.kimboo.androidjobsnewsletter.retrofit.services.GenericWebscrappingService
import dagger.Component
import okhttp3.Cache
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(RetrofitServiceModule::class, RepositoryModule::class, AppModule::class, NetworkModule::class))
interface AppComponent {

    val gson: Gson

    val cache: Cache

    val retrofit: retrofit2.Retrofit

    val newsLetterApplication: NewsletterApplication

    val applicationContext: Context

    val jobsNetworkRepository: JobsNetworkRepository

    val genericWebscrappingService: GenericWebscrappingService

}
