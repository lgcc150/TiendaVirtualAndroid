package com.example.tiendavirtualandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.util.Log

class RegistrarActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        initializeDatabase()
        initializeUI()
        setupListeners()
    }

    private fun initializeDatabase() {
        database = Firebase.database.reference
        auth = Firebase.auth
    }

    private fun initializeUI() {
        name = findViewById(R.id.username)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        btnRegister = findViewById(R.id.btnCrearCuenta)
    }

    private fun setupListeners() {
        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun createUserInDatabase(userId: String, user: User) {
        if (userId != null) {
            database.child("users").child(userId).setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showSuccessToast("Usuario Creado con éxito")
                        navigateToLogin()
                    } else {
                        showErrorToast("Error al crear el usuario")
                    }
                }
        } else {
            showErrorToast("Error al generar ID de usuario")
        }
    }

    private fun registerUser() {
        val user = User(password.text.toString(), email.text.toString(), name.text.toString())
        // Verificar si el email o la contraseña están vacíos
        if (user.email?.isEmpty()!! || user.password?.isEmpty()!!) {
            Toast.makeText(this, "Email and password cannot be empty.", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("RegistrarActivity", "createUserWithEmail:success")
                    val firebaseUser = auth.currentUser
                    this.createUserInDatabase(userId = firebaseUser?.uid!!, user = user)
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("RegistrarActivity", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        task.exception?.message.toString(),
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
            }

    }

    private fun showSuccessToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}