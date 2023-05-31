package youngdevs.production.onlinestore.presentation.ui.adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import youngdevs.production.onlinestore.data.entities.Product
import youngdevs.production.onlinestore.data.services.RetrofitClient
import youngdevs.production.onlinestore.databinding.ItemProductBinding

class ProductsAdapter(private val scope: LifecycleCoroutineScope) :
    ListAdapter<Product, ProductsAdapter.ProductViewHolder>(
        DiffCallback()
    ) {
    // Создание нового ViewHolder, которому передается экземпляр макета ItemSightseeingBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding =
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ProductViewHolder(binding, scope)
    }

    // Привязка данных к ViewHolder
    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = getItem(position)
        holder.bind(product)
    }

    // Определение класса SightseeingViewHolder, который наследуется от RecyclerView.ViewHolder
    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val scope: LifecycleCoroutineScope
    ) : RecyclerView.ViewHolder(binding.root) {

        // Привязка данных к View
        fun bind(product: Product) {
            binding.name.text = product.name
            binding.description.text = product.description
            binding.availability.text = product.availability
            loadImage(product.image)
        }

        // Загрузка изображения с помощью ImagesService и отображение его в элементе списка
        private fun loadImage(imageName: String) {
            val imagesService = RetrofitClient.imagesService
            scope.launch {
                try {
                    val response =
                        imagesService.getImage(
                            imageName.trim()
                        ) // Убедитесь, что нет пробелов перед именем файла
                    if (response.isSuccessful) {
                        val inputStream = response.body()?.byteStream()
                        inputStream?.let {
                            val bitmap =
                                BitmapFactory.decodeStream(inputStream)
                            binding.image.setImageBitmap(bitmap)
                        }
                    } else {
                        Log.e(
                            "ProductsAdapter",
                            "Failed to load image: $imageName"
                        )
                    }
                } catch (e: Exception) {
                    Log.e(
                        "ProductsAdapter",
                        "Error loading image: $imageName",
                        e
                    )
                }
            }
        }
    }

    // Определение класса DiffCallback, который используется для оптимизации обновления списка
    // достопримечательностей
    private class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }
}
