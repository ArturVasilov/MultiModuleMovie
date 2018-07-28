package ru.arturvasilov.multimodulemovie

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.widget.ImageView
import ru.arturvasilov.multimodulemovie.coreui.Movie
import ru.arturvasilov.multimodulemovie.coreui.Router
import ru.arturvasilov.multimodulemovie.details.EXTRA_MOVIE
import ru.arturvasilov.multimodulemovie.details.IMAGE
import ru.arturvasilov.multimodulemovie.details.MovieDetailsActivity
import ru.arturvasilov.multimodulemovie.youtube.YoutubePlayerApi

class AppRouter(private val youtubePlayerApi: YoutubePlayerApi) : Router {

    override fun showMovieDetails(activity: Activity, imageView: ImageView, movie: Movie) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView, IMAGE)
        ActivityCompat.startActivity(activity, intent, options.toBundle())
    }

    override fun playMovieTrailer(activity: Activity, videoId: String) {
        youtubePlayerApi.playVideo(activity, videoId)
    }
}