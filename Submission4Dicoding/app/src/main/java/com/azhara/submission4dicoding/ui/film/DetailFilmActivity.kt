package com.azhara.submission4dicoding.ui.film


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmHelper
import com.azhara.submission4dicoding.ui.film.model.FilmModel
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_detail_film.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailFilmActivity : AppCompatActivity() {

    private lateinit var filmHelper: FilmHelper
    private lateinit var dataFilm: FilmModel
    private var favorite: Boolean = false
    private lateinit var menu: Menu
    private lateinit var loadFilmDb: ArrayList<FilmModel>

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        filmHelper = FilmHelper.getInstance(applicationContext)
        filmHelper.open()

        supportActionBar?.title = "Movie Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataFilm = intent.getParcelableExtra(EXTRA_DATA)
        setData(dataFilm)
        favoriteChecked()

    }

    @SuppressLint("SimpleDateFormat")
    private fun setData(data: FilmModel) {
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

        tv_title_film_detail.text = data.title
        tv_overview_film_detail.text = data.overview
        tv_vote_film_detail.text = data.vote
        tv_rating_film_detail.text = data.rating
        tv_release_film_detail.text = releaseDate
        if (rating != null) {
            rating_film_detail.rating = rating
        }
        Glide.with(this)
            .load(data.poster)
            .centerCrop()
            .into(img_poster_film_detail)
        Glide.with(this)
            .load(data.background)
            .centerCrop()
            .into(img_film_background_detail)

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


    override fun onDestroy() {
        super.onDestroy()
        filmHelper.close()
    }

    private fun favoriteChecked() {
        loadFilmDb = filmHelper.quaryAll()

        for (film in loadFilmDb) {
            if (dataFilm.title == film.title) {
                favorite = true
            }

            if (favorite == true) {
                break
            }
        }
    }

    private fun setIcon() {
        if (favorite) {
            menu.getItem(0).setIcon(R.drawable.ic_heart_solid)
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_border_black_24dp)
        }
    }

    private fun iconPressed() {
        if (favorite) {
            try {
                dataFilm.title?.let { filmHelper.deletebytitle(it) }
                Toasty.success(this, "Remove from favorite", Toasty.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toasty.error(this, "${e.printStackTrace()}", Toasty.LENGTH_SHORT).show()
            }

        } else {
            try {
                filmHelper.insert(dataFilm)
                Toasty.success(this, "Add to favorite", Toasty.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toasty.error(this, "${e.printStackTrace()}", Toasty.LENGTH_SHORT).show()
            }


        }
        favorite = !favorite
        setIcon()
    }

}
