package com.azhara.moviesuserapp

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.azhara.moviesuserapp.adapter.MoviesAdapter
import com.azhara.moviesuserapp.db.movie.MoviesDatabaseContract.MoviesColumn.Companion.CONTENT_URI
import com.azhara.moviesuserapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var cursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesAdapter = MoviesAdapter()
        moviesAdapter.notifyDataSetChanged()

        rv_movies_user.layoutManager = GridLayoutManager(this, 2)
        rv_movies_user.setHasFixedSize(true)
        rv_movies_user.adapter = moviesAdapter

        setData()

    }

    private fun setData() {
        GlobalScope.launch(Dispatchers.Main) {
            val moviesLoad = async(Dispatchers.IO) {
                val load = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayListMovies(load)
            }
            val movies = moviesLoad.await()
            moviesAdapter.setData(movies)
            Log.e("MoviesFav", "$movies")
            if (movies.size <= 0) {
                lottie_movies_user.visibility = View.VISIBLE
            }
        }

    }

    override fun onResume() {
        super.onResume()
        setData()
    }
}
