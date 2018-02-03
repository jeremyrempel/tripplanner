package com.travelsloth.tripplanner

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.travelsloth.tripplanner.repository.LocationRepositoryFirestore
import timber.log.Timber

/**
 * Created by jrempel on 1/27/18.
 */
class SearchProvider : ContentProvider() {

    val locationRepo = LocationRepositoryFirestore().getAllLocations().replay(1).refCount()

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // uri: content://net.holidayweather.search.provider/search_suggest_query/nnn?limit=50
    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        Timber.d("query: uri: %s, projection: %s, selection: %s, selectionArgs: %s, sortOrder: %s", uri, projection, selection, selectionArgs, sortOrder)

        val query = if (uri?.pathSegments?.size!! > 1) uri?.lastPathSegment?.toLowerCase() else ""

        Timber.d("query: %s", query)
        return LocationCursor(locationRepo, query ?: "")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}