package com.kimboo.androidjobsnewsletter.di.module

import android.preference.PreferenceManager
import com.kimboo.androidjobsnewsletter.NewsletterApplication
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class AppModule(private val mApplication: NewsletterApplication) {

    @Provides
    @Singleton
    fun providesContext() = mApplication.applicationContext

    @Provides
    @Singleton
    fun providesNewsletterApplication() = mApplication

    @Provides
    @Singleton
    fun provideSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(mApplication)

    @Provides
    @Singleton
    fun provideNetworkScheduler() : Scheduler = Schedulers.io()

    @Provides
    @Singleton
    fun provideDatabaseScheduler() : Scheduler = Schedulers.computation()

}
