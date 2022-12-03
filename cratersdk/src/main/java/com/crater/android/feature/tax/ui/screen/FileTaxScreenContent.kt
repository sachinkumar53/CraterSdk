package com.crater.android.feature.tax.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.gradientCard
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun FileTaxScreenContent(
    onBackClick: () -> Unit,
    onContactClick: () -> Unit,
    onDiyClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingExtraLarge)
    ) {
        CenteredTopAppBar(
            title = stringResource(id = R.string.file_taxes),
            onBackClick = onBackClick
        )

        TaxServiceCard(
            action = onContactClick,
            title = stringResource(id = R.string.expert_assisted_filling),
            description = stringResource(id = R.string.expense_manager_desc),
            buttonLabel = stringResource(id = R.string.try_now)
        )

        TaxServiceCard(
            action = onDiyClick,
            title = stringResource(id = R.string.do_it_yourself),
            description = stringResource(id = R.string.easy_and_hassle_free_it_takes_less_than_10_minutes),
            buttonLabel = stringResource(id = R.string.coming_soon)
        )
    }
}


@Composable
private fun TaxServiceCard(
    action: () -> Unit,
    title: String,
    description: String,
    buttonLabel: String,
) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxWidth(0.8f)
            .gradientCard()
            .padding(AppTheme.dimensions.spacingLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = AppTheme.typography.title
        )

        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingTiny))
        Text(
            text = description,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = AppTheme.dimensions.spacingMedium)
        )
        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraLarge))
        FancyButton(
            onClick = action,
            text = buttonLabel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.spacingLarge),
        )
    }
}

@Preview
@Composable
fun FileTaxScreenContentPreview() {
    CraterTheme {
        FileTaxScreenContent(
            onBackClick = { },
            onContactClick = { },
            onDiyClick = {}
        )
    }
}