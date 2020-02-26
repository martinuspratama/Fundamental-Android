package com.azhara.proyekakhirdicoding.helper

import android.database.Cursor
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract
import com.azhara.proyekakhirdicoding.db.tvshowdatabase.TvShowDatabaseContract
import com.azhara.proyekakhirdicoding.model.MovieFavoriteDetail
import com.azhara.proyekakhirdicoding.model.TvShowFavoriteDetail


object MappingHelper {

    fun mapCursorToArrayListMovies(moviesCursor: Cursor?): ArrayList<MovieFavoriteDetail> {
        val moviesList = ArrayList<MovieFavoriteDetail>()

        moviesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn._ID))
                val title =
                    getString(getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.TITLE))
                val overview =
                    getString(getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.OVERVIEW))
                val rating =
                    getString(getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.RATING))
                val poster =
                    getString(getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.POSTER))
                val background =
                    getString(getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.BACKGROUND))
                val release =
                    getString(getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.RELEASE))
                val vote =
                    getString(getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.VOTE))
                moviesList.add(
                    MovieFavoriteDetail(
                        id,
                        title,
                        rating,
                        overview,
                        release,
                        vote,
                        poster,
                        background
                    )
                )
            }
        }
        return moviesList
    }

    fun mapCursorToObject(moviesCursor: Cursor): MovieFavoriteDetail {
        moviesCursor.moveToNext()
        val id =
            moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn._ID))
        val title =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.TITLE))
        val overview =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.OVERVIEW))
        val rating =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.RATING))
        val poster =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.POSTER))
        val background =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.BACKGROUND))
        val release =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.RELEASE))
        val vote =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MoviesDatabaseContract.MoviesColumn.VOTE))
        return MovieFavoriteDetail(id, title, rating, overview, release, vote, poster, background)
    }

    fun mapCursorToArrayListTvShow(tvShowCursor: Cursor): ArrayList<TvShowFavoriteDetail> {
        val tvShowlist = ArrayList<TvShowFavoriteDetail>()
        while (tvShowCursor.moveToNext()) {
            val id =
                tvShowCursor.getInt(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn._ID))
            val title =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.TITLE))
            val overview =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.OVERVIEW))
            val rating =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.RATING))
            val poster =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.POSTER))
            val background =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.BACKGROUND))
            val release =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.RELEASE))
            val vote =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.VOTE))
            tvShowlist.add(
                TvShowFavoriteDetail(
                    id,
                    title,
                    rating,
                    overview,
                    release,
                    vote,
                    poster,
                    background
                )
            )
        }
        return tvShowlist
    }

}