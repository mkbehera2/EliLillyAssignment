package com.manoj.elilillyassignment.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manoj.elilillyassignment.R
import com.manoj.elilillyassignment.model.data.model.ProductInfo

class ProductListAdapter(
    private val productList: List<ProductInfo>,
    private val addToCartClickListener: AddToCartActionInterface
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_product_details, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productInfo = productList[position]
        holder.updateViewHolder(productInfo, addToCartClickListener)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return productList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        private val tvProductName: TextView = itemView.findViewById(R.id.product_name)
        private val tvProductDesc: TextView = itemView.findViewById(R.id.product_description)
        private val tvProductPrice: TextView = itemView.findViewById(R.id.product_price)
        private val btnAddToCart: ImageView = itemView.findViewById(R.id.btn_add_to_cart)

        @SuppressLint("SetTextI18n")
        fun updateViewHolder(
            productInfo: ProductInfo,
            addToCartActionInterface: AddToCartActionInterface
        ) {
            tvProductName.text = "Name: ${productInfo.productName}"
            tvProductDesc.text = "Details: ${productInfo.productDescription}"
            tvProductPrice.text = "Price: ${productInfo.productPrice}"
            btnAddToCart.setOnClickListener {
                addToCartActionInterface.onItemAdded(it, productInfo)
            }
        }
    }

    interface AddToCartActionInterface {
        fun onItemAdded(view: View, productInfo: ProductInfo)
    }
}