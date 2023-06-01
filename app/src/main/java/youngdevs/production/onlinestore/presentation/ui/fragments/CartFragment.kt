package youngdevs.production.onlinestore.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import youngdevs.production.onlinestore.databinding.FragmentCartBinding
import youngdevs.production.onlinestore.presentation.ui.adapter.CartAdapter
import youngdevs.production.onlinestore.presentation.viewmodel.CartViewModel

@AndroidEntryPoint
class CartFragment : Fragment() {
    private val viewModel: CartViewModel by viewModels()
    private var _binding: FragmentCartBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var cartAdapter: CartAdapter  // Допустим, у вас есть адаптер для отображения элементов корзины

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = cartAdapter

        viewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.submitList(cartItems)
        }

        // Загружаем элементы корзины
        viewModel.loadCartItems()  // Допустим, у вас есть функция для загрузки элементов корзины
    }
}
