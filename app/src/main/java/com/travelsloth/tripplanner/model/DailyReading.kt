package com.travelsloth.tripplanner.model

import java.util.*

/**
 * Created by jrempel on 10/13/17.
 */
data class DailyReading(
        val readingDate: Date,
        val tempHighCelcius: Float,
        val tempLowCelcius: Float,
        val precipitationMm: Float
)