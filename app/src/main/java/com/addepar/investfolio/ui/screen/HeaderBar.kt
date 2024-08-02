package com.addepar.investfolio.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBarWithDropDown(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    showDropdown: Boolean = true,
    onJsonSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onBackClick?.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (showDropdown) {
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(text = { Text("Normal") }, onClick = {
                            onJsonSelected("investments.json")
                            expanded = false
                        })
                        DropdownMenuItem(text = { Text("Empty") }, onClick = {
                            onJsonSelected("investments-empty.json")
                            expanded = false
                        })
                        DropdownMenuItem(text = { Text("Malformed") }, onClick = {
                            onJsonSelected("investments-malformed.json")
                            expanded = false
                        })
                        DropdownMenuItem(text = { Text("Error") }, onClick = {
                            onJsonSelected("investments-error.json")
                            expanded = false
                        })
                    }
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White,
            scrolledContainerColor = Color.White
        ),
    )
}

