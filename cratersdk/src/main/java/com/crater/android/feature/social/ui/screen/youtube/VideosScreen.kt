package com.crater.android.feature.social.ui.screen.youtube

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.model.UiState
import com.crater.android.core.ui.screen.EmptyScreen
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.data.model.social.SocialMediaType
import com.crater.android.data.model.social.YoutubeVideo
import com.crater.android.feature.social.domain.model.Comment
import com.crater.android.feature.social.ui.screen.youtube.component.YoutubeVideoItem
import com.crater.android.feature.social.ui.viewmodel.EngagementViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun VideosScreen(
    viewModel: EngagementViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    LaunchedEffect(Unit) {
        viewModel.loadData(SocialMediaType.YouTube)
    }

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.videos),
                onBackClick = {
                    navigator.navigateUp()
                }
            )
        }
    ) { paddingValues ->
        when (val resource = viewModel.youtubeEngagementState) {
            is UiState.Empty, is UiState.Error -> EmptyScreen()
            is UiState.Loading -> LoadingScreen()
            is UiState.Success -> {
                if (resource.data.isEmpty()) {
                    EmptyScreen()
                } else {
                    EngagementList(
                        engagementData = resource.data,
                        contentPadding = paddingValues
                    )
                }
            }
        }
    }
}

@Composable
fun EngagementList(
    engagementData: List<YoutubeVideo>,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall),
        contentPadding = contentPadding
    ) {
        items(engagementData) { data ->
            YoutubeVideoItem(
                engagementItem = data
            )
        }
    }
}

@Preview
@Composable
fun ListPreview() {
    EngagementList(engagementData = FakeData, PaddingValues())
}

@Stable
val FakeData = (1..10).map {
    YoutubeVideo(
        likeCount = "1.43k",
        dislikeCount = "189",
        commentCount = "305",
        viewCount = "4.44k",
        watchTimeInHours = "32.1hr",
        title = "[Alan Walker] - End Of Time - 2020 Latest song",
        url = "www.google.com",
        thumbnailUrl = "https://source.unsplash.com/random/300×300",
        publishedAt = "16 Aug 2022",
        comment = Comment(
            profilePicUrl = "https://source.unsplash.com/random/100×100",
            userName = "Test user",
            commentDate = "12 Aug 2022",
            text = "Hello this is my first comment."
        )
    )
}.toList()