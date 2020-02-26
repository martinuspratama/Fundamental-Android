package com.azhara.proyekakhirdicoding.ui.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.ui.favorite.adapter.SectioPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectioPagerAdapter = context?.let { SectioPagerAdapter(it, childFragmentManager) }
        view_pager.adapter = sectioPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }


}
