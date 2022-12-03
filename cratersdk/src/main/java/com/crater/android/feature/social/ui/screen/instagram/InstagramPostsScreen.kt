package com.crater.android.feature.social.ui.screen.instagram

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.model.UiState
import com.crater.android.core.ui.screen.EmptyScreen
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.data.model.social.InstagramPost
import com.crater.android.data.model.social.SocialMediaType
import com.crater.android.feature.social.ui.screen.instagram.component.InstagramPostItem
import com.crater.android.feature.social.ui.viewmodel.EngagementViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun InstagramPostsScreen(
    viewModel: EngagementViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    LaunchedEffect(Unit) {
        viewModel.loadData(SocialMediaType.Instagram)
    }

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.posts),
                onBackClick = {
                    navigator.navigateUp()
                }
            )
        }
    ) { paddingValues ->
        when (val resource = viewModel.instagramEngagementState) {
            is UiState.Empty, is UiState.Error -> EmptyScreen()
            is UiState.Loading -> LoadingScreen()
            is UiState.Success -> {
                if (resource.data.isEmpty()) {
                    EmptyScreen()
                } else {
                    MainContent(
                        data = resource.data,
                        contentPadding = paddingValues
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(
    data: List<InstagramPost>,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall),
        contentPadding = contentPadding
    ) {
        items(data) { item ->
            InstagramPostItem(item)
        }
    }
}