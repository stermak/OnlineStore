package youngdevs.production.onlinestore.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import youngdevs.production.onlinestore.R
import youngdevs.production.onlinestore.data.utilities.DependencyInjector
import javax.inject.Inject

// LoginViewModel - аннотация Hilt, чтобы позволить DI фреймворку внедрять зависимости в этот класс
class LoginViewModel
@Inject
constructor() : ViewModel() {
    private val authenticateUserUseCase = DependencyInjector.provideAuthenticateUserUseCase()

    private lateinit var googleSignInClient:
            GoogleSignInClient // экземпляр клиента GoogleSignIn, который будет проинициализирован при

    // первом обращении к getGoogleSignInClient()
    private val _isLoginSuccessful =
        MutableLiveData<
                Boolean?
                >() // LiveData, который сообщает об успешности входа в систему
    val isLoginSuccessful: LiveData<Boolean?>
        get() = _isLoginSuccessful // открытый доступ к _isLoginSuccessful

    // функция, которая инициирует процесс аутентификации пользователя
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoginSuccessful.value =
                authenticateUserUseCase.signIn(email, password)
        }
    }

    // функция, которая обновляет UI после успешной аутентификации
    fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            _isLoginSuccessful.value = true
        }
    }

    // функция, которая возвращает клиент GoogleSignIn
    fun getGoogleSignInClient(activity: Activity): GoogleSignInClient {
        if (
            !::googleSignInClient.isInitialized
        ) { // проверяем, был ли клиент GoogleSignIn инициализирован ранее
            // создаем новый клиент GoogleSignIn с помощью GoogleSignInOptions
            val gso =
                GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN
                )
                    .requestIdToken(
                        activity.getString(R.string.default_web_client_id)
                    )
                    .requestEmail()
                    .build()

            googleSignInClient =
                GoogleSignIn.getClient(
                    activity,
                    gso
                ) // инициализируем клиент GoogleSignIn
        }

        return googleSignInClient // возвращаем клиент GoogleSignIn
    }

    // функция, которая аутентифицирует пользователя с помощью учетной записи Google
    fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch {
            _isLoginSuccessful.value =
                authenticateUserUseCase.signInWithGoogle(idToken)
        }
    }
}
