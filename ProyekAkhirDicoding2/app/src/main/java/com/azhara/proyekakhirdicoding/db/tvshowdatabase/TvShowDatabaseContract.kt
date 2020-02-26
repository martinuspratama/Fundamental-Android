package com.azhara.proyekakhirdicoding.db.tvshowdatabase

import android.net.Uri
import android.provider.BaseColumns

object TvShowDatabaseContract {

    const val TVSHOW_AUTHORITY = "com.azhara.proyekakhirdicoding.tvshow"
    const val SCHEME = "content"

    internal class TvShowColumn : BaseColumns {
        companion object {
            const val TABLE_NAME_TVSHOW = "tvshow"
            const val _ID = "_id"
            const val TITLE = "title"
            const val RATING = "rating"
            const val OVERVIEW = "overview"
            const val RELEASE = "release"
            const val VOTE = "vote"
            const val POSTER = "poster"
            const val BACKGROUND = "background"

            // untuk membuat URI content://com.azhara.proyekakhirdicoding/movie
            val TVSHOW_CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(TVSHOW_AUTHORITY)
                .appendPath(TABLE_NAME_TVSHOW)
                .build()
        }
    }

}