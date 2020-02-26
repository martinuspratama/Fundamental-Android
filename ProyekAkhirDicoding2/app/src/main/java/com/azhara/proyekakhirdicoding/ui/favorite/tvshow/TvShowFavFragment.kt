package com.azhara.proyekakhirdicoding.ui.favorite.tvshow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.db.tvshowdatabase.TvShowHelper
import com.azhara.proyekakhirdicoding.helper.MappingHelper
import com.azhara.proyekakhirdicoding.ui.favorite.tvshow.adapter.TvShowFavoriteAdapter
import kotlinx.android.synthetic.main.fragment_tv_show_fav.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class TvShowFavFragment : Fragment() {

    private lateinit var tvShowFavoriteAdapter: TvShowFavoriteAdapter
    private lateinit var tvShowHelper: TvShowHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShowHelper = TvShowHelper.getInstance(context)
        tvShowHelper.open()

        tvShowFavoriteAdapter = TvShowFavoriteAdapter()
        tvShowFavoriteAdapter.notifyDataSetChanged()

        rv_tvshow_favorite.layoutManager = GridLayoutManager(activity, 2)
        rv_tvshow_favorite.setHasFixedSize(true)
        rv_tvshow_favorite.adapter = tvShowFavoriteAdapter

        setData()
    }

    private fun setData() {
        GlobalScope.launch(Dispatchers.Main) {
            val tvShowLoad = async {
                val load = tvShowHelper.queryAll()
                MappingHelper.mapCursorToArrayListTvShow(load)
            }
            val tvShow = tvShowLoad.await()
            tvShowFavoriteAdapter.setData(tvShow)
            if (tvShow.size <= 0) {
                lottie_tvshow.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    override fun onDestroy() {
        super.onDestroy()
        tvShowHelper.close()
    }

}
