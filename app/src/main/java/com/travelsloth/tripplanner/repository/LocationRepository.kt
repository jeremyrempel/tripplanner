package com.travelsloth.tripplanner.repository

import com.travelsloth.tripplanner.model.Location
import com.travelsloth.tripplanner.model.MonthlyReading
import io.reactivex.Flowable

/**
 * Created by jrempel on 1/27/18.
 */
interface LocationRepository {
    fun getDataForLocation(id: String): Flowable<MonthlyReading>
    fun getAllLocations(): Flowable<List<Location>>
}