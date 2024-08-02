package com.addepar.investfolio.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.addepar.investfolio.data.modal.Investment

@Composable
fun InvestmentDetailScreen(navController: NavController,investment: Investment) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Name: ${investment.name}",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        investment.ticker?.let {
            Text(
                text = "Ticker: $it",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Text(
            text = "Value: ${formatValue(investment.value)}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = "Principal: ${formatValue(investment.principal)}",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
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
        Text(
            text = "Details: ${investment.details}",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}


