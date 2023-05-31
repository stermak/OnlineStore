package youngdevs.production.onlinestore.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import youngdevs.production.onlinestore.domain.repository.UserRepository
import youngdevs.production.onlinestore.domain.repository.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseAuth: FirebaseAuth
    ): UserRepository {
        return UserRepositoryImpl(firebaseAuth)
    }
}
