package com.azhara.proyekakhirdicoding.ui.favorite.movies.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.model.MovieFavoriteDetail
import com.azhara.proyekakhirdicoding.ui.favorite.movies.FavoriteMoviesDetailActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movies_fav_item.view.*
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MoviesFavoriteAdapter :
    RecyclerView.Adapter<MoviesFavoriteAdapter.MoviesFavoriteViewHolder>() {

    var listItems = ArrayList<MovieFavoriteDetail>()

    fun setData(items: ArrayList<MovieFavoriteDetail>) {
        listItems.clear()
        listItems.addAll(items)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesFavoriteAdapter.MoviesFavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movies_fav_item, parent, false)
        return MoviesFavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(
        holder: MoviesFavoriteAdapter.MoviesFavoriteViewHolder,
        position: Int
    ) {
        holder.bind(listItems[position])
    }

    inner class MoviesFavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movies: MovieFavoriteDetail) {

            val getRate = movies.rating?.toFloat()
            val roundRating = getRate?.roundToInt()
            val rating = roundRating?.toFloat()?.div(2)
            with(itemView) {
                tv_title_movies_fav.text = movies.title
                if (rating != null) {
                    rating_movies_fav.rating = rating
                }
                Glide.with(itemView)
                    .load(movies.poster)
                    .centerCrop()
                    .into(img_movies_fav)
                itemView.setOnClickListener {
                    val intent = Intent(context, FavoriteMoviesDetailActivity::class.java)
                    intent.putExtra(FavoriteMoviesDetailActivity.EXTRA_DATA, movies)
                    context?.startActivity(intent)
                }
            }
        }
    }
}