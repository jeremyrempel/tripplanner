package com.travelsloth.tripplanner

import android.app.Application
import com.google.firebase.FirebaseApp
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Created by jrempel on 1/21/18.
 */
class TripPlannerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // timber setup
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        // firebase setup
        Timber.d("Initializing Firebase ")
        FirebaseApp.initializeApp(applicationContext)
    }
}