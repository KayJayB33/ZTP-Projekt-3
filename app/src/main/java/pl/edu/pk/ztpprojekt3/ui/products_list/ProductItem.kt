package pl.edu.pk.ztpprojekt3.ui.products_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pl.edu.pk.ztpprojekt3.model.ProductBasic

@Composable
fun ProductItem(
    product: ProductBasic,
    onEvent: (ProductListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = product.id,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = product.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = product.price.toString(),
                        fontSize = 14.sp,
                    )
                    Text(
                        text = product.productState.toString(),
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        onEvent(ProductListEvent.OnEditProductClick(product))
                    }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
                IconButton(
                    onClick = {
                        onEvent(ProductListEvent.OnDeleteProductClick(product))
                    }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
}