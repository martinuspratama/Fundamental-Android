package com.azhara.proyekakhirdicoding.db.tvshowdatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.azhara.proyekakhirdicoding.db.tvshowdatabase.TvShowDatabaseContract.TvShowColumn.Companion.TITLE
import com.azhara.proyekakhirdicoding.db.tvshowdatabase.TvShowDatabaseContract.TvShowColumn.Companion._ID
import java.sql.SQLException

class TvShowHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = TvShowDatabaseContract.TvShowColumn.TABLE_NAME_TVSHOW
        private lateinit var tvShowDatabaseHelper: TvShowDatabaseHelper
        private var INSTANCE: TvShowHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context?): TvShowHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = context?.let { TvShowHelper(it) }
                    }
                }
            }
            return INSTANCE as TvShowHelper
        }
    }

    init {
        tvShowDatabaseHelper = TvShowDatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = tvShowDatabaseHelper.writableDatabase
    }

    fun close() {
        tvShowDatabaseHelper.close()
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
            "$_ID ASC",
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
        return database.delete(DATABASE_TABLE, "${TITLE} = '$title'", null)
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "${_ID} = '$id'", null)
    }
}