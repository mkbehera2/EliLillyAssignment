package com.manoj.elilillyassignment.model.data.repository

import android.content.Context
import com.manoj.elilillyassignment.model.data.database.ProductDatabase
import com.manoj.elilillyassignment.model.data.database.entity.CartItems
import com.manoj.elilillyassignment.model.data.database.entity.OrderSummary
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class StoreRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: ProductDatabase
    ) {

    fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    suspend fun insert(item: CartItems) = db.cartItemDAO().insert(item)
    suspend fun delete(item: CartItems) : Int = db.cartItemDAO().delete(item)
    suspend fun allCartItems() = db.cartItemDAO().getAllCartItems()
    suspend fun deleteAll() = db.cartItemDAO().deleteAllCartItems()
    suspend fun allOrders() = db.orderSummaryDAO().getAllOrders()
    suspend fun insertOrder(order: OrderSummary) = db.orderSummaryDAO().insertOrder(order)

}