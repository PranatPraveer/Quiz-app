package com.example.quizapp.models

data class Questions (
        var description: String = "",
        var option1: String="",
        var option2: String="",
        var option3: String="",
        var option4: String="",
        var Answer: String="",
        var UserAnswer: String=""
)