package com.azhara.sub1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Film> films;

    public void setFilms(ArrayList<Film> films){
        this.films = films;
    }

    FilmAdapter(Context context){
        this.context = context;
        films = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        Film film = (Film) getItem(position);
        viewHolder.bind(film);
        return convertView;
    }


    private class ViewHolder{
        private ImageView imgFilm;
        private TextView judulFilm;
        private TextView sinopsisFilm;

        ViewHolder(View view){
            imgFilm = view.findViewById(R.id.pic_film);
            judulFilm = view.findViewById(R.id.tvJudul);
            sinopsisFilm = view.findViewById(R.id.tvSinopsis);
        }

        void bind(Film film){
            imgFilm.setImageResource(film.getPicFilm());
            judulFilm.setText(film.getJudulFilm());
            sinopsisFilm.setText(film.getSinopsisFilm());

        }
    }
}
