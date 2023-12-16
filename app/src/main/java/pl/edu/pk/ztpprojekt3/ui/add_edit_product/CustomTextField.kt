package pl.edu.pk.ztpprojekt3.ui.add_edit_product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextFieldApp(
    placeholder: String,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    modifier: Modifier,
    errorMessage: UiText? = null,
    isError: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLine: Int = 1,
) {
    keyboardType == KeyboardType.Phone || keyboardType == KeyboardType.Number
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember {
        FocusRequester()
    }
    val colorBorder = if (isError) MaterialTheme.colorScheme.error else if (isFocused)
        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)

    Column {
        BasicTextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
            maxLines = maxLine,
            singleLine = singleLine,
            interactionSource = interactionSource,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = colorBorder
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .focusRequester(focusRequester)
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()
                    } else {
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .padding(vertical = 16.dp)
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                        Box(modifier = Modifier.fillMaxWidth()) {
                            innerTextField()
                        }
                    }
                    if (trailingIcon != null) {
                        trailingIcon()
                    } else {
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            },
        )
        Text(
            text = if (isError) errorMessage!!.asString(context) else "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier
        )
    }
}