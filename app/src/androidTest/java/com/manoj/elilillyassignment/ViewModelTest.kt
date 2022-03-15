package com.lilly.codingchallenge.viewmodel


import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.manoj.elilillyassignment.model.data.database.ProductDatabase
import com.manoj.elilillyassignment.model.data.database.entity.CartItems
import com.manoj.elilillyassignment.model.data.model.ProductInfo
import com.manoj.elilillyassignment.model.data.repository.StoreRepository
import com.manoj.elilillyassignment.viewmodel.CartViewModel
import com.manoj.elilillyassignment.viewmodel.StoreViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewModelTest {

    private lateinit var db: ProductDatabase
    private lateinit var storeViewModel: StoreViewModel
    private lateinit var cartViewModel: CartViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ProductDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        val storeRepository = StoreRepository(context, db)
        storeViewModel = StoreViewModel(storeRepository)
        cartViewModel = CartViewModel(storeRepository)

    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addProductToCart() {
        val productInfo = ProductInfo(
            productId = "ELI103",
            productName = "Eli Product4",
            productType = "health care",
            productDescription = "product description eli104",
            productPrice = 60.00,
            productImage = ""
        )
        val cartItem = storeViewModel.addItemToCart(productInfo)

        runBlocking {
            val result = cartViewModel.loadCartItemsFromDB().find {
                it.productID == cartItem.productID && it.itemName == cartItem.itemName
                        && it.itemQuantity == cartItem.itemQuantity && it.itemPrice == cartItem.itemPrice
            }
            assertThat(result).isNotNull()
        }

    }

    @Test
    fun deleteItemFromCart() {
        val productInfo = ProductInfo(
            productId = "ELI101",
            productName = "Eli Product2",
            productType = "health care",
            productDescription = "product description eli101",
            productPrice = 60.00,
            productImage = "my_img.png"
        )

        val cartItem = storeViewModel.addItemToCart(productInfo)
        runBlocking {
            delay(1000)
            val temp = cartViewModel.loadCartItemsFromDB()
            val result = cartViewModel.loadCartItemsFromDB().find {
                it.productID == cartItem.productID && it.itemName == cartItem.itemName
                        && it.itemQuantity == cartItem.itemQuantity && it.itemPrice == cartItem.itemPrice
            }
            assertThat(result).isNotNull()
        }

        runBlocking {
            val cartItems = cartViewModel.loadCartItemsFromDB()
            cartViewModel.deleteItemFromCart(cartItems[0])
            val result = cartViewModel.loadCartItemsFromDB().find {
                it.productID == cartItem.productID && it.itemName == cartItem.itemName
                        && it.itemQuantity == cartItem.itemQuantity && it.itemPrice == cartItem.itemPrice
            }
            assertThat(result).isNull()
        }
    }

    @Test
    fun processOrder() {
        val cartItemList = ArrayList<CartItems>()
        val cartItem = CartItems(
            productID = "ELI102",
            itemName = "Eli Product3",
            itemQuantity = 1,
            itemPrice = 30.00,
        )
        cartItemList.add(cartItem)

        val address = "WhiteField, Bangalore"
        cartViewModel.processOrder(cartItemList, address)

        runBlocking {
            val result = cartViewModel.loadOrderSummaryFromDB().find {
                it.productID == cartItemList[0].productID && it.productName == cartItemList[0].itemName
                        && it.itemPrice == cartItemList[0].itemPrice
                        && it.itemQuantity == cartItemList[0].itemQuantity
            }
            assertThat(result).isNotNull()
        }

    }
}