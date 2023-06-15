package com.example.to_dolist

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Task(
    val title: String,
    val description: String?,
    val date: LocalDate?,
    val finished: Boolean = false,
)

var listTask = mutableStateListOf<Task>()

fun taskItemFormat(date: LocalDate?): String {
    val dateFormatter = DateTimeFormatter.ofPattern("E, MMM d", Locale.ENGLISH)
    return date?.format(dateFormatter) ?: ""
}
fun formatCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    return currentDate.format(formatter)
}
fun getDaysOfWeek(): String {
    return LocalDate.now().dayOfWeek.toString()
}
fun getCurrentTime(): String {
    val currentTime = LocalTime.now()
    return when (currentTime.hour) {
        in 5 until 12 -> "Good\nMorning"
        in 12 until 18 -> "Good\nAfternoon"
        else -> "Good\nEvening"
    }
}

fun currentDate(): LocalDate? {
    return LocalDate.now()
}
fun tomorrowDate(daysAdded: Int): LocalDate? {
    val currentDate = LocalDate.now()
    return currentDate.plusDays(daysAdded.toLong())
}

fun allOverdueDate(): Map<Int, Task> {
    val today = LocalDate.now()
    return listTask.withIndex()
        .filter { (_, item) ->
            item.date != null
                    && item.date.isBefore(today) && !item.finished
        }
        .associate { (index, item) -> index to item}
}
fun allLaterDate(): Map<Int, Task> {
    val tomorrow = LocalDate.now().plusDays(1)
    return listTask.withIndex()
        .filter { (_, item) ->
            item.date != null
                    && item.date.isAfter(tomorrow) && !item.finished
        }
        .associate { (index, item) -> index to item}
}

fun allCurrentDate(): Map<Int, Task> {
    val currentDate = currentDate()
    return listTask.withIndex()
        .filter { (_, item) -> item.date == currentDate && !item.finished }
        .associate { (index, item) -> index to item}
}


fun allTomorrowDate(): Map<Int, Task> {
    val tomorrowDate = tomorrowDate(1)
    return listTask.withIndex()
        .filter { (_, item) -> item.date == tomorrowDate && !item.finished }
        .associate { (index, item) -> index to item}
}

fun allNoDate(): Map<Int, Task> {
    return listTask.withIndex()
        .filter { (_, item) -> item.date == null && !item.finished }
        .associate { (index, item) -> index to item }
}

fun allCompletedTask(): Map<Int, Task> {
    return listTask.withIndex()
        .filter { (_, item) -> item.finished }
        .associate { (index, item) -> index to item }
}

