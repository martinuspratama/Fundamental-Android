package com.azhara.sub1;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ImageView bgDetail, picDetail;
    TextView tvJudul, tvRilis, tvSinoposis;
    public static String MOVIE = "extra";
    Film film;

    private List<Film> testList;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bgDetail = view.findViewById(R.id.backgroud_detail);
        picDetail = view.findViewById(R.id.img_detail);
        tvJudul = view.findViewById(R.id.tv_judulDetail);
        tvRilis = view.findViewById(R.id.tv_rilisdetail);
        tvSinoposis = view.findViewById(R.id.tv_sinopsisDetail);

        film = new Film(Parcel.obtain());
        Bundle bundle = this.getArguments();
        film = bundle.getParcelable(MOVIE);

        bgDetail.setImageResource(film.getPicFilm());
        picDetail.setImageResource(film.getPicFilm());
        tvJudul.setText(film.getJudulFilm());
        tvRilis.setText(film.getRilisFilm());
        tvSinoposis.setText(film.getSinopsisFilm());


    }
}
