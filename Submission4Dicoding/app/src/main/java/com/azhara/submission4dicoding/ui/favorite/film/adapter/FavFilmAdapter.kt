package com.azhara.submission4dicoding.ui.favorite.film.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.favorite.film.DetailFavFilmActivity
import com.azhara.submission4dicoding.ui.film.model.FilmModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.favorite_item_film.view.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class FavFilmAdapter : RecyclerView.Adapter<FavFilmAdapter.FavFilmViewHolder>() {

    var listFilm = ArrayList<FilmModel>()

    fun setData(items: ArrayList<FilmModel>) {
        listFilm.clear()
        listFilm.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavFilmViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_item_film, parent, false)
        return FavFilmViewHolder(view)
    }

    override fun getItemCount(): Int = listFilm.size

    override fun onBindViewHolder(holder: FavFilmViewHolder, position: Int) {
        holder.bind(listFilm[position])
    }

    inner class FavFilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(filmFavModel: FilmModel) {
            // getDate
            val getDate = filmFavModel.release
            val simpleDate = SimpleDateFormat("yyyy-MM-dd")
            val date = simpleDate.parse(getDate)
            val changeFormat = SimpleDateFormat("d MMMM, yyyy")
            val release = changeFormat.format(date)

            val getRate = filmFavModel.rating?.toFloat()
            val roundRating = getRate?.roundToInt()
            val rating = roundRating?.div(2)?.toFloat()
            with(itemView) {
                tv_title_fav_film.text = filmFavModel.title
                tv_release_fav_film.text = filmFavModel.release
                if (rating != null) {
                    rating_fav_film.rating = rating
                }
                tv_release_fav_film.text = release
                Glide.with(itemView).load(filmFavModel.poster).centerCrop().into(img_fav_film)
                itemView.setOnClickListener {
                    val intent = Intent(context, DetailFavFilmActivity::class.java)
                    intent.putExtra(DetailFavFilmActivity.EXTRA_DATA, filmFavModel)
                    context.startActivity(intent)
                }
            }
        }

    }

}