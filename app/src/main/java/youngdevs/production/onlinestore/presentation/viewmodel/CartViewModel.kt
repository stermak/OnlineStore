package youngdevs.production.onlinestore.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import youngdevs.production.onlinestore.data.dao.CartDao
import youngdevs.production.onlinestore.data.entities.CartItem
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartDao: CartDao) : ViewModel() {
    val cartItems: LiveData<List<CartItem>> = cartDao.getAll()

    init {
        cartItems.observeForever { cartItems ->
            Log.d("CartViewModel", "Cart items updated: $cartItems")
        }
    }

    fun loadCartItems(){

    }
}
