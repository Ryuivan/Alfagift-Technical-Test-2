package com.example.technicaltest2.model

/**
 * Data class representing a student.
 */
data class Student(
    var id: String = "",               // Unique ID for the student
    var name: String = "",             // Name of the student
    var address: String = "",          // Address of the student
    var profilePicture: String = ""    // URL to the student's profile picture
)
