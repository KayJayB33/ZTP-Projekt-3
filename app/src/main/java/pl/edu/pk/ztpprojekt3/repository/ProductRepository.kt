package pl.edu.pk.ztpprojekt3.repository

import pl.edu.pk.ztpprojekt3.api.ProductService
import pl.edu.pk.ztpprojekt3.model.Product
import pl.edu.pk.ztpprojekt3.model.ProductRequest
import pl.edu.pk.ztpprojekt3.model.ProductBasic
import retrofit2.Response

class ProductRepository(private val service: ProductService) {

    suspend fun getProducts() : List<ProductBasic> {
        return service.getProduct()
    }

    suspend fun deleteProduct(id: String) : Response<String> {
        return service.deleteProduct(id)
    }

    suspend fun getProduct(id: String) : Product {
        return service.getProduct(id)
    }

    suspend fun insertProduct(productRequest: ProductRequest) : Response<String> {
        return service.insertProduct(productRequest)
    }

    suspend fun updateProduct(id: String, productRequest: ProductRequest) : Product {
        return service.updateProduct(id, productRequest)
    }
}