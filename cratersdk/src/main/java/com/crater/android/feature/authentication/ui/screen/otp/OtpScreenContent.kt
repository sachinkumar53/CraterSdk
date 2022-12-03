package com.crater.android.feature.authentication.ui.screen.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.authentication.ui.common.PartiallyClickableText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpScreenContent(
    onBackClick: () -> Unit,
    onVerifyClick: () -> Unit,
    onResendClick: () -> Unit,
    phoneNumber: String,
    onOtpChanged: (String) -> Unit,
    uiState: OtpUiState
) {
    Scaffold(
        topBar = { CenteredTopAppBar(title = "", onBackClick = onBackClick) }
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.dimensions.spacingMedium)
        ) {
            Text(
                text = stringResource(id = R.string.enter_otp),
                style = AppTheme.typography.headlineMedium
            )

            Text(text = stringResource(id = R.string.enter_otp_sent_to_format, phoneNumber))

            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingLarge))

            PinInput(
                onValueChanged = onOtpChanged,
                length = 6,
                value = uiState.otp,
                isError = uiState.hasOtpError
            )

            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingLarge))

            val remainingTime = uiState.remainingTimeToResendOtp
            if (remainingTime == 0) {
                PartiallyClickableText(
                    simpleText = stringResource(id = R.string.didnt_receive_the_code),
                    clickableText = stringResource(id = R.string.resend),
                    onClick = onResendClick,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Text(
                    text = stringResource(id = R.string.resend_otp_in_format, remainingTime),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }


            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraLarge))

            FancyButton(
                onClick = onVerifyClick,
                text = stringResource(id = R.string.verify),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (uiState.isLoading) LoadingScreen()
}


@Composable
fun OtpCell(
    modifier: Modifier,
    value: String,
    isCursorVisible: Boolean = false,
    cursorColor: Color
) {
    val scope = rememberCoroutineScope()
    val (cursorSymbol, setCursorSymbol) = remember { mutableStateOf("") }

    LaunchedEffect(key1 = cursorSymbol, isCursorVisible) {
        if (isCursorVisible) {
            scope.launch {
                delay(400)
                setCursorSymbol(if (cursorSymbol.isEmpty()) "|" else "")
            }
        }
    }

    Box(modifier = modifier) {
        Text(
            text = if (isCursorVisible) cursorSymbol else value,
            style = AppTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Center),
            color = if (isCursorVisible) cursorColor else LocalContentColor.current
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PinInput(
    modifier: Modifier = Modifier,
    length: Int = 5,
    value: String = "",
    onValueChanged: (String) -> Unit,
    isError: Boolean
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= length) {
                if (it.all { c -> c in '0'..'9' }) {
                    onValueChanged(it)
                }
                if (it.length >= length) {
                    keyboard?.hide()
                }
            }
        },
        // Hide the text field
        modifier = Modifier
            .size(0.dp)
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        repeat(length) {
            OtpCell(
                modifier = modifier
                    .size(48.dp)
                    .aspectRatio(1f)
                    .clip(AppTheme.shapes.large)
                    .background(AppTheme.colors.surface)
                    .run {
                        if (isError) {
                            border(
                                width = 2.dp,
                                color = AppTheme.colors.error,
                                shape = AppTheme.shapes.large
                            )
                        } else this
                    }
                    .clickable {
                        focusRequester.requestFocus()
                        keyboard?.show()
                    },
                value = value.getOrNull(it)?.toString() ?: "",
                isCursorVisible = value.length == it,
                cursorColor = if (isError) AppTheme.colors.error else AppTheme.colors.primary
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun OtpScreenContentPreview() {
    CraterTheme {
        OtpScreenContent(
            phoneNumber = "7890123456",
            onBackClick = {},
            onOtpChanged = {},
            onVerifyClick = {},
            uiState = OtpUiState(hasOtpError = false),
            onResendClick = {}
        )
    }
}