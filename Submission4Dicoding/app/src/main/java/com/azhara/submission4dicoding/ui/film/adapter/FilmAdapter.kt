package com.azhara.submission4dicoding.ui.film.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.film.model.FilmModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.film_item.view.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FilmAdapter : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    private val listItem = ArrayList<FilmModel>()

    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setData(items: ArrayList<FilmModel>) {
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdapter.FilmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return FilmViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: FilmAdapter.FilmViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(film: FilmModel) {
            // Change date format
            val date = film.release
            val changeTipe = SimpleDateFormat("yyyy-MM-dd")
            val format = changeTipe.parse(date)
            val changeFormat = SimpleDateFormat("d MMMM, yyyy")
            val releaseDate = changeFormat.format(format)

            // Get val vote average and change to ratingbar
            val getValueRating = film.rating?.toFloat()
            val round = getValueRating?.roundToInt()
            val rating = round?.toFloat()?.div(2)

            val posterUrl = "https://image.tmdb.org/t/p/w342/${film.poster}"
            with(itemView) {
                tv_title_film.text = film.title
                tv_release_film.text = releaseDate
                if (rating != null) {
                    rating_film.rating = rating
                }
                Glide.with(itemView)
                    .load(posterUrl)
                    .centerCrop()
                    .into(img_film)

                itemView.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(film)
                }
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: FilmModel)
    }
}


