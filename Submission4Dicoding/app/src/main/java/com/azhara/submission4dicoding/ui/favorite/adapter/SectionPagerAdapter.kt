package com.azhara.submission4dicoding.ui.favorite.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.azhara.submission4dicoding.R
import com.azhara.submission4dicoding.ui.favorite.film.FilmTabsFragment
import com.azhara.submission4dicoding.ui.favorite.tvshow.TvShowTabsFragment

class SectionPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TABS_TITLE = intArrayOf(R.string.movies_favorite, R.string.tvshow_favorite)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment =
                FilmTabsFragment()
            1 -> fragment =
                TvShowTabsFragment()
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