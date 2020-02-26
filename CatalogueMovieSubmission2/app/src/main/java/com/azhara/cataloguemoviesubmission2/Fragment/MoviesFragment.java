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

import com.azhara.cataloguemoviesubmission2.Adapter.MovieAdapter;
import com.azhara.cataloguemoviesubmission2.DetailActivity;
import com.azhara.cataloguemoviesubmission2.Model.MovieTvShow;
import com.azhara.cataloguemoviesubmission2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private TypedArray photo;
    private String[] judul;
    private String[] sinopsis;
    private String[] durasi;
    private String[] genre;
    private String[] rilis;
    private RecyclerView rvMovie;
    private ArrayList<MovieTvShow> list = new ArrayList<>();


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareDataFilm();
        rvMovie = view.findViewById(R.id.rv_movies);
        rvMovie.setHasFixedSize(true);
        list.addAll(setItem());
        init();

    }

    private void init(){
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieAdapter movieAdapter = new MovieAdapter(list);
        rvMovie.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallBack(new MovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(MovieTvShow movies) {
                detailMovie(movies);
            }
        });
    }

    private void prepareDataFilm(){
        photo = getResources().obtainTypedArray(R.array.photo_film);
        judul = getResources().getStringArray(R.array.judul_film);
        sinopsis = getResources().getStringArray(R.array.sinopsis_film);
        durasi = getResources().getStringArray(R.array.durasi_film);
        genre = getResources().getStringArray(R.array.genre_film);
        rilis = getResources().getStringArray(R.array.release_film);
    }

    private ArrayList<MovieTvShow> setItem(){
        ArrayList<MovieTvShow> listmovies = new ArrayList<>();
        for (int i = 0; i < judul.length; i++){
            MovieTvShow movieTvShow = new MovieTvShow();
            movieTvShow.setPicture(photo.getResourceId(i,-1));
            movieTvShow.setJudul(judul[i]);
            movieTvShow.setSinopsis(sinopsis[i]);
            movieTvShow.setDurasi(durasi[i]);
            movieTvShow.setGenre(genre[i]);
            movieTvShow.setRelease(rilis[i]);
            listmovies.add(movieTvShow);
        }

        return listmovies;
    }

    private void detailMovie(MovieTvShow movieTvShow){
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
