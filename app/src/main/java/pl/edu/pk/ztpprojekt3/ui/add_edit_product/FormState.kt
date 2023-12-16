package pl.edu.pk.ztpprojekt3.ui.add_edit_product

data class FormState(
    val name: String = "",
    val nameError: UiText? = null,
    val description: String = "",
    val descriptionError: UiText? = null,
    val price: String = "",
    val priceError: UiText? = null,
    val availableQuantity: String = "",
    val availableQuantityError: UiText? = null,
)