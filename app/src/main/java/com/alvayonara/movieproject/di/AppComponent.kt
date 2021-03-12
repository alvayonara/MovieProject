package com.alvayonara.movieproject.di

import com.alvayonara.movieproject.core.di.CoreComponent
import com.alvayonara.movieproject.ui.dashboard.DashboardActivity
import com.alvayonara.movieproject.ui.detail.DetailActivity
import com.alvayonara.movieproject.ui.favorite.FavoriteActivity
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(activity: DashboardActivity)
    fun inject(activity: DetailActivity)
    fun inject(activity: FavoriteActivity)
}