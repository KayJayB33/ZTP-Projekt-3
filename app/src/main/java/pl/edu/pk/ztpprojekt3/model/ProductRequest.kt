package pl.edu.pk.ztpprojekt3.model

import java.math.BigDecimal

data class ProductRequest (
    val name: String,
    val description: String,
    val price: BigDecimal,
    val availableQuantity: Int
) {
    constructor(product: Product) : this(product.name, product.description, product.price, product.availableQuantity)
}
