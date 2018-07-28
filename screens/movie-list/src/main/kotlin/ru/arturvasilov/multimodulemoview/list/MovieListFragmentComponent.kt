package ru.arturvasilov.multimodulemoview.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.arturvasilov.multimodulemovie.api.MovieApiComponent
import ru.arturvasilov.multimodulemovie.api.MovieRepository
import ru.arturvasilov.multimodulemovie.core.FragmentScope
import ru.arturvasilov.multimodulemovie.coreui.BaseViewModelProviderFactory
import ru.arturvasilov.multimodulemovie.coreui.MainTools

@FragmentScope
@Component(modules = [MovieListModule::class], dependencies = [MovieApiComponent::class, MainTools::class])
internal interface MovieListFragmentComponent {
    fun inject(movieListFragment: MovieListFragment)
}

@Module
internal class MovieListModule(private val movieListFragment: MovieListFragment) {

    @FragmentScope
    @Provides
    fun provideMovieListViewModel(factory: MovieListViewModelFactory): MovieListViewModel {
        return ViewModelProviders.of(movieListFragment, factory).get(MovieListViewModel::class.java)
    }

    @FragmentScope
    @Provides
    fun provideViewModelFactory(movieRepository: MovieRepository) = MovieListViewModelFactory(movieRepository)
}

internal class MovieListViewModelFactory(private val movieRepository: MovieRepository) : BaseViewModelProviderFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MovieListViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return MovieListViewModel(movieRepository) as T
        }

        return super.create(modelClass)
    }
}