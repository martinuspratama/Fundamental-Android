package com.azhara.cataloguemoviesubmission2.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azhara.cataloguemoviesubmission2.Model.MovieTvShow;
import com.azhara.cataloguemoviesubmission2.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private ArrayList<MovieTvShow> movieTvShows;
    private OnItemClickCallBack onItemClickCallBack;

    public MovieAdapter(ArrayList<MovieTvShow> movieTvShows){
        this.movieTvShows = movieTvShows;
    }
//
    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieHolder holder, final int position) {
        final MovieTvShow movie= movieTvShows.get(position);
        Glide.with(holder.itemView.getContext())
                .load(movie.getPicture())
                .centerCrop()
                .into(holder.imgFilm);
        holder.tvJudul.setText(movie.getJudul());
        holder.tvSinopsis.setText(movie.getSinopsis());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallBack.onItemClicked(movieTvShows.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieTvShows.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        ImageView imgFilm;
        TextView tvJudul, tvSinopsis;
        private MovieHolder(@NonNull View itemView) {
            super(itemView);

            imgFilm = itemView.findViewById(R.id.img_film);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvSinopsis = itemView.findViewById(R.id.txt_sinopsis);
        }
    }

    public interface OnItemClickCallBack{
        void onItemClicked(MovieTvShow movies);
    }
}
