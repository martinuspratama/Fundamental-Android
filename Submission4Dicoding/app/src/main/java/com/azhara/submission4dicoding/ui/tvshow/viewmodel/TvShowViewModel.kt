package com.azhara.submission4dicoding.ui.tvshow.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azhara.submission4dicoding.BuildConfig
import com.azhara.submission4dicoding.ui.tvshow.model.TvShowModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TvShowViewModel : ViewModel() {

    companion object {
        private const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    val TAG = TvShowViewModel::class.java.simpleName
    val listFilm = MutableLiveData<ArrayList<TvShowModel>>()

    internal fun setData() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<TvShowModel>()
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
                    val listJson = responseObject.getJSONArray("results")

                    for (item in 0 until listJson.length()) {
                        val film = listJson.getJSONObject(item)
                        val tvShowItems = TvShowModel()
                        tvShowItems.title = film.getString("name")
                        tvShowItems.release = film.getString("first_air_date")
                        tvShowItems.rating = film.getDouble("vote_average").toString()
                        tvShowItems.overview = film.getString("overview")
                        tvShowItems.background = film.getString("backdrop_path")
                        tvShowItems.poster = film.getString("poster_path")
                        tvShowItems.vote = film.getInt("vote_count").toString()
                        listItems.add(tvShowItems)
                    }
                    listFilm.postValue(listItems)
                    Log.d(TAG, "Success get data film from server")

                } catch (e: Exception) {
                    Log.d(TAG, "Error: ", e)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "Error onFailure: ", error)
            }

        })

    }

    internal fun getData(): LiveData<ArrayList<TvShowModel>> {
        return listFilm
    }

}