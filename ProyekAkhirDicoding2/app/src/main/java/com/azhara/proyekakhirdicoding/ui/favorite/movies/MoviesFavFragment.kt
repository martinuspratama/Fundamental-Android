package com.azhara.proyekakhirdicoding.ui.favorite.movies


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesHelper
import com.azhara.proyekakhirdicoding.helper.MappingHelper
import com.azhara.proyekakhirdicoding.ui.favorite.movies.adapter.MoviesFavoriteAdapter
import kotlinx.android.synthetic.main.fragment_movies_fav.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class MoviesFavFragment : Fragment() {

    private lateinit var moviesFavoriteAdapter: MoviesFavoriteAdapter
    private lateinit var moviesHelper: MoviesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesHelper = MoviesHelper.getInstance(context)
        moviesHelper.open()

        moviesFavoriteAdapter = MoviesFavoriteAdapter()
        moviesFavoriteAdapter.notifyDataSetChanged()
        rv_movies_favorite.layoutManager = GridLayoutManager(activity, 2)
        rv_movies_favorite.setHasFixedSize(true)
        rv_movies_favorite.adapter = moviesFavoriteAdapter

        setData()

    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    private fun setData() {
        GlobalScope.launch(Dispatchers.Main) {
            val moviesLoad = async(Dispatchers.IO) {
                val load = moviesHelper.queryAll()
                MappingHelper.mapCursorToArrayListMovies(load)
            }
            val movies = moviesLoad.await()
            moviesFavoriteAdapter.setData(movies)
            Log.e("MoviesFav", "$movies")
            if (movies.size <= 0) {
                lottie_movies.visibility = View.VISIBLE
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        moviesHelper.close()
    }
}
