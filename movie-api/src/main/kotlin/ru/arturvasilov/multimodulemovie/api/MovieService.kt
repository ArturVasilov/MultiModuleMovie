package ru.arturvasilov.multimodulemovie.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movie/popular")
    fun popularMovies(): Single<MoviesResponse>

    @GET("movie/{movieId}/videos")
    fun movieVideos(@Path("movieId") movieId: Int): Single<VideosResponse>
}