package com.azhara.submission4dicoding.ui.favorite.tvshow.db

import android.provider.BaseColumns

internal class TVDatabaseContract {

    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "tv"
            const val _ID = "_id"
            const val TITLE = "title"
            const val RATING = "rating"
            const val OVERVIEW = "overview"
            const val RELEASE = "release"
            const val VOTE = "vote"
            const val POSTER = "poster"
            const val BACKGROUND = "background"
        }
    }

}