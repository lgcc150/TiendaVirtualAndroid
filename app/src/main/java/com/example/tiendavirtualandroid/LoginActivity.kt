package com.example.tiendavirtualandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.util.Log
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnCrearCuenta: Button
    private lateinit var auth: FirebaseAuth
// ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeDatabase()
        initializeUI()
        setupListeners()
    }

    private fun initializeUI() {
        btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        btnLogin = findViewById<Button>(R.id.loginButton)
    }

    private fun setupListeners() {
        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        /*// Verificar si el email o la contraseña están vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty.", Toast.LENGTH_SHORT).show()
            return
        }*/
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.d("LoginActivity", "${user?.email}")
                    val intent = Intent(this, ProductListActivity::class.java)
                    startActivity(intent)
                    //finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

    }

    private fun initializeDatabase() {
        database = Firebase.database.reference
        auth = Firebase.auth
    }
}