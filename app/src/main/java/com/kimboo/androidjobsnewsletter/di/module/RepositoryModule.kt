package com.kimboo.androidjobsnewsletter.di.module

import com.kimboo.androidjobsnewsletter.repository.JobsNetworkRepository
import com.kimboo.androidjobsnewsletter.repository.JobsNetworkRepositoryImp
import com.kimboo.androidjobsnewsletter.retrofit.services.AndroidJobsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    //TODO See how to improve this
    @Singleton
    @Provides
    fun providesJobsNetworkRepository(retrofit: Retrofit) : JobsNetworkRepository {
        return JobsNetworkRepositoryImp(retrofit.create(AndroidJobsService::class.java))
    }

}
