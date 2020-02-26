package com.azhara.submission4dicoding.ui.favorite.tvshow.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.favorite.tvshow.DetailFavTvShowActivity
import com.azhara.submission4dicoding.ui.tvshow.model.TvShowModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.favorite_item_tv.view.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class FavTvAdapter : RecyclerView.Adapter<FavTvAdapter.FavTvViewHolder>() {

    var listTV = ArrayList<TvShowModel>()

    fun setData(items: ArrayList<TvShowModel>) {
        listTV.clear()
        listTV.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavTvAdapter.FavTvViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_item_tv, parent, false)
        return FavTvViewHolder(view)
    }

    override fun getItemCount(): Int = listTV.size

    override fun onBindViewHolder(holder: FavTvAdapter.FavTvViewHolder, position: Int) {
        holder.bind(listTV[position])
    }

    inner class FavTvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(tvShowModel: TvShowModel) {
            // getDate
            val getDate = tvShowModel.release
            val simpleDate = SimpleDateFormat("yyyy-MM-dd")
            val date = simpleDate.parse(getDate)
            val changeFormat = SimpleDateFormat("d MMMM, yyyy")
            val release = changeFormat.format(date)

            val getRate = tvShowModel.rating?.toFloat()
            val roundRating = getRate?.roundToInt()
            val rating = roundRating?.div(2)?.toFloat()
            with(itemView) {
                tv_title_fav_tv.text = tvShowModel.title
                tv_release_fav_tv.text = tvShowModel.release
                if (rating != null) {
                    rating_fav_tv.rating = rating
                }
                tv_release_fav_tv.text = release
                Glide.with(itemView).load(tvShowModel.poster).centerCrop().into(img_fav_tv)
                itemView.setOnClickListener {
                    val intent = Intent(context, DetailFavTvShowActivity::class.java)
                    intent.putExtra(DetailFavTvShowActivity.EXTRA_DATA, tvShowModel)
                    context.startActivity(intent)
                }
            }
        }
    }

}