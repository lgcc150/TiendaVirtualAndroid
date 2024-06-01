package com.example.tiendavirtualandroid

import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationService {

    suspend fun getUserLocation(context: Context): Location? {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val locationManger = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
            locationManger.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManger.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        if (!isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        cont.resume(task.result) {}
                    } else {
                        cont.resume(null) {}
                    }
                }
            } else {
                cont.resume(null) {}
            }
        }
    }
}