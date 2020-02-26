package com.azhara.proyekakhirdicoding.db.moviesdatabase

import android.net.Uri
import android.provider.BaseColumns

object MoviesDatabaseContract {

    const val MOVIES_AUTHORITY = "com.azhara.proyekakhirdicoding"
    const val SCHEME = "content"


    internal class MoviesColumn : BaseColumns {
        companion object {
            const val TABLE_NAME_MOVIES = "movie"
            const val _ID = "_id"
            const val TITLE = "title"
            const val RATING = "rating"
            const val OVERVIEW = "overview"
            const val RELEASE = "release"
            const val VOTE = "vote"
            const val POSTER = "poster"
            const val BACKGROUND = "background"

            // untuk membuat URI content://com.azhara.proyekakhirdicoding/movie
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(MOVIES_AUTHORITY)
                .appendPath(TABLE_NAME_MOVIES)
                .build()
        }
    }

}