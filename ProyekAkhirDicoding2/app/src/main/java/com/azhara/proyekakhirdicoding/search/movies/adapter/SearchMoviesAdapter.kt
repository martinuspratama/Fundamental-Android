package com.azhara.proyekakhirdicoding.search.movies.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.search.movies.SearchDetailActivity
import com.azhara.proyekakhirdicoding.search.movies.model.SearchMoviesModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.search_movies_item.view.*
import kotlin.math.roundToInt

class SearchMoviesAdapter : RecyclerView.Adapter<SearchMoviesAdapter.SearchMoviesViewHolder>() {

    val list = ArrayList<SearchMoviesModel>()

    fun setData(items: ArrayList<SearchMoviesModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMoviesAdapter.SearchMoviesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_movies_item, parent, false)
        return SearchMoviesViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        holder: SearchMoviesAdapter.SearchMoviesViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    inner class SearchMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(search: SearchMoviesModel) {
            // Get val vote average and change to ratingbar
            val getValueRating = search.rating?.toFloat()
            val round = getValueRating?.roundToInt()
            val rating = round?.toFloat()?.div(2)

            val posterUrl = "https://image.tmdb.org/t/p/w342/${search.poster}"
            with(itemView) {

                tv_title_movies_search.text = search.title
                if (rating != null) {
                    rating_movies_search.rating = rating
                }
                Glide.with(itemView)
                    .load(posterUrl)
                    .centerCrop()
                    .into(img_movies_search)

                itemView.setOnClickListener {
                    val intent = Intent(context, SearchDetailActivity::class.java)
                    intent.putExtra(SearchDetailActivity.EXTRA_DATA, search)
                    context?.startActivity(intent)
                }
            }
        }
    }

}