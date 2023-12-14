package pl.edu.pk.ztpprojekt3.api

import pl.edu.pk.ztpprojekt3.model.ProductRequest
import pl.edu.pk.ztpprojekt3.model.ProductBasic
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    suspend fun getProducts() : List<ProductBasic>

    @GET("products/{id}")
    suspend fun getProducts(@Path("id") id: String) : ProductRequest

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: String) : Response<String>

    @POST("products")
    suspend fun insertProduct(@Body productRequest: ProductRequest) : Response<String>
}