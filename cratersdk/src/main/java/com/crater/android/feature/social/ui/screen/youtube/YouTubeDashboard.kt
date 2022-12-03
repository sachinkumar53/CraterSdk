package com.crater.android.feature.social.ui.screen.youtube

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.data.Resource
import com.crater.android.core.ui.FancyCard
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.data.model.social.SocialMediaType
import com.crater.android.data.model.social.YouTubeProfileData
import com.crater.android.feature.destinations.VideosScreenDestination
import com.crater.android.feature.social.ui.common.DashboardAppBar
import com.crater.android.feature.social.ui.common.DashboardSubAppBar
import com.crater.android.feature.social.ui.common.InformationBox
import com.crater.android.feature.social.ui.screen.youtube.component.EngagementSection
import com.crater.android.feature.social.ui.viewmodel.SocialMediaViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun YouTubeDashboardScreen(
    viewModel: SocialMediaViewModel,
    navigator: DestinationsNavigator
) {
    val profileData by viewModel.youTubeProfileData.collectAsState()
    val resource = viewModel.youtubeEngagementSummary
    Scaffold(
        topBar = {
            DashboardAppBar(
                iconPainter = painterResource(id = R.drawable.ic_youtube_new),
                title = stringResource(id = R.string.youtube),
                onBackClick = {
                    navigator.navigateUp()
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
        ) {
            item {
                DashboardSubAppBar(
                    iconPainter = painterResource(id = R.drawable.ic_youtube_new),
                    title = stringResource(id = R.string.youtube),
                    subtitle = profileData.platformUserName ?: "",
                    onDisconnectClick = {
                        viewModel.disconnectAccount(SocialMediaType.YouTube) {
                            navigator.navigateUp()
                        }
                    }
                )
            }
            item {
                IdentityCard(profileData)
            }

            if (resource is Resource.Success) {
                item {
                    EngagementSection(engagementData = resource.data,
                        onViewClick = {
                            navigator.navigate(VideosScreenDestination)
                        }
                    )
                }
            }
        }

        if (resource is Resource.Loading) {
            LoadingScreen()
        }
    }
}

@Composable
private fun IdentityCard(
    data: YouTubeProfileData
) {
    FancyCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimensions.spacingSmall)
        ) {
            FancyHeader(
                title = { Text(text = stringResource(id = R.string.identity)) },
                subtitle = { Text(text = stringResource(id = R.string.last_28_days)) },
                topMargin = 0.dp
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
                modifier = Modifier
                    .heightIn(max = 400.dp)
                    .padding(top = AppTheme.dimensions.spacingMedium),
                userScrollEnabled = false
            ) {
                item {
                    InformationBox(
                        text = stringResource(id = R.string.subscribers),
                        data = data.subscriberCount ?: "--",
                        painter = painterResource(id = R.drawable.ic_subscribers)
                    )
                }

                item {
                    InformationBox(
                        text = stringResource(id = R.string.content_count),
                        data = data.contentCount ?: "--",
                        painter = painterResource(id = R.drawable.ic_play)
                    )
                }

                item {
                    InformationBox(
                        text = stringResource(id = R.string.watch_time),
                        data = data.watchTime ?: "--",
                        painter = painterResource(id = R.drawable.ic_watch_time)
                    )
                }
            }
        }

    }
}
