package com.crater.android.feature.expense.ui.screen.dashboard

import android.Manifest
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.screen.EmptyScreen
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.destinations.ExpenseListScreenDestination
import com.crater.android.feature.expense.domain.model.TransactionDetail
import com.crater.android.feature.expense.ui.component.AddExpenseFab
import com.crater.android.feature.expense.ui.component.ExpenseRow
import com.crater.android.feature.expense.ui.screen.dashboard.chart.LineChart
import com.crater.android.feature.expense.ui.screen.dashboard.chart.PieChartCard
import com.crater.android.feature.expense.ui.screen.expenses.ListHeader
import com.crater.android.feature.expense.ui.viewmodel.ExpenseSummaryViewModel
import com.crater.android.utils.FormatterUtil
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.marcelpibi.permissionktx.compose.rememberLauncherForPermissionsResult
import dev.marcelpinto.permissionktx.MultiplePermissionsLauncher
import dev.marcelpinto.permissionktx.Permission
import dev.marcelpinto.permissionktx.areGranted
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun ExpenseSummaryScreen(
    vm: ExpenseSummaryViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val isPieChart = rememberSaveable { mutableStateOf(true) }
    val categoryWiseExpenses by vm.getCategoryWiseExpense().collectAsState()
    val monthWiseExpenses by vm.getMonthWiseExpense().collectAsState()
    val dataPoints by vm.getDataPoints().collectAsState()
    val recentTransactions by vm.getRecentTransactions().collectAsState()
    val selectedMonth by vm.selectedYearMonth.collectAsState()
    val isLoading by vm.isLoading.collectAsState(initial = false)

    val readSms = Permission(Manifest.permission.READ_SMS)
    val receiveSms = Permission(Manifest.permission.RECEIVE_SMS)
    val permissionState = rememberLauncherForPermissionsResult(arrayOf(readSms, receiveSms)) {
        if (it[readSms] == true) vm.fetchExpenseFromDevice()
    }

    HandleReadSmsPermission(permissions = permissionState)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.expense_manager),
                onBackClick = navigator::navigateUp,
                menu = {
                    IconButton(
                        onClick = {
                            if (permissionState.types.areGranted()) {
                                vm.fetchExpenseFromDevice()
                            } else permissionState.launch()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sync),
                            contentDescription = stringResource(id = R.string.sync_sms)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AddExpenseFab(navigator = navigator)
        }
    ) { padding ->
        when {
            isLoading -> LoadingScreen()
            recentTransactions.isEmpty() -> EmptyScreen()
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Column(
                        modifier = Modifier
                            .background(AppTheme.colors.surface)
                            .fillMaxWidth()
                            .padding(AppTheme.dimensions.spacingSmall),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isPieChart.value) {
                            PieChartCard(
                                modifier = Modifier.height(300.dp),
                                categoryWiseExpenses = categoryWiseExpenses,
                                colors = vm.colors,
                                selectedMonth = selectedMonth,
                                onMonthChange = {
                                    vm.onMonthChanged(it)
                                }
                            )
                        } else {
                            LineChart(
                                modifier = Modifier.height(300.dp),
                                dataPoints = dataPoints,
                                popValueFormatter = { index, point ->
                                    val xVal =
                                        monthWiseExpenses[index].yearMonth.format(
                                            DateTimeFormatter.ofPattern(
                                                "MMM yyyy"
                                            )
                                        )
                                    val yVal = FormatterUtil.getFormattedNumber(point.y)
                                    yVal to xVal
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingSmall))
                        Divider(color = AppTheme.colors.divider)
                        ChartSelector(
                            isPieChart = isPieChart.value,
                            onClick = {
                                isPieChart.value = it
                            }
                        )
                    }

                    RecentTransactionsList(
                        recentTransactions = recentTransactions,
                        onSeeAllClick = {
                            navigator.navigate(ExpenseListScreenDestination)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ChartSelector(
    isPieChart: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row {
        IconButton(onClick = { onClick(true) }) {
            Icon(
                painter = painterResource(id = R.drawable.pie_chart),
                contentDescription = null,
                tint = if (isPieChart) AppTheme.colors.selected else AppTheme.colors.unselected
            )
        }

        IconButton(onClick = { onClick(false) }) {
            Icon(
                painter = painterResource(id = R.drawable.line_graph),
                contentDescription = null,
                tint = if (isPieChart) AppTheme.colors.unselected else AppTheme.colors.selected
            )
        }
    }
}

@Composable
fun RecentTransactionsList(
    recentTransactions: List<TransactionDetail>,
    onSeeAllClick: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(AppTheme.dimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingExtraSmall)
    ) {
        item {
            ListHeader(
                title = {
                    Text(text = stringResource(id = R.string.recent_transactions))
                },
                trailingContent = {
                    Text(
                        text = stringResource(id = R.string.see_all),
                        modifier = Modifier
                            .clickable(onClick = onSeeAllClick)
                            .padding(8.dp)
                    )
                }
            )
        }
        items(recentTransactions) {
            ExpenseRow(transaction = it)
        }
    }
}


@Composable
fun HandleReadSmsPermission(permissions: MultiplePermissionsLauncher) {
    var rationalDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!permissions.types.areGranted()) {
            permissions.safeLaunch(
                onRequireRational = {
                    rationalDialog = true
                }
            )
        }
    }

    if (rationalDialog) {
        AlertDialog(
            onDismissRequest = {
                rationalDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        rationalDialog = false
                        permissions.launch()
                    }
                ) {
                    Text(text = stringResource(id = R.string.allow_now))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        rationalDialog = false
                    }
                ) {
                    Text(text = stringResource(id = R.string.maybe_later))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.allow_sms_permission))
            },
            text = {
                Text(text = stringResource(id = R.string.allow_sms_permission_desc))
            },
            properties = DialogProperties(dismissOnBackPress = false)
        )
    }
}

