package com.azhara.proyekakhirdicoding.search.tvshow.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azhara.proyekakhirdicoding.BuildConfig
import com.azhara.proyekakhirdicoding.search.tvshow.model.SearchTvShowModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TvShowSearchViewModel : ViewModel() {
    private var listSearchTvShow = MutableLiveData<ArrayList<SearchTvShowModel>>()
    private val TAG = TvShowSearchViewModel::class.java.simpleName

    companion object {
        private const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    internal fun setData(text: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<SearchTvShowModel>()
        val url =
            "https://api.themoviedb.org/3/search/tv?api_key=${API_KEY}&language=en-US&query=${text}"

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
                        val searchTvItems = SearchTvShowModel()

                        searchTvItems.poster = jsonObject.getString("poster_path")
                        searchTvItems.title = jsonObject.getString("name")
                        searchTvItems.rating = jsonObject.getDouble("vote_average").toString()
                        searchTvItems.background = jsonObject.getString("backdrop_path")
                        searchTvItems.overview = jsonObject.getString("overview")
                        searchTvItems.release = jsonObject.getString("first_air_date")
                        searchTvItems.vote = jsonObject.getString("vote_count")
                        listItems.add(searchTvItems)
                    }
                    listSearchTvShow.postValue(listItems)

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

    internal fun getData(): LiveData<ArrayList<SearchTvShowModel>> {
        return listSearchTvShow
    }

}