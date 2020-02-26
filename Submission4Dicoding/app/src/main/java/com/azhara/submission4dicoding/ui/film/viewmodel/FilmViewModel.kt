package com.azhara.submission4dicoding.ui.film.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azhara.submission4dicoding.BuildConfig
import com.azhara.submission4dicoding.ui.film.model.FilmModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class FilmViewModel : ViewModel() {

    companion object {
        private const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    val TAG = FilmViewModel::class.java.simpleName
    val listFilm = MutableLiveData<ArrayList<FilmModel>>()

    internal fun setData() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<FilmModel>()
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
                    val listJson = responseObject.getJSONArray("results")

                    for (item in 0 until listJson.length()) {
                        val film = listJson.getJSONObject(item)
                        val filmItems = FilmModel()
                        filmItems.title = film.getString("title")
                        filmItems.release = film.getString("release_date")
                        filmItems.rating = film.getDouble("vote_average").toString()
                        filmItems.overview = film.getString("overview")
                        filmItems.background = film.getString("backdrop_path")
                        filmItems.poster = film.getString("poster_path")
                        filmItems.vote = film.getInt("vote_count").toString()
                        listItems.add(filmItems)
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

    internal fun getData(): LiveData<ArrayList<FilmModel>> {
        return listFilm
    }

}