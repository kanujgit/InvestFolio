package com.addepar.investfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.addepar.investfolio.ui.screen.InvestmentApp
import com.addepar.investfolio.ui.theme.InvestFolioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InvestFolioTheme {
                setContent {
                    InvestmentApp()
                }
            }
        }
    }
}
