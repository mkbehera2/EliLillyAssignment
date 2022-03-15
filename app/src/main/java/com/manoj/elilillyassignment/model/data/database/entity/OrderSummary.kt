package com.manoj.elilillyassignment.model.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_summary")
data class OrderSummary(

    // create orderID variable.
    @ColumnInfo(name = "orderID")
    var orderID: Long,

    // create productID variable to
    // order items.
    @ColumnInfo(name = "productID")
    var productID: String,

    // create itemName variable to
    // order items.
    @ColumnInfo(name = "productName")
    var productName: String,

    // create itemPrice variable to
    // store price.
    @ColumnInfo(name = "itemPrice")
    var itemPrice: Double,

    // create itemPrice variable to
    // store price.
    @ColumnInfo(name = "itemQuantity")
    var itemQuantity: Int,

    // create itemQuantity variable
    // to store quantity.
    @ColumnInfo(name = "address")
    var address: String,


) {
    // Primary key is a unique key
    // for different database.
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
