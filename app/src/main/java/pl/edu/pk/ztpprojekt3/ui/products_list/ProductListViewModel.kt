package pl.edu.pk.ztpprojekt3.ui.products_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.edu.pk.ztpprojekt3.model.Product
import pl.edu.pk.ztpprojekt3.model.ProductBasic
import pl.edu.pk.ztpprojekt3.repository.ProductRepository
import pl.edu.pk.ztpprojekt3.util.Routes
import pl.edu.pk.ztpprojekt3.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _products = MutableLiveData<List<ProductBasic>>()
    val products: LiveData<List<ProductBasic>> get() = _products

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedProduct: Product? = null

    fun fetchProducts() {
        viewModelScope.launch {
            _products.postValue(repository.getProducts())
        }
    }

    fun onEvent(event: ProductListEvent) {
        when(event) {
            is ProductListEvent.OnProductClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?productId=${event.product.id}"))
            }
            is ProductListEvent.OnAddProductClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is ProductListEvent.OnDeleteProductClick -> {
                viewModelScope.launch {
                    deletedProduct = repository.getProduct(event.product.id)
                    val result = repository.deleteProduct(event.product.id)
                    if(result.isSuccessful) {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = result.body().toString(),
                                action = "Undo"
                            )
                        )
                    } else {
                        sendUiEvent(UiEvent.ShowSnackbar(message = result.body().toString()))
                    }
                }
            }
            is ProductListEvent.OnUndoDeleteClick -> {

            }
            is ProductListEvent.OnDoneChange -> {

            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}