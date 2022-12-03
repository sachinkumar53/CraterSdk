package com.crater.android.core.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.crater.android.core.ui.event.EventHandler
import com.crater.android.core.ui.event.LocalEventHandler
import com.crater.android.core.ui.theme.CraterTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class ComposeActivity : ComponentActivity() {
    @Inject
    lateinit var eventHandler: EventHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventHandler.attachTo(this)

        setContent {
            CraterTheme {
                CompositionLocalProvider(LocalEventHandler provides eventHandler) {
                    Content()
                }
            }
        }
    }

    @Composable
    abstract fun Content()
}