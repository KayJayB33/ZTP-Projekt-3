package pl.edu.pk.ztpprojekt3.ui.products_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import pl.edu.pk.ztpprojekt3.model.ProductBasic
import pl.edu.pk.ztpprojekt3.model.ProductRequest
import pl.edu.pk.ztpprojekt3.repository.ProductRepository
import pl.edu.pk.ztpprojekt3.util.Routes
import pl.edu.pk.ztpprojekt3.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val TAG = "ProductList"

    private val _products = MutableLiveData<List<ProductBasic>>()
    val products: LiveData<List<ProductBasic>> get() = _products

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedProductRequest: ProductRequest? = null

    private val _refreshInterval = MutableStateFlow(5000L)
    val refreshInterval = _refreshInterval.asStateFlow()

    private var job: Job? = null

    fun setRefreshInterval(interval: Long) {
        _refreshInterval.value = interval
        Log.i(TAG, "Cancel periodic task")
        job?.cancel()
        if(_refreshInterval.value > 0) {
            // Cancel the existing job and start a new one with the updated interval
            Log.i(TAG, "Start periodic task with interval ${_refreshInterval.value}")
            job = startPeriodicFetch()
        }
    }

    private fun startPeriodicFetch(): Job {
        return viewModelScope.launch {
            while (isActive) {
                fetchProducts()
                delay(_refreshInterval.value)
            }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _products.postValue(repository.getProducts())
        }
    }

    fun onEvent(event: ProductListEvent) {
        when(event) {
            is ProductListEvent.OnProductClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.PRODUCT_DETAILS + "?productId=${event.product.id}"))
            }
            is ProductListEvent.OnEditProductClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_PRODUCT + "?productId=${event.product.id}"))
            }
            is ProductListEvent.OnAddProductClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_PRODUCT))
            }
            is ProductListEvent.OnDeleteProductClick -> {
                viewModelScope.launch {
                    deletedProductRequest = ProductRequest(repository.getProduct(event.product.id))
                    val result = repository.deleteProduct(event.product.id)
                    if(result.isSuccessful) {
                        Log.i(TAG, result.body().toString())
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = result.body().toString(),
                                action = "Undo"
                            )
                        )
                        fetchProducts()
                    } else {
                        Log.w(TAG, result.body().toString())
                        sendUiEvent(UiEvent.ShowSnackbar(message = result.body().toString()))
                    }
                }
            }
            is ProductListEvent.OnUndoDeleteClick -> {
                viewModelScope.launch {
                    repository.insertProduct(deletedProductRequest!!)
                    fetchProducts()
                }
            }
            is ProductListEvent.OnSettingClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.SETTINGS))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel() // Cancel the job when the ViewModel is cleared
    }
}