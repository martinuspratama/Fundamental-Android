package com.azhara.proyekakhirdicoding.search.tvshow

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
import com.azhara.proyekakhirdicoding.search.tvshow.model.SearchTvShowModel
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_search_tv_show_detail.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SearchTvShowDetailActivity : AppCompatActivity() {

    private lateinit var searchTvShowModel: SearchTvShowModel
    private lateinit var tvShowFavoriteModel: TvShowFavoriteDetail
    private lateinit var tvShowHelper: TvShowHelper
    private var isFavorite: Boolean = false
    private lateinit var menuIcon: Menu

    companion object {
        const val EXTRA_DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_tv_show_detail)

        tvShowHelper = TvShowHelper.getInstance(this)
        tvShowHelper.open()

        supportActionBar?.title = resources.getString(R.string.detail_search_tvshow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        searchTvShowModel = intent.getParcelableExtra(EXTRA_DATA)
        setData(searchTvShowModel)
        favoriteChecked()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setData(data: SearchTvShowModel) {
        tvShowFavoriteModel = TvShowFavoriteDetail()

        // Change date format
        val date = data.release
        val changeTipe = SimpleDateFormat("yyyy-MM-dd")
        val format = changeTipe.parse(date)
        val changeFormat = SimpleDateFormat("d MMMM, yyyy")
        val releaseDate = changeFormat.format(format)

        val posterPathUrl = "https://image.tmdb.org/t/p/w342${data.poster}"
        val backgroundUrl = "https://image.tmdb.org/t/p/original${data.background}"

        tvShowFavoriteModel.title = data.title
        tvShowFavoriteModel.background = backgroundUrl
        tvShowFavoriteModel.poster = posterPathUrl
        tvShowFavoriteModel.vote = data.vote
        tvShowFavoriteModel.rating = data.rating
        tvShowFavoriteModel.release = releaseDate
        tvShowFavoriteModel.overview = data.overview


        // Get val vote average and change to ratingbar
        val getValueRating = tvShowFavoriteModel.rating?.toFloat()
        val round = getValueRating?.roundToInt()
        val rating = round?.toFloat()?.div(2)

        tv_title_tvshow_search_detail.text = tvShowFavoriteModel.title
        tv_overview_tvshow_search_detail.text = tvShowFavoriteModel.overview
        tv_rating_tvshow_search_detail.text = tvShowFavoriteModel.rating
        if (rating != null) {
            rating_tvshow_search_detail.rating = rating
        }
        tv_release_search_tvshow_detail.text = tvShowFavoriteModel.release
        tv_vote_tvshow_search_detail.text = tvShowFavoriteModel.vote
        Glide.with(this)
            .load(posterPathUrl)
            .centerCrop()
            .into(img_poster_search_tvshow_detail)

        Glide.with(this)
            .load(backgroundUrl)
            .centerCrop()
            .into(img_tvshow_search_background_detail)
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
            }
            R.id.btn_unfavorite -> {
                isFavorite = true
                setDataToDatabase()
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
            menuIcon.getItem(0).isVisible = false
            menuIcon.getItem(1).isVisible = true
        } else {
            menuIcon.getItem(0).isVisible = true
            menuIcon.getItem(1).isVisible = false
        }
    }

    private fun favoriteChecked() {
        val cursor = tvShowHelper.queryAll()
        val mapCursor = MappingHelper.mapCursorToArrayListTvShow(cursor)

        for (movies in mapCursor) {
            if (tvShowFavoriteModel.title == movies.title) {
                isFavorite = true
            }

            if (isFavorite) break
        }
    }

    private fun setDataToDatabase() {
        try {
            val values = ContentValues()
            values.put(TvShowDatabaseContract.TvShowColumn.TITLE, tvShowFavoriteModel.title)
            values.put(TvShowDatabaseContract.TvShowColumn.OVERVIEW, tvShowFavoriteModel.overview)
            values.put(TvShowDatabaseContract.TvShowColumn.RELEASE, tvShowFavoriteModel.release)
            values.put(TvShowDatabaseContract.TvShowColumn.RATING, tvShowFavoriteModel.rating)
            values.put(TvShowDatabaseContract.TvShowColumn.VOTE, tvShowFavoriteModel.vote)
            values.put(TvShowDatabaseContract.TvShowColumn.POSTER, tvShowFavoriteModel.poster)
            values.put(
                TvShowDatabaseContract.TvShowColumn.BACKGROUND,
                tvShowFavoriteModel.background
            )
            tvShowHelper.insert(values)
            Toasty.success(this, "Add to Favorite", Toasty.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toasty.error(this, "Error to favorite: $e", Toasty.LENGTH_SHORT).show()
        }
    }

    private fun deleteDataFromDatabase() {
        try {
            tvShowFavoriteModel.title?.let { tvShowHelper.deleteByTitle(it) }
            Toasty.success(this, "Remove from favorite", Toasty.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toasty.error(this, "Error remove from favorite: $e", Toasty.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tvShowHelper.close()
    }
}
