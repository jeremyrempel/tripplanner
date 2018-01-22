package com.travelsloth.tripplanner

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Created by jrempel on 1/21/18.
 */
class TripPlannerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}