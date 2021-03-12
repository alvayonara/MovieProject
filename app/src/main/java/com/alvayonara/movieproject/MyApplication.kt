package com.alvayonara.movieproject

import android.app.Application
import com.alvayonara.movieproject.core.di.CoreComponent
import com.alvayonara.movieproject.core.di.DaggerCoreComponent
import com.alvayonara.movieproject.di.AppComponent
import com.alvayonara.movieproject.di.DaggerAppComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}