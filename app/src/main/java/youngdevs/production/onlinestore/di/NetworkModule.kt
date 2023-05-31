package youngdevs.production.onlinestore.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import youngdevs.production.onlinestore.data.services.ProductsService
import youngdevs.production.onlinestore.data.services.ImagesService
import youngdevs.production.onlinestore.data.services.RetrofitClient
import javax.inject.Singleton

// Определение модуля NetworkModule для предоставления зависимостей, связанных с сетью
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideProductsService(): ProductsService {
        return RetrofitClient.productsService
    }

    @Provides
    @Singleton
    fun provideImagesService(): ImagesService {
        return RetrofitClient.imagesService
    }
}
