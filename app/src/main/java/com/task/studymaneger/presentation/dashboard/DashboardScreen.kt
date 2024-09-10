package com.task.studymaneger.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.task.studymaneger.R
import com.task.studymaneger.doman.model.Subject
import com.task.studymaneger.presentation.components.AddSubjectDialog
import com.task.studymaneger.presentation.components.CountCard
import com.task.studymaneger.presentation.components.DeleteDialog
import com.task.studymaneger.presentation.components.SubjectCart
import com.task.studymaneger.presentation.components.studySessionList
import com.task.studymaneger.presentation.components.tasksList
import com.task.studymaneger.session
import com.task.studymaneger.subject
import com.task.studymaneger.tasks

@Preview
@Composable
fun DashboardScreen(){

    var isAddSubjectDialog by rememberSaveable { mutableStateOf(false) }
    var subjectName by remember{ mutableStateOf("") }
    var goalHours by remember{ mutableStateOf("") }
    var selectedColor by remember{ mutableStateOf(Subject.subjectCartsColors.random()) }

    var isDeleteDialog by rememberSaveable { mutableStateOf(false) }


    AddSubjectDialog(
        isOpen = isAddSubjectDialog,
        subjectName = subjectName ,
        goalHours =goalHours ,
        onSubjectNameChange ={subjectName = it} ,
        onGoalHorsChange = {goalHours = it},
        selectedColors = selectedColor,
        onColorChange = {selectedColor = it},
        onDismissRequest ={isAddSubjectDialog = false } ,
        onConfirmButtonClick ={ isAddSubjectDialog = false },
        title = "Add/Update Subject"
    )
    DeleteDialog(
        isOpen =isDeleteDialog ,
        title = "Delete Session?",
        bodyText = "Are you want to delete this session? You studied hours will be reduced " +
                "by this session time. this action can not be unde " ,
        onDismissRequest = {isDeleteDialog = false},
        onConfirmButtonClick = {isDeleteDialog = false}
    )



    Scaffold (
        topBar = { TopBar() }
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardsSection(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    subjectCount = 5,
                    studiedHours = "5",
                    goalStudyHours = "15"
                )
            }
            item {
                SunjectCardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subject,
                    onAddIconClick = {isAddSubjectDialog = true}
                )
            }
            item{
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 48.dp)
                ) {
                    Text(
                        text = "Start Study Session",
                    )
                }
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                text = "You don't have any upcoming tasks.\n Click the + button in subject screen to add new subject",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item{
                Spacer(modifier = Modifier.height(20.dp))
            }
            studySessionList(
                sectionTitle = "RECENT STUDY SESSION",
                sessions = session,
                onDeleteIconClick = {isDeleteDialog = true}
            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Study Manager",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}

@Composable
private fun CountCardsSection(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalStudyHours : String


){
    Row(modifier= modifier) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count",
            count = subjectCount.toString()
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalStudyHours
        )
    }
}
@Composable
private fun SunjectCardsSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    onAddIconClick: ()->Unit
){
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick =onAddIconClick) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Subject"
                )
            }

        }
        if (subjectList.isEmpty()){
            Image(
                modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.img_books),
                contentDescription = "empty"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "You don't have any subject.\n Click the + button to add new subject",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList){subject ->
                SubjectCart(
                    modifier = Modifier,
                    subjectName = subject.name,
                    gradientColors = subject.colors,
                    onClick = {}
                )
            }
        }

    }
}

