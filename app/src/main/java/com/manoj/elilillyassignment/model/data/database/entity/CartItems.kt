package com.manoj.elilillyassignment.model.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItems(
    // create productID variable to
    // store items.
    @ColumnInfo(name = "productID")
    var productID: String,

    // create itemName variable to
    // store items.
    @ColumnInfo(name = "itemName")
    var itemName: String,

    // create itemQuantity variable
    // to store quantity.
    @ColumnInfo(name = "itemQuantity")
    var itemQuantity: Int,

    // create itemPrice variable to
    // store price.
    @ColumnInfo(name = "itemPrice")
    var itemPrice: Double

) {
    // Primary key is a unique key
    // for different database.
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
