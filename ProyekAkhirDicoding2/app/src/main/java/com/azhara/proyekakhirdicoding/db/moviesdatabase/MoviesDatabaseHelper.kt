package com.azhara.proyekakhirdicoding.db.moviesdatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract.MoviesColumn.Companion.TABLE_NAME_MOVIES

internal class MoviesDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "moviedb"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME_MOVIES" +
                " (${MoviesDatabaseContract.MoviesColumn._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${MoviesDatabaseContract.MoviesColumn.TITLE} TEXT NOT NULL," +
                " ${MoviesDatabaseContract.MoviesColumn.RATING} TEXT NOT NULL," +
                " ${MoviesDatabaseContract.MoviesColumn.OVERVIEW} TEXT NOT NULL," +
                " ${MoviesDatabaseContract.MoviesColumn.RELEASE} TEXT NOT NULL," +
                " ${MoviesDatabaseContract.MoviesColumn.VOTE} TEXT NOT NULL," +
                " ${MoviesDatabaseContract.MoviesColumn.POSTER} TEXT NOT NULL," +
                " ${MoviesDatabaseContract.MoviesColumn.BACKGROUND} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_MOVIES")
        onCreate(db)
    }


}