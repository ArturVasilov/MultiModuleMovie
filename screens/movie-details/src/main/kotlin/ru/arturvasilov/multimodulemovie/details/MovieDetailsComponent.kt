package ru.arturvasilov.multimodulemovie.details

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.arturvasilov.multimodulemovie.api.MovieApiComponent
import ru.arturvasilov.multimodulemovie.api.MovieRepository
import ru.arturvasilov.multimodulemovie.core.ActivityScope
import ru.arturvasilov.multimodulemovie.coreui.BaseViewModelProviderFactory
import ru.arturvasilov.multimodulemovie.coreui.MainTools

@ActivityScope
@Component(modules = [MovieDetailsModule::class], dependencies = [MovieApiComponent::class, MainTools::class])
internal interface MovieDetailsComponent {
    fun inject(activity: MovieDetailsActivity)
}

@Module
internal class MovieDetailsModule(private val activity: MovieDetailsActivity) {

    @ActivityScope
    @Provides
    fun provideMovieListViewModel(factory: MovieDetailsViewModelFactory): MovieDetailsViewModel {
        return ViewModelProviders.of(activity, factory).get(MovieDetailsViewModel::class.java)
    }

    @ActivityScope
    @Provides
    fun provideViewModelFactory(movieRepository: MovieRepository) = MovieDetailsViewModelFactory(movieRepository)
}

internal class MovieDetailsViewModelFactory(private val movieRepository: MovieRepository) : BaseViewModelProviderFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MovieDetailsViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return MovieDetailsViewModel(movieRepository) as T
        }

        return super.create(modelClass)
    }
}