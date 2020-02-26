package com.azhara.proyekakhirdicoding.ui.tvshow.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.ui.tvshow.model.TvShowModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.tvshow_item.view.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private val listItem = ArrayList<TvShowModel>()

    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setData(items: ArrayList<TvShowModel>) {
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowAdapter.TvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tvshow_item, parent, false)
        return TvShowViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: TvShowAdapter.TvShowViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    inner class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(tvShow: TvShowModel) {
            // Change date format
            val date = tvShow.release
            val changeTipe = SimpleDateFormat("yyyy-MM-dd")
            val format = changeTipe.parse(date)
            val changeFormat = SimpleDateFormat("d MMMM, yyyy")
            val releaseDate = changeFormat.format(format)

            // Get val vote average and change to ratingbar
            val getValueRating = tvShow.rating?.toFloat()
            val round = getValueRating?.roundToInt()
            val rating = round?.toFloat()?.div(2)

            val posterUrl = "https://image.tmdb.org/t/p/w342/${tvShow.poster}"
            with(itemView) {
                tv_title_tvshow.text = tvShow.title
                tv_release_tvshow.text = releaseDate
                if (rating != null) {
                    rating_tvshow.rating = rating
                }
                Glide.with(itemView)
                    .load(posterUrl)
                    .centerCrop()
                    .into(img_tvshow)

                itemView.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(tvShow)
                }
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: TvShowModel)
    }

}