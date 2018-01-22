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

    val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Activity Created")

        FirebaseApp.initializeApp(applicationContext)
        val db = FirebaseFirestore.getInstance()
        val locationRef = db.collection("locations").document("newyork").collection("months")
        locationRef.addSnapshotListener({ querySnapshot, exception ->
            if (exception != null) {
                Timber.e(exception, "error getting data")
                return@addSnapshotListener
            }

            val data = querySnapshot.map { doc ->
                DailyReading(
                        doc.id.toInt(),
                        doc.getDouble("averagetemp").toFloat(),
                        0f
                )
            }

            showData(data)
            data.forEach { row -> Timber.d("data: %s", row) }
        })

        setContentView(R.layout.view_month)
        actionBar.title = "New York"
        actionBar.subtitle = "Average Temp"
    }

    fun showData(data: List<DailyReading>) {
        val entries = data.map { BarEntry(it.month.toFloat(), it.averageTemp) }

        val dataSet = BarDataSet(entries, "Temperature (F)")
        val lineData = BarData(dataSet)

        val chart = findViewById<BarChart>(R.id.monthlytempchart)
        chart.data = lineData
        chart.isHorizontalScrollBarEnabled = true

        chart.invalidate()
    }
}