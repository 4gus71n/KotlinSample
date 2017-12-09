package com.kimboo.androidjobsnewsletter.di.module

import com.kimboo.androidjobsnewsletter.retrofit.services.GenericWebscrappingService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton
/**
 * Created by Agustin Tomas Larghi on 9/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
@Module
class RetrofitServiceModule {

    @Provides
    @Singleton
    fun provideGenericWebscrappingService(retrofit: Retrofit): GenericWebscrappingService {
        return retrofit.create(GenericWebscrappingService::class.java)
    }

}

