package youngdevs.production.onlinestore.presentation.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import youngdevs.production.onlinestore.data.entities.Product
import youngdevs.production.onlinestore.data.utilities.LoadingStatus
import youngdevs.production.onlinestore.databinding.FragmentMainBinding
import youngdevs.production.onlinestore.presentation.ui.adapter.ProductsAdapter
import youngdevs.production.onlinestore.presentation.viewmodel.MainViewModel

// Фрагмент, отображающий список событий
@AndroidEntryPoint // аннотация для использования Hilt DI
class MainFragment : Fragment() {
    private var isBackPressed = false

    // ViewModel для работы с данными
    private val viewModel: MainViewModel by viewModels()

    // Поле для привязки View Binding
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инициализация View Binding и возвращение корневого View макета фрагмента
        _binding =
            FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsAdapter = ProductsAdapter(viewLifecycleOwner.lifecycleScope) { product ->
            viewModel.addProductToCart(product, 1) // добавить 1 штуку товара в корзину
        }

        binding.searchField.addTextChangedListener { text ->
            viewModel.searchProducts(text.toString())
        }

        // Настраиваем RecyclerView с LinearLayoutManager и устанавливаем адаптер
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = productsAdapter

        viewModel.products.observe(viewLifecycleOwner) { products
            ->
            productsAdapter.submitList(products)
        }


        viewModel.loadingStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                LoadingStatus.LOADING -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.standing.visibility = View.VISIBLE
                    binding.sitting.visibility = View.VISIBLE
                    binding.errorServer.visibility = View.VISIBLE
                    binding.sorry.visibility = View.VISIBLE
                    binding.imageView8.visibility = View.VISIBLE
                    binding.imageView9.visibility = View.VISIBLE
                }

                LoadingStatus.LOADED -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.standing.visibility = View.GONE
                    binding.sitting.visibility = View.GONE
                    binding.errorServer.visibility = View.GONE
                    binding.sorry.visibility = View.GONE
                    binding.imageView8.visibility = View.GONE
                    binding.imageView9.visibility = View.GONE
                }

                LoadingStatus.ERROR -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.standing.visibility = View.VISIBLE
                    binding.sitting.visibility = View.VISIBLE
                    binding.errorServer.visibility = View.VISIBLE
                    binding.sorry.visibility = View.VISIBLE
                    binding.imageView8.visibility = View.VISIBLE
                    binding.imageView9.visibility = View.VISIBLE
                }
            }
        }


        // Загружаем список достопримечательностей
        viewModel.loadProducts()
    }

    fun handleOnBackPressed() {
        if (isBackPressed) {
            requireActivity().finish()
        } else {
            isBackPressed = true
            Toast.makeText(
                requireContext(),
                "Нажмите еще раз, чтобы выйти",
                Toast.LENGTH_SHORT
            )
                .show()
            Handler(Looper.getMainLooper())
                .postDelayed({ isBackPressed = false }, 2000)
        }
    }
}
