package com.task.studymaneger.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.task.studymaneger.doman.model.Subject
import com.task.studymaneger.presentation.components.AddSubjectDialog
import com.task.studymaneger.presentation.components.CountCard
import com.task.studymaneger.presentation.components.DeleteDialog
import com.task.studymaneger.presentation.components.studySessionList
import com.task.studymaneger.presentation.components.tasksList
import com.task.studymaneger.session
import com.task.studymaneger.tasks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(){
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val isFABEexpended by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    var isAddSubjectDialog by rememberSaveable { mutableStateOf(false) }
    var subjectName by remember{ mutableStateOf("") }
    var goalHours by remember{ mutableStateOf("") }
    var selectedColor by remember{ mutableStateOf(Subject.subjectCartsColors.random()) }

    var isDeleteSessionDialog by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialog by rememberSaveable { mutableStateOf(false) }


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
        isOpen =isDeleteSubjectDialog,
        title = "Delete Subject?",
        bodyText = "Are you want to delete this subject? You studied hours will be reduced " +
                "by this session time. this action can not be unde " ,
        onDismissRequest = {isDeleteSubjectDialog = false},
        onConfirmButtonClick = {isDeleteSubjectDialog = false}
    )
    DeleteDialog(
        isOpen =isDeleteSessionDialog ,
        title = "Delete Session?",
        bodyText = "Are you want to delete this session? You studied hours will be reduced " +
                "by this session time. this action can not be unde " ,
        onDismissRequest = {isDeleteSessionDialog = false},
        onConfirmButtonClick = {isDeleteSessionDialog = false}
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                title = "English",
                onBackButtonClick = {},
                onDeleteButtonClick = {isDeleteSubjectDialog = true},
                onEditButtonClick = {isAddSubjectDialog = true},
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {},
                icon = { Icon(Icons.Default.Add, contentDescription = "") },
                text = { Text(text = "Add Task") },
                expanded = isFABEexpended
            )
        }
    ) {paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item{

                SubjectOverviewSection(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    studiedHours = "12",
                    goalHours = "18",
                    progress = .45f
                )
            }
            item{
                Spacer(modifier = Modifier.height(20.dp))
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                tasks = tasks,
                text = "You don't have any upcoming tasks.\n Click the + button in subject screen to add new subject",
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item{
                Spacer(modifier = Modifier.height(20.dp))
            }
            tasksList(
                sectionTitle = "Completed TASKS",
                tasks = tasks,
                text = "You don't complete tasks.",
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item{
                Spacer(modifier = Modifier.height(20.dp))
            }
            studySessionList(
                sectionTitle = "RECENT STUDY SESSION",
                sessions = session,
                onDeleteIconClick = {isDeleteSessionDialog = true}
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title:String,
    onBackButtonClick:()->Unit,
    onDeleteButtonClick:()->Unit,
    onEditButtonClick:()-> Unit,
    scrollBehavior: TopAppBarScrollBehavior
){

    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "navigation back"
                )
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )

        },
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Subject"
                )
            }
            IconButton(onClick = onEditButtonClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Subject"
                )
            }

        }
    )
}

@Composable
private fun SubjectOverviewSection(
    modifier: Modifier,
    studiedHours:String,
    goalHours:String,
    progress:Float
){
    val percentageProcess = remember(progress) { (progress*100).toInt().coerceIn(0,100)}

    Row(
        modifier= modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Study Hours",
            count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceVariant,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )
            Text(text = "$percentageProcess%")
        }
    }
}






















