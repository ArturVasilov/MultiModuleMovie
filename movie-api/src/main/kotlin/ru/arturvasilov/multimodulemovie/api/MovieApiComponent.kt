package ru.arturvasilov.multimodulemovie.api

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.arturvasilov.multimodulemovie.core.MovieApiScope
import ru.arturvasilov.multimodulemovie.network.NetworkComponent

interface MovieApiComponentProvider {
    fun provideMovieApiComponent(): MovieApiComponent
}

fun Context.findMovieApiComponent(): MovieApiComponent {
    if (applicationContext is MovieApiComponentProvider) {
        return (applicationContext as MovieApiComponentProvider).provideMovieApiComponent()
    }
    throw NullPointerException("Failed to find provider for MovieApiComponent")
}

@MovieApiScope
@Component(modules = [MovieApiModule::class], dependencies = [(NetworkComponent::class)])
interface MovieApiComponent {
    fun provideMovieRepository(): MovieRepository
}

@Module
class MovieApiModule(private val context: Context) {

    @MovieApiScope
    @Provides
    fun provideMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)!!

    @MovieApiScope
    @Provides
    fun provideMovieDatabase(): MoviesDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java,
                "movies-database"
        ).build()
    }

    @MovieApiScope
    @Provides
    fun provideMovieDao(moviesDatabase: MoviesDatabase): MoviesDao {
        return moviesDatabase.moviesDao()
    }

    @MovieApiScope
    @Provides
    fun provideMovieRepository(movieService: MovieService, moviesDao: MoviesDao): MovieRepository {
        return MovieRepositoryImpl(movieService, moviesDao)
    }
}