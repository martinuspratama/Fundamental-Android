package com.azhara.submission4dicoding.ui.favorite.film.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion.BACKGROUND
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion.OVERVIEW
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion.POSTER
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion.RATING
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion.RELEASE
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion.TITLE
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion.VOTE
import com.azhara.submission4dicoding.ui.favorite.film.db.FilmDatabaseContract.NoteColumns.Companion._ID
import com.azhara.submission4dicoding.ui.film.model.FilmModel
import java.sql.SQLException

class FilmHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var filmDatabaseHelper: FilmDatabaseHelper
        private var INSTANCE: FilmHelper? = null

        private lateinit var database: SQLiteDatabase
        fun getInstance(context: Context?): FilmHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = context?.let { FilmHelper(it) }
                    }
                }
            }
            return INSTANCE as FilmHelper
        }
    }

    init {
        filmDatabaseHelper = FilmDatabaseHelper(context)
    }


    @Throws(SQLException::class)
    fun open() {
        database = filmDatabaseHelper.writableDatabase
    }

    fun close() {
        filmDatabaseHelper.close()

        if (database.isOpen) {
            database.close()
        }
    }

    fun quaryAll(): ArrayList<FilmModel> {
        val arrayList = ArrayList<FilmModel>()
        val cursor = database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
        cursor.moveToFirst()
        var filmModel: FilmModel
        if (cursor.count > 0) {
            do {
                filmModel = FilmModel()
                filmModel.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                filmModel.title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                filmModel.overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW))
                filmModel.release = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE))
                filmModel.rating = cursor.getString(cursor.getColumnIndexOrThrow(RATING))
                filmModel.vote = cursor.getString(cursor.getColumnIndexOrThrow(VOTE))
                filmModel.poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
                filmModel.background = cursor.getString(cursor.getColumnIndexOrThrow(BACKGROUND))

                arrayList.add(filmModel)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)

        }

        cursor.close()
        return arrayList
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

    fun insert(filmModel: FilmModel): Long {
        val insertDb = ContentValues()
        insertDb.put(TITLE, filmModel.title)
        insertDb.put(OVERVIEW, filmModel.overview)
        insertDb.put(RELEASE, filmModel.release)
        insertDb.put(VOTE, filmModel.vote)
        insertDb.put(RATING, filmModel.rating)
        insertDb.put(POSTER, filmModel.poster)
        insertDb.put(BACKGROUND, filmModel.background)
        return database.insert(DATABASE_TABLE, null, insertDb)
    }

    fun deletebytitle(title: String): Int {
        return database.delete(DATABASE_TABLE, "$TITLE = '$title'", null)
    }
}