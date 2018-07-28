package ru.arturvasilov.multimodulemovie.youtube

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

class YoutubeVideoActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

    companion object {
        private const val YOUTUBE_VIDEO_ID_KEY = "YOUTUBE_VIDEO_ID_KEY"

        fun start(activity: Activity, videoId: String) {
            val intent = Intent(activity, YoutubeVideoActivity::class.java)
            intent.putExtra(YOUTUBE_VIDEO_ID_KEY, videoId)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.youtube_enter_animation, 0)
        setContentView(R.layout.youtube_activity_layout)

        val playerFragment = supportFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerSupportFragment
        playerFragment.initialize(BuildConfig.YOUTUBE_API_KEY, this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, wasRestored: Boolean) {
        val videoId = intent.getStringExtra(YOUTUBE_VIDEO_ID_KEY)
        if (TextUtils.isEmpty(videoId)) {
            onInitializationFailure(provider, YouTubeInitializationResult.INTERNAL_ERROR)
            return
        }

        if (!wasRestored) {
            player.loadVideo(videoId)
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
        // show error
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.youtube_exit_animation)
    }
}