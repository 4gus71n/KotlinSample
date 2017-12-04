package com.kimboo.androidjobsnewsletter

import android.app.Application
import com.kimboo.androidjobsnewsletter.di.component.AppComponent
import com.kimboo.androidjobsnewsletter.di.component.DaggerAppComponent
import com.kimboo.androidjobsnewsletter.di.component.DaggerViewInjectorComponent
import com.kimboo.androidjobsnewsletter.di.component.ViewInjectorComponent
import com.kimboo.androidjobsnewsletter.di.module.AppModule

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */

class NewsletterApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        viewInjector = DaggerViewInjectorComponent.builder().appComponent(appComponent).build();
    }



    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
        @JvmStatic lateinit var viewInjector: ViewInjectorComponent

        lateinit var instance: NewsletterApplication
            private set
    }
}