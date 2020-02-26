package com.azhara.submission3movie.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azhara.submission3movie.ui.movies.model.Movies
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MoviesViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "8aae05c228b9a235ccc3e9558aaa705d"

    }

    private val TAG = MoviesViewModel::class.java.simpleName
    val listMovies = MutableLiveData<ArrayList<Movies>>()

    internal fun setMovies() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movies>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val listMoviesJSON = responseObject.getJSONArray("results")

                    for (i in 0 until listMoviesJSON.length()) {
                        val movies = listMoviesJSON.getJSONObject(i)
                        val moviesItems = Movies()
                        moviesItems.title = movies.getString("title")
                        moviesItems.releaseDate = movies.getString("release_date")
                        moviesItems.background = movies.getString("backdrop_path")
                        moviesItems.poster = movies.getString("poster_path")
                        moviesItems.rating = movies.getInt("vote_average").toString()
                        moviesItems.overview = movies.getString("overview")
                        listItems.add(moviesItems)
                    }
                    listMovies.postValue(listItems)
                } catch (e: Exception) {
                    Log.d(TAG, "Error Load data Movies")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "onFailure(): Error Load data Movies...")
            }

        })
    }

    internal fun getMovies(): LiveData<ArrayList<Movies>> {
        return listMovies
    }
}