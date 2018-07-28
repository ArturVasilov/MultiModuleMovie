package ru.arturvasilov.multimodulemovie.details

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.transition.Slide
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import ru.arturvasilov.multimodulemovie.api.findMovieApiComponent
import ru.arturvasilov.multimodulemovie.coreui.*
import javax.inject.Inject

const val IMAGE = "image"
const val EXTRA_MOVIE = "extraMovie"

private const val MAXIMUM_RATING = "10"

class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var movieDetailsViewModel: MovieDetailsViewModel

    @Inject
    lateinit var router: Router

    private var contentLayout: LinearLayout? = null

    private var titleTextView: TextView? = null
    private var overviewTextView: TextView? = null
    private var ratingTextView: TextView? = null

    private var videosProgress: View? = null
    private var videosTitle: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareWindowForAnimation()
        setContentView(R.layout.activity_movie_details)

        DaggerMovieDetailsComponent.builder()
                .movieDetailsModule(MovieDetailsModule(this))
                .movieApiComponent(findMovieApiComponent())
                .mainTools(findMainTools())
                .build()
                .inject(this)

        contentLayout = findViewById(R.id.content_layout)

        titleTextView = findViewById(R.id.title)
        overviewTextView = findViewById(R.id.overview)
        ratingTextView = findViewById(R.id.rating)

        videosProgress = findViewById(R.id.videos_progress)
        videosTitle = findViewById(R.id.videos_title)

        ViewCompat.setTransitionName(findViewById(R.id.app_bar), IMAGE)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        showMovie(movie)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun prepareWindowForAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transition = Slide()
            transition.excludeTarget(android.R.id.statusBarBackground, true)
            window.statusBarColor = Color.TRANSPARENT
            window.enterTransition = transition
            window.returnTransition = transition
        }
    }

    private fun showMovie(movie: Movie) {
        showToolbarTitle(getString(R.string.movie_details))

        val image = findViewById<View>(R.id.image) as ImageView
        image.loadMoviePoster(movie, ImageSize.LARGE)

        val year = movie.releasedDate.substring(0, 4)
        titleTextView?.text = getString(R.string.movie_title, movie.title, year)
        overviewTextView?.text = movie.overview

        showRating(movie)

        movieDetailsViewModel.getVideos(movie.id)?.observe(this, Observer {
            contentLayout?.removeView(videosProgress)
            videosTitle?.visibility = View.VISIBLE

            showVideos(it!!)
        })

        movieDetailsViewModel.getError().observe(this, Observer {
            contentLayout?.removeView(videosProgress)
            Toast.makeText(this, R.string.error_loading_videos, Toast.LENGTH_LONG).show()
        })
    }

    private fun showToolbarTitle(title: String) {
        val toolbarLayout = findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
        toolbarLayout.title = title
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
    }

    private fun showRating(movie: Movie) {
        var average = movie.voteAverage.toString()
        average = if (average.length > 3) average.substring(0, 3) else average
        average = if (average.length == 3 && average[2] == '0') average.substring(0, 1) else average
        ratingTextView?.text = getString(R.string.rating, average, MAXIMUM_RATING)
    }

    private fun showVideos(videos: List<Video>) {
        videos.forEach {
            val view = buildViewForVideo(it)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            contentLayout?.addView(view, layoutParams)
        }
    }

    private fun buildViewForVideo(video: Video): View {
        val view = TextView(this)
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        view.gravity = Gravity.CENTER_VERTICAL
        val text = SpannableString(video.name)
        text.setSpan(UnderlineSpan(), 0, video.name.length, 0)
        view.text = text
        view.setOnClickListener { router.playMovieTrailer(this, video.videoId) }
        val verticalPadding = dpToPx(4f, resources.displayMetrics)
        view.setPadding(0, verticalPadding, 0, verticalPadding)
        return view
    }
}