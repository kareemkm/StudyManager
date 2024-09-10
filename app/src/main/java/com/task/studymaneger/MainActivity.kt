package com.task.studymaneger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import com.task.studymaneger.doman.model.Session
import com.task.studymaneger.doman.model.Subject
import com.task.studymaneger.doman.model.Task
import com.task.studymaneger.presentation.dashboard.DashboardScreen
import com.task.studymaneger.presentation.subject.SubjectScreen
import com.task.studymaneger.presentation.task.TaskScreen
import com.task.studymaneger.presentation.theme.StudyManegerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyManegerTheme {
                TaskScreen()

            }
        }
    }
}
val subject = listOf(
    Subject("English" , 10f , Subject.subjectCartsColors[0],0),
    Subject("Arabic" , 20f , Subject.subjectCartsColors[1],0),
    Subject("Math" , 15f , Subject.subjectCartsColors[2],0),
    Subject("History" , 50f , Subject.subjectCartsColors[3],0),
    Subject("French" , 10f , Subject.subjectCartsColors[4],0)
)
val tasks = listOf(
    Task("kareem","",0L,0,"",  true,0,0),
    Task("kareem","",0L,1,"",  false,0,0),
    Task("kareem","",0L,2,"",  true,0,0)
)
val session = listOf(
    Session(1,"English",0L,2,1),
    Session(1,"Arabic",0L,2,1),
    Session(1,"Math",0L,2,1),
    Session(1,"History",0L,2,1),
    Session(1,"French",0L,2,1),
)