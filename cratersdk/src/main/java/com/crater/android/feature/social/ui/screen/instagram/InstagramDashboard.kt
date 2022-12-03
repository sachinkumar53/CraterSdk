package com.crater.android.feature.social.ui.screen.instagram

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.data.Resource
import com.crater.android.core.ui.FancyCard
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.data.model.social.InstagramEngagement
import com.crater.android.data.model.social.InstagramProfileData
import com.crater.android.data.model.social.SocialMediaType
import com.crater.android.feature.destinations.InstagramPostsScreenDestination
import com.crater.android.feature.social.ui.common.DashboardAppBar
import com.crater.android.feature.social.ui.common.DashboardSubAppBar
import com.crater.android.feature.social.ui.common.InformationBox
import com.crater.android.feature.social.ui.common.InformationBoxSmall
import com.crater.android.feature.social.ui.viewmodel.SocialMediaViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun InstagramDashboardScreen(
    viewModel: SocialMediaViewModel,
    navigator: DestinationsNavigator
) {
    val profileData by viewModel.instagramProfileData.collectAsState()
    val isLast28days by viewModel.isLast28Days.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DashboardAppBar(
                iconPainter = painterResource(id = R.drawable.ic_instagram),
                title = stringResource(id = R.string.instagram),
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
                    iconPainter = painterResource(id = R.drawable.ic_instagram),
                    title = stringResource(id = R.string.instagram),
                    subtitle = profileData.platformUserName ?: "",
                    onDisconnectClick = {
                        viewModel.disconnectAccount(SocialMediaType.YouTube) {
                            navigator.navigateUp()
                        }
                    }
                )
            }
            item {

                OverviewCard(
                    data = profileData,
                    isLast28days = isLast28days,
                    onExpandChange = {
                        expanded = !expanded
                    },
                    expanded = expanded
                )
            }

            when (val resource = viewModel.instagramEngagementSummary) {
                is Resource.Success -> item {
                    InstagramEngagementCard(
                        engagement = resource.data,
                        onViewClick = {
                            navigator.navigate(InstagramPostsScreenDestination)
                        }
                    )
                }
                else -> Unit
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OverviewCard(
    data: InstagramProfileData,
    isLast28days: Boolean,
    expanded: Boolean,
    onExpandChange: () -> Unit
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
                title = { Text(text = stringResource(id = R.string.overview)) },
                subtitle = {
                    Text(
                        text = if (isLast28days) {
                            stringResource(id = R.string.last_28_days)
                        } else stringResource(id = R.string.last_90_days)
                    )
                }
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
                        text = stringResource(id = R.string.followers),
                        data = data.followerCount ?: "--",
                        painter = painterResource(id = R.drawable.ic_account)
                    )
                }

                item {
                    InformationBox(
                        text = stringResource(id = R.string.following),
                        data = data.followingCount ?: "--",
                        painter = painterResource(id = R.drawable.ic_green_profile)
                    )
                }

                item {
                    InformationBox(
                        text = stringResource(id = R.string.content_count),
                        data = data.contentCount ?: "--",
                        painter = painterResource(id = R.drawable.ic_play)
                    )
                }
            }
        }
    }
}

@Composable
private fun InstagramEngagementCard(
    engagement: InstagramEngagement,
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
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
                modifier = Modifier
                    .heightIn(max = 400.dp)
                    .padding(top = AppTheme.dimensions.spacingMedium),
                userScrollEnabled = false
            ) {
                item {
                    InformationBoxSmall(
                        painter = painterResource(id = R.drawable.ic_heart),
                        text = stringResource(id = R.string.likes),
                        data = engagement.likeCount,
                        modifier = Modifier.weight(1f)
                    )
                }
                item {
                    InformationBoxSmall(
                        painter = painterResource(id = R.drawable.ic_comments),
                        text = stringResource(id = R.string.comments),
                        data = engagement.commentCount,
                        modifier = Modifier.weight(1f)
                    )
                }
                item {
                    InformationBoxSmall(
                        painter = painterResource(id = R.drawable.ic_send),
                        text = stringResource(id = R.string.shared),
                        data = engagement.shareCount,
                        modifier = Modifier.weight(1f)
                    )
                }
                item {
                    InformationBoxSmall(
                        painter = painterResource(id = R.drawable.ic_save),
                        text = stringResource(id = R.string.saved),
                        data = engagement.saveCount,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun EngagementSectionPreview() {
    CraterTheme {
        /*InstagramEngagementCard(
            engagement = InstagramEngagement(
                engagement = "570 minutes",
                contentLikes = "1.43k",
                contentComments = "245",
                contentViews = "73.6k",
                saveCount = "173",
                shareCount = "78"
            )
        )*/
        OverviewCard(
            data = InstagramProfileData(
                workPlatformName = "Instagram",
                workPlatformLogoUrl = "",
                platformUserName = "my_user_name",
                contentCount = "5.3k",
                followerCount = "22.1k",
                followingCount = "879.2k"
            ),
            isLast28days = true,
            onExpandChange = {},
            expanded = true
        )
    }
}