package com.example.to_dolist

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(navController: NavHostController, state: TaskState) {
    val scrollState = rememberLazyListState()
    val today = remember { mutableStateOf(true) }

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
            TodaySection(today.value) { today.value = !today.value }
            if (today.value) {
                TaskList(scrollState, contentPadding, state = state)
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
fun TaskList(scrollState: LazyListState, contentPadding: PaddingValues, state: TaskState) {
    LazyColumn(
        state = scrollState,
        contentPadding = contentPadding
    ) {
        itemsIndexed(state.tasks) { _, item ->
            TaskItem(item)
        }
    }
}

@Composable
fun TaskItem(item: Task) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val checkedState = rememberSaveable { mutableStateOf(false) }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
        )
        Text(
            text = "${item.title} ${item.description} ${item.date}",
            modifier = Modifier
                .padding(start = 8.dp)
                .widthIn(max = 200.dp), // Set a maximum width for the text
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis, // Truncate the text when it's too long
            maxLines = 1, // Display only a single line of text
            style = if (checkedState.value) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle.Default,
            color = if (checkedState.value) Color.DarkGray else Color.White
        )
    }
    Divider(
        color = Color.Gray,
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 56.dp, top = 16.dp)
    )
}