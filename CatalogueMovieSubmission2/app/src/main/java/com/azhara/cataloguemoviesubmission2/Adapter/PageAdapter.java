package com.azhara.cataloguemoviesubmission2.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.azhara.cataloguemoviesubmission2.Fragment.MoviesFragment;
import com.azhara.cataloguemoviesubmission2.Fragment.TvShowFragment;
import com.azhara.cataloguemoviesubmission2.R;

public class PageAdapter extends FragmentPagerAdapter {
    Context context;

    public PageAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    private final int[] getTitles = new int[]{
            R.string.movies,
            R.string.tv_show
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0 :
                fragment = new MoviesFragment();
                break;
            case 1 :
                fragment = new TvShowFragment();
                break;
        }
        return fragment;
    }

    public CharSequence getPageTitle(int pos){
        return context.getResources().getString(getTitles[pos]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
