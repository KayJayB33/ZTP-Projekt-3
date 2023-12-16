package pl.edu.pk.ztpprojekt3.ui.settings_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.edu.pk.ztpprojekt3.ui.products_list.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onPopBackStack: () -> Unit,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val refreshInterval = viewModel.refreshInterval.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp)
        ) {
            Text("Refresh Interval: ${refreshInterval / 60_000} min")
            Slider(
                value = refreshInterval.toFloat(),
                onValueChange = {
                    viewModel.setRefreshInterval(it.toLong())
                },
                valueRange = 0f..300000f,
                steps = 4,
            )
        }
    }
}