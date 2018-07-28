package ru.arturvasilov.multimodulemovie.coreui

import android.app.Activity
import android.widget.ImageView

interface Router {
    fun showMovieDetails(activity: Activity, imageView: ImageView, movie: Movie)

    fun playMovieTrailer(activity: Activity, videoId: String)
}