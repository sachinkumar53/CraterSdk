package com.crater.android.feature.invoicing.ui.screen.dashboard.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.util.UiText
import com.crater.android.feature.NavGraphs
import com.crater.android.feature.appCurrentDestinationAsState
import com.crater.android.feature.destinations.InvoiceHomeScreenDestination
import com.crater.android.feature.destinations.InvoiceListScreenDestination
import com.crater.android.feature.destinations.PaymentTrackerScreenDestination
import com.crater.android.feature.destinations.ProfileScreenDestination
import com.crater.android.feature.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@Composable
fun BottomBar(navController: NavController) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    BottomNavigation(
        backgroundColor = Color.Transparent,
        modifier = Modifier.background(
            Brush.verticalGradient(listOf(Color(0x590B100D), Color(0x0D0B100D)))
        ),
        elevation = 0.dp
    ) {
        BottomBarDestination.values().forEach { destination ->
            InvoiceBottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true

                    }
                },
                destination = destination
            )
        }
    }
}

@Composable
fun RowScope.InvoiceBottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    destination: BottomBarDestination
) {
    BottomNavigationItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = destination.iconResId),
                contentDescription = destination.label.asString(),
                modifier = Modifier.size(24.dp)
            )
        },
        label = {
            Text(
                text = destination.label.asString(),
                modifier = Modifier.padding(top = AppTheme.dimensions.spacingExtraSmall)
            )
        },
        unselectedContentColor = Color(0xFF8AAAA2)
    )
}


enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val label: UiText,
    @DrawableRes val iconResId: Int
) {
    Home(
        InvoiceHomeScreenDestination,
        UiText.StringResource(R.string.home),
        R.drawable.ic_home
    ),
    Invoice(
        InvoiceListScreenDestination,
        UiText.StringResource(R.string.invoice),
        R.drawable.ic_invoice
    ),
    Tracker(
        PaymentTrackerScreenDestination,
        UiText.StringResource(R.string.tracker),
        R.drawable.ic_tracker
    );
}
