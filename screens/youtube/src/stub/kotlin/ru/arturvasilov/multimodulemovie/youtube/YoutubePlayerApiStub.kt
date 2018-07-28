package ru.arturvasilov.multimodulemovie.youtube

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri

internal class YoutubePlayerApiStub : YoutubePlayerApi {

    override fun isEnabled(): Boolean = false

    override fun playVideo(activity: Activity, videoId: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        intent.putExtra("VIDEO_ID", videoId)
        try {
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // shit happens sometimes..
        }
    }
}