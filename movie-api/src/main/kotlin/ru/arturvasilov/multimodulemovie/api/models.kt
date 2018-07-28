package ru.arturvasilov.multimodulemovie.api

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class MoviesResponse {

    @SerializedName("results")
    private val movies: List<Movie>? = null

    fun getMovies(): List<Movie> {
        return movies ?: ArrayList()
    }
}

class VideosResponse {

    @SerializedName("results")
    private val videos: List<Video>? = null

    fun getVideos(): List<Video> {
        return videos ?: ArrayList()
    }
}