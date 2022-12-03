package com.crater.android.feature.invoicing.ui.screen.customer.list

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.data.Resource
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.screen.EmptyScreen
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.feature.invoicing.domain.model.Customer
import com.crater.android.feature.invoicing.ui.screen.customer.CustomerViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination
@Composable
fun AllCustomerScreen(
    viewModel: CustomerViewModel = hiltViewModel(),
    resultBackNavigator: ResultBackNavigator<Customer>
) {
    val customerResource by viewModel.allCustomers.collectAsState()

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.customers),
                onBackClick = resultBackNavigator::navigateBack
            )
        }
    ) {
        when (val data = customerResource) {
            is Resource.Loading -> LoadingScreen()
            is Resource.Error -> EmptyScreen()
            is Resource.Success -> {
                if (data.data.isNotEmpty()) {
                    AllCustomerScreenContent(
                        customers = data.data,
                        onCustomerClick = resultBackNavigator::navigateBack
                    )
                } else {
                    EmptyScreen()
                }
            }
        }
    }
}

