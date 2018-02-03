package com.travelsloth.tripplanner

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.travelsloth.tripplanner.model.MonthlyAverage
import com.travelsloth.tripplanner.repository.LocationRepositoryFirestore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


/**
 * Created by jrempel on 10/8/17.
 */
class ViewMonthActivity : Activity() {

    var searchView: SearchView? = null
    var searchMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate")

        setContentView(R.layout.view_month)
        actionBar.subtitle = getString(R.string.average_temp)

        // Get the intent, verify the action and get the query
        handleIntent(intent)
    }

    private fun showData(data: List<MonthlyAverage>, name: String) {
        actionBar.title = name

        val entries = data.map { BarEntry(it.month.toFloat(), it.tempF) }
        val dataSet = BarDataSet(entries, getString(R.string.farenheit_label))
        dataSet.setColors(intArrayOf(R.color.primaryDarkColor), this)

        val chart = findViewById<BarChart>(R.id.monthlytempchart)
        chart.data = BarData(dataSet)
        chart.description.isEnabled = false

        chart.invalidate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        searchMenu = menu.findItem(R.id.search)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView
        // Assumes current activity is the searchable activity
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.setIconifiedByDefault(true) // Do not iconify the widget; expand it by default

        return true
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        // intent.getBundleExtra(SearchManager.APP_DATA)
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Timber.d("Search submit, query: %s", query)

            //use the query to search your data somehow
        } else if (Intent.ACTION_VIEW == intent.action) {
            // Handle a suggestions click (because the suggestions all use ACTION_VIEW)
            val data = intent.data.toString()
            loadDataFromFirebase(data)
            collapseSearch()
        }
    }

    fun collapseSearch() {
        searchView?.isIconified = true
        searchMenu?.collapseActionView()
        searchView?.clearFocus()
    }

    private fun loadDataFromFirebase(locationId: String) {

        val locationRepo = LocationRepositoryFirestore()
        locationRepo.getDataForLocation(locationId)
                .doOnSubscribe {
                    /* show loading */
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dro ->
                            dro?.let { data ->
                                showData(data.data, data.name)
                                data.data.forEach { Timber.d("data: %s", it) }
                            }
                        },
                        { Timber.e(it, "Error retrieving data") }
                )
    }
}