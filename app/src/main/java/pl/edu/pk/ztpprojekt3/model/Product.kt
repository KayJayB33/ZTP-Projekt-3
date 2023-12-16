package pl.edu.pk.ztpprojekt3.model

import java.math.BigDecimal
import java.time.Instant

data class Product (
    val id: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val availableQuantity: Int,
    val createdDate: Instant,
    val lastModifiedDate: Instant
)