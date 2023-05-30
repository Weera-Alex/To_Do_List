package com.example.to_dolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.to_dolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {
    val appDatabase: TaskDatabase by lazy {
        Room.databaseBuilder(applicationContext, TaskDatabase::class.java, TaskDatabase.NAME)
            .build()
    }
    private val viewModel by viewModels<TaskViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskViewModel(appDatabase.dao) as T

            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    App(state, onEvent = viewModel::onEvent)
                }
            }
        }
    }
}

@Composable
fun App(state: TaskState, onEvent: (TaskEvent) -> Unit) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            TaskScreen(navController, state = state)
        }
        composable("create") {
            Screen(navController, state = state, onEvent)
        }
    }
}




