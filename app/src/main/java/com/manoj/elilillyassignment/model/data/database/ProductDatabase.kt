package com.manoj.elilillyassignment.model.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manoj.elilillyassignment.model.data.database.dao.CartItemsDAO
import com.manoj.elilillyassignment.model.data.database.dao.OrderSummaryDAO
import com.manoj.elilillyassignment.model.data.database.entity.CartItems
import com.manoj.elilillyassignment.model.data.database.entity.OrderSummary

@Database(entities = [CartItems::class, OrderSummary::class], version = 1, exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun cartItemDAO(): CartItemsDAO
    abstract fun orderSummaryDAO(): OrderSummaryDAO

}