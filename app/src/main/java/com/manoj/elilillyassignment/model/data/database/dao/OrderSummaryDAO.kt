package com.manoj.elilillyassignment.model.data.database.dao

import androidx.room.*
import com.manoj.elilillyassignment.model.data.database.entity.OrderSummary

@Dao
interface OrderSummaryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderSummary)

    // Delete function is used to
    // delete data in database.
    @Delete
    suspend fun delete(order: OrderSummary)

    // getAllCartItems function is used to get
    // all the cart items of database.
    @Query("SELECT * FROM order_summary")
    suspend fun getAllOrders(): List<OrderSummary>

    @Query("DELETE  FROM order_summary")
    suspend fun deleteAllOrders()
}