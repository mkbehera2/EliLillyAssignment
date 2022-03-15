package com.manoj.elilillyassignment.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.manoj.elilillyassignment.R
import com.manoj.elilillyassignment.view.adapter.CartItemsAdapter
import com.manoj.elilillyassignment.model.data.database.entity.CartItems
import com.manoj.elilillyassignment.databinding.ActivityOrderSummaryBinding
import com.manoj.elilillyassignment.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartListActivity : AppCompatActivity(),
    CartItemsAdapter.RemoveItemFromCartActionInterface {

    companion object {
        val TAG: String = CartListActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityOrderSummaryBinding
    private val viewModel: CartViewModel by viewModels()

    private val cartItems = ArrayList<CartItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheckout.setOnClickListener {
            if (binding.addressInput.text?.isNotEmpty() == true) {
                binding.checkoutProgressLayout.visibility = View.VISIBLE
                binding.btnCheckout.isClickable = false
                viewModel.processOrder(cartItems, binding.addressInput.text.toString())
                startTimer()
            } else {
                Snackbar
                    .make(
                        binding.root,
                        "Please enter delivery address to proceed", Snackbar.LENGTH_SHORT
                    )
                    .show()
            }

        }
        addCartItemsDataObserver()

    }

    private fun startTimer() {
        val handler = Handler(mainLooper)
        handler.postDelayed({
            binding.checkoutProgressLayout.visibility = View.GONE
            binding.btnCheckout.isClickable = true
            val intent = Intent(
                this@CartListActivity, SuccessOrderActivity::class.java
            )
            startActivity(intent)
            finish()
        }, 5000)

    }

    @SuppressLint("SetTextI18n")
    private fun addCartItemsDataObserver() {
        viewModel.cartItemList.observe(this) {
            Log.i(TAG, "received cart list")

            binding.rvCartItems.layoutManager = LinearLayoutManager(this@CartListActivity)

            binding.btnCheckout.isEnabled = it.isNotEmpty()
            cartItems.clear()
            cartItems.addAll( it)
            // This will pass the ArrayList to our Adapter
            val adapter = CartItemsAdapter(it, this@CartListActivity)

            binding.rvCartItems.adapter = adapter
            var totalPrice = 0.0
            for (cartItem in it) {
                totalPrice += cartItem.itemPrice
            }
            binding.tvTotalPrice.text = "Rs $totalPrice"

            val layoutCartItems = findViewById<View>(R.id.layout_items) as LinearLayout
            val layoutCartPayments = findViewById<View>(R.id.layout_payment) as LinearLayout
            val layoutCartNoItems = findViewById<View>(R.id.layout_cart_empty) as LinearLayout
            val layoutAddress = findViewById<View>(R.id.layout_address) as LinearLayout

            if (it.isNotEmpty()) {
                layoutCartNoItems.visibility = View.GONE
                layoutCartItems.visibility = View.VISIBLE
                layoutCartPayments.visibility = View.VISIBLE
                layoutAddress.visibility = View.VISIBLE
            } else {
                layoutCartNoItems.visibility = View.VISIBLE
                layoutAddress.visibility = View.GONE
                layoutCartItems.visibility = View.GONE
                layoutCartPayments.visibility = View.GONE
                val bStartShopping = findViewById<View>(R.id.bAddNew) as Button
                bStartShopping.setOnClickListener { finish() }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemDeleted(view: View, cartItems: CartItems) {
        binding.rvCartItems.adapter?.notifyDataSetChanged()
        viewModel.deleteItemFromCart(cartItems)
    }

    override fun onItemEdit(view: View, cartItems: CartItems) {
        finish()
    }
}