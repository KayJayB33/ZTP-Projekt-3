package pl.edu.pk.ztpprojekt3.ui.product_details

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
import pl.edu.pk.ztpprojekt3.repository.ProductRepository
import pl.edu.pk.ztpprojekt3.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var name by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    var availableQuantity by mutableStateOf("")
        private set

    var createdDate by mutableStateOf("")
        private set

    var lastModifiedDate by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val productId = savedStateHandle.get<String>("productId")!!
        if (productId.isNotEmpty()) {
            viewModelScope.launch {
                repository.getProduct(productId).let { product ->
                    name = product.name
                    description = product.description
                    price = product.price.toString()
                    availableQuantity = product.availableQuantity.toString()
                    createdDate = product.createdDate.toString()
                    lastModifiedDate = product.lastModifiedDate.toString()
                }
            }
        }
    }
}