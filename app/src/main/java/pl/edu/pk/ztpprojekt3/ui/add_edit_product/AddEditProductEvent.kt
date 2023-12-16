package pl.edu.pk.ztpprojekt3.ui.add_edit_product

sealed class AddEditProductEvent {
    data class OnNameChange(val name: String): AddEditProductEvent()
    data class OnDescriptionChange(val description: String): AddEditProductEvent()
    data class OnPriceChange(val price: String): AddEditProductEvent()
    data class OnAvailableQuantityChange(val availableQuantity: String): AddEditProductEvent()
    object OnSaveProductClick: AddEditProductEvent()
}