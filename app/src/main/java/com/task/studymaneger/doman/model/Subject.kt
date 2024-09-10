package com.task.studymaneger.doman.model


import androidx.compose.ui.graphics.Color
import com.task.studymaneger.presentation.theme.gradient1
import com.task.studymaneger.presentation.theme.gradient2
import com.task.studymaneger.presentation.theme.gradient3
import com.task.studymaneger.presentation.theme.gradient4
import com.task.studymaneger.presentation.theme.gradient5

data class Subject(
    val name : String,
    val goalHours: Float,
    val colors: List<Color>,
    val subjectId: Int
){
    companion object{
        val subjectCartsColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
