package ru.arturvasilov.multimodulemovie.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.arturvasilov.multimodulemovie.api.MovieRepository
import ru.arturvasilov.multimodulemovie.coreui.Video

@MainThread
class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private var movieLiveData: MutableLiveData<List<Video>>? = null

    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    fun getVideos(movieId: Int): LiveData<List<Video>>? {
        if (movieLiveData == null) {
            movieLiveData = MutableLiveData()
            movieRepository.movieVideos(movieId)
                    .map { it ->
                        val videos = ArrayList<Video>()
                        it.forEach { videos.add(Video(it.videoId, it.site, it.name)) }
                        if (videos.size > 3) {
                            return@map videos.subList(0, 3)
                        }
                        return@map videos
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { movie -> movieLiveData?.setValue(movie) },
                            { errorLiveData.value = it }
                    )
        }
        return movieLiveData
    }

    fun getError(): LiveData<Throwable> = errorLiveData
}