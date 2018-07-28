package ru.arturvasilov.multimodulemovie.youtube

import android.app.Activity

interface YoutubePlayerApi {
    fun isEnabled(): Boolean

    fun playVideo(activity: Activity, videoId: String)
}