package pl.edu.pk.ztpprojekt3.ui.add_edit_product.validation

import pl.edu.pk.ztpprojekt3.R
import pl.edu.pk.ztpprojekt3.model.ValidationResult
import pl.edu.pk.ztpprojekt3.ui.add_edit_product.UiText

class ValidateNameUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strTheNameCanNotBeBlank)
            )
        }
        if (input.length !in 2..32) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strTheNamesLengthShouldBeWithinTwoAndThirtyTwoCharacters)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}