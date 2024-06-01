package com.example.tiendavirtualandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LocationActivity : AppCompatActivity() {
    private val locationService: LocationService = LocationService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val btnLocation = findViewById<Button>(R.id.btnLocation)
        val textLocation = findViewById<TextView>(R.id.locationText)

        btnLocation.setOnClickListener {
            lifecycleScope.launch {
                val result = locationService.getUserLocation(this@LocationActivity)
                if (result != null) {
                    textLocation.text = "Latitud: ${result.latitude} - Longitud: ${result.longitude}"
                } else {
                    textLocation.text = "No se pudo obtener la ubicación"
                }
            }
        }
    }
}