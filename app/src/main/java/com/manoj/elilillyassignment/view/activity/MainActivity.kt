package com.manoj.elilillyassignment.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.manoj.elilillyassignment.view.adapter.ProductListAdapter
import com.manoj.elilillyassignment.model.data.model.ProductInfo
import com.manoj.elilillyassignment.databinding.ActivityMainBinding
import com.manoj.elilillyassignment.viewmodel.StoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProductListAdapter.AddToCartActionInterface {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: StoreViewModel by viewModels()

    private val cartItems = ArrayList<ProductInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
        storeDetailsDataObserver()
        productsDataObserver()
        initView()
    }

    private fun initView() {
        binding.btnCheckout.setOnClickListener {
            val intent = Intent(this@MainActivity, CartListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun storeDetailsDataObserver() {
        viewModel.storeInfoList.observe(this) {
            Log.i(TAG, "storeDetailsDataObserver(): received store info")
            val storeInfo = it[0]
            binding.storeTitle.text = storeInfo.storeName
            binding.cityName.text = storeInfo.city
            binding.address.text = storeInfo.address
        }
    }

    private fun productsDataObserver() {
        viewModel.productInfoList.observe(this) {
            Log.i(TAG, "productsDataObserver(): received product info")
            binding.rvProductDetails.layoutManager = LinearLayoutManager(this@MainActivity)

            // passing list to adapter
            val adapter = ProductListAdapter(it, this@MainActivity)

            binding.rvProductDetails.adapter = adapter
        }
    }

    override fun onItemAdded(view: View, productInfo: ProductInfo) {
        cartItems.add(productInfo)
        viewModel.addItemToCart(productInfo)
        Toast.makeText(this@MainActivity, "Item added to cart.", Toast.LENGTH_LONG).show()
    }

}