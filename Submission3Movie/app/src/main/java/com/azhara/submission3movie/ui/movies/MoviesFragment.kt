package com.azhara.submission3movie.ui.movies


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
import com.azhara.submission3movie.ui.movies.adapter.MoviesAdapter
import com.azhara.submission3movie.ui.movies.model.Movies
import com.github.ybq.android.spinkit.style.DoubleBounce
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MoviesAdapter()
        moviesAdapter.notifyDataSetChanged()
        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.adapter = moviesAdapter

        val doubleBounce = DoubleBounce()
        spin_kit_movies.setIndeterminateDrawable(doubleBounce)

        //View Model object
        moviesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MoviesViewModel::class.java)
        moviesViewModel.setMovies()
        showLoading(true)

        moviesViewModel.getMovies().observe(viewLifecycleOwner, Observer { moviesItems ->
            if (moviesItems != null) {
                moviesAdapter.setData(moviesItems)
                showLoading(false)
            }
        })

        moviesAdapter.setOnItemClickCallBack(object : MoviesAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: Movies) {
                toDetail(data)
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            spin_kit_movies.visibility = View.VISIBLE
        } else {
            spin_kit_movies.visibility = View.GONE
        }
    }

    private fun toDetail(movies: Movies) {
        val moviesData = Movies()
        val posterPathUrl = "https://image.tmdb.org/t/p/w342${movies.poster}"
        val backgroundUrl = "https://image.tmdb.org/t/p/original${movies.background}"

        moviesData.title = movies.title
        moviesData.background = backgroundUrl
        moviesData.poster = posterPathUrl
        moviesData.rating = movies.rating
        moviesData.releaseDate = movies.releaseDate
        moviesData.overview = movies.overview

        val intent = Intent(activity, MoviesDetailActivity::class.java)
        intent.putExtra(MoviesDetailActivity.EXTRA_DATA_MOVIES, moviesData)
        startActivity(intent)
    }

}
