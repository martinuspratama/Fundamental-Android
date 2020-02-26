package com.azhara.submission4dicoding.ui.favorite.film


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.favorite.film.adapter.FavFilmAdapter
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmHelper
import com.azhara.submission4dicoding.ui.film.model.FilmModel
import kotlinx.android.synthetic.main.fragment_film_tabs.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FilmTabsFragment : Fragment() {
    private lateinit var favFilmAdapter: FavFilmAdapter
    private lateinit var filmHelper: FilmHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_film_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filmHelper = FilmHelper.getInstance(activity?.applicationContext)
        filmHelper.open()

        rv_fav_film.layoutManager = LinearLayoutManager(activity)
        rv_fav_film.setHasFixedSize(true)
        favFilmAdapter = FavFilmAdapter()
        rv_fav_film.adapter = favFilmAdapter

        if (savedInstanceState == null) {
            setData()
        } else {
            val list = savedInstanceState.getParcelableArrayList<FilmModel>(EXTRA_STATE)
            if (list != null) {
                favFilmAdapter.listFilm = list
            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favFilmAdapter.listFilm)
    }

    override fun onResume() {
        super.onResume()
        setData()
        filmHelper.open()
    }

    private fun setData() {
        GlobalScope.launch(Dispatchers.Main) {
            val load = async {
                filmHelper.quaryAll()
            }
            val film = load.await()
            favFilmAdapter.setData(film)
            if (film.size <= 0) {
                lottie_film.visibility = View.VISIBLE
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        filmHelper.close()
    }
}
