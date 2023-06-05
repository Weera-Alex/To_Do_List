package com.example.to_dolist

import androidx.compose.runtime.mutableStateListOf

data class Task(
    val title: String,
    val description: String?,
    val date: String?,
)

val listTask = mutableStateListOf<Task>()

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
        Task("Learn a new recipe", "Try out a new recipe and cook a delicious meal", "2023-06-28"),
        Task("Donate to charity", "Support a charitable cause by making a donation", "2023-06-29"),
        Task("Buy groceries", "Go to the supermarket and buy groceries for the week", "2023-06-05"),
        Task("Finish project", "Complete the remaining tasks for the project", "2023-06-06"),
        Task("Call friend", "Give your friend a call to catch up", "2023-06-07"),
        Task("Read book", "Spend some time reading a new book", "2023-06-08"),
        Task("Go for a walk", "Take a leisurely walk in the park", "2023-06-09"),
        Task("Write a blog post", "Share your thoughts and experiences in a blog post", "2023-06-10"),
        Task("Prepare for presentation", "Gather all the necessary materials for the upcoming presentation", "2023-06-11"),
        Task("Clean the house", "Dedicate some time to cleaning and organizing your living space", "2023-06-12"),
        Task("Exercise", "Engage in a workout or physical activity to stay fit", "2023-06-13"),
        Task("Plan weekend trip", "Research and plan for an upcoming weekend getaway", "2023-06-14"),
        Task("Attend meeting", "Attend an important meeting at work or school", "2023-06-15"),
        Task("Cook dinner", "Prepare a delicious meal for yourself or your loved ones", "2023-06-16"),
        Task("Learn a new skill", "Dedicate time to learn something new, like playing an instrument or coding", "2023-06-17"),
        Task("Watch a movie", "Relax and enjoy a movie or your favorite TV show", "2023-06-18"),
        Task("Volunteer for a cause", "Find a local organization and volunteer your time to help others", "2023-06-19")
    )

    listTask.addAll(tasks)
}
