package com.azhara.proyekakhirdicoding.ui.tvshow

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.db.tvshowdatabase.TvShowDatabaseContract
import com.azhara.proyekakhirdicoding.db.tvshowdatabase.TvShowHelper
import com.azhara.proyekakhirdicoding.helper.MappingHelper
import com.azhara.proyekakhirdicoding.model.TvShowFavoriteDetail
import com.azhara.proyekakhirdicoding.ui.tvshow.model.TvShowModel
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailTvShowActivity : AppCompatActivity() {

    private lateinit var tvData: TvShowModel
    private lateinit var tvDetailModel: TvShowFavoriteDetail
    private lateinit var tvShowHelper: TvShowHelper
    private lateinit var menuTvShow: Menu
    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_DATA = "EXTRA DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)

        supportActionBar?.title = resources.getString(R.string.tv_show_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvShowHelper = TvShowHelper.getInstance(applicationContext)
        tvShowHelper.open()

        tvData = intent.getParcelableExtra(EXTRA_DATA)
        setData(tvData)
        favoriteChecked()
    }

    override fun onDestroy() {
        super.onDestroy()
        tvShowHelper.close()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setData(data: TvShowModel) {
        // Change date format
        val date = data.release
        val changeTipe = SimpleDateFormat("yyyy-MM-dd")
        val format = changeTipe.parse(date)
        val changeFormat = SimpleDateFormat("d MMMM, yyyy")
        val releaseDate = changeFormat.format(format)

        tvDetailModel = TvShowFavoriteDetail()
        tvDetailModel.title = data.title
        tvDetailModel.overview = data.overview
        tvDetailModel.release = releaseDate
        tvDetailModel.rating = data.rating
        tvDetailModel.vote = data.vote
        tvDetailModel.poster = data.poster
        tvDetailModel.background = data.background


        // Get val vote average and change to ratingbar
        val getValueRating = tvDetailModel.rating?.toFloat()
        val round = getValueRating?.roundToInt()
        val rating = round?.toFloat()?.div(2)

        tv_title_tvshow_detail.text = tvDetailModel.title
        tv_overview_tvshow_detail.text = tvDetailModel.overview
        tv_vote_tvshow_detail.text = tvDetailModel.vote
        if (rating != null) {
            rating_tvshow_detail.rating = rating
        }
        tv_release_tvshow_detail.text = tvDetailModel.release
        tv_rating_tvshow_detail.text = tvDetailModel.rating
        Glide.with(this)
            .load(tvDetailModel.background)
            .centerCrop()
            .into(img_tvshow_background_detail)
        Glide.with(this)
            .load(tvDetailModel.poster)
            .centerCrop()
            .into(img_poster_tvshow_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menuTvShow = menu
        setIcon()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_unfavorite -> {
                isFavorite = true
                addToDatabase()
                setIcon()
            }
            R.id.btn_favoritee -> {
                isFavorite = false
                deleteFromDatabase()
                setIcon()
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setIcon() {
        if (isFavorite) {
            menuTvShow.getItem(0).setVisible(false)
            menuTvShow.getItem(1).setVisible(true)
        } else {
            menuTvShow.getItem(0).setVisible(true)
            menuTvShow.getItem(1).setVisible(false)
        }
    }

    private fun deleteFromDatabase() {
        try {
            tvDetailModel.title?.let { tvShowHelper.deleteByTitle(it) }
            Toasty.success(this, "Remove from favorite", Toasty.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toasty.error(this, "Error Remove from favorite: $e", Toasty.LENGTH_SHORT).show()
        }

    }

    private fun addToDatabase() {
        try {
            val values = ContentValues()
            values.put(TvShowDatabaseContract.TvShowColumn.TITLE, tvDetailModel.title)
            values.put(TvShowDatabaseContract.TvShowColumn.OVERVIEW, tvDetailModel.overview)
            values.put(TvShowDatabaseContract.TvShowColumn.RELEASE, tvDetailModel.release)
            values.put(TvShowDatabaseContract.TvShowColumn.RATING, tvDetailModel.rating)
            values.put(TvShowDatabaseContract.TvShowColumn.VOTE, tvDetailModel.vote)
            values.put(TvShowDatabaseContract.TvShowColumn.POSTER, tvDetailModel.poster)
            values.put(TvShowDatabaseContract.TvShowColumn.BACKGROUND, tvDetailModel.background)
            tvShowHelper.insert(values)
            Toasty.success(this, "Add to favorite", Toasty.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toasty.error(this, "Error add to favorite: $e", Toasty.LENGTH_SHORT).show()
        }
    }

    private fun favoriteChecked() {
        val load = tvShowHelper.queryAll()
        val mapCursor = MappingHelper.mapCursorToArrayListTvShow(load)

        for (data in mapCursor) {
            if (data.title == tvData.title) {
                isFavorite = true
            }
            if (isFavorite) break

        }
    }

}
