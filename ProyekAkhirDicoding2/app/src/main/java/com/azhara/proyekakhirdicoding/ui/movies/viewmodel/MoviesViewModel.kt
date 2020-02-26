package com.azhara.proyekakhirdicoding.ui.movies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azhara.proyekakhirdicoding.BuildConfig
import com.azhara.proyekakhirdicoding.ui.movies.model.MoviesModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MoviesViewModel : ViewModel() {

    private val listMovies = MutableLiveData<ArrayList<MoviesModel>>()
    private val TAG = MoviesViewModel::class.java.simpleName

    companion object {
        private const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    internal fun setData() {
        val client = AsyncHttpClient()
        val listItem = ArrayList<MoviesModel>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val moviesItems = MoviesModel()

                        moviesItems.title = movie.getString("title")
                        moviesItems.overview = movie.getString("overview")
                        moviesItems.release = movie.getString("release_date")
                        moviesItems.vote = movie.getInt("vote_count").toString()
                        moviesItems.background = movie.getString("backdrop_path")
                        moviesItems.poster = movie.getString("poster_path")
                        moviesItems.rating = movie.getDouble("vote_average").toString()
                        listItem.add(moviesItems)
                    }
                    listMovies.postValue(listItem)

                } catch (e: Exception) {
                    Log.d(TAG, "error: $e")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "onFailure get api: $error")
            }

        })
    }

    internal fun getData(): LiveData<ArrayList<MoviesModel>> {
        return listMovies
    }

}