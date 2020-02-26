package com.azhara.proyekakhirdicoding.ui.favorite.tvshow

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.db.tvshowdatabase.TvShowHelper
import com.azhara.proyekakhirdicoding.model.TvShowFavoriteDetail
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_favorite_tv_show_detail.*
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FavoriteTvShowDetailActivity : AppCompatActivity() {

    private lateinit var tvData: TvShowFavoriteDetail
    private lateinit var tvShowHelper: TvShowHelper

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_tv_show_detail)

        tvShowHelper = TvShowHelper.getInstance(applicationContext)
        tvShowHelper.open()

        supportActionBar?.title = resources.getString(R.string.detail_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = resources.getString(R.string.detail_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvData = intent.getParcelableExtra(EXTRA_DATA)
        setData(tvData)
    }

    private fun setData(data: TvShowFavoriteDetail) {

        // Get val vote average and change to ratingbar
        val getValueRating = data.rating?.toFloat()
        val round = getValueRating?.roundToInt()
        val rating = round?.toFloat()?.div(2)

        tv_favorite_title_tvshow_detail.text = data.title
        tv_favorite_overview_tvshow_detail.text = data.overview
        tv_favorite_vote_tvshow_detail.text = "${data.vote} votes"
        if (rating != null) {
            rating_favorite_tvshow_detail.rating = rating
        }
        tv_favorite_release_tvshow_detail.text = data.release
        tv_favorite_rating_tvshow_detail.text = data.rating
        Glide.with(this)
            .load(data.background)
            .centerCrop()
            .into(img_favorite_tvshow_background)
        Glide.with(this)
            .load(data.poster)
            .centerCrop()
            .into(img_favorite_tvshow_detail)
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
                tvShowHelper.deleteByTitle(tvData.title.toString()).toLong()
                finish()
            }
            .setNegativeButton(negativeButton) { dialog, _ ->
                dialog.cancel()
            }
            .create()

        materialDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        tvShowHelper.close()
    }
}
