package com.addepar.investfolio.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.addepar.investfolio.data.modal.Investment
import com.addepar.investfolio.viewmodel.InvestmentIntent
import com.addepar.investfolio.viewmodel.InvestmentState
import com.addepar.investfolio.viewmodel.InvestmentViewModel


@Composable
fun InvestmentApp(
    viewModel: InvestmentViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route


    Scaffold(
        topBar = {
            HeaderBarWithDropDown(
                title = if (currentRoute == "detail") "Investment Details" else "Investment Portfolio",
                showBackButton = currentRoute == "detail",
                onBackClick = { navController.popBackStack() },
                showDropdown = currentRoute != "detail",
                onJsonSelected = { jsonFile ->
                    viewModel.sendIntent(InvestmentIntent.LoadInvestments(jsonFile))
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Navigation
            NavHost(navController = navController, startDestination = "list") {
                composable("list") {
                    InvestmentListScreen(
                        state = state,
                        onItemClick = { investment ->
                            viewModel.sendIntent(InvestmentIntent.SelectInvestment(investment))
                            navController.navigate("detail")
                        }
                    )
                }
                composable("detail") {
                    state.selectedInvestment?.let { investment ->
                        InvestmentDetailScreen(
                            navController = navController,
                            investment = investment
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(InvestmentIntent.LoadInvestments("investments.json"))
    }
}


@Composable
fun InvestmentListScreen(
    state: InvestmentState,
    onItemClick: (Investment) -> Unit
) {
    when {
        state.isLoading -> LoadingScreen()
        state.error != null -> ErrorScreen(state.error)
        state.investments.isEmpty() -> EmptyScreen()
        else -> DataScreen(state.investments, onItemClick)
    }


}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(error: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "An unexpected error occurred.",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Red
        )
    }
}

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No investments available.",
            style = MaterialTheme.typography.titleLarge
        )
    }
}


@Composable
fun DataScreen(data: List<Investment>, onItemClick: (Investment) -> Unit) {
    var sortedData by remember { mutableStateOf(data.sortedByDescending { it.value.toLongOrNull() }) }
    var ascendingOrder by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("Sort by Value Desc") }


    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                ascendingOrder = !ascendingOrder
                buttonText = if (ascendingOrder) "Sort by Value Asc" else "Sort by Value Desc"
                sortedData = if (ascendingOrder) {
                    sortedData.sortedBy { it.value.toLongOrNull() }
                } else {
                    sortedData.sortedByDescending { it.value.toLongOrNull() }
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(buttonText)
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sortedData) { investment ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onItemClick(investment) },
                    elevation = CardDefaults.elevatedCardElevation(6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = investment.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 18.sp
                            )
                            investment.ticker?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                        Text(
                            text = "$${formatValue(investment.value)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Icon(
                            imageVector = if ((investment.value.toLongOrNull()
                                    ?: 0) > (investment.principal.toLongOrNull() ?: 0)
                            )
                                Icons.Default.ArrowUpward
                            else
                                Icons.Default.ArrowDownward,
                            contentDescription = null,
                            tint = if ((investment.value.toLongOrNull()
                                    ?: 0) > (investment.principal.toLongOrNull() ?: 0)
                            )
                                Color.Green
                            else
                                Color.Red
                        )
                    }
                }
            }
        }
    }
}

fun formatValue(value: String): String {
    return try {
        val num = value.toLong()
        when {
            num >= 1_000_000_000 -> "${num / 1_000_000_000}B"
            num >= 1_000_000 -> "${num / 1_000_000}M"
            num >= 1_000 -> "${num / 1_000}K"
            else -> num.toString()
        }
    } catch (e: NumberFormatException) {
        value
    }
}


