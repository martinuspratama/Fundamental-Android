package com.azhara.proyekakhirdicoding.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract.MOVIES_AUTHORITY
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract.MoviesColumn.Companion.CONTENT_URI
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract.MoviesColumn.Companion.TABLE_NAME_MOVIES
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesHelper

class DatabaseFavoriteContentProvider : ContentProvider() {

    companion object {
        /*
       Integer digunakan sebagai identifier antara select all sama select by title
        */
        private const val MOVIES = 1
        private const val MOVIES_ID = 2

        private val MovieUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        private lateinit var moviesHelper: MoviesHelper


        init {
            //content://com.azhara.proyekakhirdicoding/movie
            MovieUriMatcher.addURI(MOVIES_AUTHORITY, TABLE_NAME_MOVIES, MOVIES)
            //content://com.azhara.proyekakhirdicoding/movie/id
            MovieUriMatcher.addURI(MOVIES_AUTHORITY, "$TABLE_NAME_MOVIES/#", MOVIES_ID)

        }
    }

    override fun onCreate(): Boolean {
        moviesHelper = MoviesHelper.getInstance(context as Context)
        moviesHelper.open()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (MovieUriMatcher.match(uri)) {
            MOVIES -> cursor = moviesHelper.queryAll()
            else -> cursor = null
        }
        Log.d("Provider", "$cursor")

        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (MOVIES) {
            MovieUriMatcher.match(uri) -> moviesHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val delete: Int = when (MOVIES_ID) {
            MovieUriMatcher.match(uri) -> {
                moviesHelper.deleteById(uri.lastPathSegment.toString())
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(uri, null)
        return delete
    }

}
