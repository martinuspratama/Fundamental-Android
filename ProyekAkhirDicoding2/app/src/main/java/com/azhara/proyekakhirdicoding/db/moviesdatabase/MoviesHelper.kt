package com.azhara.proyekakhirdicoding.db.moviesdatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract.MoviesColumn.Companion.TABLE_NAME_MOVIES
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract.MoviesColumn.Companion.TITLE
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract.MoviesColumn.Companion._ID
import java.sql.SQLException

class MoviesHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME_MOVIES
        private lateinit var moviesDatabaseHelper: MoviesDatabaseHelper
        private var INSTANCE: MoviesHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context?): MoviesHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = context?.let { MoviesHelper(it) }
                    }
                }
            }
            return INSTANCE as MoviesHelper
        }

    }

    init {
        moviesDatabaseHelper = MoviesDatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = moviesDatabaseHelper.writableDatabase
    }

    fun close() {
        moviesDatabaseHelper.close()
        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID DESC",
            null
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteByTitle(title: String): Int {
        return database.delete(DATABASE_TABLE, "$TITLE = '$title'", null)
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}