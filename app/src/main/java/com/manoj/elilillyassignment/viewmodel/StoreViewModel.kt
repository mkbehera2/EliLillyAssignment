package com.manoj.elilillyassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.manoj.elilillyassignment.model.data.database.entity.CartItems
import com.manoj.elilillyassignment.model.data.model.ProductInfo
import com.manoj.elilillyassignment.model.data.model.StoreInfo
import com.manoj.elilillyassignment.model.data.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel() {
        companion object {
            val TAG: String = StoreViewModel::class.java.simpleName
        }

    private var storeInfo: String? = storeRepository.getJsonDataFromAsset( "store.json")
    private var productInfo: String? = storeRepository.getJsonDataFromAsset( "products.json")

    private val _storeInfoList = MutableLiveData<List<StoreInfo>>()
        .also {
            it.value = fetchStoreInfo()
        }
    var storeInfoList: LiveData<List<StoreInfo>> = _storeInfoList

    private val _productInfoList = MutableLiveData<List<ProductInfo>>()
        .also {
            it.value =  fetchProductsList()
        }
    var productInfoList: LiveData<List<ProductInfo>> = _productInfoList

    private fun fetchStoreInfo(): List<StoreInfo>{
        Log.i(TAG, "fetchStoreInfo() : $storeInfo")
        val gson = Gson()
        val listStoreInfoTypeToken = object : TypeToken<List<StoreInfo>>() {}.type

        val storeInfoList: List<StoreInfo> = gson.fromJson(storeInfo, listStoreInfoTypeToken)
        storeInfoList.forEachIndexed { idx, storeInfo -> Log.i("Store info data", "> Item $idx:\n$storeInfo") }
        return storeInfoList
    }

    private fun fetchProductsList(): List<ProductInfo> {
        Log.i(TAG, "fetchProductsList() : $productInfo")
        val gson = Gson()
        val listProductInfoTypeToken = object : TypeToken<List<ProductInfo>>() {}.type

        val productInfoList: List<ProductInfo> = gson.fromJson(productInfo, listProductInfoTypeToken)
        productInfoList.forEachIndexed { idx, productInfo -> Log.i("product info data", "> Item $idx:\n$productInfo") }
        return productInfoList
    }

    fun addItemToCart(productInfo: ProductInfo): CartItems {
        val cartItem = CartItems(
            productID = productInfo.productId,
            itemName = productInfo.productName,
            itemQuantity = 1,
            itemPrice = productInfo.productPrice
        )
        viewModelScope.launch {
            storeRepository.insert(cartItem)
        }
        return cartItem
    }

}