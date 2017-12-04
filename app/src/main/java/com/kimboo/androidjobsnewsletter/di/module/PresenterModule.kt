package com.kimboo.androidjobsnewsletter.di.module

import android.content.Context
import com.kimboo.androidjobsnewsletter.di.ActivityScope
import com.kimboo.androidjobsnewsletter.interactors.GetJobs
import com.kimboo.androidjobsnewsletter.ui.main.MainPresenter
import com.kimboo.androidjobsnewsletter.ui.main.MainPresenterImp
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @ActivityScope
    @Provides
    fun provideMainPresenter(context: Context, getJobs: GetJobs) : MainPresenter = MainPresenterImp(context, getJobs)

}
