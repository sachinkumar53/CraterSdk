package com.crater.android.feature.invoicing.ui.screen.customer.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.data.Resource
import com.crater.android.core.ui.FancyDialog
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.destinations.AllCustomerScreenDestination
import com.crater.android.feature.destinations.ContactsScreenDestination
import com.crater.android.feature.destinations.EditCustomerScreenDestination
import com.crater.android.feature.invoicing.domain.model.Customer
import com.crater.android.feature.invoicing.ui.common.BoxButton
import com.crater.android.feature.invoicing.ui.common.CustomerListItem
import com.crater.android.feature.invoicing.ui.screen.customer.CustomerViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun CustomerDialog(
    navigator: DestinationsNavigator,
    viewModel: CustomerViewModel = hiltViewModel(),
    resultBackNavigator: ResultBackNavigator<Customer>
) {
    val recentCustomer by viewModel.recentCustomers.collectAsState()

    when (val data = recentCustomer) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Error -> navigator.navigateUp()
        is Resource.Success -> CustomerDialogContent(
            recentCustomers = data.data,
            onBackClick = resultBackNavigator::navigateBack,
            onFromContactClick = { navigator.navigate(ContactsScreenDestination()) },
            onAddNewClick = {
                navigator.navigate(EditCustomerScreenDestination())
            },
            onCustomerClick = { customer ->
                resultBackNavigator.navigateBack(customer)
            },
            onSeeAllClick = {
                navigator.navigate(AllCustomerScreenDestination())
            }
        )
    }
}


@Composable
private fun CustomerDialogContent(
    onBackClick: () -> Unit,
    onFromContactClick: () -> Unit,
    onAddNewClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    onCustomerClick: (Customer) -> Unit,
    recentCustomers: List<Customer>
) {
    FancyDialog(
        onDismissRequest = onBackClick,
        title = stringResource(id = R.string.add_customer)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)) {
            BoxButton(
                onClick = onFromContactClick,
                iconPainter = painterResource(id = R.drawable.ic_contacts),
                text = stringResource(id = R.string.from_contacts)
            )

            BoxButton(
                onClick = onAddNewClick,
                iconPainter = painterResource(id = R.drawable.ic_plus),
                text = stringResource(id = R.string.add_manual)
            )
        }

        if (recentCustomers.isNotEmpty()) {
            FancyHeader(
                title = { Text(text = stringResource(id = R.string.recent_customers)) },
                trailing = {
                    Text(
                        text = stringResource(id = R.string.see_all),
                        modifier = Modifier.clickable {
                            onSeeAllClick()
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
            ) {
                items(recentCustomers) {
                    CustomerListItem(
                        onClick = { onCustomerClick(it) },
                        customer = it
                    )
                }
            }
        }

    }
}
