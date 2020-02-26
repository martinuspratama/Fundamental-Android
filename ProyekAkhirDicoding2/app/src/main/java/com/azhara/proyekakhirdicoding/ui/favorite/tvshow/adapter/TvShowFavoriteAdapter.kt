package com.azhara.proyekakhirdicoding.ui.favorite.tvshow.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.model.TvShowFavoriteDetail
import com.azhara.proyekakhirdicoding.ui.favorite.tvshow.FavoriteTvShowDetailActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fav_tv_item.view.*
import kotlin.math.roundToInt

class TvShowFavoriteAdapter : RecyclerView.Adapter<TvShowFavoriteAdapter.TvShowViewHolder>() {

    private val listItem = ArrayList<TvShowFavoriteDetail>()

    fun setData(tvShow: ArrayList<TvShowFavoriteDetail>) {
        listItem.clear()
        listItem.addAll(tvShow)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowFavoriteAdapter.TvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_tv_item, parent, false)
        return TvShowViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: TvShowFavoriteAdapter.TvShowViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    inner class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShowFavoriteDetail) {
            val getRate = tvShow.rating?.toFloat()
            val roundRating = getRate?.roundToInt()
            val rating = roundRating?.toFloat()?.div(2)
            with(itemView) {
                tv_title_tvshow_fav.text = tvShow.title
                if (rating != null) {
                    rating_tvshow_fav.rating = rating
                }
                Glide.with(itemView)
                    .load(tvShow.poster)
                    .centerCrop()
                    .into(img_tvshow_fav)
                itemView.setOnClickListener {
                    val intent = Intent(context, FavoriteTvShowDetailActivity::class.java)
                    intent.putExtra(FavoriteTvShowDetailActivity.EXTRA_DATA, tvShow)
                    context?.startActivity(intent)
                }
            }
        }
    }

}