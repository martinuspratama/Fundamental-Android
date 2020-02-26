package com.azhara.cataloguemoviesubmission2.Fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azhara.cataloguemoviesubmission2.Adapter.TvShowAdapter;
import com.azhara.cataloguemoviesubmission2.DetailActivity;
import com.azhara.cataloguemoviesubmission2.Model.MovieTvShow;
import com.azhara.cataloguemoviesubmission2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private TypedArray photo;
    private String[] judul;
    private String[] sinopsis;
    private String[] durasi;
    private String[] genre;
    private String[] rilis;
    private RecyclerView rvTvShow;
    private ArrayList<MovieTvShow> list = new ArrayList<>();
    private TvShowAdapter tvShowAdapter;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareDataFilm();
        rvTvShow = view.findViewById(R.id.rv_tv_show);
        rvTvShow.setHasFixedSize(true);
        list.addAll(setItemTv());
        init();

    }

    private void init(){
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowAdapter = new TvShowAdapter(list);
        rvTvShow.setAdapter(tvShowAdapter);

        tvShowAdapter.setOnItemClickCallBack(new TvShowAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(MovieTvShow tv) {
                detailTv(tv);
            }
        });
    }

    private void prepareDataFilm(){
        photo = getResources().obtainTypedArray(R.array.photo_tv);
        judul = getResources().getStringArray(R.array.judul_tv);
        sinopsis = getResources().getStringArray(R.array.sinopsis_tv);
        durasi = getResources().getStringArray(R.array.durasi_tv);
        genre = getResources().getStringArray(R.array.genre_tv);
        rilis = getResources().getStringArray(R.array.release_tv);
    }

    private ArrayList<MovieTvShow> setItemTv(){
        ArrayList<MovieTvShow> listTvShow = new ArrayList<>();
        for (int i = 0; i < judul.length; i++){
            MovieTvShow movieTvShow = new MovieTvShow();
            movieTvShow.setPicture(photo.getResourceId(i,-1));
            movieTvShow.setJudul(judul[i]);
            movieTvShow.setSinopsis(sinopsis[i]);
            movieTvShow.setDurasi(durasi[i]);
            movieTvShow.setGenre(genre[i]);
            movieTvShow.setRelease(rilis[i]);
            listTvShow.add(movieTvShow);
        }

        return listTvShow;
    }

    private void detailTv(MovieTvShow movieTvShow){
        MovieTvShow movies = new MovieTvShow();
        movies.setPicture(movieTvShow.getPicture());
        movies.setJudul(movieTvShow.getJudul());
        movies.setSinopsis(movieTvShow.getSinopsis());
        movies.setDurasi(movieTvShow.getDurasi());
        movies.setGenre(movieTvShow.getGenre());
        movies.setRelease(movieTvShow.getRelease());

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL_FILM, movies);
        startActivity(intent);

    }
}
