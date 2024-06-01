package com.example.tiendavirtualandroid.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendavirtualandroid.Producto
import com.example.tiendavirtualandroid.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class ShoppingCartViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    private lateinit var database: DatabaseReference

    val name = view.findViewById<TextView>(R.id.shopping_title)
    val image = view.findViewById<ImageView>(R.id.shopping_image)
    val btnDelte = view.findViewById<Button>(R.id.btnDeleteItem)
    fun render(producto: Producto, user: FirebaseUser, onClickDelete: (Int) -> Unit) {

        name.text = producto.titulo;
        Glide.with(image.context).load(producto.imagenUrl).into(image)
        btnDelte.setOnClickListener {
            database = Firebase.database.reference
            database.child("shoppingCart").child(user.uid).child(producto.id!!).removeValue()
            onClickDelete(adapterPosition)
        }
    }
}