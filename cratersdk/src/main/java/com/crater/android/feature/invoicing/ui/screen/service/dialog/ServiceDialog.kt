package com.crater.android.feature.invoicing.ui.screen.service.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.data.Resource
import com.crater.android.core.ui.FancyDialog
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.destinations.EditServiceScreenDestination
import com.crater.android.feature.invoicing.domain.model.Service
import com.crater.android.feature.invoicing.ui.common.BoxButton
import com.crater.android.feature.invoicing.ui.screen.service.ServiceViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun ServiceDialog(
    viewModel: ServiceViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val services by viewModel.recentServices.collectAsState()

    when (val data = services) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Error -> navigator.navigateUp()
        is Resource.Success -> ServiceDialogContent(
            onBackClick = navigator::navigateUp,
            onNewServiceClick = {
                navigator.navigate(EditServiceScreenDestination())
            },
            onServiceClick = { service ->
                navigator.navigate(EditServiceScreenDestination(service))
            },
            services = data.data
        )
    }

}

@Composable
private fun ServiceDialogContent(
    onBackClick: () -> Unit,
    onNewServiceClick: () -> Unit,
    onServiceClick: (Service) -> Unit,
    services: List<Service>
) {
    FancyDialog(
        onDismissRequest = onBackClick,
        title = stringResource(id = R.string.add_service)
    ) {
        BoxButton(
            onClick = onNewServiceClick,
            iconPainter = painterResource(id = R.drawable.ic_plus),
            text = stringResource(id = R.string.new_service)
        )

        if (services.isNotEmpty()) {
            FancyHeader(title = { Text(text = stringResource(id = R.string.recent_services)) })
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
            ) {
                items(services) { service ->
                    ServiceRowItem(
                        service = service,
                        onClick = onServiceClick
                    )
                }
            }
        }
    }
}

@Composable
private fun ServiceRowItem(
    service: Service,
    onClick: (Service) -> Unit
) {
    Row(modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .fillMaxWidth()
        .background(Color(0xFF243931))
        .clickable { onClick(service) }
        .padding(16.dp)
    ) {
        Text(text = service.name)
    }
}
