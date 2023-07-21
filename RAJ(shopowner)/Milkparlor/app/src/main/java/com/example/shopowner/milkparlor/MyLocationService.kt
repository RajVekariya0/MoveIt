package com.rakshinfotech.fireimagedemo

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast

class MyLocationService : Service() {
    lateinit var locationManager: LocationManager
    lateinit var listener: MyLocationListener
    lateinit var context : Context

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun onStart(intent: Intent, startId: Int) {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        listener = MyLocationListener()
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0f, listener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0f, listener)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("STOP_SERVICE", "DONE")
        locationManager.removeUpdates(listener)
    }

    inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(loc: Location?) {
            try {
                if (loc == null) return
                Log.e("Location Latitude", loc.latitude.toString())
                Log.e("Location Longitude", loc.longitude.toString())


            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            }

        }

        override fun onProviderDisabled(provider: String) {
            Log.e(provider,"Disabled")
        }
        override fun onProviderEnabled(provider: String) {
            Log.e(provider,"Enabled")
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }
}
