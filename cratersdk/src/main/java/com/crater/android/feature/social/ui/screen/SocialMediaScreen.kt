package com.crater.android.feature.social.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.crater.android.R
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.destinations.InstagramDashboardScreenDestination
import com.crater.android.feature.destinations.YouTubeDashboardScreenDestination
import com.crater.android.feature.social.ui.viewmodel.SocialMediaViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SocialMediaScreen(
    viewModel: SocialMediaViewModel,
    navigator: DestinationsNavigator
) {
    val isInstagramConnected by viewModel.isInstagramConnected.collectAsState()
    val isYoutubeConnected by viewModel.isYoutubeConnected.collectAsState()
    val isLoading by viewModel.isLoadingProfile.collectAsState(initial = false)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimensions.spacingMedium)
            ) {
                SocialMediaAnimation()
                Text(
                    text = stringResource(id = R.string.social_media_desc_two_line),
                    style = AppTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraLarge))
                SocialMediaButton(
                    painter = painterResource(id = R.drawable.ic_instagram),
                    platformName = stringResource(id = R.string.instagram),
                    isConnected = isInstagramConnected,
                    onClick = {
                        if (viewModel.onInstagramClick()) {
                            navigator.navigate(InstagramDashboardScreenDestination)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))
                SocialMediaButton(
                    painter = painterResource(id = R.drawable.ic_youtube_icon),
                    platformName = stringResource(id = R.string.youtube),
                    isConnected = isYoutubeConnected,
                    onClick = {
                        if (viewModel.onYoutubeClick()) {
                            navigator.navigate(YouTubeDashboardScreenDestination)
                        }
                    }
                )
            }
        }
        if (isLoading) {
            LoadingScreen()
        }
    }
}

@Composable
fun SocialMediaButton(
    painter: Painter,
    platformName: String,
    isConnected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(AppTheme.colors.surfaceVariant)
            .heightIn(64.dp)
            .padding(horizontal = AppTheme.dimensions.spacingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(AppTheme.dimensions.iconLarge),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = platformName,
                style = AppTheme.typography.labelLarge
            )
            Text(
                text = stringResource(
                    id = if (isConnected) R.string.connected
                    else R.string.not_connected
                ),
                style = AppTheme.typography.labelMedium,
                color = AppTheme.colors.textSecondary
            )
        }

        if (!isConnected) {
            TextButton(
                onClick = onClick
            ) {
                Text(
                    text = stringResource(id = R.string.connect),
                    style = AppTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun SocialMediaAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.social_media_animation)
    )
    LottieAnimation(
        composition = composition,
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(horizontal = 16.dp),
        iterations = LottieConstants.IterateForever
    )
}

