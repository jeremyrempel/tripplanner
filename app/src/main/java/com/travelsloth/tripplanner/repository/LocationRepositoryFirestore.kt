package com.travelsloth.tripplanner.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.travelsloth.tripplanner.model.Location
import com.travelsloth.tripplanner.model.MonthlyAverage
import com.travelsloth.tripplanner.model.MonthlyReading
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Flowable

/**
 * Created by jrempel on 1/27/18.
 */
class LocationRepositoryFirestore : LocationRepository {

    private val ds = FirebaseFirestore.getInstance()

    override fun getAllLocations(): Flowable<List<Location>> {
        val stations = ds.collection("stations")

        return RxFirestore.getCollection(stations).map {
            it.documents.map {
                Location(it.getString("id"), it.getString("name"))
            }.toList()
        }.toFlowable()
    }

    override fun getDataForLocation(id: String): Flowable<MonthlyReading> {
        val ref = ds.collection("stations").document(id)

        return RxFirestore.observeDocumentRef(ref).map { querySnapshot ->
            val daily = listOf(
                    MonthlyAverage(1, querySnapshot.getDouble("jan").toFloat(), 0f),
                    MonthlyAverage(2, querySnapshot.getDouble("feb").toFloat(), 0f),
                    MonthlyAverage(3, querySnapshot.getDouble("mar").toFloat(), 0f),
                    MonthlyAverage(4, querySnapshot.getDouble("apr").toFloat(), 0f),
                    MonthlyAverage(5, querySnapshot.getDouble("may").toFloat(), 0f),
                    MonthlyAverage(6, querySnapshot.getDouble("jun").toFloat(), 0f),
                    MonthlyAverage(7, querySnapshot.getDouble("jul").toFloat(), 0f),
                    MonthlyAverage(8, querySnapshot.getDouble("aug").toFloat(), 0f),
                    MonthlyAverage(9, querySnapshot.getDouble("sep").toFloat(), 0f),
                    MonthlyAverage(10, querySnapshot.getDouble("oct").toFloat(), 0f),
                    MonthlyAverage(11, querySnapshot.getDouble("nov").toFloat(), 0f),
                    MonthlyAverage(12, querySnapshot.getDouble("dec").toFloat(), 0f)
            )

            MonthlyReading(id, querySnapshot.getString("name"), daily)
        }
    }
}