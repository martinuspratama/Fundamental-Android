package com.azhara.proyekakhirdicoding.db.tvshowdatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class TvShowDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "tvdb"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE =
            "CREATE TABLE ${TvShowDatabaseContract.TvShowColumn.TABLE_NAME_TVSHOW}" +
                    " (${TvShowDatabaseContract.TvShowColumn._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${TvShowDatabaseContract.TvShowColumn.TITLE} TEXT NOT NULL," +
                    " ${TvShowDatabaseContract.TvShowColumn.RATING} TEXT NOT NULL," +
                    " ${TvShowDatabaseContract.TvShowColumn.OVERVIEW} TEXT NOT NULL," +
                    " ${TvShowDatabaseContract.TvShowColumn.RELEASE} TEXT NOT NULL," +
                    " ${TvShowDatabaseContract.TvShowColumn.VOTE} TEXT NOT NULL," +
                    " ${TvShowDatabaseContract.TvShowColumn.POSTER} TEXT NOT NULL," +
                    " ${TvShowDatabaseContract.TvShowColumn.BACKGROUND} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TvShowDatabaseContract.TvShowColumn.TABLE_NAME_TVSHOW}")
        onCreate(db)
    }
}