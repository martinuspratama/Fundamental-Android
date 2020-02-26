package com.azhara.submission3movie.ui.tvshow


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhara.submission3movie.R
import com.azhara.submission3movie.ui.tvshow.adapter.TVShowAdapter
import com.azhara.submission3movie.ui.tvshow.model.TVShow
import com.github.ybq.android.spinkit.style.DoubleBounce
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var tvShowAdapter: TVShowAdapter
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
        tvShowAdapter = TVShowAdapter()
        tvShowAdapter.notifyDataSetChanged()
        rv_tvshow.layoutManager = LinearLayoutManager(context)
        rv_tvshow.adapter = tvShowAdapter

        // Loading SpinKit
        val doubleBounce = DoubleBounce()
        spin_kit_tvshow.setIndeterminateDrawable(doubleBounce)

        tvShowViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TvShowViewModel::class.java)
        tvShowViewModel.setTVShow()
        showLoading(true)

        tvShowViewModel.getTVShow().observe(viewLifecycleOwner, Observer { listTVShow ->
            if (listTVShow != null) {
                tvShowAdapter.setData(listTVShow)
                showLoading(false)
            }
        })

        tvShowAdapter.setOnItemClickCallBack(object : TVShowAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: TVShow) {
                detailTVShow(data)
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            spin_kit_tvshow.visibility = View.VISIBLE
        } else {
            spin_kit_tvshow.visibility = View.GONE
        }
    }

    private fun detailTVShow(tvshow: TVShow) {
        val tvshowdata = TVShow()
        val posterPathUrl = "https://image.tmdb.org/t/p/w342${tvshow.poster}"
        val backgroundUrl = "https://image.tmdb.org/t/p/original${tvshow.background}"
        tvshowdata.title = tvshow.title
        tvshowdata.poster = posterPathUrl
        tvshowdata.background = backgroundUrl
        tvshowdata.releaseDate = tvshow.releaseDate
        tvshowdata.rating = tvshow.rating
        tvshowdata.overview = tvshow.overview

        val intent = Intent(activity, TVShowDetailActivity::class.java)
        intent.putExtra(TVShowDetailActivity.EXTRA_DATA, tvshowdata)
        startActivity(intent)
    }


}
