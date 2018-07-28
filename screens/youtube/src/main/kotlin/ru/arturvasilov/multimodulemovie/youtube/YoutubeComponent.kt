package ru.arturvasilov.multimodulemovie.youtube

import dagger.Component
import ru.arturvasilov.multimodulemovie.core.YoutubeScope

fun createYoutubeComponent(): YoutubeComponent = DaggerYoutubeComponent.builder()
        .youtubeModule(YoutubeModule())
        .build()

@YoutubeScope
@Component(modules = [YoutubeModule::class])
interface YoutubeComponent {
    fun provideYoutubePlayerApi(): YoutubePlayerApi
}