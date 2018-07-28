package ru.arturvasilov.multimodulemovie

import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.arturvasilov.multimodulemovie.core.ApplicationScope
import ru.arturvasilov.multimodulemovie.coreui.MainTools
import ru.arturvasilov.multimodulemovie.coreui.Router
import ru.arturvasilov.multimodulemovie.youtube.YoutubeComponent
import ru.arturvasilov.multimodulemovie.youtube.YoutubePlayerApi

interface AppComponentProvider {
    fun provideAppComponent(): AppComponent
}

fun Context.findAppComponent(): AppComponent {
    if (applicationContext is AppComponentProvider) {
        return (applicationContext as AppComponentProvider).provideAppComponent()
    }
    throw NullPointerException("Failed to find provider for NetworkComponent")
}

@ApplicationScope
@Component(modules = [MainToolsModule::class], dependencies = [YoutubeComponent::class])
interface AppComponent : MainTools, YoutubeComponent

@Module
class MainToolsModule {

    @ApplicationScope
    @Provides
    fun provideRouter(youtubePlayerApi: YoutubePlayerApi): Router = AppRouter(youtubePlayerApi)
}