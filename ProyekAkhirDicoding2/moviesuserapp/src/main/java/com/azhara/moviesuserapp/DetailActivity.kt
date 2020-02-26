package com.azhara.moviesuserapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.azhara.moviesuserapp.db.movie.MoviesDatabaseContract.MoviesColumn.Companion.CONTENT_URI
import com.azhara.moviesuserapp.model.MoviesModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailActivity : AppCompatActivity() {

    private lateinit var moviesModel: MoviesModel
    private lateinit var uriId: Uri

    companion object {
        const val EXTRA_DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        moviesModel = intent.getParcelableExtra(EXTRA_DATA)
        uriId = Uri.parse(CONTENT_URI.toString() + "/" + moviesModel.id)
        setData(moviesModel)
    }

    private fun setData(data: MoviesModel) {

        // Get val vote average and change to ratingbar
        val getValueRating = data.rating?.toFloat()
        val round = getValueRating?.roundToInt()
        val rating = round?.toFloat()?.div(2)

        tv_user_title_movies_detail.text = data.title
        tv_user_overview_movies_detail.text = data.overview
        tv_user_vote_movies_detail.text = "${data.vote} votes"
        if (rating != null) {
            rating_user_movies_detail.rating = rating
        }
        tv_user_release_movies_detail.text = data.release
        tv_user_rating_movies_detail.text = data.rating
        Glide.with(this)
            .load(data.background)
            .centerCrop()
            .into(img_user_movies_background)
        Glide.with(this)
            .load(data.poster)
            .centerCrop()
            .into(img_user_movies_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
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
