package ru.arturvasilov.multimodulemoview.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.arturvasilov.multimodulemovie.api.MovieRepository
import ru.arturvasilov.multimodulemovie.coreui.Movie
import ru.arturvasilov.multimodulemovie.coreui.preloadMoviePoster
import java.util.*
import kotlin.collections.ArrayList

@MainThread
class MovieListViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private var moviesLiveData: MutableLiveData<List<Movie>>? = null

    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    fun getMoviesList(): LiveData<List<Movie>>? {
        if (moviesLiveData == null) {
            moviesLiveData = MutableLiveData()
            loadMovies()
        }
        return moviesLiveData
    }

    fun reloadMovies() {
        loadMovies()
    }

    private fun loadMovies() {
        movieRepository.popularMovies()
                .map { movies ->
                    val result = ArrayList(movies)
                    result.sortWith(Comparator { first, second -> second.id - first.id })
                    return@map result
                }
                .toObservable()
                .flatMapIterable { it -> it }
                .map {
                    Movie(it.id, it.posterPath, it.overview, it.title,
                            it.releasedDate, it.voteAverage)
                }
                .doOnNext { preloadMoviePoster(it) }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { movies -> moviesLiveData?.setValue(movies) },
                        { errorLiveData.value = it }
                )
    }

    fun getError(): LiveData<Throwable> = errorLiveData
}