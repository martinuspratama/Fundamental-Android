package com.azhara.submission4dicoding.ui.tvshow


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.tvshow.adapter.TvShowAdapter
import com.azhara.submission4dicoding.ui.tvshow.model.TvShowModel
import com.azhara.submission4dicoding.ui.tvshow.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var tvShowViewModel: TvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmer_view_tvshow.startShimmer()
        tvShowAdapter = TvShowAdapter()
        tvShowAdapter.notifyDataSetChanged()

        rv_tvshow.layoutManager = LinearLayoutManager(activity)
        rv_tvshow.adapter = tvShowAdapter

        setData()

        getdata()

        tvShowAdapter.setOnItemClickCallBack(object : TvShowAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: TvShowModel) {
                toDetailFilm(data)
            }


        })
    }

    private fun setData() {
        tvShowViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            TvShowViewModel::class.java
        )
        tvShowViewModel.setData()
    }

    private fun getdata() {
        tvShowViewModel.getData().observe(this, Observer { tvItems ->
            tvShowAdapter.setData(tvItems)
            if (shimmer_view_tvshow.visibility == View.VISIBLE) {
                shimmer_view_tvshow.stopShimmer()
                shimmer_view_tvshow.visibility = View.GONE
            }

        })
    }

    private fun toDetailFilm(data: TvShowModel) {
        val tvShowData = TvShowModel()
        val posterPathUrl = "https://image.tmdb.org/t/p/w342${data.poster}"
        val backgroundUrl = "https://image.tmdb.org/t/p/original${data.background}"

        tvShowData.title = data.title
        tvShowData.vote = data.vote
        tvShowData.rating = data.rating
        tvShowData.overview = data.overview
        tvShowData.release = data.release
        tvShowData.poster = posterPathUrl
        tvShowData.background = backgroundUrl

        val intent = Intent(activity, DetailTvShowActivity::class.java)
        intent.putExtra(DetailTvShowActivity.EXTRA_DATA, tvShowData)
        startActivity(intent)
    }

}
