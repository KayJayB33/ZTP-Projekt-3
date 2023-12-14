package pl.edu.pk.ztpprojekt3.ui.add_edit_product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import pl.edu.pk.ztpprojekt3.util.UiEvent
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var productRequest by mutableStateOf<ProductRequest?>(null)
        private set

    var name by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var price by mutableDoubleStateOf(0.0)
        private set

    var availableQuantity by mutableIntStateOf(0)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val productId = savedStateHandle.get<String>("productId")!!
        if (productId.isNotEmpty()) {
            viewModelScope.launch {
                repository.getProduct(productId).let { product ->
                    name = product.name
                    description = product.description ?: ""
                    price = product.price.toDouble()
                    availableQuantity = product.availableQuantity
                    this@AddEditProductViewModel.productRequest = product
                }
            }
        }
    }

    fun onEvent(event: AddEditProductEvent) {
        when (event) {
            is AddEditProductEvent.OnNameChange -> {
                name = event.name
            }

            is AddEditProductEvent.OnDescriptionChange -> {
                description = event.description
            }

            is AddEditProductEvent.OnAvailableQuantityChange -> {
                availableQuantity = event.availableQuantity
            }

            is AddEditProductEvent.OnPriceChange -> {
                price = event.price
            }

            is AddEditProductEvent.OnSaveProductClick -> {
                viewModelScope.launch {
                    if (name.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "The name can't be empty"
                            )
                        )
                        return@launch
                    }
                    repository.insertProduct(
                        ProductRequest(
                            name = name,
                            description = description,
                            price = BigDecimal.valueOf(price),
                            availableQuantity = availableQuantity
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}