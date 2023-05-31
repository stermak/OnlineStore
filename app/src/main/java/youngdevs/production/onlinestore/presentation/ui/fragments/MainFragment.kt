package youngdevs.production.onlinestore.presentation.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import youngdevs.production.onlinestore.databinding.FragmentMainBinding
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
