package com.azhara.proyekakhirdicoding.search.movies

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.search.movies.adapter.SearchMoviesAdapter
import com.azhara.proyekakhirdicoding.search.movies.viewmodel.MoviesSearchViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_search_movies.*

class SearchMoviesActivity : AppCompatActivity() {

    private lateinit var searchViewModel: MoviesSearchViewModel
    private lateinit var searchMoviesAdapter: SearchMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movies)

        searchMoviesAdapter = SearchMoviesAdapter()
        searchMoviesAdapter.notifyDataSetChanged()

        rv_search_movies.setHasFixedSize(true)
        rv_search_movies.layoutManager = GridLayoutManager(this, 2)
        rv_search_movies.adapter = searchMoviesAdapter

        supportActionBar?.title = resources.getString(R.string.search_movies)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getData()
    }

    private fun setData(movies: String) {
        searchViewModel.setData(movies)
    }

    private fun getData() {
        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MoviesSearchViewModel::class.java
        )
        searchViewModel.getData().observe(this, Observer { textMovies ->
            searchMoviesAdapter.setData(textMovies)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_item, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_text).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint_movies)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    setData(query)
                }
                Toasty.info(this@SearchMoviesActivity, "$query", Toasty.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    setData(newText)
                }
                return true
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
