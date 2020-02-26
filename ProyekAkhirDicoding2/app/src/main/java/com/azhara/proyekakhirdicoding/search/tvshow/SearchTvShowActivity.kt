package com.azhara.proyekakhirdicoding.search.tvshow

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
import com.azhara.proyekakhirdicoding.search.tvshow.adapter.SearchTvShowAdapter
import com.azhara.proyekakhirdicoding.search.tvshow.viewmodel.TvShowSearchViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_search_tv_show.*

class SearchTvShowActivity : AppCompatActivity() {

    private lateinit var tvShowSearchViewModel: TvShowSearchViewModel
    private lateinit var searchTvShowAdapter: SearchTvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_tv_show)

        searchTvShowAdapter = SearchTvShowAdapter()
        searchTvShowAdapter.notifyDataSetChanged()

        rv_search_tvshow.setHasFixedSize(true)
        rv_search_tvshow.layoutManager = GridLayoutManager(this, 2)
        rv_search_tvshow.adapter = searchTvShowAdapter

        supportActionBar?.title = resources.getString(R.string.search_tvshow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getData()
    }

    private fun setData(text: String) {
        tvShowSearchViewModel.setData(text)
    }

    private fun getData() {
        tvShowSearchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            TvShowSearchViewModel::class.java
        )
        tvShowSearchViewModel.getData().observe(this, Observer { tvShowItems ->
            searchTvShowAdapter.setData(tvShowItems)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_item, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_text)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint_tvshow)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    setData(query)
                }
                Toasty.info(this@SearchTvShowActivity, "$query", Toasty.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    setData(newText)
                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
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
