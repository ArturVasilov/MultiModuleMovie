package ru.arturvasilov.multimodulemovie.youtube

import android.app.Activity

internal class YoutubePlayerApiImpl : YoutubePlayerApi {

    override fun isEnabled(): Boolean = true

    override fun playVideo(activity: Activity, videoId: String) {
        YoutubeVideoActivity.start(activity, videoId)
    }
}