package com.crater.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.MainViewModel
import com.crater.android.feature.NavGraphs
import com.crater.android.feature.destinations.DashboardScreenDestination
import com.crater.android.feature.destinations.PersonalDetailsScreenDestination
import com.crater.android.feature.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CraterActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { viewModel.isLoadingDetails }
        super.onCreate(savedInstanceState)

        setContent {
            CraterTheme {
                val accessToken by viewModel.accessToken.collectAsStateWithLifecycle()
                val userDetails by viewModel.userDetails.collectAsStateWithLifecycle()

                key(accessToken, userDetails) {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        startRoute = when {
                            accessToken.isNullOrBlank() -> RegisterScreenDestination
                            userDetails.userName.firstName.isBlank() -> PersonalDetailsScreenDestination
                            else -> DashboardScreenDestination
                        }
                    )
                }
            }
        }
    }

}