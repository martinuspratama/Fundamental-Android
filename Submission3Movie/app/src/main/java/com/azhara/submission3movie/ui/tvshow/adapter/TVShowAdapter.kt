package com.azhara.submission3movie.ui.tvshow.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.submission3movie.R
import com.azhara.submission3movie.ui.tvshow.model.TVShow
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.tvshow_item.view.*
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TVShowAdapter : RecyclerView.Adapter<TVShowAdapter.TVShowHolder>() {

    private val listItems = ArrayList<TVShow>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setData(list: ArrayList<TVShow>) {
        listItems.clear()
        listItems.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowAdapter.TVShowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tvshow_item, parent, false)
        return TVShowHolder(view)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: TVShowAdapter.TVShowHolder, position: Int) {
        holder.bind(listItems[position])
    }

    inner class TVShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(tvshow: TVShow) {
            //DateRelease ChangeFormat
            val date = tvshow.releaseDate
            val toSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val parse = toSimpleDateFormat.parse(date)
            val changeFormat = SimpleDateFormat("d MMMM, yyyy")
            val releaseDate = changeFormat.format(parse)

            //Rating
            val getRate = tvshow.rating?.toFloat()
            val round = Math.round(getRate!!)
            val rating = round.toFloat() / 2

            //Picture path
            val pictureUrl = "https://image.tmdb.org/t/p/w342${tvshow.poster}"

            with(itemView) {
                tv_title_tvshow.text = tvshow.title
                rating_tvshow.rating = rating
                tv_release_tvshow.text = releaseDate
                Glide.with(itemView)
                    .load(pictureUrl)
                    .centerCrop()
                    .into(img_tvshow)

                itemView.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(tvshow)
                }
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: TVShow)
    }

}