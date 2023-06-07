package com.example.to_dolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    allDateItems: List<Pair<String, MutableMap<Int, Task>>>,
    allUniqueDate: MutableSet<String>
) {
    val scrollState = rememberLazyListState()
    val expandedState = remember { mutableStateListOf<Boolean>().apply { repeat(allDateItems.size) { add(true) } } }
    var title by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var dividerColor by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth(), title = { } )
            LazyRow {
                allUniqueDate.forEach { item ->
                    item {
                        Box(modifier = Modifier.clickable { dividerColor = !dividerColor }) {
                            Column {
                                Text(text = item,
                                    fontWeight = FontWeight.Thin,
                                    modifier = Modifier.padding(12.dp)
                                )
                                Divider(
                                    thickness = 2.dp,
                                    color = if (dividerColor) Color(0xFFFE552F) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create") },
                shape = CircleShape,
            ) {
                Icon(Icons.Default.Add, contentDescription = "")
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
                                    listTask.add(Task(title, description = "", date = ""))
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
                                    showDateIcon = title != "No date"
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
fun DueDateTopBar(title: String, expanded: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 32.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(index: Int, item: Task, navController: NavHostController, showDateIcon: Boolean = true) {
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
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = item.title,
                    modifier = Modifier.widthIn(max = 255.dp),
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = if (checkedState) Color.Gray else Color.White,
                    style = if (checkedState) TextStyle(textDecoration = TextDecoration.LineThrough)
                    else TextStyle(textDecoration = TextDecoration.None),
                )
                if (showDateIcon) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "DateRange",
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = item.date!!,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

