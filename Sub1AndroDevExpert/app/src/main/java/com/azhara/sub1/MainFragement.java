package com.azhara.sub1;


import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragement extends Fragment {
    private String[] dataJudul;
    private String[] dataRilis;
    private String[] dataSinopsis;
    private TypedArray dataPhoto;
    private ListView listView;
    private FilmAdapter adapter;
    private ArrayList<Film> films = new ArrayList<>();



    public MainFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_fragement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FilmAdapter(view.getContext());
        listView = view.findViewById(R.id.lv_fragment);
        listView.setAdapter(adapter);

        getData();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), films.get(position).getJudulFilm(), Toast.LENGTH_SHORT).show();
                FragmentManager mFragmentManager = getFragmentManager();
                if (mFragmentManager != null){
                    Film film = new Film();
                    film.setPicFilm(films.get(position).getPicFilm());
                    film.setJudulFilm(films.get(position).getJudulFilm());
                    film.setRilisFilm(films.get(position).getRilisFilm());
                    film.setSinopsisFilm(films.get(position).getSinopsisFilm());
                    DetailFragment mDetailFragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(mDetailFragment.MOVIE, film);
                    mDetailFragment.setArguments(bundle);
                    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.container, mDetailFragment, DetailFragment.class.getSimpleName());
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                }
            }
        });


    }

    private void getData(){
        dataJudul = getResources().getStringArray(R.array.judul);
        dataSinopsis = getResources().getStringArray(R.array.sinopsis);
        dataPhoto = getResources().obtainTypedArray(R.array.photo);
        dataRilis = getResources().getStringArray(R.array.rilis);
    }

    private void addItem(){
        films = new ArrayList<>();

        for (int i = 0; i < dataJudul.length; i++){
            Film film = new Film();
            film.setJudulFilm(dataJudul[i]);
            film.setSinopsisFilm(dataSinopsis[i]);
            film.setRilisFilm(dataRilis[i]);
            film.setPicFilm(dataPhoto.getResourceId(i, -1));
            films.add(film);
        }
        adapter.setFilms(films);
    }



}
