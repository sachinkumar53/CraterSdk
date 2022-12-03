package com.crater.android.feature.authentication.ui.screen.register.filldetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.*
import com.crater.android.core.ui.icons.ArrowDropDown
import com.crater.android.core.ui.icons.Calendar
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.core.util.DateFormatterUtil
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonalDetailsScreenContent(
    uiState: PersonalDetailsUiState,
    uiActions: PersonalDetailsUiActions
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            StateListDialog(
                onItemClick = { uiActions.onStateChanged(it) },
                onCloseClick = { coroutineScope.launch { bottomSheetState.hide() } })
        },
        sheetBackgroundColor = AppTheme.colors.surfaceVariant,
        sheetState = bottomSheetState,
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        Scaffold(
            topBar = {
                CenteredTopAppBar(title = "", onBackClick = uiActions.onBackClick)
            },
            bottomBar = {
                FancyButton(
                    onClick = uiActions.onContinueClick,
                    text = stringResource(id = R.string.txt_continue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimensions.spacingMedium)
                )
            }
        ) { paddingValues ->
            PersonalDetailsForm(
                uiState = uiState,
                uiActions = uiActions,
                onStateClick = { coroutineScope.launch { bottomSheetState.show() } },
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(AppTheme.dimensions.spacingMedium)
            )
        }
    }
    if (uiState.isLoading) LoadingScreen()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PersonalDetailsForm(
    modifier: Modifier = Modifier,
    uiState: PersonalDetailsUiState,
    uiActions: PersonalDetailsUiActions,
    onStateClick: () -> Unit
) {
    var genderDropDownExpanded by remember { mutableStateOf(false) }
    val dialogState = rememberMaterialDialogState()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
    ) {
        Text(
            text = stringResource(id = R.string.enter_your_personal_details),
            style = AppTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = AppTheme.dimensions.spacingSmall)
        )

        FancyTextField(
            value = uiState.firstName,
            onValueChange = uiActions.onFirstNameChanged,
            placeholder = { Text(text = stringResource(id = R.string.first_name)) },
            isError = uiState.hasFirstNameError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        FancyTextField(
            value = uiState.lastName,
            onValueChange = uiActions.onLastNameChanged,
            placeholder = { Text(text = stringResource(id = R.string.last_name)) },
            isError = uiState.hasLastNameError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
        ) {

            ExposedDropdownMenuBox(
                expanded = genderDropDownExpanded,
                onExpandedChange = { genderDropDownExpanded = !genderDropDownExpanded },
                modifier = Modifier.weight(0.5f)
            ) {
                FancyReadOnlyTextField(
                    value = uiState.gender,
                    placeholder = stringResource(id = R.string.gender),
                    onClick = { },
                    trailingIcon = { Icon(CraterIcons.ArrowDropDown, null) },
                    isError = uiState.hasGenderError,
                    modifier = Modifier.wrapContentWidth(),

                    )
                ExposedDropdownMenu(
                    expanded = genderDropDownExpanded,
                    onDismissRequest = { genderDropDownExpanded = false }
                ) {
                    stringArrayResource(id = R.array.gender).forEach {
                        DropdownMenuItem(
                            onClick = {
                                uiActions.onGenderChanged(it)
                                genderDropDownExpanded = false
                            },
                            content = { Text(it) }
                        )
                    }
                }
            }

            FancyReadOnlyTextField(
                value = uiState.dob,
                placeholder = stringResource(id = R.string.date_of_birth),
                onClick = { dialogState.show() },
                trailingIcon = { Icon(CraterIcons.Calendar, null) },
                isError = uiState.hasDobError,
                modifier = Modifier.weight(0.5f)
            )
        }

        FancyTextField(
            value = uiState.email,
            onValueChange = uiActions.onEmailChanged,
            placeholder = { Text(text = stringResource(id = R.string.email)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = uiState.hasEmailError
        )

        FancyTextField(
            value = uiState.city,
            onValueChange = uiActions.onCityChanged,
            placeholder = { Text(text = stringResource(id = R.string.city)) },
            isError = uiState.hasCityError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        FancyReadOnlyTextField(
            value = uiState.state,
            placeholder = stringResource(id = R.string.state),
            onClick = onStateClick,
            trailingIcon = { Icon(CraterIcons.ArrowDropDown, null) },
            isError = uiState.hasStateError
        )


        FancyTextField(
            value = uiState.pinCode,
            onValueChange = uiActions.onPinCodeChanged,
            placeholder = { Text(text = stringResource(id = R.string.pin_code)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            isError = uiState.hasPinCodeError,
            keyboardActions = KeyboardActions(onDone = { uiActions.onContinueClick() })
        )

    }
    DatePickerDialog(
        dialogState = dialogState,
        onDateSelect = {
            uiActions.onDobChanged(DateFormatterUtil.formatInShortStyle(it))
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun PersonalDetailsScreenContentPreview() {
    CraterTheme {
        PersonalDetailsScreenContent(
            uiActions = PersonalDetailsUiActions(),
            uiState = PersonalDetailsUiState()
        )
    }
}