package com.crater.android.feature.support.ui.screen.help

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.destinations.FaqScreenDestination
import com.crater.android.feature.destinations.PrivacyPolicyScreenDestination
import com.crater.android.feature.destinations.TermsAndConditionsScreenDestination
import com.crater.android.feature.support.ui.navigation.SupportNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SupportNavGraph(start = true)
@Destination
@Composable
fun HelpScreen(navigator: DestinationsNavigator) {
    val context = LocalContext.current

    HelpScreenContent(
        onBackClick = navigator::navigateUp,
        onTermsAndConditionsClick = { navigator.navigate(TermsAndConditionsScreenDestination()) },
        onPrivacyPolicyClick = { navigator.navigate(PrivacyPolicyScreenDestination()) },
        onFaqClick = { navigator.navigate(FaqScreenDestination()) }
    )
}


@Composable
fun HelpScreenContent(
    onBackClick: () -> Unit,
    onTermsAndConditionsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onFaqClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        CenteredTopAppBar(
            title = stringResource(id = R.string.help_and_support),
            onBackClick = onBackClick
        )
        NavigationMenu(
            action = onTermsAndConditionsClick,
            label = stringResource(id = R.string.terms_and_conditions)
        )

        NavigationMenu(
            action = onPrivacyPolicyClick,
            label = stringResource(id = R.string.privacy_policy)
        )

        NavigationMenu(
            action = onFaqClick,
            label = stringResource(id = R.string.faqs)
        )
    }
}

@Composable
fun NavigationMenu(
    modifier: Modifier = Modifier,
    action: () -> Unit,
    label: String
) {
    Row(
        modifier = Modifier
            .padding(horizontal = AppTheme.dimensions.spacingMedium)
            .clip(AppTheme.shapes.medium)
            .fillMaxWidth()
            .background(AppTheme.colors.surfaceVariant)
            .clickable { action() }
            .padding(AppTheme.dimensions.spacingMedium)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = AppTheme.typography.labelLarge
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_next),
            contentDescription = null
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun HelpScreenContentPreview() {
    CraterTheme {
        HelpScreenContent(
            onBackClick = {},
            onTermsAndConditionsClick = {},
            onFaqClick = {},
            onPrivacyPolicyClick = {}
        )
    }
}
