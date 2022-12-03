package com.crater.android.feature.social.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.crater.android.R
import com.crater.android.core.base.ComposeActivity
import com.crater.android.core.extension.observeFlow
import com.crater.android.core.extension.showToast
import com.crater.android.data.model.social.SocialMediaType
import com.crater.android.feature.NavGraphs
import com.crater.android.feature.destinations.SocialMediaScreenDestination
import com.crater.android.feature.social.ui.viewmodel.SocialMediaViewModel
import com.getphyllo.ConnectCallback
import com.getphyllo.PhylloConnect
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialMediaActivity : ComposeActivity(), ConnectCallback {
    private val viewModel: SocialMediaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeFlow(viewModel.errorMessageFlow) { message ->
            showToast(message)
        }

        observeFlow(viewModel.onInitSdkFlow) { (sdkToken, userId, platformId) ->
            initSDK(
                token = sdkToken.value,
                userId = userId.value,
                platformId = platformId
            )
        }
    }

    @Composable
    override fun Content() {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            startRoute = SocialMediaScreenDestination,
            dependenciesContainerBuilder = {
                dependency(viewModel)
            }
        )
    }


    private fun initSDK(
        userId: String,
        token: String,
        platformId: String,
    ) {
        PhylloConnect.initialize(
            context = this,
            clientDisplayName = getString(R.string.app_name),
            userId = userId,
            token = token,
            workPlatformId = platformId,
            environment = PhylloConnect.ENVIRONMENT.PRODUCTION,
            callback = this
        )

        PhylloConnect.open()
    }


    override fun onAccountConnected(
        account_id: String?,
        work_platform_id: String?,
        user_id: String?,
    ) {
        when (work_platform_id) {
            SocialMediaType.Instagram.workPlatformId -> {
                viewModel.onAccountConnected(SocialMediaType.Instagram, account_id)
            }

            SocialMediaType.YouTube.workPlatformId -> {
                viewModel.onAccountConnected(SocialMediaType.YouTube, account_id)
            }
            else -> Unit
        }
    }

    override fun onAccountDisconnected(
        account_id: String?,
        work_platform_id: String?,
        user_id: String?,
    ) {
    }

    override fun onTokenExpired(user_id: String?) {}

    override fun onExit(reason: String?, user_id: String?) {
        if (reason == "BACK_PRESSED") {
            showToast(getString(R.string.connection_failed))
        } else {
            showToast(getString(R.string.connected_successfully))
        }
    }

}