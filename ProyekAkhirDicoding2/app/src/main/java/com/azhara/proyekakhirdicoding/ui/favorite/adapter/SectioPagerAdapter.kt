package com.azhara.proyekakhirdicoding.ui.favorite.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.ui.favorite.movies.MoviesFavFragment
import com.azhara.proyekakhirdicoding.ui.favorite.tvshow.TvShowFavFragment

class SectioPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TABS_TITLE = intArrayOf(R.string.movies, R.string.tv_show)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MoviesFavFragment()
            1 -> fragment = TvShowFavFragment()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TABS_TITLE[position])

    }

    override fun getCount(): Int {
        return 2
    }

}