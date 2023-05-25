package youngdevs.production.onlinestore.data.utilities

import com.google.firebase.auth.FirebaseAuth
import youngdevs.production.onlinestore.domain.repository.UserRepository
import youngdevs.production.onlinestore.domain.repository.UserRepositoryImpl
import youngdevs.production.onlinestore.domain.usecases.AuthenticateUserUseCase
import youngdevs.production.onlinestore.domain.usecases.AuthenticateUserUseCaseImpl

object DependencyInjector {

    fun provideUserRepository(): UserRepository {
        val firebaseAuth = FirebaseAuth.getInstance() // создаем FirebaseAuth. Ваш код может отличаться в зависимости от того, как вы инициализируете FirebaseAuth
        return UserRepositoryImpl(firebaseAuth)
    }

    fun provideAuthenticateUserUseCase(): AuthenticateUserUseCase {
        val userRepository = provideUserRepository()
        return AuthenticateUserUseCaseImpl(userRepository)
    }
}
