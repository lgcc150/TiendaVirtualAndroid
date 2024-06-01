package com.example.tiendavirtualandroid

data class Producto(
    var id: String? = null,
    val titulo: String?=null,
    val precio: Int? = null,
    val descripcion: String? = null,
    val marca: String? = null,
    val imagenUrl: String?= null,
    val unidades: Int? = null
)