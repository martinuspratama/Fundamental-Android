package com.azhara.submission3movie.ui.tvshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.azhara.submission3movie.R
import com.azhara.submission3movie.ui.tvshow.model.TVShow
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.style.DoubleBounce
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import maes.tech.intentanim.CustomIntent
import java.text.SimpleDateFormat

@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class TVShowDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "DATA"
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)
        setTitle(R.string.detailTV)
        CustomIntent.customType(this, "left-to-right")
        val doubleBouncetv = DoubleBounce()
        spin_kit_tvshow_detail.setIndeterminateDrawable(doubleBouncetv)

        showLoading(true)
        if (intent != null) {
            val data = intent.getParcelableExtra<TVShow>(EXTRA_DATA)

            //DateRelease ChangeFormat
            val date = data.releaseDate
            val toSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val parse = toSimpleDateFormat.parse(date)
            val changeFormat = SimpleDateFormat("d MMMM, yyyy")
            val releaseDate = changeFormat.format(parse)

            //Rating
            val getRate = data.rating?.toFloat()
            val round = Math.round(getRate!!)
            val ratingTV = round.toFloat() / 2

            Glide.with(this)
                .load(data.background)
                .centerCrop()
                .into(img_tvshow_background)
            Glide.with(this)
                .load(data.poster)
                .centerCrop()
                .into(img_tvshow_detail)
            tv_title_tvshow_detail.text = data.title
            tv_release_tvshow_detail.text = releaseDate
            rating_tvshow_detail.rating = ratingTV
            tv_overview_tvshow_detail.text = data.overview
        }
        showLoading(false)

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            spin_kit_tvshow_detail.visibility = View.VISIBLE
        } else {
            spin_kit_tvshow_detail.visibility = View.GONE
        }
    }
}
