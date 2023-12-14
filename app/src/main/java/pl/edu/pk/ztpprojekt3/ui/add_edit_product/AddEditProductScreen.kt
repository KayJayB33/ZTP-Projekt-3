package pl.edu.pk.ztpprojekt3.ui.add_edit_product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.edu.pk.ztpprojekt3.util.UiEvent

@Composable
fun AddEditProductScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditProductEvent.OnSaveProductClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            TextField(
                value = viewModel.name,
                onValueChange = {
                    viewModel.onEvent(AddEditProductEvent.OnNameChange(it))
                },
                label = { Text(text = "Name") },
                placeholder = {
                    Text(text = "e.g. Laptop")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(AddEditProductEvent.OnDescriptionChange(it))
                },
                label = { Text(text = "Description") },
                placeholder = {
                    Text(text = "e.g. This is laptop")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.price.toString(),
                onValueChange = {
                    viewModel.onEvent(AddEditProductEvent.OnPriceChange(it.toDouble()))
                },
                label = { Text(text = "Price") },
                placeholder = {
                    Text(text = "e.g. 12.34")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.availableQuantity.toString(),
                onValueChange = {
                    viewModel.onEvent(AddEditProductEvent.OnPriceChange(it.toDouble()))
                },
                label = { Text(text = "Price") },
                placeholder = {
                    Text(text = "e.g. 12.34")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}