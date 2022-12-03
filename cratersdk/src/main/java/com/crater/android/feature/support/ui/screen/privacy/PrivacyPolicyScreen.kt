package com.crater.android.feature.support.ui.screen.privacy

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.support.ui.navigation.SupportNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SupportNavGraph
@Destination
@Composable
fun PrivacyPolicyScreen(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val text = rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        kotlin.runCatching {
            val inputStream = context.assets.open("privacy_policy.txt")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            String(buffer)
        }.onSuccess {
            text.value = it
        }.onFailure {
            Log.e("PrivacyPolicyScreen", "loadPrivacyPolicy: ", it)
        }
    }

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.privacy_policy),
                onBackClick = navigator::navigateUp
            )
        }
    ) {
        Text(
            text = text.value,
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.dimensions.spacingMedium)
                .verticalScroll(scrollState)
        )
    }
}