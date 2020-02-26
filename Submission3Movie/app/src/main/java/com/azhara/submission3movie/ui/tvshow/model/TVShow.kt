package com.azhara.submission3movie.ui.tvshow.model

import android.os.Parcel
import android.os.Parcelable

data class TVShow(
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

    companion object CREATOR : Parcelable.Creator<TVShow> {
        override fun createFromParcel(parcel: Parcel): TVShow {
            return TVShow(parcel)
        }

        override fun newArray(size: Int): Array<TVShow?> {
            return arrayOfNulls(size)
        }
    }
}