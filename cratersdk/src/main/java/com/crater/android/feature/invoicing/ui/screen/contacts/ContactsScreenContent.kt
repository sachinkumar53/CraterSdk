package com.crater.android.feature.invoicing.ui.screen.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.screen.EmptyScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.data.model.contact.Contact

@Composable
fun ContactsScreenContent(
    onBackClick: () -> Unit,
    onItemClick: (Contact) -> Unit,
    onQueryChanged: (String) -> Unit,
    contacts: List<Contact>,
    searchQuery: String
) {

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.my_contacts),
                onBackClick = onBackClick
            )
        }
    ) {
        if (contacts.isEmpty()) {
            EmptyScreen("No contacts found")
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = AppTheme.dimensions.spacingMedium)
            ) {
                SearchField(searchQuery = searchQuery, onQueryChanged = onQueryChanged)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
                ) {
                    items(contacts) { item ->
                        ContactItemRow(
                            displayName = item.displayName ?: "",
                            phoneNumber = item.phoneNumbers.firstOrNull() ?: "",
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun ContactItemRow(
    displayName: String,
    phoneNumber: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(AppTheme.colors.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(AppTheme.dimensions.spacingMedium)
    ) {
        Text(text = displayName)

        Text(
            text = phoneNumber,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
        )
    }
}


@Composable
private fun SearchField(
    searchQuery: String,
    onQueryChanged: (String) -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = onQueryChanged,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            leadingIconColor = Color.White,
            placeholderColor = Color.White
        ),
        placeholder = {
            Text(text = stringResource(id = R.string.search))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        },
        maxLines = 1
    )
}

@Preview(showSystemUi = true)
@Composable
fun ContactsScreenContentPreview() {
    CraterTheme {
        ContactsScreenContent(
            onBackClick = {},
            onItemClick = {},
            onQueryChanged = {},
            searchQuery = "",
            contacts = emptyList()
        )
    }
}