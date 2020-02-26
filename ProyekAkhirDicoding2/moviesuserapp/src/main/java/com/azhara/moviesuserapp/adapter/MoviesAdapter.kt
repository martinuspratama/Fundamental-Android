package com.azhara.moviesuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.moviesuserapp.DetailActivity
import com.azhara.moviesuserapp.R
import com.azhara.moviesuserapp.model.MoviesModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movies_user_item.view.*
import kotlin.math.roundToInt

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    val listMovies = ArrayList<MoviesModel>()

    fun setData(item: ArrayList<MoviesModel>) {
        listMovies.clear()
        listMovies.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movies_user_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int = listMovies.size

    override fun onBindViewHolder(holder: MoviesAdapter.MoviesViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movies: MoviesModel) {
            val getRate = movies.rating?.toFloat()
            val roundRating = getRate?.roundToInt()
            val rating = roundRating?.toFloat()?.div(2)
            with(itemView) {
                tv_title_movies_user.text = movies.title
                if (rating != null) {
                    rating_movies_user.rating = rating
                }
                Glide.with(itemView)
                    .load(movies.poster)
                    .centerCrop()
                    .into(img_movies_user)
                itemView.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, movies)
                    context?.startActivity(intent)
                }
            }
        }
    }

}