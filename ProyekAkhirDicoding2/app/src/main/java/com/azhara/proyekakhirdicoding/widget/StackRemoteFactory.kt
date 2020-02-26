package com.azhara.proyekakhirdicoding.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.db.moviesdatabase.MoviesDatabaseContract.MoviesColumn.Companion.CONTENT_URI
import com.azhara.proyekakhirdicoding.helper.MappingHelper
import com.azhara.proyekakhirdicoding.model.MovieFavoriteDetail
import com.bumptech.glide.Glide

internal class StackRemoteFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {


    private val moviesData = ArrayList<MovieFavoriteDetail>()

    private lateinit var cursor: Cursor

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long {
        if (cursor.moveToPosition(position)) {
            return cursor.getLong(0)
        } else {
            return position.toLong()
        }
    }

    override fun onDataSetChanged() {

        val token = Binder.clearCallingIdentity()

        cursor = context.contentResolver?.query(CONTENT_URI, null, null, null, null) as Cursor
        val movies = MappingHelper.mapCursorToArrayListMovies(cursor)
        moviesData.addAll(movies)

        Binder.restoreCallingIdentity(token)
    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews? {

        if (position == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(
                position
            )
        ) {
            return null
        }

        val rv = RemoteViews(context.packageName, R.layout.widget_items_movies)
        val poster = Glide.with(context)
            .asBitmap()
            .load(moviesData[position].poster)
            .submit()
            .get()
        rv.setImageViewBitmap(R.id.imageView, poster)

        val extras = bundleOf(
            MoviesStackAppWidget.EXTRA_ITEM to position
        )

        val fillIntent = Intent()
        fillIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)
        return rv
    }

    override fun getCount(): Int = moviesData.size

    override fun getViewTypeCount(): Int = 1
    override fun onDestroy() {

    }


}