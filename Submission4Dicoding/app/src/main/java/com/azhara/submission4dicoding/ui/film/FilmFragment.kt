package com.azhara.submission4dicoding.ui.film


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.film.adapter.FilmAdapter
import com.azhara.submission4dicoding.ui.film.model.FilmModel
import com.azhara.submission4dicoding.ui.film.viewmodel.FilmViewModel
import kotlinx.android.synthetic.main.fragment_film.*

/**
 * A simple [Fragment] subclass.
 */
class FilmFragment : Fragment() {

    private lateinit var filmAdapter: FilmAdapter
    private lateinit var filmViewModel: FilmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmer_view_film.startShimmer()
        filmAdapter = FilmAdapter()
        filmAdapter.notifyDataSetChanged()

        rv_film.layoutManager = LinearLayoutManager(activity)
        rv_film.adapter = filmAdapter
        setData()

        getData()
        filmAdapter.setOnItemClickCallBack(object : FilmAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: FilmModel) {
                toDetailFilm(data)
            }

        })
    }

    private fun setData() {
        filmViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FilmViewModel::class.java)
        filmViewModel.setData()
    }


    private fun getData() {
        filmViewModel.getData().observe(this, Observer { filmItems ->
            filmAdapter.setData(filmItems)
            if (shimmer_view_film.visibility == View.VISIBLE) {
                shimmer_view_film.stopShimmer()
                shimmer_view_film.visibility = View.GONE
            }
        })

    }

    private fun toDetailFilm(data: FilmModel) {
        val filmData = FilmModel()
        val posterPathUrl = "https://image.tmdb.org/t/p/w342${data.poster}"
        val backgroundUrl = "https://image.tmdb.org/t/p/original${data.background}"

        filmData.title = data.title
        filmData.vote = data.vote
        filmData.rating = data.rating
        filmData.overview = data.overview
        filmData.release = data.release
        filmData.poster = posterPathUrl
        filmData.background = backgroundUrl

        val intent = Intent(activity, DetailFilmActivity::class.java)
        intent.putExtra(DetailFilmActivity.EXTRA_DATA, filmData)
        startActivity(intent)
    }


}
