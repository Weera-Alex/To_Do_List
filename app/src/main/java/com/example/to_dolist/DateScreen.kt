package com.example.to_dolist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ScrollDate(navController: NavHostController, allUniqueDate: MutableSet<String>) {
    var tabIndex by remember { mutableStateOf(0) }
    ScrollableTabRow(
        selectedTabIndex = tabIndex,
        edgePadding = 16.dp,
    ) {
        allUniqueDate.forEachIndexed { index, item ->
            Tab(
                selected = tabIndex == index,
                onClick = { tabIndex = index },
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                Text(
                    text = item,
                    fontWeight = FontWeight.Thin,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}