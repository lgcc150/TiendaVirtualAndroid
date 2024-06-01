package com.example.tiendavirtualandroid

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val password: String? = null, val email: String? = null, val name: String? = null) {

}