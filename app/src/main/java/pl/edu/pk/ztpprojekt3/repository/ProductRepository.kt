package pl.edu.pk.ztpprojekt3.repository

import pl.edu.pk.ztpprojekt3.api.ProductService
import pl.edu.pk.ztpprojekt3.model.Product
import pl.edu.pk.ztpprojekt3.model.ProductBasic
import retrofit2.Response
import java.math.BigDecimal

class ProductRepository(private val service: ProductService) {

    suspend fun getProducts() : List<ProductBasic> {
        return service.getProducts()
    }

    suspend fun deleteProduct(id: String) : Response<String> {
        return service.deleteProduct(id)
    }

    suspend fun getProduct(id: String): Product {
        return Product("1", "test", "test", BigDecimal(1), 1)
    }
}