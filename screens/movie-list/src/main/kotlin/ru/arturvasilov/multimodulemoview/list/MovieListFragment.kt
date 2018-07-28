package ru.arturvasilov.multimodulemoview.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.arturvasilov.multimodulemovie.api.findMovieApiComponent
import ru.arturvasilov.multimodulemovie.coreui.Movie
import ru.arturvasilov.multimodulemovie.coreui.Router
import ru.arturvasilov.multimodulemovie.coreui.findMainTools
import javax.inject.Inject

class MovieListFragment : Fragment() {

    @Inject
    lateinit var movieListViewModel: MovieListViewModel

    @Inject
    lateinit var router: Router

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var loadingView: View
    private lateinit var errorView: View

    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerMovieListFragmentComponent.builder()
                .movieListModule(MovieListModule(this))
                .movieApiComponent(requireContext().findMovieApiComponent())
                .mainTools(requireContext().findMainTools())
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)

        movieListViewModel.getMoviesList()?.observe(this, Observer { showMovies(it!!) })
        movieListViewModel.getError().observe(this, Observer { showError() })
    }

    private fun bindViews(rootView: View) {
        moviesRecyclerView = rootView.findViewById(R.id.moviesRecycler)
        val columns = resources.getInteger(R.integer.columns_count)
        moviesRecyclerView.layoutManager = GridLayoutManager(requireContext(), columns)

        val imageWidth = resources.displayMetrics.widthPixels / columns

        val typedValue = TypedValue()
        resources.getValue(R.dimen.rows_count, typedValue, true)
        val rowsCount = typedValue.float
        val actionBarHeight = requireActivity().theme.run {
            if (resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
                TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
            } else {
                0
            }
        }

        val imageHeight = ((resources.displayMetrics.heightPixels - actionBarHeight) / rowsCount).toInt()

        adapter = MoviesAdapter(imageWidth, imageHeight) { movie, _, imageView ->
            router.showMovieDetails(requireActivity(), imageView, movie)
        }
        moviesRecyclerView.adapter = adapter

        loadingView = rootView.findViewById(R.id.loadingView)
        errorView = rootView.findViewById(R.id.errorView)
        errorView.setOnClickListener {
            showLoading()
            movieListViewModel.reloadMovies()
        }
    }

    private fun showLoading() {
        moviesRecyclerView.visibility = View.GONE
        errorView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
    }

    private fun showMovies(movies: List<Movie>) {
        adapter.setMovies(movies)
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
        moviesRecyclerView.visibility = View.VISIBLE
    }

    private fun showError() {
        moviesRecyclerView.visibility = View.GONE
        loadingView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }
}