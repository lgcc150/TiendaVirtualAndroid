package com.example.tiendavirtualandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.example.tiendavirtualandroid.adapter.ShoppingCartAdapter

class ShoppingCartActivity : AppCompatActivity() {
    private var database: DatabaseReference = Firebase.database.reference
    private var productArrayList = ArrayList<Producto>()
    private lateinit var auth: FirebaseAuth
    private lateinit var recicyleView: RecyclerView
    private lateinit var btnConfirmPayment: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_shopping_cart)
        initRecicyleView()

        // Inicializa btnConfirmPayment antes de intentar acceder a ella
        btnConfirmPayment = findViewById(R.id.btnPaymentConfirm)

        btnConfirmPayment.setOnClickListener {
            val intent = Intent(this, ConfirmUserInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onDeleteItem(position: Int) {
        productArrayList.removeAt(position)
        recicyleView.adapter?.notifyItemRemoved(position)
    }

    private fun initRecicyleView() {
        val currentUser = auth.currentUser
        recicyleView = findViewById<RecyclerView>(R.id.shopping_cart_recycle)
        val emptyCartMessage = findViewById<TextView>(R.id.empty_cart_message)

        recicyleView.layoutManager = LinearLayoutManager(this)
        database.child("shoppingCart").child(currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    productArrayList.clear()
                    for (productSnapshot in dataSnapshot.children) {
                        if (productSnapshot.exists()) {
                            val item = productSnapshot.getValue(Producto::class.java)
                            item?.id = productSnapshot.key!!
                            productArrayList.add(item!!)
                        }
                    }
                    recicyleView.adapter = ShoppingCartAdapter(
                        productArrayList,
                        currentUser!!,
                        onClickDelete = { position -> onDeleteItem(position) }
                    )

                    // Actualizar la visibilidad basada en si la lista está vacía o no
                    if (productArrayList.isEmpty()) {
                        emptyCartMessage.visibility = View.VISIBLE
                        recicyleView.visibility = View.GONE
                    } else {
                        emptyCartMessage.visibility = View.GONE
                        recicyleView.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
    }
}
