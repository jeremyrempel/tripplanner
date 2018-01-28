package com.travelsloth.tripplanner

import android.app.SearchManager
import android.database.AbstractCursor
import android.database.DataSetObserver
import android.provider.BaseColumns
import com.travelsloth.tripplanner.repository.LocationRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class LocationCursor(private val locationRepo: LocationRepository) : AbstractCursor() {

    private val columnNames = arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1)
    private var data: Array<Array<String>> = arrayOf()

    val disposable = CompositeDisposable()

    override fun registerDataSetObserver(observer: DataSetObserver) {
        super.registerDataSetObserver(observer)

        disposable.add(locationRepo.getAllLocations()
                .map { loclist ->
                    loclist.map { loc ->
                        arrayOf(loc.id, loc.name)
                    }
                }
                .subscribe(
                        {
                            data = it.toTypedArray()
                        },
                        { Timber.e(it) }
                ))

        onChange(true)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        super.unregisterDataSetObserver(observer)
        disposable.dispose()
    }

    override fun getLong(column: Int) = data[position][column].toLong()
    override fun getCount(): Int = data.count()
    override fun getColumnNames() = columnNames
    override fun getShort(column: Int) = data[position][column].toShort()
    override fun getFloat(column: Int) = data[position][column].toFloat()
    override fun getDouble(column: Int) = data[position][column].toDouble()
    override fun isNull(column: Int) = data[position][column] == null
    override fun getInt(column: Int) = data[position][column].toInt()
    override fun getString(column: Int) = data[position][column]
}