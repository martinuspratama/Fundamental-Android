package com.azhara.submission4dicoding.ui.favorite.tvshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVHelper
import com.azhara.submission4dicoding.ui.tvshow.model.TvShowModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_fav_tv_show.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailFavTvShowActivity : AppCompatActivity() {

    private lateinit var tvHelper: TVHelper
    private lateinit var dataTv: TvShowModel

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_fav_tv_show)

        tvHelper = TVHelper.getInstance(applicationContext)
        tvHelper.open()

        supportActionBar?.title = "Favorite Film Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataTv = intent.getParcelableExtra(EXTRA_DATA)

        setData(dataTv)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setData(data: TvShowModel) {
        // Change date format
        val date = data.release
        val changeTipe = SimpleDateFormat("yyyy-MM-dd")
        val format = changeTipe.parse(date)
        val changeFormat = SimpleDateFormat("d MMMM, yyyy")
        val releaseDate = changeFormat.format(format)

        // Get val vote average and change to ratingbar
        val getValueRating = data.rating?.toFloat()
        val round = getValueRating?.roundToInt()
        val rating = round?.toFloat()?.div(2)

        tv_fav_title_tvshow_detail.text = data.title
        tv_fav_overview_tvshow_detail.text = data.overview
        tv_fav_vote_tvshow_detail.text = data.vote
        tv_fav_rating_tvshow_detail.text = data.rating
        tv_fav_release_tvshow_detail.text = releaseDate
        if (rating != null) {
            rating_fav_tvshow_detail.rating = rating
        }
        Glide.with(this)
            .load(data.poster)
            .centerCrop()
            .into(img_fav_poster_tvshow_detail)
        Glide.with(this)
            .load(data.background)
            .centerCrop()
            .into(img_fav_tvshow_background_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_fav_menu_button, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_fav -> {
                showAlertDialog()
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {
        val dialogTitle = "Hapus Favorit"
        val dialogMessage = "Apakah anda yakin menghapus film dari favorit?"

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder.setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->
                tvHelper.deletebytitle(dataTv.title.toString()).toLong()
                finish()
            }
            .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        tvHelper.close()
    }
}
