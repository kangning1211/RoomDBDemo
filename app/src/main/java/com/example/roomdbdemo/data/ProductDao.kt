package com.example.roomdbdemo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(p:Product)

    @Query("SELECT * FROM product_table")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product_table WHERE price < :priceRange")
    fun getPriceBelow(priceRange: Double) : List<Product>
}