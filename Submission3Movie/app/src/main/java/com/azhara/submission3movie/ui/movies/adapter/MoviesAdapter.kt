package com.azhara.submission3movie.ui.movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.submission3movie.R
import com.azhara.submission3movie.ui.movies.model.Movies
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movies_item.view.*
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val listItems = ArrayList<Movies>()

    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setData(items: ArrayList<Movies>) {
        listItems.clear()
        listItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: MoviesAdapter.MoviesViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(moviesItems: Movies) {
            val date = moviesItems.releaseDate
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val tosimpleDate = simpleDateFormat.parse(date)
            val changeFormat = SimpleDateFormat("d MMMM, yyyy")
            val release = changeFormat.format(tosimpleDate)

            val tofloat = moviesItems.rating?.toFloat()
            val round = Math.round(tofloat!!)
            val rating = round.toFloat() / 2
            val posterPathUrl = "https://image.tmdb.org/t/p/w342${moviesItems.poster}"

            with(itemView) {
                Glide.with(itemView)
                    .load(posterPathUrl)
                    .centerCrop()
                    .into(img_movies)
                tv_title_movies.text = moviesItems.title
                tv_release_movies.text = release
                rating_movies.rating = rating
                itemView.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(moviesItems)
                }

            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: Movies)
    }

}