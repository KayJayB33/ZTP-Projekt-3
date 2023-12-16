package pl.edu.pk.ztpprojekt3.api

import pl.edu.pk.ztpprojekt3.model.Product
import pl.edu.pk.ztpprojekt3.model.ProductRequest
import pl.edu.pk.ztpprojekt3.model.ProductBasic
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    suspend fun getProduct() : List<ProductBasic>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: String) : Product

    @POST("products")
    suspend fun insertProduct(@Body productRequest: ProductRequest) : Response<String>

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: String, @Body productRequest: ProductRequest) : Product

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: String) : Response<String>
}