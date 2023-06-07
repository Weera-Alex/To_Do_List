package com.example.to_dolist

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Task(
    val title: String,
    val description: String?,
    val date: String?,
)

var listTask = mutableListOf<Task>()
fun sortTask() {
    val formatter = SimpleDateFormat("E, MMM d", Locale.ENGLISH)
    listTask = listTask.sortedBy { it.date }.map { task ->
        val date = if (!task.date.isNullOrEmpty()) {
            task.date.let { SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(it) }
        } else {
            null
        }
        val formattedDate = date?.let { formatter.format(it) } ?: ""
        task.copy(date = formattedDate)
    }.toMutableList()
}



fun currentDate(): String? {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("E, MMM d")
    return currentDate.format(formatter)
}
fun tomorrowDate(daysAdded: Int): String? {
    val currentDate = LocalDate.now()
    val tomorrow = currentDate.plusDays(daysAdded.toLong())
    val formatter = DateTimeFormatter.ofPattern("E, MMM d")
    return tomorrow.format(formatter)
}

fun allOverdueDate(): MutableMap<Int, Task> {
    val taskOverdue = mutableMapOf<Int, Task>()
    val currentDate = currentDate()
    for ((index, item) in listTask.withIndex()) {
        if (item.date == "") {
            continue
        }
        if (item.date != currentDate) {
            taskOverdue[index] = item
        } else {
            break
        }
    }
    return taskOverdue
}
fun allCurrentDate(): MutableMap<Int, Task> {
    val taskToday = mutableMapOf<Int, Task>()
    val currentDate = currentDate()
    val tomorrowDate = tomorrowDate(1)
    for ((index, item) in listTask.withIndex()) {
        if (item.date == "") {
            continue
        }
        if (item.date == currentDate) {
            taskToday[index] = item
        }
        if (item.date == tomorrowDate) {
            break
        }
    }
    return taskToday
}
fun allLaterDate(): MutableMap<Int, Task> {
    val taskLater = mutableMapOf<Int, Task>()
    val tomorrowDate = tomorrowDate(1)
    var turn = false
    for ((index, item) in listTask.withIndex()) {
        if (item.date == "") {
            continue
        }
        if (item.date == tomorrowDate) {
            turn = true
            continue
        }
        if (turn) {
            taskLater[index] = item
        }
    }
    return taskLater
}
fun allTomorrowDate(): MutableMap<Int, Task> {
    val taskTomorrow = mutableMapOf<Int, Task>()
    val tomorrowDate = tomorrowDate(1)
    for ((index, item) in listTask.withIndex()) {
        if (item.date == "") {
            continue
        }
        if (item.date == tomorrowDate) {
            taskTomorrow[index] = item
        }
        if (item.date == tomorrowDate(2)) {
            break
        }
    }
    return taskTomorrow
}

fun allNoDate(): MutableMap<Int, Task> {
    val taskNoDate = mutableMapOf<Int, Task>()
    for ((index, item) in listTask.withIndex()) {
        if (item.date == "") {
            taskNoDate[index] = item
        }
    }
    return taskNoDate
}

fun createSampleTasks() {
    val tasks = listOf(
        Task("Go hiking", "Explore a nearby hiking trail and enjoy the nature", "2023-06-20"),
        Task("Update resume", "Review and update your resume for future opportunities", "2023-06-21"),
        Task("Practice meditation", "Take some time to practice mindfulness and meditation", "2023-06-22"),
        Task("Organize digital files", "Sort and organize your computer files and folders", "2023-06-23"),
        Task("Visit a museum", "Visit a local museum or art gallery to appreciate the exhibits", "2023-06-24"),
        Task("Start a journal", "Begin a journaling practice to reflect on your thoughts and experiences", "2023-06-25"),
        Task("Have a picnic", "Enjoy a picnic in a nearby park or scenic spot", "2023-06-26"),
        Task("Take a photography walk", "Go for a walk with your camera and capture interesting photos", "2023-06-27"),
        Task("Try a new recipe", "Pick a recipe you've been wanting to try and challenge yourself in the kitchen. Enjoy the process of cooking and savor the delicious outcome.", "2023-06-06"),
        Task("Read a book", "Choose a book from your reading list and spend some time immersed in its pages.", "2023-06-06"),
        Task("Take a yoga class", "Find a local yoga studio or follow an online yoga tutorial to stretch your body and calm your mind.", "2023-06-06"),
        Task("Go for a bike ride", "Hop on your bicycle and explore your neighborhood or find a scenic cycling route nearby.", "2023-06-07"),
        Task("Write in a journal", "Spend some time reflecting on your day and write down your thoughts and feelings in a journal.", "2023-06-07"),
        Task("Try a new hobby", "Pick up a new hobby or revisit an old one that you enjoy, such as painting, knitting, or playing a musical instrument.", "2023-06-07"),
        Task("Have a picnic", "Pack a delicious lunch or snacks, find a picturesque spot outdoors, and enjoy a relaxing picnic.", "2023-06-08"),
        Task("Take a scenic drive", "Explore the countryside or coastal areas by taking a leisurely drive and appreciating the natural beauty around you.", "2023-06-08"),
        Task("Visit a local museum", "Discover the art, history, or culture of your city by visiting a nearby museum or art gallery.", "2023-06-08"),
        Task("Task with empty values", "", ""),)
    listTask.addAll(tasks)
    sortTask()
}
