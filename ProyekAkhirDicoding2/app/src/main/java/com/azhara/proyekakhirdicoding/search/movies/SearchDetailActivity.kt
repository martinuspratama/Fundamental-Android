package com.azhara.proyekakhirdicoding.search.movies

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesHelper
import com.azhara.proyekakhirdicoding.helper.MappingHelper
import com.azhara.proyekakhirdicoding.model.MovieFavoriteDetail
import com.azhara.proyekakhirdicoding.search.movies.model.SearchMoviesModel
import com.azhara.proyekakhirdicoding.widget.MoviesStackAppWidget
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_search_detail.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SearchDetailActivity : AppCompatActivity() {

    private lateinit var movieFavoriteDetail: MovieFavoriteDetail
    private lateinit var searchMoviesModel: SearchMoviesModel
    private lateinit var moviesHelper: MoviesHelper
    private var isFavorite: Boolean = false
    private lateinit var menuIcon: Menu

    companion object {
        const val EXTRA_DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail)

        moviesHelper = MoviesHelper.getInstance(this)
        moviesHelper.open()

        supportActionBar?.title = resources.getString(R.string.detail_search_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        searchMoviesModel = intent.getParcelableExtra(EXTRA_DATA)
        setData(searchMoviesModel)
        favoriteChecked()

    }

    @SuppressLint("SimpleDateFormat")
    private fun setData(data: SearchMoviesModel) {
        movieFavoriteDetail = MovieFavoriteDetail()

        // Change date format
        val date = data.release
        val changeTipe = SimpleDateFormat("yyyy-MM-dd")
        val format = changeTipe.parse(date)
        val changeFormat = SimpleDateFormat("d MMMM, yyyy")
        val releaseDate = changeFormat.format(format)

        val posterPathUrl = "https://image.tmdb.org/t/p/w342${data.poster}"
        val backgroundUrl = "https://image.tmdb.org/t/p/original${data.background}"

        movieFavoriteDetail.title = data.title
        movieFavoriteDetail.background = backgroundUrl
        movieFavoriteDetail.poster = posterPathUrl
        movieFavoriteDetail.vote = data.vote
        movieFavoriteDetail.rating = data.rating
        movieFavoriteDetail.release = releaseDate
        movieFavoriteDetail.overview = data.overview


        // Get val vote average and change to ratingbar
        val getValueRating = movieFavoriteDetail.rating?.toFloat()
        val round = getValueRating?.roundToInt()
        val rating = round?.toFloat()?.div(2)

        tv_title_movies_search_detail.text = movieFavoriteDetail.title
        tv_overview_movies_search_detail.text = movieFavoriteDetail.overview
        tv_rating_movies_search_detail.text = movieFavoriteDetail.rating
        if (rating != null) {
            rating_movies_search_detail.rating = rating
        }
        tv_release_search_movies_detail.text = movieFavoriteDetail.release
        tv_vote_movies_search_detail.text = movieFavoriteDetail.vote
        Glide.with(this)
            .load(posterPathUrl)
            .centerCrop()
            .into(img_poster_search_movies_detail)

        Glide.with(this)
            .load(backgroundUrl)
            .centerCrop()
            .into(img_movies_search_background_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuIcon = menu
        setIcon()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_favoritee -> {
                isFavorite = false
                setIcon()
                deleteDataFromDatabase()
                sendBroadCastWidget(this)
            }
            R.id.btn_unfavorite -> {
                isFavorite = true
                setDataToDatabase()
                setIcon()
                sendBroadCastWidget(this)
            }
            android.R.id.home -> {
                onBackPressed()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendBroadCastWidget(context: Context) {
        val intent = Intent(context, MoviesStackAppWidget::class.java)
        intent.action = MoviesStackAppWidget.EXTRA_UPDATE
        context.sendBroadcast(intent)
    }

    private fun setIcon() {
        if (isFavorite) {
            menuIcon.getItem(0).isVisible = false
            menuIcon.getItem(1).isVisible = true
        } else {
            menuIcon.getItem(0).isVisible = true
            menuIcon.getItem(1).isVisible = false
        }
    }

    private fun favoriteChecked() {
        val cursor = moviesHelper.queryAll()
        val mapCursor = MappingHelper.mapCursorToArrayListMovies(cursor)

        for (movies in mapCursor) {
            if (searchMoviesModel.title == movies.title) {
                isFavorite = true
            }

            if (isFavorite) break
        }
    }

    private fun setDataToDatabase() {
        try {
            val values = ContentValues()
            values.put(MoviesDatabaseContract.MoviesColumn.TITLE, movieFavoriteDetail.title)
            values.put(MoviesDatabaseContract.MoviesColumn.OVERVIEW, movieFavoriteDetail.overview)
            values.put(MoviesDatabaseContract.MoviesColumn.RELEASE, movieFavoriteDetail.release)
            values.put(MoviesDatabaseContract.MoviesColumn.RATING, movieFavoriteDetail.rating)
            values.put(MoviesDatabaseContract.MoviesColumn.VOTE, movieFavoriteDetail.vote)
            values.put(MoviesDatabaseContract.MoviesColumn.POSTER, movieFavoriteDetail.poster)
            values.put(
                MoviesDatabaseContract.MoviesColumn.BACKGROUND,
                movieFavoriteDetail.background
            )
            moviesHelper.insert(values)
            Toasty.success(this, "Add to Favorite", Toasty.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toasty.error(this, "Error to favorite: $e", Toasty.LENGTH_SHORT).show()
        }
    }

    private fun deleteDataFromDatabase() {
        try {
            movieFavoriteDetail.title?.let { moviesHelper.deleteByTitle(it) }
            Toasty.success(this, "Remove from favorite", Toasty.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toasty.error(this, "Error remove from favorite: $e", Toasty.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesHelper.close()
    }
}
