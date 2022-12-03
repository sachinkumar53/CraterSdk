package com.crater.android.feature.invoicing.ui.screen.service

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun AllServicesScreen(
    viewModel: ServiceViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val services by viewModel.allServices.collectAsState()

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.services),
                onBackClick = navigator::navigateUp
            )
        }
    ) { padding ->
        /*LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spacingSmall),
            modifier = Modifier
                .padding(padding)
                .padding(MaterialTheme.dimens.spacingMedium)
        ) {
            items(services) {

            }
        }*/
    }
}