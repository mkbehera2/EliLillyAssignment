package com.manoj.elilillyassignment.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manoj.elilillyassignment.model.data.database.entity.CartItems
import com.manoj.elilillyassignment.R

class CartItemsAdapter (
    private val cartItems: List<CartItems>,
    private val removeItemFromCartClickListener: RemoveItemFromCartActionInterface
        ): RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_cart_items, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.updateViewHolder(cartItem, removeItemFromCartClickListener)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return cartItems.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        private val layoutRemove: LinearLayout = itemView.findViewById(R.id.remove_layout)
        private val editLayout: LinearLayout = itemView.findViewById(R.id.editLayout)
        private val tvCartItemName: TextView = itemView.findViewById(R.id.tv_cart_item_name)
        private val tvCartItemQuantity: TextView = itemView.findViewById(R.id.tv_cart_item_quantity)
        private val tvCartItemPrice: TextView = itemView.findViewById(R.id.tv_cart_item_price)
        private val btnDeleteItemFromCart: ImageView = itemView.findViewById(R.id.btn_delete)

        @SuppressLint("SetTextI18n")
        fun updateViewHolder(
            cartItem: CartItems,
            addToCartActionInterface: RemoveItemFromCartActionInterface
        ) {
            tvCartItemName.text = cartItem.itemName
            tvCartItemQuantity.text = "Qty : ${cartItem.itemQuantity}"
            tvCartItemPrice.text = "Price : ${cartItem.itemPrice}"
            layoutRemove.setOnClickListener {
                addToCartActionInterface.onItemDeleted(it, cartItem)
            }

            editLayout.setOnClickListener {
                addToCartActionInterface.onItemEdit(it, cartItem)
            }
        }
    }

    interface RemoveItemFromCartActionInterface {
        fun onItemDeleted(view: View, cartItems: CartItems)
        fun onItemEdit(view: View, cartItems: CartItems)
    }
}
