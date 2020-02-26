package com.azhara.submission4dicoding.ui.favorite.tvshow.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion.BACKGROUND
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion.OVERVIEW
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion.POSTER
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion.RATING
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion.RELEASE
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion.TITLE
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion.VOTE
import com.azhara.submission4dicoding.ui.favorite.tvshow.db.TVDatabaseContract.NoteColumns.Companion._ID
import com.azhara.submission4dicoding.ui.tvshow.model.TvShowModel
import java.sql.SQLException

class TVHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var tvDatabaseHelper: TVDatabaseHelper
        private var INSTANCE: TVHelper? = null

        private lateinit var databaseTv: SQLiteDatabase
        fun getInstance(context: Context?): TVHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = context?.let { TVHelper(it) }
                    }
                }
            }
            return INSTANCE as TVHelper
        }
    }

    init {
        tvDatabaseHelper = TVDatabaseHelper(context)
    }


    @Throws(SQLException::class)
    fun open() {
        databaseTv = tvDatabaseHelper.writableDatabase
    }

    fun close() {
        tvDatabaseHelper.close()

        if (databaseTv.isOpen) {
            databaseTv.close()
        }
    }

    fun quaryAll(): ArrayList<TvShowModel> {
        val arrayList = ArrayList<TvShowModel>()
        val cursor = databaseTv.query(
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
        var tvShowModel: TvShowModel
        if (cursor.count > 0) {
            do {
                tvShowModel = TvShowModel()
                tvShowModel.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                tvShowModel.title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                tvShowModel.overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW))
                tvShowModel.release = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE))
                tvShowModel.rating = cursor.getString(cursor.getColumnIndexOrThrow(RATING))
                tvShowModel.vote = cursor.getString(cursor.getColumnIndexOrThrow(VOTE))
                tvShowModel.poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
                tvShowModel.background = cursor.getString(cursor.getColumnIndexOrThrow(BACKGROUND))

                arrayList.add(tvShowModel)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)

        }

        cursor.close()
        return arrayList
    }

    fun insert(tvShowModel: TvShowModel): Long {
        val insertDb = ContentValues()
        insertDb.put(TITLE, tvShowModel.title)
        insertDb.put(OVERVIEW, tvShowModel.overview)
        insertDb.put(RELEASE, tvShowModel.release)
        insertDb.put(VOTE, tvShowModel.vote)
        insertDb.put(RATING, tvShowModel.rating)
        insertDb.put(POSTER, tvShowModel.poster)
        insertDb.put(BACKGROUND, tvShowModel.background)
        return databaseTv.insert(DATABASE_TABLE, null, insertDb)
    }

    fun deletebytitle(title: String): Int {
        return databaseTv.delete(DATABASE_TABLE, "$TITLE = '$title'", null)
    }
}