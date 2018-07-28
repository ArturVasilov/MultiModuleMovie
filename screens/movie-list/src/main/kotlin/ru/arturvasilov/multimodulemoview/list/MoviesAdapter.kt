package ru.arturvasilov.multimodulemoview.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.arturvasilov.multimodulemovie.coreui.ImageSize
import ru.arturvasilov.multimodulemovie.coreui.loadMoviePoster
import ru.arturvasilov.multimodulemovie.coreui.Movie

internal class MoviesAdapter(private val imageWidth: Int,
                             private val imageHeight: Int,
                             private val itemClickListener: (Movie, position: Int, ImageView) -> Unit) : RecyclerView.Adapter<MovieHolder>() {

    private val movies: MutableList<Movie> = ArrayList()

    private val internalClickListener = View.OnClickListener {
        val movie = it.tag as Movie
        val position = movies.indexOf(movie)
        if (position >= 0) {
            itemClickListener.invoke(movie, position, it as ImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(imageWidth, imageHeight)
        imageView.setOnClickListener(internalClickListener)
        return MovieHolder(imageView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = movies[position]
        holder.image.loadMoviePoster(movie, ImageSize.SMALL)
        holder.image.tag = movie
    }

    override fun getItemCount(): Int = movies.size

    fun setMovies(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }
}

class MovieHolder(val image: ImageView) : RecyclerView.ViewHolder(image)