package pl.edu.pk.ztpprojekt3.ui.products_list

import pl.edu.pk.ztpprojekt3.model.ProductBasic

sealed class ProductListEvent {
    data class OnDeleteProductClick(val product: ProductBasic) : ProductListEvent()

    object OnUndoDeleteClick : ProductListEvent()
    data class OnProductClick(val product: ProductBasic) : ProductListEvent()
    data class OnEditProductClick(val product: ProductBasic) : ProductListEvent()
    object OnAddProductClick : ProductListEvent()
    object OnSettingClick : ProductListEvent()
}