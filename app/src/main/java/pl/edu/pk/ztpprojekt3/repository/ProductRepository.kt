package pl.edu.pk.ztpprojekt3.repository

import pl.edu.pk.ztpprojekt3.api.ProductService
import pl.edu.pk.ztpprojekt3.model.ProductRequest
import pl.edu.pk.ztpprojekt3.model.ProductBasic
import retrofit2.Response

class ProductRepository(private val service: ProductService) {

    suspend fun getProducts() : List<ProductBasic> {
        return service.getProducts()
    }

    suspend fun deleteProduct(id: String) : Response<String> {
        return service.deleteProduct(id)
    }

    suspend fun getProduct(id: String): ProductRequest {
        return service.getProducts(id)
    }

    suspend fun insertProduct(productRequest: ProductRequest) : Response<String> {
        return service.insertProduct(productRequest)
    }
}