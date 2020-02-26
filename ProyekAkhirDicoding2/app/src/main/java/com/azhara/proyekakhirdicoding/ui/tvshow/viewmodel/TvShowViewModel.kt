package com.azhara.proyekakhirdicoding.ui.tvshow.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azhara.proyekakhirdicoding.BuildConfig
import com.azhara.proyekakhirdicoding.ui.tvshow.model.TvShowModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TvShowViewModel : ViewModel() {

    private val listTvShow = MutableLiveData<ArrayList<TvShowModel>>()
    private val TAG = TvShowViewModel::class.java.simpleName

    companion object {
        private const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    internal fun setData() {
        val client = AsyncHttpClient()
        val listItem = ArrayList<TvShowModel>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=${API_KEY}&language=en-US"

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
                        val tvItems = TvShowModel()

                        tvItems.title = movie.getString("name")
                        tvItems.overview = movie.getString("overview")
                        tvItems.release = movie.getString("first_air_date")
                        tvItems.vote = movie.getInt("vote_count").toString()
                        tvItems.background = movie.getString("backdrop_path")
                        tvItems.poster = movie.getString("poster_path")
                        tvItems.rating = movie.getDouble("vote_average").toString()
                        listItem.add(tvItems)
                    }
                    listTvShow.postValue(listItem)

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

    internal fun getData(): LiveData<ArrayList<TvShowModel>> {
        return listTvShow
    }

}