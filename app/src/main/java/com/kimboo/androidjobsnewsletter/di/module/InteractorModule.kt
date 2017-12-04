package com.kimboo.androidjobsnewsletter.di.module

import com.kimboo.androidjobsnewsletter.di.ActivityScope
import com.kimboo.androidjobsnewsletter.interactors.GetJobs
import com.kimboo.androidjobsnewsletter.interactors.GetJobsInteractor
import com.kimboo.androidjobsnewsletter.repository.JobsNetworkRepository

import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    @ActivityScope
    fun provideGetJobs(jobsNetworkRepository: JobsNetworkRepository): GetJobs {
        return GetJobsInteractor(jobsNetworkRepository)
    }

}
