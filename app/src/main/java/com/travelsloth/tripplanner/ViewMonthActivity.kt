package com.travelsloth.tripplanner

import android.app.Activity
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.travelsloth.tripplanner.model.DailyReading
import java.util.*
import java.util.Calendar.DAY_OF_MONTH


/**
 * Created by jrempel on 10/8/17.
 */

class ViewMonthActivity : Activity() {

    val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.view_month)
        actionBar.title = "July Average"
        actionBar.subtitle = "New York"

        val chart: BarChart = findViewById(R.id.monthlytempchart)

        val dailyReadingList = arrayListOf<DailyReading>()
        for (i in 1..5) {

            val cal = Calendar.getInstance()
            cal.time = Date()
            cal.set(Calendar.DAY_OF_MONTH, i)

            val rand1 = rand(80, 90)
            dailyReadingList.add(DailyReading(cal.time, rand1.toFloat(), rand1.toFloat(), 0f))
        }

        val cal = Calendar.getInstance()
        val entries = dailyReadingList.map {
            cal.time = it.readingDate
            BarEntry(cal.get(DAY_OF_MONTH).toFloat(), it.tempHighCelcius)
        }

        val dataSet = BarDataSet(entries, "Temperature (F)")

        val lineData = BarData(dataSet)
        chart.data = lineData
        chart.isHorizontalScrollBarEnabled = true

        val description = Description()
        description.text = "Daily Average Temp (F)"
        chart.description = description

        chart.invalidate()
    }

    fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }
}