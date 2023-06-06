package com.example.to_dolist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val scrollState = rememberLazyListState()
    var today by remember { mutableStateOf(true) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create") }, shape = CircleShape,) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { contentPadding ->
        Column {
            TodaySection(today) { today = !today }
            if (today) {
                TaskList(scrollState, contentPadding, navController)
            }
        }
    }
}

@Composable
fun TodaySection(expanded: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 32.dp)
    ) {
        Text(
            "Today",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Box(modifier = Modifier
                .graphicsLayer(rotationZ = if (expanded) 180f else 0f)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = if (expanded) "Expand less" else "Expand more"
                )
            }
        }
    }
}

@Composable
fun TaskList(
    scrollState: LazyListState,
    contentPadding: PaddingValues,
    navController: NavHostController
) {
    LazyColumn(
        state = scrollState,
        contentPadding = contentPadding
    ) {
        itemsIndexed(listTask) { index, item ->
            TaskItem(index, item, navController)
        }
    }
}

@Composable
fun TaskItem(index: Int, item: Task, navController: NavHostController) {
    var checkedState by rememberSaveable { mutableStateOf(false) }
    Box(modifier = Modifier
        .padding(bottom = 2.dp, start = 6.dp, end = 6.dp)
        .background(Color.DarkGray)
        .fillMaxWidth()
        .clickable {
            navController.navigate("info/$index")
        }){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { isChecked ->
                    checkedState = isChecked
                },
            )
            Text(
                text = item.title,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .widthIn(max = 255.dp),
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = if (checkedState) Color.Gray else Color.White,
                style = if (checkedState) TextStyle(textDecoration = TextDecoration.LineThrough)
                else TextStyle(textDecoration = TextDecoration.None),
            )
        }
    }
}

