package com.example.to_dolist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(navController: NavHostController, value: String) {
    val task by remember {
        mutableStateOf(listTask[value.toInt()])
    }
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    var date by remember { mutableStateOf(task.date.toString()) }
    var isTitleBlank by remember { mutableStateOf(false) }
    val dateDialogState = rememberMaterialDialogState()
    var isDateFieldFocused by remember { mutableStateOf(true) }

    Column {
        TopAppBar(
            title = { Text(text = "Edit task") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(red = 20, green = 20, blue = 20)),
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                Button(modifier = Modifier.padding(end = 14.dp),
                    onClick = {
                        if (title.isNotBlank()) {
                            listTask[value.toInt()] = Task(title, description, LocalDate.parse(date))
                            navController.navigate("home")
                        } else {
                            isTitleBlank = true
                        }
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text(text = "Save", color = Color.White)
                }
            }
        )
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { newValue ->
                    title = newValue
                    isTitleBlank = false
                },
                label = { Text(text = "Title") },
                modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
                trailingIcon = {
                    if (isTitleBlank) {
                        Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color.Red)
                    }
                }
            )
            description?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newValue -> description = newValue },
                    label = { Text(text = "Description") },
                    modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
                )
            }
            date?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newValue ->
                        date = newValue
                    },
                    label = { Text(text = "Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            dateDialogState.show()
                        },
                    enabled = false,
                    leadingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "DateRange")
                    },
                    trailingIcon = {
                        if (isDateFieldFocused)
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear",
                                modifier = Modifier.clickable {
                                    date = ""
                                    isDateFieldFocused = false
                                }
                            )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = if (isDateFieldFocused) Color.White else Color.DarkGray,
                        disabledBorderColor = if (isDateFieldFocused) Color.White else Color.DarkGray,
                        disabledLeadingIconColor = if (isDateFieldFocused) Color.White else Color.DarkGray,
                        disabledPlaceholderColor = if (isDateFieldFocused) Color.White else Color.DarkGray,
                        disabledLabelColor = if (isDateFieldFocused) Color.White else Color.DarkGray,
                    )
                )
            }
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "${LocalDate.now().year}",
        ) {
            isDateFieldFocused = true
            date = it.toString()
        }
    }
}