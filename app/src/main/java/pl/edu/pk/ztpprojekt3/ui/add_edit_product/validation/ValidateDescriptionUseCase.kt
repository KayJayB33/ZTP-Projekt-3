package pl.edu.pk.ztpprojekt3.ui.add_edit_product.validation

import pl.edu.pk.ztpprojekt3.R
import pl.edu.pk.ztpprojekt3.model.ValidationResult
import pl.edu.pk.ztpprojekt3.ui.add_edit_product.UiText

class ValidateDescriptionUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strTheDescriptionCanNotBeBlank)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}