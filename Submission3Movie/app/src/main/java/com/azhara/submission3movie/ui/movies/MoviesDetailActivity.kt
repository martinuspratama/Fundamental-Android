package com.azhara.submission3movie.ui.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.azhara.submission3movie.R
import com.azhara.submission3movie.ui.movies.model.Movies
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.style.DoubleBounce
import kotlinx.android.synthetic.main.activity_movies_detail.*
import maes.tech.intentanim.CustomIntent.customType
import java.text.SimpleDateFormat


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class MoviesDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA_MOVIES = "MOVIES"
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_detail)
        setTitle(R.string.detailMovies)
        customType(this, "left-to-right")
        val doubleBounce = DoubleBounce()
        spin_kit_movies_detail.setIndeterminateDrawable(doubleBounce)

        showLoading(true)
        if (intent != null) {
            val dataMovies = intent.getParcelableExtra<Movies>(EXTRA_DATA_MOVIES)

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val toSimpleDateFormat = simpleDateFormat.parse(dataMovies.releaseDate)
            val changeFormatDate = SimpleDateFormat("d MMMM, yyyy")
            val releaseDate = changeFormatDate.format(toSimpleDateFormat)

            val tofloat = dataMovies.rating?.toFloat()
            val round = Math.round(tofloat!!)
            val ratingMovies = round.toFloat() / 2

            Glide.with(this)
                .load(dataMovies.background)
                .centerCrop()
                .into(img_movies_background)

            Glide.with(this)
                .load(dataMovies.poster)
                .centerCrop()
                .into(img_movies_detail)

            tv_title_movies_detail.text = dataMovies.title
            tv_release_movies_detail.text = releaseDate
            rating_movies_detail.rating = ratingMovies
            tv_overview_movies_detail.text = dataMovies.overview
        }
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            spin_kit_movies_detail.visibility = View.VISIBLE
        } else {
            spin_kit_movies_detail.visibility = View.GONE
        }
    }
}
