package com.travelsloth.tripplanner.model

import java.util.*

/**
 * Created by jrempel on 10/13/17.
 */
data class DailyReading(
        val month: Int,
        val averageTemp: Float,
        val precipitationMm: Float
)