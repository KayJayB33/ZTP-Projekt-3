package pl.edu.pk.ztpprojekt3.model

import java.math.BigDecimal

data class ProductBasic(
    val id: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val productState: ProductState
)
