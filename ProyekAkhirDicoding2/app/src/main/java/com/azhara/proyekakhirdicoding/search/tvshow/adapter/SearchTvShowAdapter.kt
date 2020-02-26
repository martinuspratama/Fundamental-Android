package com.azhara.proyekakhirdicoding.search.tvshow.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.search.tvshow.SearchTvShowDetailActivity
import com.azhara.proyekakhirdicoding.search.tvshow.model.SearchTvShowModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.search_tvshow_item.view.*
import kotlin.math.roundToInt

class SearchTvShowAdapter : RecyclerView.Adapter<SearchTvShowAdapter.SearchTvShowViewHolder>() {

    val listTvSearch = ArrayList<SearchTvShowModel>()

    fun setData(item: ArrayList<SearchTvShowModel>) {
        listTvSearch.clear()
        listTvSearch.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchTvShowAdapter.SearchTvShowViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_tvshow_item, parent, false)
        return SearchTvShowViewHolder(view)
    }

    override fun getItemCount(): Int = listTvSearch.size

    override fun onBindViewHolder(
        holder: SearchTvShowAdapter.SearchTvShowViewHolder,
        position: Int
    ) {
        holder.bind(listTvSearch[position])
    }

    inner class SearchTvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(search: SearchTvShowModel) {
            // Get val vote average and change to ratingbar
            val getValueRating = search.rating?.toFloat()
            val round = getValueRating?.roundToInt()
            val rating = round?.toFloat()?.div(2)

            val posterUrl = "https://image.tmdb.org/t/p/w342/${search.poster}"
            with(itemView) {
                tv_title_tvshow_search.text = search.title
                if (rating != null) {
                    rating_tvshow_search.rating = rating
                }
                Glide.with(itemView)
                    .load(posterUrl)
                    .centerCrop()
                    .into(img_tvshow_search)

                itemView.setOnClickListener {
                    val intent = Intent(context, SearchTvShowDetailActivity::class.java)
                    intent.putExtra(SearchTvShowDetailActivity.EXTRA_DATA, search)
                    context?.startActivity(intent)
                }
            }
        }
    }

}