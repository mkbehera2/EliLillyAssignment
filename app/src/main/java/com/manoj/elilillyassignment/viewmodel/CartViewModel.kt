package com.manoj.elilillyassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoj.elilillyassignment.model.data.database.entity.CartItems
import com.manoj.elilillyassignment.model.data.database.entity.OrderSummary
import com.manoj.elilillyassignment.model.data.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel() {

    private val _cartItemList = MutableLiveData<List<CartItems>>()
        .also {
            viewModelScope.launch {
                it.postValue(loadCartItemsFromDB())
            }
        }

    var cartItemList: LiveData<List<CartItems>> = _cartItemList

    suspend fun loadCartItemsFromDB(): List<CartItems> {
        val cartItems: ArrayList<CartItems> = ArrayList()
        val getCartItemsFromDB = viewModelScope.async(Dispatchers.IO) {
            storeRepository.allCartItems()
        }
        cartItems.addAll(getCartItemsFromDB.await())
        return cartItems
    }

    fun deleteItemFromCart(cartItem: CartItems) {
        runBlocking {
            storeRepository.delete(cartItem)
        }
        updateView()
    }

    private fun updateView() {
        viewModelScope.launch {
            _cartItemList.postValue(loadCartItemsFromDB())
        }
    }

    fun processOrder(cartItems: ArrayList<CartItems>, address: String) {
        val orderID = System.currentTimeMillis()
        viewModelScope.launch {
            for( item in cartItems) {
                val orderSummary = OrderSummary(
                    orderID = orderID,
                    productID = item.productID,
                    productName = item.itemName,
                    itemPrice = item.itemPrice,
                    itemQuantity = item.itemQuantity,
                    address = address
                )
                storeRepository.insertOrder(orderSummary)
            }
            storeRepository.deleteAll()
        }
    }

    suspend fun loadOrderSummaryFromDB(): List<OrderSummary> {
        val getCartItemsFromDB = viewModelScope.async(Dispatchers.IO) {
            storeRepository.allOrders()
        }
        return getCartItemsFromDB.await()
    }
}