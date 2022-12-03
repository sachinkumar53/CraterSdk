package com.crater.android.feature.authentication.ui.screen.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyTextField
import com.crater.android.core.ui.PhoneTextField
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.authentication.ui.common.PartiallyClickableText

@Composable
fun RegisterScreenContent(
    onTermsAndConditionClick: () -> Unit,
    onContinueClick: () -> Unit,
    onLoginClick: () -> Unit,
    onTermsAndConditionCheckChange: (Boolean) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onInviteCodeChanged: (String) -> Unit,
    uiState: RegisterUiState
) {
    Scaffold {
        Column(modifier = Modifier.padding(AppTheme.dimensions.spacingMedium)) {

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = stringResource(id = R.string.create_an_account),
                style = AppTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingLarge))
            PhoneTextField(
                value = uiState.phoneNumber,
                onValueChange = onPhoneNumberChanged,
                isError = uiState.hasPhoneNumberError
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingSmall))
            FancyTextField(
                value = uiState.inviteCode,
                onValueChange = onInviteCodeChanged,
                placeholder = { Text(text = stringResource(id = R.string.invite_code_if_any)) }
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingSmall))
            TermsAndConditionCheckBox(
                onCheckChange = onTermsAndConditionCheckChange,
                onTermsAndConditionClick = onTermsAndConditionClick,
                checked = uiState.isTermsAndConditionChecked,
                isError = uiState.hasTermsAndConditionError
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraLarge))
            FancyButton(
                onClick = onContinueClick,
                text = stringResource(id = R.string.txt_continue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimensions.spacingSmall)
            )

            Spacer(modifier = Modifier.weight(1f))

            PartiallyClickableText(
                simpleText = stringResource(id = R.string.already_have_an_account),
                clickableText = stringResource(id = R.string.login),
                onClick = onLoginClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = AppTheme.dimensions.spacingExtraLarge)
            )

        }
    }

    if (uiState.isLoading) LoadingScreen()
}

@Composable
fun TermsAndConditionCheckBox(
    onCheckChange: (Boolean) -> Unit,
    onTermsAndConditionClick: () -> Unit,
    checked: Boolean,
    isError: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckChange,
            colors = CheckboxDefaults.colors(
                uncheckedColor = if (isError) {
                    AppTheme.colors.error
                } else {
                    AppTheme.colors.textSecondary
                }
            )
        )

        val annotatedText = buildAnnotatedString {
            withStyle(SpanStyle(color = AppTheme.colors.textPrimary)) {
                append(stringResource(id = R.string.i_accept_the))
            }
            append(" ")
            pushStringAnnotation(tag = "terms", annotation = "terms")
            withStyle(SpanStyle(color = AppTheme.colors.primary)) {
                append(stringResource(id = R.string.terms_and_conditions).lowercase())
            }
            pop()
        }

        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "terms", start = offset, end = offset
                ).firstOrNull()?.let { onTermsAndConditionClick() }
            },
            style = AppTheme.typography.labelLarge,
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun RegisterScreenContentPreview() {
    CraterTheme {
        RegisterScreenContent(
            onContinueClick = {},
            onInviteCodeChanged = {},
            onLoginClick = {},
            onPhoneNumberChanged = {},
            onTermsAndConditionCheckChange = {},
            onTermsAndConditionClick = {},
            uiState = RegisterUiState()
        )
    }
}