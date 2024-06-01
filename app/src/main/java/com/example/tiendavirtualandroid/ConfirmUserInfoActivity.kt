package com.example.tiendavirtualandroid


import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ConfirmUserInfoActivity : AppCompatActivity() {

    private lateinit var name : EditText
    private lateinit var email : EditText
    private lateinit var phone : EditText
    private lateinit var address : EditText
    private lateinit var city : EditText
    private lateinit var btnConfirmDelivery: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_user_info)

        // Inicializa las vistas
        name = findViewById(R.id.username)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        address = findViewById(R.id.address)
        city = findViewById(R.id.city)
        btnConfirmDelivery = findViewById(R.id.btnConfirmDelivery)

        // Agrega el listener al botón
        btnConfirmDelivery.setOnClickListener {
            // Accede a los valores de los EditText
            val nameValue = name.text.toString()
            val emailValue = email.text.toString()
            val phoneValue = phone.text.toString()
            val addressValue = address.text.toString()
            val cityValue = city.text.toString()

            // Crea un Intent para iniciar la actividad PurchaseCompleteActivity
            val intent = Intent(this, PurchaseCompleateActivity::class.java)
            startActivity(intent)
        }
    }
}
