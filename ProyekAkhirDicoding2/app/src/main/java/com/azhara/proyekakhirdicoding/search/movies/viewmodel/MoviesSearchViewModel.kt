package com.azhara.proyekakhirdicoding.search.movies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azhara.proyekakhirdicoding.BuildConfig
import com.azhara.proyekakhirdicoding.search.movies.model.SearchMoviesModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MoviesSearchViewModel : ViewModel() {

    private var listSearcMovies = MutableLiveData<ArrayList<SearchMoviesModel>>()
    private val TAG = MoviesSearchViewModel::class.java.simpleName

    companion object {
        private const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    internal fun setData(text: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<SearchMoviesModel>()
        val url =
            "https://api.themoviedb.org/3/search/movie?api_key=${API_KEY}&language=en-US&query=${text}"

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
                        val jsonObject = list.getJSONObject(i)
                        val searcMoviesItems = SearchMoviesModel()

                        searcMoviesItems.poster = jsonObject.getString("poster_path")
                        searcMoviesItems.title = jsonObject.getString("title")
                        searcMoviesItems.rating = jsonObject.getDouble("vote_average").toString()
                        searcMoviesItems.background = jsonObject.getString("backdrop_path")
                        searcMoviesItems.overview = jsonObject.getString("overview")
                        searcMoviesItems.release = jsonObject.getString("release_date")
                        searcMoviesItems.vote = jsonObject.getString("vote_count")
                        listItems.add(searcMoviesItems)
                    }
                    listSearcMovies.postValue(listItems)

                } catch (e: Exception) {
                    Log.d(TAG, "Error : $e")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "onFailure: $error")
            }

        })

    }

    internal fun getData(): LiveData<ArrayList<SearchMoviesModel>> {
        return listSearcMovies
    }

}