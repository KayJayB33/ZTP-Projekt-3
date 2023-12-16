package pl.edu.pk.ztpprojekt3.ui.add_edit_product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.edu.pk.ztpprojekt3.R
import pl.edu.pk.ztpprojekt3.util.UiEvent

@Composable
fun AddEditProductScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = stringResource(id = R.string.strName),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
            CustomTextFieldApp(
                placeholder = stringResource(id = R.string.strNamePlaceholder),
                text = viewModel.formState.name,
                onValueChange = {
                    viewModel.onEvent(AddEditProductEvent.OnNameChange(it))
                },
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                errorMessage = viewModel.formState.nameError,
                isError = viewModel.formState.nameError != null,
                singleLine = true,
            )
            Text(
                text = stringResource(id = R.string.strDescription),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
            CustomTextFieldApp(
                placeholder = stringResource(id = R.string.strDescriptionPlaceholder),
                text = viewModel.formState.description,
                onValueChange = {
                    viewModel.onEvent(AddEditProductEvent.OnDescriptionChange(it))
                },
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                errorMessage = viewModel.formState.descriptionError,
                isError = viewModel.formState.descriptionError != null,
                singleLine = true,
            )
            Text(
                text = stringResource(id = R.string.strPrice),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
            CustomTextFieldApp(
                placeholder = stringResource(id = R.string.strPricePlaceholder),
                text = viewModel.formState.price,
                onValueChange = {
                    viewModel.onEvent(AddEditProductEvent.OnPriceChange(it))
                },
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                errorMessage = viewModel.formState.priceError,
                isError = viewModel.formState.priceError != null,
                singleLine = true,
            )
            Text(
                text = stringResource(id = R.string.strAvailableQuantity),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
            CustomTextFieldApp(
                placeholder = stringResource(id = R.string.strAvailableQuantity),
                text = viewModel.formState.availableQuantity,
                onValueChange = {
                    viewModel.onEvent(AddEditProductEvent.OnAvailableQuantityChange(it))
                },
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                errorMessage = viewModel.formState.availableQuantityError,
                isError = viewModel.formState.availableQuantityError != null,
                singleLine = true,
            )
        }
    }
}