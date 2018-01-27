package com.travelsloth.tripplanner

import android.app.Activity
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.travelsloth.tripplanner.model.DailyReading
import timber.log.Timber
import java.util.*
import java.util.Calendar.DAY_OF_MONTH


/**
 * Created by jrempel on 10/8/17.
 */

class ViewMonthActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Activity Created")

        FirebaseApp.initializeApp(applicationContext)
        val db = FirebaseFirestore.getInstance()
        val locationRef = db.collection("stations").document("USW00094789")
        locationRef.addSnapshotListener({ querySnapshot, exception ->
            if (exception != null) {
                Timber.e(exception, "error getting data")
                return@addSnapshotListener
            }

            val data = listOf(
                    DailyReading(1, querySnapshot.getDouble("jan").toFloat(), 0f),
                    DailyReading(2, querySnapshot.getDouble("feb").toFloat(), 0f),
                    DailyReading(3, querySnapshot.getDouble("mar").toFloat(), 0f),
                    DailyReading(4, querySnapshot.getDouble("apr").toFloat(), 0f),
                    DailyReading(5, querySnapshot.getDouble("may").toFloat(), 0f),
                    DailyReading(6, querySnapshot.getDouble("jun").toFloat(), 0f),
                    DailyReading(7, querySnapshot.getDouble("jul").toFloat(), 0f),
                    DailyReading(8, querySnapshot.getDouble("aug").toFloat(), 0f),
                    DailyReading(9, querySnapshot.getDouble("sep").toFloat(), 0f),
                    DailyReading(10, querySnapshot.getDouble("oct").toFloat(), 0f),
                    DailyReading(11, querySnapshot.getDouble("nov").toFloat(), 0f),
                    DailyReading(12, querySnapshot.getDouble("dec").toFloat(), 0f)
            )

            showData(data, querySnapshot.get("name").toString())
            data.forEach { Timber.d("data: %s", it) }
        })

        setContentView(R.layout.view_month)
        actionBar.subtitle = "Average Temp"
    }

    private fun showData(data: List<DailyReading>, name: String) {
        actionBar.title = name

        val entries = data.map { BarEntry(it.month.toFloat(), it.averageTemp) }

        val dataSet = BarDataSet(entries, "Temperature (F)")
        dataSet.setColors(intArrayOf(R.color.primaryDarkColor), this)

        val chart = findViewById<BarChart>(R.id.monthlytempchart)

        chart.data = BarData(dataSet)
        chart.description.isEnabled = false
        chart.isHorizontalScrollBarEnabled = true

        chart.invalidate()
    }
}