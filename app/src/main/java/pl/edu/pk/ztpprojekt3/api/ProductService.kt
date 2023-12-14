package pl.edu.pk.ztpprojekt3.api

import pl.edu.pk.ztpprojekt3.model.ProductBasic
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProducts() : List<ProductBasic>

    @DELETE("products/{id}")
    suspend fun deleteProduct(id: String) : Response<String>
}