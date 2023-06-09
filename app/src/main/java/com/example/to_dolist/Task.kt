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
)

var listTask = mutableStateListOf<Task>()

fun taskItemFormat(date: LocalDate?): String {
    val dateFormatter = DateTimeFormatter.ofPattern("E, MMM d", Locale.ENGLISH)
    return date?.format(dateFormatter) ?: ""
}

fun getCurrentTime(): String {
    val currentTime = LocalTime.now()
    return when (currentTime.hour) {
        in 5 until 12 -> "Good\nmorning"
        in 12 until 18 -> "Good\nafternoon"
        else -> "Good\nevening"
    }
}

fun currentDate(): LocalDate? {
    return LocalDate.now()
}
fun tomorrowDate(daysAdded: Int): LocalDate? {
    val currentDate = LocalDate.now()
    return currentDate.plusDays(daysAdded.toLong())
}
fun allUniqueDate(): MutableSet<String> {
    val taskUniqueDate = mutableSetOf<String>()
    for (item in listTask) {
        if (item.date == null) {
            continue
        } else {
            val dateFormatter = DateTimeFormatter.ofPattern("d EEEE")
            taskUniqueDate.add(item.date.format(dateFormatter).replace(" ", "\n"))
        }
    }
    return taskUniqueDate
}

fun allOverdueDate(): Map<Int, Task> {
    val today = LocalDate.now()
    return listTask.withIndex()
        .filter { (_, item) ->
            item.date != null
                    && item.date.isBefore(today)
        }
        .associate { (index, item) -> index to item}
}
fun allLaterDate(): Map<Int, Task> {
    val tomorrow = LocalDate.now().plusDays(1)
    return listTask.withIndex()
        .filter { (_, item) ->
            item.date != null
                    && item.date.isAfter(tomorrow)
        }
        .associate { (index, item) -> index to item}
}

fun allCurrentDate(): Map<Int, Task> {
    val currentDate = currentDate()
    return listTask.withIndex()
        .filter { (_, item) -> item.date == currentDate }
        .associate { (index, item) -> index to item}
}


fun allTomorrowDate(): Map<Int, Task> {
    val tomorrowDate = tomorrowDate(1)
    return listTask.withIndex()
        .filter { (_, item) -> item.date == tomorrowDate }
        .associate { (index, item) -> index to item}
}

fun allNoDate(): Map<Int, Task> {
    return listTask.withIndex()
        .filter { (_, item) -> item.date == null }
        .associate { (index, item) -> index to item }
}

fun createSampleTasks() {
    val tasks = listOf(
        Task("Defeat the final boss",
            "Embark on an epic quest to defeat the final boss and save the virtual world.",
            LocalDate.of(2023, 6, 10)
        ),
        Task("Update resume",
            null,
            LocalDate.of(2023, 6, 11)
        ),
        Task("Practice meditation",
            "Take some time to practice mindfulness and meditation",
            null
        ),
        Task("Search for the lost treasure",
            null,
            LocalDate.of(2023, 6, 12)
        ),
        Task("Conquer the multiplayer battlefield",
            "Assemble your team, strategize, and dominate the online battlefield with your superior gaming skills.",
            LocalDate.of(2023, 6, 13)
        ),
        Task("Train with the elite gamers",
            null,
            LocalDate.of(2023, 6, 14)
        ),
        Task("Unlock the secret cheat codes",
            "Embark on a quest to uncover the legendary cheat codes that grant unimaginable powers in the virtual world.",
            null
        ),
        Task("Rescue the princess",
            null,
            null
        ),
        Task("Go hiking",
            "Explore a nearby hiking trail and enjoy the nature",
            LocalDate.of(2023, 6, 10)
        ),
        Task("Update resume",
            "Review and update your resume for future opportunities",
            LocalDate.of(2023, 6, 11)
        ),
        Task("Practice meditation",
            "Take some time to practice mindfulness and meditation",
            LocalDate.of(2023, 6, 9)
        ))
    listTask.addAll(tasks)
}
