package com.azhara.proyekakhirdicoding.ui.movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.ui.movies.model.MoviesModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movies_item.view.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val listItemMovies = ArrayList<MoviesModel>()

    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setData(items: ArrayList<MoviesModel>) {
        listItemMovies.clear()
        listItemMovies.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int = listItemMovies.size

    override fun onBindViewHolder(holder: MoviesAdapter.MoviesViewHolder, position: Int) {
        holder.bind(listItemMovies[position])
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(movies: MoviesModel) {
            // Change date format
            val date = movies.release
            val changeTipe = SimpleDateFormat("yyyy-MM-dd")
            val format = changeTipe.parse(date)
            val changeFormat = SimpleDateFormat("d MMMM, yyyy")
            val releaseDate = changeFormat.format(format)

            // Get val vote average and change to ratingbar
            val getValueRating = movies.rating?.toFloat()
            val round = getValueRating?.roundToInt()
            val rating = round?.toFloat()?.div(2)

            val posterUrl = "https://image.tmdb.org/t/p/w342/${movies.poster}"
            with(itemView) {
                tv_title_movies.text = movies.title
                tv_release_movies.text = releaseDate
                if (rating != null) {
                    rating_movies.rating = rating
                }
                Glide.with(itemView)
                    .load(posterUrl)
                    .centerCrop()
                    .into(img_movies)

                itemView.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(movies)
                }
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: MoviesModel)
    }

}