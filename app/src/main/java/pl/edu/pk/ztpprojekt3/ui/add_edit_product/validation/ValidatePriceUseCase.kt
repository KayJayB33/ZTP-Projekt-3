package pl.edu.pk.ztpprojekt3.ui.add_edit_product.validation

import pl.edu.pk.ztpprojekt3.R
import pl.edu.pk.ztpprojekt3.model.ValidationResult
import pl.edu.pk.ztpprojekt3.ui.add_edit_product.UiText
import java.lang.NumberFormatException
import java.math.BigDecimal

class ValidatePriceUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strTheDescriptionCanNotBeBlank)
            )
        }
        val result : BigDecimal
        try {
            result = BigDecimal(input)
        } catch (e: NumberFormatException) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePriceShouldBeANumber)
            )
        }
        if (result.scale() > 2) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePriceShouldHaveMaxTwoDigitsAfterADecimal)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}