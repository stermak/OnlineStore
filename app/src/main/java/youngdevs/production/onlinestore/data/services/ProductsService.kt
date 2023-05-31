package youngdevs.production.onlinestore.data.services

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import youngdevs.production.onlinestore.data.entities.Product


interface ProductsService {
    @GET("/api/products")
    suspend fun getProducts(): List<Product>
}

interface ImagesService {
    @GET("/api/images/{imageName}")
    suspend fun getImage(
        @Path("imageName") imageName: String
    ): Response<ResponseBody>
}

object RetrofitClient {
    private const val BASE_URL = "http://46.188.35.184:12345/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productsService: ProductsService by lazy {
        retrofit.create(ProductsService::class.java)
    }

    val imagesService: ImagesService by lazy {
        retrofit.create(ImagesService::class.java)
    }
}
