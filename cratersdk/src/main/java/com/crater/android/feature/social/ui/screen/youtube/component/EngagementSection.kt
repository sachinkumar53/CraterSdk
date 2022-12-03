package com.crater.android.feature.social.ui.screen.youtube.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.FancyCard
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.data.model.social.YoutubeEngagement
import com.crater.android.feature.social.ui.common.InformationBoxSmall

@Composable
fun EngagementSection(
    engagementData: YoutubeEngagement,
    onViewClick: () -> Unit
) {
    FancyCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimensions.spacingMedium)
        ) {
            FancyHeader(
                title = { Text(text = stringResource(id = R.string.engagement)) },
                subtitle = { Text(text = stringResource(id = R.string.last_28_days)) },
                trailing = {
                    Text(
                        text = stringResource(id = R.string.view_all),
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable(onClick = onViewClick)
                    )
                },
                topMargin = 0.dp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
            ) {
                InformationBoxSmall(
                    painter = painterResource(id = R.drawable.ic_likes),
                    text = stringResource(id = R.string.likes),
                    data = engagementData.contentLikes,
                    modifier = Modifier.weight(1f)
                )
                InformationBoxSmall(
                    painter = painterResource(id = R.drawable.ic_comments),
                    text = stringResource(id = R.string.comments),
                    data = engagementData.contentComments,
                    modifier = Modifier.weight(1f)
                )
                InformationBoxSmall(
                    painter = painterResource(id = R.drawable.ic_views),
                    text = stringResource(id = R.string.views),
                    data = engagementData.contentComments,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
fun EngagementSectionPreview() {
    CraterTheme {
        EngagementSection(
            engagementData = YoutubeEngagement(
                //watchTime = "570 minutes",
                contentLikes = "1.43k",
                contentComments = "245",
                contentViews = "73.6k",
                //videoViewDuration = "2:45 minutes"
            )
        ) {}
    }
}