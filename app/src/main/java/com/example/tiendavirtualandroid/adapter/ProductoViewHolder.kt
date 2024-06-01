package com.example.tiendavirtualandroid.adapter

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.tiendavirtualandroid.Producto
import com.example.tiendavirtualandroid.R
import com.example.tiendavirtualandroid.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.text.NumberFormat
import java.util.Locale

class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.findViewById<TextView>(R.id.product_title)
    val description = view.findViewById<TextView>(R.id.product_description)
    val image = view.findViewById<ImageView>(R.id.product_image)
    val precio = view.findViewById<TextView>(R.id.product_price)
    val marca = view.findViewById<TextView>(R.id.product_brand)
    val btnAddCart = view.findViewById<Button>(R.id.add_product_button)
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    fun render(producto: Producto, user: FirebaseUser) {
        auth = Firebase.auth
        name.text = producto.titulo;
        description.text = producto.descripcion
        Glide.with(image.context).load(producto.imagenUrl).into(image)
        val format = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(0);
        precio.text = "Precio: ${format.format(producto.precio)}"
        marca.text = "Marca: ${producto.marca}"

        btnAddCart.setOnClickListener {
            database = Firebase.database.reference

            database.child("shoppingCart").child(user.uid).push().setValue(producto)
            Log.w("carrito", "${producto.id}")
        }
    }
}