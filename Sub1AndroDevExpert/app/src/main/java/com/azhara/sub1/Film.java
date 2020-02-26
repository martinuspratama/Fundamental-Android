package com.azhara.sub1;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {
    private int picFilm;
    private String judulFilm;
    private String rilisFilm;
    private String sinopsisFilm;

    public int getPicFilm() {
        return picFilm;
    }

    public void setPicFilm(int picFilm) {
        this.picFilm = picFilm;
    }

    public String getJudulFilm() {
        return judulFilm;
    }

    public void setJudulFilm(String judulFilm) {
        this.judulFilm = judulFilm;
    }

    public String getRilisFilm() {
        return rilisFilm;
    }

    public void setRilisFilm(String rilisFilm) {
        this.rilisFilm = rilisFilm;
    }

    public String getSinopsisFilm() {
        return sinopsisFilm;
    }

    public void setSinopsisFilm(String sinopsisFilm) {
        this.sinopsisFilm = sinopsisFilm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.picFilm);
        dest.writeString(this.judulFilm);
        dest.writeString(this.rilisFilm);
        dest.writeString(this.sinopsisFilm);
    }

    public Film() {
    }

    protected Film(Parcel in) {
        this.picFilm = in.readInt();
        this.judulFilm = in.readString();
        this.rilisFilm = in.readString();
        this.sinopsisFilm = in.readString();
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
