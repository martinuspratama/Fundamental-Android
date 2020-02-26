package com.azhara.submission4dicoding.ui.favorite.tvshow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.favorite.tvshow.adapter.FavTvAdapter
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVHelper
import com.azhara.submission4dicoding.ui.tvshow.model.TvShowModel
import kotlinx.android.synthetic.main.fragment_tv_show_tabs.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class TvShowTabsFragment : Fragment() {

    private lateinit var favTvAdapter: FavTvAdapter
    private lateinit var tvHelper: TVHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvHelper = TVHelper.getInstance(activity)
        tvHelper.open()

        rv_fav_tv.layoutManager = LinearLayoutManager(activity)
        rv_fav_tv.setHasFixedSize(true)
        favTvAdapter = FavTvAdapter()
        rv_fav_tv.adapter = favTvAdapter

        if (savedInstanceState == null) {
            setData()
        } else {
            val list = savedInstanceState.getParcelableArrayList<TvShowModel>(EXTRA_STATE)
            if (list != null) {
                favTvAdapter.listTV = list
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favTvAdapter.listTV)
    }

    override fun onResume() {
        super.onResume()
        setData()
        tvHelper.open()
    }

    private fun setData() {
        GlobalScope.launch(Dispatchers.Main) {
            val load = async {
                tvHelper.quaryAll()
            }
            val film = load.await()
            favTvAdapter.setData(film)
            if (film.size <= 0) {
                lottie_tv.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tvHelper.close()
    }

}
