package com.crater.android.core.ui.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.crater.android.R

@Composable
fun ResultAnimation(
    isSuccess: Boolean,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            if (isSuccess) R.raw.success else R.raw.bouncy_fail
        )
    )

    LottieAnimation(
        composition = composition,
        modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .fillMaxHeight(fraction = 0.3f)
            .then(modifier)
    )
}