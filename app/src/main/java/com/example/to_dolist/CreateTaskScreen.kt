package com.example.to_dolist

import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTaskScreen(navController: NavHostController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var isTitleNotEmpty by remember { mutableStateOf(false) }
    val dateDialogState = rememberMaterialDialogState()
    var isDateFieldFocused by remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            title = { Text(text = "Create task") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(red = 20, green = 20, blue = 20)),
            navigationIcon = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                Button(modifier = Modifier.padding(end = 14.dp),
                    onClick = {
                        if (title.isNotEmpty()) {
                            listTask.add(Task(title, description, date))
                            navController.navigateUp()
                        } else {
                            isTitleNotEmpty = true
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
                    isTitleNotEmpty = false
                                },
                label = { Text(text = "Title") },
                placeholder = { Text(text = "Enter title") },
                modifier = Modifier.padding(vertical = 4.dp),
                trailingIcon = {
                    if (isTitleNotEmpty) {
                        Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color.Red)
                    }
                }
            )
            OutlinedTextField(
                value = description,
                onValueChange = { newValue -> description = newValue },
                label = { Text(text = "Description") },
                placeholder = { Text(text = "Enter description") },
                modifier = Modifier.padding(vertical = 4.dp)
            )
            OutlinedTextField(
                value = date,
                onValueChange = { newValue ->
                    date = newValue
                },
                label = { Text(text = "Date") },
                placeholder = { Text(text = "Enter date") },
                modifier = Modifier
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
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {

            }
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