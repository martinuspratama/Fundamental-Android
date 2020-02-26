package com.azhara.proyekakhirdicoding.ui.movies


import android.annotation.SuppressLint
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
import com.azhara.proyekakhirdicoding.ui.movies.adapter.MoviesAdapter
import com.azhara.proyekakhirdicoding.ui.movies.model.MoviesModel
import com.azhara.proyekakhirdicoding.ui.movies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shimmer_movies.startShimmer()

        moviesAdapter = MoviesAdapter()
        moviesAdapter.notifyDataSetChanged()
        rv_movies.layoutManager = LinearLayoutManager(activity)
        rv_movies.adapter = moviesAdapter

        setData()
        getData()

        moviesAdapter.setOnItemClickCallBack(object : MoviesAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: MoviesModel) {
                toDetail(data)
            }

        })

    }

    private fun toDetail(moviesData: MoviesModel) {
        val movies = MoviesModel()
        val posterPathUrl = "https://image.tmdb.org/t/p/w342${moviesData.poster}"
        val backgroundUrl = "https://image.tmdb.org/t/p/original${moviesData.background}"

        movies.title = moviesData.title
        movies.poster = posterPathUrl
        movies.rating = moviesData.rating
        movies.background = backgroundUrl
        movies.vote = moviesData.vote
        movies.release = moviesData.release
        movies.overview = moviesData.overview

        val intent = Intent(activity, DetailMoviesActivity::class.java)
        intent.putExtra(DetailMoviesActivity.EXTRA_DATA, movies)
        startActivity(intent)

    }

    private fun setData() {
        moviesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MoviesViewModel::class.java)
        moviesViewModel.setData()
    }

    @SuppressLint("FragmentLive DataObserve")
    private fun getData() {
        moviesViewModel.getData().observe(viewLifecycleOwner, Observer { moviesData ->
            moviesAdapter.setData(moviesData)
            if (shimmer_movies.visibility == View.VISIBLE) {
                shimmer_movies.stopShimmer()
                shimmer_movies.visibility = View.GONE
            }
        })
    }

}
