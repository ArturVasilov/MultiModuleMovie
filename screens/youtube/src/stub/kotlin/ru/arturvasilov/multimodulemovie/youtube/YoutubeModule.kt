package ru.arturvasilov.multimodulemovie.youtube

import dagger.Module
import dagger.Provides
import ru.arturvasilov.multimodulemovie.core.YoutubeScope

@Module
class YoutubeModule {

    @YoutubeScope
    @Provides
    fun provideYoutubePlayerApi(): YoutubePlayerApi = YoutubePlayerApiStub()
}