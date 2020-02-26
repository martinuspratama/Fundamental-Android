package com.azhara.proyekakhirdicoding.ui.tvshow


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.ui.tvshow.adapter.TvShowAdapter
import com.azhara.proyekakhirdicoding.ui.tvshow.model.TvShowModel
import com.azhara.proyekakhirdicoding.ui.tvshow.viewmodel.TvShowViewModel
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

        shimmer_tvshow.startShimmer()

        tvShowAdapter = TvShowAdapter()
        tvShowAdapter.notifyDataSetChanged()

        rv_tvshow.layoutManager = LinearLayoutManager(activity)
        rv_tvshow.adapter = tvShowAdapter

        setData()
        getData()

        tvShowAdapter.setOnItemClickCallBack(object : TvShowAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: TvShowModel) {
                toDetail(data)
            }

        })

    }

    private fun toDetail(data: TvShowModel) {
        val tvShow = TvShowModel()
        val posterPathUrl = "https://image.tmdb.org/t/p/w342${data.poster}"
        val backgroundUrl = "https://image.tmdb.org/t/p/original${data.background}"

        tvShow.title = data.title
        tvShow.overview = data.overview
        tvShow.release = data.release
        tvShow.vote = data.vote
        tvShow.rating = data.rating
        tvShow.poster = posterPathUrl
        tvShow.background = backgroundUrl

        val intent = Intent(activity, DetailTvShowActivity::class.java)
        intent.putExtra(DetailTvShowActivity.EXTRA_DATA, tvShow)
        startActivity(intent)
    }

    private fun setData() {
        tvShowViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TvShowViewModel::class.java)
        tvShowViewModel.setData()
    }

    private fun getData() {
        tvShowViewModel.getData().observe(viewLifecycleOwner, Observer { tvShowData ->
            tvShowAdapter.setData(tvShowData)
            if (shimmer_tvshow.visibility == View.VISIBLE) {
                shimmer_tvshow.stopShimmer()
                shimmer_tvshow.visibility = View.GONE
            }
        })
    }


}
