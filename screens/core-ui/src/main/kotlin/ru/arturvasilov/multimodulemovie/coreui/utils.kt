package ru.arturvasilov.multimodulemovie.coreui

import android.support.annotation.Dimension
import android.support.annotation.Px
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

enum class ImageSize(val size: String) {
    SMALL("w185"),
    LARGE("w780")
}

fun ImageView.loadMoviePoster(movie: Movie, imageSize: ImageSize) {
    createMovieImageRequest(movie, imageSize).into(this)
}

fun preloadMoviePoster(movie: Movie) {
    createMovieImageRequest(movie, ImageSize.SMALL).fetch()
}

private fun createMovieImageRequest(movie: Movie, imageSize: ImageSize): RequestCreator {
    val url = BuildConfig.IMAGES_BASE_URL + imageSize.size + movie.posterPath
    return Picasso.get()
            .load(url)
            .noFade()
}

@Px
fun dpToPx(@Dimension(unit = Dimension.DP) dp: Float, metrics: DisplayMetrics): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
}