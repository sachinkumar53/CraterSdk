package com.crater.android.feature.support.ui.screen.faq

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.support.ui.model.FAQ
import com.crater.android.feature.support.ui.navigation.SupportNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@SupportNavGraph
@Destination
@Composable
fun FaqScreen(
    navigator: DestinationsNavigator
) {
    val questions = stringArrayResource(id = R.array.faq_questions)
    val answers = stringArrayResource(id = R.array.faq_answers)
    val faqs = remember(questions, answers) {
        questions.zip(answers).toMap().map { FAQ(question = it.key, answer = it.value) }
    }

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(R.string.faqs),
                onBackClick = navigator::navigateUp
            )
        }
    ) {
        LazyColumn {
            itemsIndexed(faqs) { index, item ->
                FaqExpandableCard(index = index + 1, faq = item)
                if (index != faqs.size - 1) {
                    Divider(color = AppTheme.colors.divider)
                }
            }
        }
    }
}

@Composable
fun FaqExpandableCard(
    modifier: Modifier = Modifier,
    index: Int,
    faq: FAQ
) {
    var expanded by remember { mutableStateOf(false) }
    val iconId by remember {
        derivedStateOf { if (expanded) R.drawable.ic_minus else R.drawable.ic_plus }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingExtraSmall)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }
                .padding(AppTheme.dimensions.spacingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$index.  ${faq.question}",
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Medium
            )
            Crossfade(targetState = iconId) {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.size(AppTheme.dimensions.iconSmall)
                )
            }
        }

        if (expanded) {
            Text(
                text = faq.answer,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimensions.spacingMedium)
                    .padding(bottom = AppTheme.dimensions.spacingMedium)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FaqScreenPreview() {
    CraterTheme {
        FaqScreen(navigator = EmptyDestinationsNavigator)
    }
}