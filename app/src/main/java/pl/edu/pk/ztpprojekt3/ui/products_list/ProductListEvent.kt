package pl.edu.pk.ztpprojekt3.ui.products_list

import pl.edu.pk.ztpprojekt3.model.ProductBasic
import java.math.BigDecimal

sealed class ProductListEvent {
    data class OnDeleteProductClick(val product: ProductBasic) : ProductListEvent()
    data class OnDoneChange(
        val product: ProductBasic,
        val name: String,
        val description: String,
        val price: BigDecimal,
        val availableQuantity: Int
    ) : ProductListEvent()

    object OnUndoDeleteClick : ProductListEvent()
    data class OnProductClick(val product: ProductBasic) : ProductListEvent()
    data class OnEditProductClick(val product: ProductBasic) : ProductListEvent()
    object OnAddProductClick : ProductListEvent()
}