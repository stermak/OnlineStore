package youngdevs.production.onlinestore.presentation.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import youngdevs.production.onlinestore.data.utilities.DependencyInjector
import youngdevs.production.onlinestore.databinding.FragmentProfileBinding
import youngdevs.production.onlinestore.domain.repository.UserRepositoryImpl
import youngdevs.production.onlinestore.presentation.viewmodel.ProfileViewModel
import youngdevs.production.onlinestore.presentation.viewmodel.ProfileViewModelFactory

class ProfileFragment : Fragment() {
    companion object {
        private const val SELECT_IMAGE_REQUEST_CODE = 100
    }

    lateinit var userRepository: UserRepositoryImpl
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(DependencyInjector.provideUserRepository() as UserRepositoryImpl)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRepository = DependencyInjector.provideUserRepository() as UserRepositoryImpl
        viewModel.loadUserData(userRepository, binding)
        binding.saveProfile.setOnClickListener {
            viewModel.saveProfileChanges(
                userRepository,
                binding,
                childFragmentManager,
                requireContext()
            )
        }
        binding.uploadPhotoButton.setOnClickListener {
            selectImageFromGallery()
        }
    }


    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            selectedImage?.let {
                binding.profilePic.setImageURI(selectedImage)
                viewModel.uploadProfileImage(userRepository, it, requireContext())
            }
        }
    }
}
