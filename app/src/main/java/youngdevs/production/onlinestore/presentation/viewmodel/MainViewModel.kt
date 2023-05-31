package youngdevs.production.onlinestore.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import youngdevs.production.onlinestore.data.entities.Product
import youngdevs.production.onlinestore.data.services.ImagesService
import youngdevs.production.onlinestore.data.services.ProductsService
import youngdevs.production.onlinestore.data.utilities.LoadingStatus
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    // Внедряем сервисы для работы с достопримечательностями и изображениями
    private val productsService: ProductsService,
    private val imagesService: ImagesService
) : ViewModel() {

    // Используем MutableLiveData для изменения списка достопримечательностей внутри ViewModel
    private val _products = MutableLiveData<List<Product>>()

    // Объявляем LiveData для предоставления списка достопримечательностей во внешний код
    val products: LiveData<List<Product>> = _products
    val loadingStatus = MutableLiveData<LoadingStatus>()


    // Загружаем список достопримечательностей с использованием корутин
    private val _allProducts = mutableListOf<Product>()

    fun loadProducts() {
        loadingStatus.value = LoadingStatus.LOADING
        viewModelScope.launch {
            try {
                val products = productsService.getProducts()
                _allProducts.clear()
                _allProducts.addAll(products)
                _products.value = products
                loadingStatus.value = LoadingStatus.LOADED
                Log.d(
                    "ProductsViewModel",
                    "Loaded ${products.size} products"
                )
            } catch (e: Exception) {
                loadingStatus.value = LoadingStatus.ERROR
                Log.e(
                    "ProductsViewModel",
                    "Failed to load products",
                    e
                )
                // Обработка ошибки
            }
        }
    }

    fun searchProducts(query: String) {
        val filteredProducts = _allProducts.filter { it.name.contains(query, ignoreCase = true) }
        _products.value = filteredProducts
    }

    // Загружаем изображение с использованием корутин и обрабатываем исключения
    suspend fun loadImage(imageName: String): Bitmap? {
        return try {
            val response = imagesService.getImage(imageName)
            if (response.isSuccessful) {
                val inputStream = response.body()?.byteStream()
                inputStream?.let { BitmapFactory.decodeStream(it) }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}
