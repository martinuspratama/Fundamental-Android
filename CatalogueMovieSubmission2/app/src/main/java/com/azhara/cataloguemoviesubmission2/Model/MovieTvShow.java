package com.azhara.cataloguemoviesubmission2.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieTvShow implements Parcelable {
    private int picture;
    private String judul, sinopsis, durasi, genre, release;

    public MovieTvShow(){

    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.picture);
        dest.writeString(this.judul);
        dest.writeString(this.sinopsis);
        dest.writeString(this.durasi);
        dest.writeString(this.genre);
        dest.writeString(this.release);
    }

    private MovieTvShow(Parcel in) {
        this.picture = in.readInt();
        this.judul = in.readString();
        this.sinopsis = in.readString();
        this.durasi = in.readString();
        this.genre = in.readString();
        this.release = in.readString();
    }

    public static final Parcelable.Creator<MovieTvShow> CREATOR = new Parcelable.Creator<MovieTvShow>() {
        @Override
        public MovieTvShow createFromParcel(Parcel source) {
            return new MovieTvShow(source);
        }

        @Override
        public MovieTvShow[] newArray(int size) {
            return new MovieTvShow[size];
        }
    };
}
