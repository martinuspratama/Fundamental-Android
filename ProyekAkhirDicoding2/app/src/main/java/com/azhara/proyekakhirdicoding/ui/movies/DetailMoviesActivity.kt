package com.azhara.proyekakhirdicoding.ui.movies

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
import com.azhara.proyekakhirdicoding.ui.movies.model.MoviesModel
import com.azhara.proyekakhirdicoding.widget.MoviesStackAppWidget
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_detail_movies.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailMoviesActivity : AppCompatActivity() {

    private lateinit var moviesData: MoviesModel
    private lateinit var moviesDetail: MovieFavoriteDetail
    private lateinit var moviesHelper: MoviesHelper
    private var isFavorite: Boolean = false
    private lateinit var menuIcon: Menu

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movies)

        moviesHelper = MoviesHelper.getInstance(this)
        moviesHelper.open()

        supportActionBar?.title = "Detail Movies"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        moviesData = intent.getParcelableExtra(EXTRA_DATA)
        setData(moviesData)
        favoriteChecked()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setData(data: MoviesModel) {
        // Change date format
        val date = data.release
        val changeTipe = SimpleDateFormat("yyyy-MM-dd")
        val format = changeTipe.parse(date)
        val changeFormat = SimpleDateFormat("d MMMM, yyyy")
        val releaseDate = changeFormat.format(format)

        moviesDetail = MovieFavoriteDetail()
        moviesDetail.title = data.title
        moviesDetail.overview = data.overview
        moviesDetail.release = releaseDate
        moviesDetail.rating = data.rating
        moviesDetail.vote = data.vote
        moviesDetail.poster = data.poster
        moviesDetail.background = data.background

        // Get val vote average and change to ratingbar
        val getValueRating = moviesDetail.rating?.toFloat()
        val round = getValueRating?.roundToInt()
        val rating = round?.toFloat()?.div(2)

        tv_title_movies_detail.text = moviesDetail.title
        tv_overview_movies_detail.text = moviesDetail.overview
        tv_vote_movies_detail.text = moviesDetail.vote
        if (rating != null) {
            rating_movies_detail.rating = rating
        }
        tv_release_movies_detail.text = moviesDetail.release
        tv_rating_movies_detail.text = moviesDetail.rating
        Glide.with(this)
            .load(moviesDetail.background)
            .centerCrop()
            .into(img_movies_background_detail)
        Glide.with(this)
            .load(moviesDetail.poster)
            .centerCrop()
            .into(img_poster_movies_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuIcon = menu
        setIcon()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_unfavorite -> {
                isFavorite = true
                setDatatoDatabase()
                setIcon()
                sendBroadCastWidget(this)
            }
            R.id.btn_favoritee -> {
                isFavorite = false
                setIcon()
                deleteDataFromDatabase()
                sendBroadCastWidget(this)
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun sendBroadCastWidget(context: Context) {
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
            if (moviesData.title == movies.title) {
                isFavorite = true
            }

            if (isFavorite) break
        }
    }

    private fun setDatatoDatabase() {
        try {
            val values = ContentValues()
            values.put(MoviesDatabaseContract.MoviesColumn.TITLE, moviesDetail.title)
            values.put(MoviesDatabaseContract.MoviesColumn.OVERVIEW, moviesDetail.overview)
            values.put(MoviesDatabaseContract.MoviesColumn.RELEASE, moviesDetail.release)
            values.put(MoviesDatabaseContract.MoviesColumn.RATING, moviesDetail.rating)
            values.put(MoviesDatabaseContract.MoviesColumn.VOTE, moviesDetail.vote)
            values.put(MoviesDatabaseContract.MoviesColumn.POSTER, moviesDetail.poster)
            values.put(MoviesDatabaseContract.MoviesColumn.BACKGROUND, moviesDetail.background)
            moviesHelper.insert(values)
            Toasty.success(this, "Add to Favorite", Toasty.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toasty.error(this, "Error to favorite: $e", Toasty.LENGTH_SHORT).show()
        }
    }

    private fun deleteDataFromDatabase() {
        try {
            moviesDetail.title?.let { moviesHelper.deleteByTitle(it) }
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
