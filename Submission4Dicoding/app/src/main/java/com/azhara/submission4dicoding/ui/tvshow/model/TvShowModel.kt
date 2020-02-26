package com.azhara.submission4dicoding.ui.tvshow.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowModel(
    var id: Int = 0,
    var title: String? = null,
    var rating: String? = null,
    var overview: String? = null,
    var release: String? = null,
    var vote: String? = null,
    var poster: String? = null,
    var background: String? = null
) : Parcelable