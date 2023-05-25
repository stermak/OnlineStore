package youngdevs.production.onlinestore.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import youngdevs.production.onlinestore.data.utilities.DependencyInjector
import youngdevs.production.onlinestore.domain.usecases.AuthenticateUserUseCase
import javax.inject.Inject

class RegistrationViewModel : ViewModel() {

    private val authenticateUserUseCase = DependencyInjector.provideAuthenticateUserUseCase()


    private val _registrationResult = MutableLiveData<Int?>()
    val registrationResult: LiveData<Int?>
        get() = _registrationResult

    private val _accountExists = MutableLiveData<Boolean>()
    val accountExists: LiveData<Boolean>
        get() = _accountExists

    fun registration(
        email: String,
        password: String,
        repeatPassword: String,
        name: String
    ) {
        viewModelScope.launch {
            val exists = authenticateUserUseCase.checkAccountExists(email)
            _accountExists.value = exists
            if (!exists) {
                _registrationResult.value =
                    authenticateUserUseCase.createAccount(
                        email,
                        password,
                        repeatPassword,
                        name
                    )
            }
        }
    }
}
