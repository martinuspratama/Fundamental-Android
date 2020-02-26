package com.azhara.cataloguemoviesubmission2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.azhara.cataloguemoviesubmission2.Model.MovieTvShow;
import com.bumptech.glide.Glide;

import static maes.tech.intentanim.CustomIntent.customType;


public class DetailActivity extends AppCompatActivity {
    public static String DETAIL_FILM = "DETAIL_FILM";
    ImageView backgroundFilm, imgFilm;
    TextView tvJudul, tvSinopsis, tvDurasi,tvGenre,tvRilis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        customType(DetailActivity.this, "left-to-right");
        backgroundFilm = findViewById(R.id.detail_background);
        imgFilm = findViewById(R.id.detail_img);
        tvJudul = findViewById(R.id.detail_judul);
        tvSinopsis = findViewById(R.id.detail_sinopsis);
        tvDurasi = findViewById(R.id.detail_durasi);
        tvGenre = findViewById(R.id.detail_genre);
        tvRilis = findViewById(R.id.detail_rilis);

        MovieTvShow movieTvShow = getIntent().getParcelableExtra(DETAIL_FILM);
        Glide.with(this)
                .load(movieTvShow.getPicture())
                .centerCrop()
                .into(backgroundFilm);
        Glide.with(this)
                .load(movieTvShow.getPicture())
                .centerCrop()
                .into(imgFilm);
        tvJudul.setText(movieTvShow.getJudul());
        tvRilis.setText(movieTvShow.getRelease());
        tvSinopsis.setText(movieTvShow.getSinopsis());
        tvDurasi.setText(movieTvShow.getDurasi());
        tvGenre.setText(movieTvShow.getGenre());

    }
}
