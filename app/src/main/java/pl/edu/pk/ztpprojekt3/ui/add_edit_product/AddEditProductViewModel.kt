package pl.edu.pk.ztpprojekt3.ui.add_edit_product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.edu.pk.ztpprojekt3.model.ProductRequest
import pl.edu.pk.ztpprojekt3.repository.ProductRepository
import pl.edu.pk.ztpprojekt3.ui.add_edit_product.validation.ValidateAvailableQuantityUseCase
import pl.edu.pk.ztpprojekt3.ui.add_edit_product.validation.ValidateDescriptionUseCase
import pl.edu.pk.ztpprojekt3.ui.add_edit_product.validation.ValidateNameUseCase
import pl.edu.pk.ztpprojekt3.ui.add_edit_product.validation.ValidatePriceUseCase
import pl.edu.pk.ztpprojekt3.util.UiEvent
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: String
    private var productRequest by mutableStateOf<ProductRequest?>(null)

    private val validateNameUseCase = ValidateNameUseCase()
    private val validateDescriptionUseCase = ValidateDescriptionUseCase()
    private val validatePriceUseCase = ValidatePriceUseCase()
    private val validateAvailableQuantityUseCase = ValidateAvailableQuantityUseCase()

    var formState by mutableStateOf(FormState())

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        productId = savedStateHandle.get<String>("productId")!!
        if (productId.isNotEmpty()) {
            viewModelScope.launch {
                repository.getProduct(productId).let { product ->
                    formState = formState.copy(
                        name = product.name,
                        description = product.description,
                        price = product.price.toString(),
                        availableQuantity = product.availableQuantity.toString()
                    )
                    this@AddEditProductViewModel.productRequest = ProductRequest(product)
                }
            }
        }
    }

    fun onEvent(event: AddEditProductEvent) {
        when (event) {
            is AddEditProductEvent.OnNameChange -> {
                formState = formState.copy(name = event.name)
                validateName()
            }

            is AddEditProductEvent.OnDescriptionChange -> {
                formState = formState.copy(description = event.description)
                validateDescription()
            }

            is AddEditProductEvent.OnPriceChange -> {
                formState = formState.copy(price = event.price)
                validatePrice()
            }

            is AddEditProductEvent.OnAvailableQuantityChange -> {
                formState = formState.copy(availableQuantity = event.availableQuantity)
                validateAvailableQuantity()
            }

            is AddEditProductEvent.OnSaveProductClick -> {
                if (!validateName() || !validateDescription() || !validatePrice() || !validateAvailableQuantity()) {
                    return
                }
                if (productId.isEmpty()) {
                    viewModelScope.launch {
                        repository.insertProduct(
                            ProductRequest(
                                name = formState.name,
                                description = formState.description,
                                price = BigDecimal(formState.price),
                                availableQuantity = formState.availableQuantity.toInt()
                            )
                        )
                        sendUiEvent(UiEvent.PopBackStack)
                    }
                } else {
                    viewModelScope.launch {
                        repository.updateProduct(
                            productId,
                            ProductRequest(
                                name = formState.name,
                                description = formState.description,
                                price = BigDecimal(formState.price),
                                availableQuantity = formState.availableQuantity.toInt()
                            )
                        )
                        sendUiEvent(UiEvent.PopBackStack)
                    }
                }
            }

        }
    }

    private fun validateName(): Boolean {
        val nameResult = validateNameUseCase.execute(formState.name)
        formState = formState.copy(nameError = nameResult.errorMessage)
        return nameResult.successful
    }

    private fun validateDescription(): Boolean {
        val descriptionResult = validateDescriptionUseCase.execute(formState.description)
        formState = formState.copy(descriptionError = descriptionResult.errorMessage)
        return descriptionResult.successful
    }

    private fun validatePrice(): Boolean {
        val priceResult = validatePriceUseCase.execute(formState.price)
        formState = formState.copy(priceError = priceResult.errorMessage)
        return priceResult.successful
    }

    private fun validateAvailableQuantity(): Boolean {
        val availableQuantityResult =
            validateAvailableQuantityUseCase.execute(formState.availableQuantity)
        formState = formState.copy(availableQuantityError = availableQuantityResult.errorMessage)
        return availableQuantityResult.successful
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}