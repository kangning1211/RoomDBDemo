package com.example.roomdbdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbdemo.data.MyAdapter
import com.example.roomdbdemo.data.Product
import com.example.roomdbdemo.data.ProductDB
import com.example.roomdbdemo.data.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var dao: ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = ProductDB.getInstance(application).productDao

        val btnInsert: Button = findViewById(R.id.btnInsert)
        btnInsert.setOnClickListener() {

            val name: String = findViewById<TextView>(R.id.tfName).text.toString()
            val price: Double = findViewById<TextView>(R.id.tfPrice).text.toString().toDouble()

            val p = Product(0, name, price)

            CoroutineScope(IO).launch {
                dao.insertProduct(p)
            }
        }

        val btnGet: Button = findViewById(R.id.btnGet)
        btnGet.setOnClickListener() {

            CoroutineScope(IO).launch {
                val productList: List<Product> = dao.getAll()

                /*var nameList = ""
                for (product : Product in productList){
                    nameList += product.name + "\n"
                }*/

                CoroutineScope(Main).launch {
                    val productAdapter = MyAdapter(productList)
                    val recycleView : RecyclerView = findViewById(R.id.productRecycleView)
                    recycleView.adapter = productAdapter
                    recycleView.setHasFixedSize(true)

                    /*val tvResult: TextView = findViewById(R.id.tvResult)
                    tvResult.text = nameList*/
                }
            }
        }
    }
}