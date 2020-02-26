package com.azhara.submission3movie.ui.tvshow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azhara.submission3movie.ui.tvshow.model.TVShow
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TvShowViewModel : ViewModel() {

    companion object {
        private const val API_KEY = "8aae05c228b9a235ccc3e9558aaa705d"
    }

    private val TAG = TvShowViewModel::class.java.simpleName

    val listTV = MutableLiveData<ArrayList<TVShow>>()

    internal fun setTVShow() {
        val client = AsyncHttpClient()
        val listItem = ArrayList<TVShow>()
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
                    val tvShowJSON = responseObject.getJSONArray("results")

                    for (i in 0 until tvShowJSON.length()) {
                        val tvShow = tvShowJSON.getJSONObject(i)
                        var tvItem = TVShow()
                        tvItem.title = tvShow.getString("name")
                        tvItem.poster = tvShow.getString("poster_path")
                        tvItem.background = tvShow.getString("backdrop_path")
                        tvItem.rating = tvShow.getString("vote_average")
                        tvItem.releaseDate = tvShow.getString("first_air_date")
                        tvItem.overview = tvShow.getString("overview")
                        listItem.add(tvItem)
                    }
                    listTV.postValue(listItem)
                } catch (e: Exception) {
                    Log.d(TAG, "error load data")
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "error load data: onFailure()")
            }
        })


    }

    internal fun getTVShow(): LiveData<ArrayList<TVShow>> {
        return listTV
    }


}