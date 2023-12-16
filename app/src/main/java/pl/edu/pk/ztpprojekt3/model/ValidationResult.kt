package pl.edu.pk.ztpprojekt3.model

import pl.edu.pk.ztpprojekt3.ui.add_edit_product.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)