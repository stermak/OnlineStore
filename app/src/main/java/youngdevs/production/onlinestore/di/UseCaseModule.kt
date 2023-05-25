package youngdevs.production.onlinestore.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import youngdevs.production.onlinestore.domain.repository.UserRepository
import youngdevs.production.onlinestore.domain.usecases.AuthenticateUserUseCase
import youngdevs.production.onlinestore.domain.usecases.AuthenticateUserUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    // Функция, которая предоставляет экземпляр AuthenticateUserUseCase для зависимостей
    @Provides
    @Singleton
    fun provideAuthenticateUserUseCase(
        userRepository: UserRepository
    ): AuthenticateUserUseCase {
        return AuthenticateUserUseCaseImpl(userRepository = userRepository)
    }
}
