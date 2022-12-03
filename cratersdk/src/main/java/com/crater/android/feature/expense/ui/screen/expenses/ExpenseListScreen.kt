package com.crater.android.feature.expense.ui.screen.expenses

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.data.Resource
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.CraterIcons
import com.crater.android.core.ui.icons.Filter
import com.crater.android.core.ui.screen.EmptyScreen
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.feature.expense.domain.model.TransactionDetail
import com.crater.android.feature.expense.ui.component.AddExpenseFab
import com.crater.android.feature.expense.ui.component.ExpenseRow
import com.crater.android.feature.expense.ui.component.FilterDialog
import com.crater.android.feature.expense.ui.viewmodel.ExpenseViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Destination
@Composable
fun ExpenseListScreen(
    viewModel: ExpenseViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val result by viewModel.transactions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val filterDialogVisible = rememberSaveable { mutableStateOf(false) }
    val isResultFiltered = viewModel.isResultFiltered.collectAsState(initial = false)

    BackHandler {
        if (isResultFiltered.value) {
            viewModel.clearFilter()
        } else {
            navigator.navigateUp()
        }
    }

    Scaffold(
        topBar = {
            ExpenseToAppBar(
                title = if (isResultFiltered.value) stringResource(R.string.filtered_expenses)
                else stringResource(R.string.expense_manager),
                onBackClick = {
                    if (isResultFiltered.value) {
                        viewModel.clearFilter()
                    } else {
                        navigator.navigateUp()
                    }
                },
                onFilterClick = {
                    filterDialogVisible.value = !filterDialogVisible.value
                }
            )
        },
        floatingActionButton = {
            AddExpenseFab(navigator = navigator)
        },
    ) { padding ->
        when (val resource = result) {
            is Resource.Success -> TransactionList(padding, transactions = resource.data)
            is Resource.Error -> EmptyScreen()
            is Resource.Loading -> Unit
        }

        if (isLoading) {
            LoadingScreen()
        }

        if (filterDialogVisible.value) {
            FilterDialog(
                viewModel = viewModel,
                onDismissRequest = {
                    filterDialogVisible.value = filterDialogVisible.value.not()
                }
            )
        }
    }
}

@Composable
fun TransactionList(
    padding: PaddingValues,
    transactions: Map<YearMonth, List<TransactionDetail>>
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        transactions.forEach { (date, list) ->
            item {
                ListHeader(
                    title = {
                        Text(
                            text = "${
                                date.month.getDisplayName(
                                    TextStyle.FULL,
                                    Locale.getDefault()
                                )
                            } ${date.year}"
                        )
                    }
                )
            }

            items(list) {
                ExpenseRow(transaction = it)
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ExpenseToAppBar(
    title: String,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    CenteredTopAppBar(
        title = title,
        onBackClick = onBackClick,
        menu = {
            IconButton(onClick = onFilterClick) {
                Icon(imageVector = CraterIcons.Filter, contentDescription = null)
            }
        }
    )
}


@Composable
fun ListHeader(
    modifier: Modifier = Modifier,
    title: @Composable RowScope.() -> Unit,
    trailingContent: (@Composable RowScope.() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val mainStyle = LocalTextStyle.current.copy(
            color = colorResource(id = R.color.white_text),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        CompositionLocalProvider(LocalTextStyle provides mainStyle) {
            title()
        }

        if (trailingContent != null) {
            val trailingStyle = LocalTextStyle.current.copy(
                color = colorResource(id = R.color.gray_text),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            CompositionLocalProvider(LocalTextStyle provides trailingStyle) {
                trailingContent()
            }
        }
    }
}
