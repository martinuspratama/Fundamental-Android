package com.azhara.submission3movie.ui.movies.model

import android.os.Parcel
import android.os.Parcelable

data class Movies(
    var background: String? = null,
    var title: String? = null,
    var poster: String? = null,
    var rating: String? = null,
    var releaseDate: String? = null,
    var overview: String? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(background)
        parcel.writeString(title)
        parcel.writeString(poster)
        parcel.writeString(rating)
        parcel.writeString(releaseDate)
        parcel.writeString(overview)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movies> {
        override fun createFromParcel(parcel: Parcel): Movies {
            return Movies(parcel)
        }

        override fun newArray(size: Int): Array<Movies?> {
            return arrayOfNulls(size)
        }
    }
}