package com.azhara.proyekakhirdicoding.ui.favorite.movies

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract.MoviesColumn.Companion.CONTENT_URI
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesHelper
import com.azhara.proyekakhirdicoding.model.MovieFavoriteDetail
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_favorite_detail.*
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FavoriteMoviesDetailActivity : AppCompatActivity() {

    private lateinit var moviesData: MovieFavoriteDetail
    private lateinit var uriId: Uri
    private var isEdit = false

    private lateinit var moviesHelper: MoviesHelper


    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorite_detail)
        supportActionBar?.title = resources.getString(R.string.detail_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        moviesData = intent.getParcelableExtra(EXTRA_DATA)
        Log.e("Data movies", moviesData.id.toString())

        uriId = Uri.parse(CONTENT_URI.toString() + "/" + moviesData.id)
        Log.e("URI", uriId.lastPathSegment.toString())



        setData(moviesData)


    }

    @SuppressLint("SimpleDateFormat")
    private fun setData(data: MovieFavoriteDetail) {

        // Get val vote average and change to ratingbar
        val getValueRating = data.rating?.toFloat()
        val round = getValueRating?.roundToInt()
        val rating = round?.toFloat()?.div(2)

        tv_favorite_title_movies_detail.text = data.title
        tv_favorite_overview_movies_detail.text = data.overview
        tv_favorite_vote_movies_detail.text = "${data.vote} votes"
        if (rating != null) {
            rating_favorite_movies_detail.rating = rating
        }
        tv_favorite_release_movies_detail.text = data.release
        tv_favorite_rating_movies_detail.text = data.rating
        Glide.with(this)
            .load(data.background)
            .centerCrop()
            .into(img_favorite_movies_background)
        Glide.with(this)
            .load(data.poster)
            .centerCrop()
            .into(img_favorite_movies_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_favorite -> {
                showAlertDelete()
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDelete() {
        val dialogtitle = resources.getString(R.string.remove_film_alert_title)
        val dialogMessage = resources.getString(R.string.remove_film_alert_sub)
        val positiveButton = resources.getString(R.string.delete)
        val negativeButton = resources.getString(R.string.cancel)

        val materialDialog = AlertDialog.Builder(this)
            .setTitle(dialogtitle)
            .setMessage(dialogMessage)
            .setPositiveButton(positiveButton) { _, _ ->
                contentResolver.delete(uriId, null, null)
                Log.e("Favorite Detail", uriId.toString())
                finish()
            }
            .setNegativeButton(negativeButton) { dialog, _ ->
                dialog.cancel()
            }
            .create()

        materialDialog.show()
    }
}
