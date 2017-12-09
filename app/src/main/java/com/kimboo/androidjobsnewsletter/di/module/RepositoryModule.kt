package com.kimboo.androidjobsnewsletter.di.module

import com.kimboo.androidjobsnewsletter.repository.JobsNetworkRepository
import com.kimboo.androidjobsnewsletter.repository.JobsNetworkRepositoryImp
import com.kimboo.androidjobsnewsletter.retrofit.services.GenericWebscrappingService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesJobsNetworkRepository(genericWebscrappingService: GenericWebscrappingService) : JobsNetworkRepository {
        return JobsNetworkRepositoryImp(genericWebscrappingService)
    }

}
