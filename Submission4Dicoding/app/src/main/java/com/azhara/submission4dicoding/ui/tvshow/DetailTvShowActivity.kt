package com.azhara.submission4dicoding.ui.tvshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVHelper
import com.azhara.submission4dicoding.ui.tvshow.model.TvShowModel
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailTvShowActivity : AppCompatActivity() {

    private lateinit var tvHelper: TVHelper
    private lateinit var dataTv: TvShowModel
    private var favorite: Boolean = false
    private lateinit var menu: Menu
    private lateinit var loadFilmDb: ArrayList<TvShowModel>

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)

        supportActionBar?.title = "TV Show Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataTv = intent.getParcelableExtra(EXTRA_DATA)
        setDataTv(dataTv)

        tvHelper = TVHelper.getInstance(this)
        tvHelper.open()

        favoriteChecked()

    }

    private fun setDataTv(dataTv: TvShowModel) {
        // Change date format
        val date = dataTv.release
        val changeTipe = SimpleDateFormat("yyyy-MM-dd")
        val format = changeTipe.parse(date)
        val changeFormat = SimpleDateFormat("d MMMM, yyyy")
        val releaseDate = changeFormat.format(format)

        // Get val vote average and change to ratingbar
        val getValueRating = dataTv.rating?.toFloat()
        val round = getValueRating?.roundToInt()
        val rating = round?.toFloat()?.div(2)

        tv_title_tvshow_detail.text = dataTv.title
        tv_overview_tvshow_detail.text = dataTv.overview
        tv_vote_tvshow_detail.text = dataTv.vote
        tv_rating_tvshow_detail.text = dataTv.rating
        tv_release_tvshow_detail.text = releaseDate
        if (rating != null) {
            rating_tvshow_detail.rating = rating
        }
        Glide.with(this)
            .load(dataTv.poster)
            .centerCrop()
            .into(img_poster_tvshow_detail)
        Glide.with(this)
            .load(dataTv.background)
            .centerCrop()
            .into(img_tvshow_background_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_button, menu)
        this.menu = menu
        setIcon()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                iconPressed()
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun iconPressed() {
        if (favorite) {
            try {
                dataTv.title?.let { tvHelper.deletebytitle(it) }
                Toasty.success(this, "Remove from favorite", Toasty.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toasty.error(this, "${e.printStackTrace()}", Toasty.LENGTH_SHORT).show()
            }

        } else {
            try {
                tvHelper.insert(dataTv)
                Toasty.success(this, "Add to favorite", Toasty.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toasty.error(this, "${e.printStackTrace()}", Toasty.LENGTH_SHORT).show()
            }


        }
        favorite = !favorite
        setIcon()
    }

    private fun setIcon() {
        if (favorite) {
            menu.getItem(0).setIcon(R.drawable.ic_heart_solid)
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_border_black_24dp)
        }
    }

    private fun favoriteChecked() {
        loadFilmDb = tvHelper.quaryAll()

        for (film in loadFilmDb) {
            if (dataTv.title == film.title) {
                favorite = true
            }

            if (favorite == true) {
                break
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tvHelper.close()
    }
}
