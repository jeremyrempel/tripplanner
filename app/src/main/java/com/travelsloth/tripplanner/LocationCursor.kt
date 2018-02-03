package com.travelsloth.tripplanner

import android.app.SearchManager
import android.database.AbstractCursor
import android.database.DataSetObserver
import android.provider.BaseColumns
import com.travelsloth.tripplanner.model.Location
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LocationCursor(private val locationRepo: Flowable<List<Location>>, private val query: String) : AbstractCursor() {

    private val columnNames = arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_DATA)
    private var data: Array<Array<String>> = arrayOf()

    private val disposable = CompositeDisposable()

    override fun registerDataSetObserver(observer: DataSetObserver) {
        super.registerDataSetObserver(observer)


        locationRepo.map { loclist: List<Location> ->
            loclist
                    .filter { it.name.contains(query, true) }
                    .map { loc ->
                        arrayOf(loc.id, loc.name, loc.id)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            data = it.toTypedArray()
                            onChange(true)
                        },
                        { Timber.e(it) }
                )
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        super.unregisterDataSetObserver(observer)
        disposable.dispose()
    }

    override fun getLong(column: Int) = position.toLong()
    override fun getCount(): Int = data.count()
    override fun getColumnNames() = columnNames
    override fun getShort(column: Int) = data[position][column].toShort()
    override fun getFloat(column: Int) = data[position][column].toFloat()
    override fun getDouble(column: Int) = data[position][column].toDouble()
    override fun isNull(column: Int) = data[position][column] == null
    override fun getInt(column: Int) = data[position][column].toInt()
    override fun getString(column: Int) = data[position][column]
}