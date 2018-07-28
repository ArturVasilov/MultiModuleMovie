package ru.arturvasilov.multimodulemovie

import android.app.Application
import ru.arturvasilov.multimodulemovie.api.DaggerMovieApiComponent
import ru.arturvasilov.multimodulemovie.api.MovieApiComponent
import ru.arturvasilov.multimodulemovie.api.MovieApiComponentProvider
import ru.arturvasilov.multimodulemovie.api.MovieApiModule
import ru.arturvasilov.multimodulemovie.coreui.MainTools
import ru.arturvasilov.multimodulemovie.coreui.MainToolsProvider
import ru.arturvasilov.multimodulemovie.network.DaggerNetworkComponent
import ru.arturvasilov.multimodulemovie.network.NetworkModule
import ru.arturvasilov.multimodulemovie.youtube.createYoutubeComponent

@Suppress("unused")
class App : Application(), AppComponentProvider, MovieApiComponentProvider, MainToolsProvider {

    private lateinit var movieApiComponent: MovieApiComponent

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        val networkComponent = DaggerNetworkComponent.builder()
                .networkModule(NetworkModule())
                .build()

        movieApiComponent = DaggerMovieApiComponent.builder()
                .movieApiModule(MovieApiModule(applicationContext))
                .networkComponent(networkComponent)
                .build()

        appComponent = DaggerAppComponent.builder()
                .mainToolsModule(MainToolsModule())
                .youtubeComponent(createYoutubeComponent())
                .build()
    }

    override fun provideAppComponent(): AppComponent = appComponent

    override fun provideMovieApiComponent(): MovieApiComponent = movieApiComponent

    override fun provideMainTools(): MainTools = appComponent
}