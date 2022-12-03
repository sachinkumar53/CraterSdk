package com.crater.android.feature.invoicing.ui.screen.invoice.create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.CraterIcons
import com.crater.android.core.ui.FancyDropDownMenu
import com.crater.android.core.ui.icons.ArrowDropDown
import com.crater.android.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaxTextField(
    onValueChange: (String) -> Unit,
    onTaxTypeChanged: (String) -> Unit,
    value: String,
    taxType: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    var dropDownExpanded by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(0.dp),
                trailingIcon = {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        Text(
                            text = "%",
                            color = LocalContentColor.current
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = "0",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
                leadingIcon = {
                    TaxDropDownMenu(
                        expanded = dropDownExpanded,
                        onExpandChanged = {
                            dropDownExpanded = it
                        },
                        onItemClick = onTaxTypeChanged,
                        modifier = Modifier.padding(start = AppTheme.dimensions.spacingSmall),
                        value = taxType
                    )
                }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(AppTheme.colors.surface),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.End,
            color = LocalContentColor.current
        ),
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun TaxDropDownMenu(
    value: String,
    expanded: Boolean,
    onExpandChanged: (Boolean) -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { onExpandChanged(!expanded) }
            .padding(AppTheme.dimensions.spacingExtraSmall)
            .then(modifier),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingExtraSmall)
    ) {
        Text(
            text = value.ifBlank { stringResource(id = R.string.tax) },
            color = LocalContentColor.current.copy(alpha = if (value.isBlank()) 0.8f else 1f)
        )
        Icon(
            imageVector = CraterIcons.ArrowDropDown,
            contentDescription = null
        )
    }
    FancyDropDownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandChanged(false) }
    ) {
        stringArrayResource(id = R.array.tax_types).forEach { taxType ->
            DropdownMenuItem(onClick = {
                onItemClick(taxType)
                onExpandChanged(false)

            }) {
                Text(text = taxType)
            }
        }
    }

}