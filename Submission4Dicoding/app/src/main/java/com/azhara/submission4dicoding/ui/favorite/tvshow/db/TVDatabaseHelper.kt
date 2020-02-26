package com.azhara.submission4dicoding.ui.favorite.tvshow.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion.TABLE_NAME

internal class TVDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbtvcatalog"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_TV = "CREATE TABLE $TABLE_NAME" +
                " (${TVDatabaseContract.NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${TVDatabaseContract.NoteColumns.TITLE} TEXT NOT NULL," +
                " ${TVDatabaseContract.NoteColumns.RATING} TEXT NOT NULL," +
                " ${TVDatabaseContract.NoteColumns.OVERVIEW} TEXT NOT NULL," +
                " ${TVDatabaseContract.NoteColumns.RELEASE} TEXT NOT NULL," +
                " ${TVDatabaseContract.NoteColumns.VOTE} TEXT NOT NULL," +
                " ${TVDatabaseContract.NoteColumns.POSTER} TEXT NOT NULL," +
                " ${TVDatabaseContract.NoteColumns.BACKGROUND} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_TV)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}