package com.kimboo.androidjobsnewsletter.di.component


import com.kimboo.androidjobsnewsletter.di.ActivityScope
import com.kimboo.androidjobsnewsletter.di.module.InteractorModule
import com.kimboo.androidjobsnewsletter.di.module.PresenterModule
import com.kimboo.androidjobsnewsletter.ui.main.MainFragment
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(PresenterModule::class, InteractorModule::class))
interface ViewInjectorComponent {

    fun inject(fragment: MainFragment)

}
