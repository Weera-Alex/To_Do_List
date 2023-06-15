package com.example.to_dolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import java.util.Locale


@Preview
@Composable
private fun Preview() {
    TopBarContent()
}

@Composable
private fun TopBarContent() {
    val currentTime = getCurrentTime()
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentTime,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
                fontSize = 32.sp,
                lineHeight = 32.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Today's ${getDaysOfWeek().lowercase()
            .replaceFirstChar { 
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}")
        Text(
            text = formatCurrentDate(),
            color = Color.DarkGray
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    allDateItems: List<Pair<String, Map<Int, Task>>>
) {
    val scrollState = rememberLazyListState()
    val expandedState = remember { mutableStateListOf<Boolean>().apply { repeat(allDateItems.size) { add(true) } } }
    var title by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            LargeTopAppBar(modifier = Modifier.wrapContentWidth(), title = { } )
            TopBarContent()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create") },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
                TextField(
                    value = title,
                    onValueChange = { newValue -> title = newValue },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter Quick Task Here") },
                    trailingIcon = {
                        if (title.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    listTask.add(Task(title, description = "", date = null))
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Task added",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                    title = ""
                                    focusManager.clearFocus()
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                )

        }
    ) { contentPadding ->
        LazyColumn(
            state = scrollState,
            contentPadding = contentPadding
        ) {
            allDateItems.forEachIndexed { index, (title, items) ->
                item {
                    if (items.isNotEmpty()) {
                        DueDateTopBar(title, expandedState[index]) { expandedState[index] = !expandedState[index] }
                        if (expandedState[index]) {
                            items.forEach { (key, item) ->
                                TaskItem(
                                    index = key,
                                    item = item,
                                    navController = navController,
                                    showDateIcon = item.date != null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DueDateTopBar(title: String, expanded: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 32.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.headlineSmall,
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
private fun TaskItem(index: Int, item: Task, navController: NavHostController, showDateIcon: Boolean = true) {
    val complete = item.finished
    val color = if (complete) Color.Gray else Color.White
    val style = if (complete) TextStyle(textDecoration = TextDecoration.LineThrough)
    else TextStyle(textDecoration = TextDecoration.None)
    Box(modifier = Modifier
        .padding(bottom = 2.dp, start = 6.dp, end = 6.dp)
        .fillMaxWidth()
        .background(Color.DarkGray)
        .clickable {
            navController.navigate("info/$index")
        }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                onCheckedChange = {
                    listTask[index] = listTask[index].copy(finished = !item.finished)
                },
                checked = complete,
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = item.title,
                    modifier = Modifier.widthIn(max = 255.dp),
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = color,
                    style = style,
                )
                if (showDateIcon) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "DateRange",
                            modifier = Modifier.size(10.dp),
                            tint = color,
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = taskItemFormat(item.date),
                            fontSize = 12.sp,
                            color = color,
                        )
                    }
                }
            }
        }
    }
}

