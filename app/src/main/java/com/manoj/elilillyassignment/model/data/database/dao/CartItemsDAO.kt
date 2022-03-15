package com.manoj.elilillyassignment.model.data.database.dao

import androidx.room.*
import com.manoj.elilillyassignment.model.data.database.entity.CartItems

// This class is used to create
// function for database.
@Dao
interface CartItemsDAO {

    // Insert function is used to
    // insert data in database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItems)

    // Delete function is used to
    // delete data in database.
    @Delete
    suspend fun delete(item: CartItems) : Int

    // getAllCartItems function is used to get
    // all the cart items of database.
    @Query("SELECT * FROM cart_items")
    suspend fun getAllCartItems(): List<CartItems>

    @Query("DELETE  FROM cart_items")
    suspend fun deleteAllCartItems()
}