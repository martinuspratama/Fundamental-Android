package com.azhara.moviesuserapp.helper

import android.database.Cursor
import com.azhara.moviesuserapp.db.movie.MoviesDatabaseContract
import com.azhara.moviesuserapp.model.MoviesModel

object MappingHelper {

    fun mapCursorToArrayListMovies(moviesCursor: Cursor?): ArrayList<MoviesModel> {
        val moviesList = ArrayList<MoviesModel>()

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
                    MoviesModel(
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

//    fun mapCursorToArrayListTvShow(tvShowCursor: Cursor): ArrayList<TvShowModel>{
//        val tvShowlist = ArrayList<TvShowModel>()
//        while (tvShowCursor.moveToNext()){
//            val id = tvShowCursor.getInt(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn._ID))
//            val title = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.TITLE))
//            val overview = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.OVERVIEW))
//            val rating = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.RATING))
//            val poster = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.POSTER))
//            val background = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.BACKGROUND))
//            val release = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.RELEASE))
//            val vote = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowDatabaseContract.TvShowColumn.VOTE))
//            tvShowlist.add(TvShowModel(id, title, rating, overview, release, vote, poster, background))
//        }
//        return tvShowlist
//    }
}