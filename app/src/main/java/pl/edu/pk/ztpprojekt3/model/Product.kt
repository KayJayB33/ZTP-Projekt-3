package pl.edu.pk.ztpprojekt3.model

import java.math.BigDecimal

data class Product (
    val id: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val availableQuantity: Int
)
