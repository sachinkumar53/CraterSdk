package com.crater.android.feature.dashboard.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.dashboard.ui.screen.components.ServiceCard
import kotlinx.coroutines.delay

@Composable
fun DashboardScreenContent(
    userName: String,
    onProfileClick: () -> Unit,
    onCreateInvoiceClick: () -> Unit,
    onCraterCashClick: () -> Unit,
    onExpenseManagerClick: () -> Unit,
    onFileTaxClick: () -> Unit,
    onSocialMediaClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(name = userName, onProfileClick = onProfileClick)
        },
        modifier = Modifier.padding(horizontal = AppTheme.dimensions.spacingMedium)
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = AppTheme.dimensions.spacingMedium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
        ) {
            item { HeaderSection() }

            item {
                FancyHeader(title = { Text(text = stringResource(id = R.string.our_services)) })
            }

            item {
                ServiceCard(
                    iconPainter = painterResource(id = R.drawable.ic_invoice),
                    title = stringResource(id = R.string.invoicing),
                    description = stringResource(id = R.string.create_invoice_desc),
                    onClick = onCreateInvoiceClick
                )
            }

            item {
                ServiceCard(
                    iconPainter = painterResource(id = R.drawable.ic_crater_cash),
                    title = stringResource(id = R.string.crater_cash),
                    description = stringResource(id = R.string.crater_cash_desc),
                    onClick = onCraterCashClick
                )
            }

            item {
                ServiceCard(
                    iconPainter = painterResource(id = R.drawable.ic_expense_manager),
                    title = stringResource(id = R.string.expense_manager),
                    description = stringResource(id = R.string.expense_manager_desc),
                    onClick = onExpenseManagerClick
                )
            }

            item {
                ServiceCard(
                    iconPainter = painterResource(id = R.drawable.ic_tax),
                    title = stringResource(id = R.string.file_taxes),
                    description = stringResource(id = R.string.file_taxes_desc),
                    onClick = onFileTaxClick
                )
            }

            item {
                ServiceCard(
                    iconPainter = painterResource(id = R.drawable.ic_social_network),
                    title = stringResource(id = R.string.social_media),
                    description = stringResource(id = R.string.social_media_desc),
                    onClick = onSocialMediaClick
                )
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeaderSection() {
    var textIndex by remember { mutableStateOf(0) }
    val craterTexts = stringArrayResource(id = R.array.crater_texts)
    LaunchedEffect(key1 = textIndex) {
        delay(4000L)
        textIndex = (textIndex + 1) % craterTexts.size
    }

    Column {
        Text(
            text = stringResource(id = R.string.the_business_app_for),
            style = AppTheme.typography.headlineMedium
        )

        AnimatedContent(
            targetState = textIndex,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(600)
                ) with fadeOut(
                    animationSpec = tween(600)
                )
            }
        ) { targetIndex ->
            Text(
                text = craterTexts[targetIndex],
                color = AppTheme.colors.primary,
                style = AppTheme.typography.headlineMedium
            )
        }

    }
}


@Composable
fun TopBar(
    name: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.hello),
                style = AppTheme.typography.labelLarge,
                color = AppTheme.colors.textSecondary
            )
            Text(
                text = name,
                style = AppTheme.typography.title
            )
        }

        IconButton(onClick = onProfileClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile_circle),
                contentDescription = stringResource(id = R.string.profile)
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun PreviewDashboardScreenContent() {
    CraterTheme {
        DashboardScreenContent(
            userName = "Rahul",
            onCraterCashClick = {},
            onCreateInvoiceClick = {},
            onExpenseManagerClick = {},
            onFileTaxClick = {},
            onProfileClick = {},
            onSocialMediaClick = {}
        )
    }
}