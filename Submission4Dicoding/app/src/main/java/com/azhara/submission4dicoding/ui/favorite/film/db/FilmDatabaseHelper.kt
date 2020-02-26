package com.azhara.submission4dicoding.ui.favorite.film.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion.TABLE_NAME

internal class FilmDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbfilmcatalog"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_FILM = "CREATE TABLE $TABLE_NAME" +
                " (${NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${NoteColumns.TITLE} TEXT NOT NULL," +
                " ${NoteColumns.RATING} TEXT NOT NULL," +
                " ${NoteColumns.OVERVIEW} TEXT NOT NULL," +
                " ${NoteColumns.RELEASE} TEXT NOT NULL," +
                " ${NoteColumns.VOTE} TEXT NOT NULL," +
                " ${NoteColumns.POSTER} TEXT NOT NULL," +
                " ${NoteColumns.BACKGROUND} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FILM)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}