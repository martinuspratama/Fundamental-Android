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

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowHolder> {

    private ArrayList<MovieTvShow> listTv;
    private OnItemClickCallBack onItemClickCallBack;

    public TvShowAdapter(ArrayList<MovieTvShow> listTv){
        this.listTv = listTv;
    }

    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack;
    }

    @NonNull
    @Override
    public TvShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow, parent, false);
        return new TvShowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowHolder holder, int position) {
        MovieTvShow tvShow = listTv.get(position);
        Glide.with(holder.itemView.getContext())
                .load(tvShow.getPicture())
                .centerCrop()
                .into(holder.imgTv);
        holder.tvJudulTv.setText(tvShow.getJudul());
        holder.sinopisTv.setText(tvShow.getSinopsis());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallBack.onItemClicked(listTv.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    public class TvShowHolder extends RecyclerView.ViewHolder {
        ImageView imgTv;
        TextView tvJudulTv, sinopisTv;
        public TvShowHolder(@NonNull View itemView) {
            super(itemView);

            imgTv = itemView.findViewById(R.id.img_tv);
            tvJudulTv = itemView.findViewById(R.id.tv_judul_tv);
            sinopisTv = itemView.findViewById(R.id.tv_sinopsis_tv);
        }
    }
    public interface OnItemClickCallBack{
        void onItemClicked(MovieTvShow tv);
    }
}
