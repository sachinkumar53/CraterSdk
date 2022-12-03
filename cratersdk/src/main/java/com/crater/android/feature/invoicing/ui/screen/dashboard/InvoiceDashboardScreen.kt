package com.crater.android.feature.invoicing.ui.screen.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.NavGraphs
import com.crater.android.feature.appCurrentDestinationAsState
import com.crater.android.feature.destinations.InvoiceHomeScreenDestination
import com.crater.android.feature.destinations.PaymentTrackerScreenDestination
import com.crater.android.feature.invoicing.ui.screen.dashboard.component.BottomBar
import com.crater.android.feature.invoicing.ui.screen.dashboard.component.BottomBarDestination
import com.crater.android.feature.startAppDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun InvoiceDashboardScreen(navigator: DestinationsNavigator) {
    val navController = rememberNavController()

    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    val bottomNavDestinations = rememberSaveable {
        BottomBarDestination.values().toSet().map { it.direction.route }
    }

    val titleResId = remember(currentDestination) {
        when (currentDestination.route) {
            PaymentTrackerScreenDestination.route -> R.string.tracker
            else -> R.string.invoice
        }
    }
    if (currentDestination.route in bottomNavDestinations) {
        Scaffold(
            topBar = {
                CenteredTopAppBar(
                    title = stringResource(id = titleResId),
                    onBackClick = navigator::navigateUp
                )
            },
            bottomBar = { BottomBar(navController) }
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                startRoute = InvoiceHomeScreenDestination,
                modifier = Modifier.padding(it),
                navController = navController
            )
        }
    } else {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            startRoute = InvoiceHomeScreenDestination,
            navController = navController
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun InvoiceHomeScreenPreview() {
    CraterTheme {
        InvoiceDashboardScreen(navigator = EmptyDestinationsNavigator)
    }
}