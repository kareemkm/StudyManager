package com.task.studymaneger.doman.model

data class Task(
    val title:String,
    val description: String,
    val dueDate:Long,
    val property:Int,
    val relatedToSubject:String,
    val isComplete: Boolean,
    val taskSubjectId:Int,
    val taskId: Int
)
