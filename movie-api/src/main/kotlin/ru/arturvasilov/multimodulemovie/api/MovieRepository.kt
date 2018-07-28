package ru.arturvasilov.multimodulemovie.api

import io.reactivex.Single

interface MovieRepository {
    fun popularMovies(): Single<List<Movie>>

    fun movieVideos(movieId: Int): Single<List<Video>>
}

internal class MovieRepositoryImpl(private val movieService: MovieService,
                                   private val moviesDao: MoviesDao) : MovieRepository {

    override fun popularMovies(): Single<List<Movie>> {
        return movieService.popularMovies()
                .map { it.getMovies() }
                .map {
                    val currentMovies = moviesDao.movies()
                    moviesDao.clear(currentMovies)
                    moviesDao.saveAll(it)
                    return@map it
                }
                .onErrorResumeNext {
                    val movies = moviesDao.movies()

                    return@onErrorResumeNext if (movies.isEmpty()) {
                        Single.error(it)
                    } else {
                        Single.just(movies)
                    }
                }

    }

    override fun movieVideos(movieId: Int): Single<List<Video>> {
        return movieService.movieVideos(movieId)
                .map { it.getVideos() }
    }
}