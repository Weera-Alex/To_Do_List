package com.example.to_dolist

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val allDateItems = listOf(
        "Overdue" to allOverdueDate(),
        "Today" to allCurrentDate(),
        "Tomorrow" to allTomorrowDate(),
        "Later" to allLaterDate(),
        "No date" to allNoDate()
    )
    val allUniqueDate = allUniqueDate()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController, allDateItems)
        }
        composable("date") {
            ScrollDate(navController, allUniqueDate)
        }
        composable(route = "create") {
            AddNewTaskScreen(navController)
        }
        composable(route = "edit/{value}",
            arguments = listOf(navArgument("value") {type = NavType.StringType})
        ) { backStackEntry ->
            val value = backStackEntry.arguments?.getString("value") ?: ""
            EditTaskScreen(navController, value)
        }
        composable(route = "info/{value}",
        arguments = listOf(navArgument("value") {type = NavType.StringType})
        ) { backStackEntry ->
            val value = backStackEntry.arguments?.getString("value") ?: ""
            InfoScreen(navController, value)
        }
    }
}