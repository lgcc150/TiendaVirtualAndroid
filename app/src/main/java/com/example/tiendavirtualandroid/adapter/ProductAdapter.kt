package com.example.tiendavirtualandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendavirtualandroid.Producto
import com.example.tiendavirtualandroid.R
import com.example.tiendavirtualandroid.User
import com.google.firebase.auth.FirebaseUser

class ProductAdapter(private val productosList: List<Producto>,private val user: FirebaseUser ) :
    RecyclerView.Adapter<ProductoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(
            layoutInflater.inflate(
                R.layout.activity_card_product,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return productosList.size
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val item = productosList[position]
        holder.render(item, user)
    }
}