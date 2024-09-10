package com.task.studymaneger.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.task.studymaneger.presentation.components.DeleteDialog
import com.task.studymaneger.presentation.components.TaskCheckBox
import com.task.studymaneger.presentation.components.TaskDatePicker
import com.task.studymaneger.presentation.theme.Red
import com.task.studymaneger.util.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(){

    var title by remember{ mutableStateOf("") }
    var direction by remember { mutableStateOf("") }

    var taskTitleError by remember { mutableStateOf<String?>(null) }
    var taskDescriptionError by remember { mutableStateOf<String?>(null) }

    taskTitleError = when{
        title.isBlank() -> "Please enter task title"
        title.length < 4 -> "Task title is too short"
        title.length > 30 -> "Task title is roo long"
        else -> null
    }

    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isTaskDateDialogOpen by rememberSaveable { mutableStateOf(false) }
    val isDatePickerState = rememberDatePickerState()

    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Delete Task",
        bodyText = "Are you want delete this task?" +
                "this action can not be undone.",
        onDismissRequest = {isDeleteDialogOpen = false},
        onConfirmButtonClick = {isDeleteDialogOpen = false}
    )
    TaskDatePicker(
        state =isDatePickerState,
        isOpen = isTaskDateDialogOpen,
        onDismissRequest = { isTaskDateDialogOpen = false},
        onConfirmRequest = { isTaskDateDialogOpen = false})

    Scaffold(
        topBar = {
            TaskScreenTopBar(
                isTaskExist = true,
                isComplete = false,
                checkBoxBorderColor = Red,
                onBackButtonClick = {},
                onDeleteButtonClick = {isDeleteDialogOpen = true},
                onChickBoxClick = {}

            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = {title = it},
                label = { Text(text = "Title") },
                singleLine = true,
                isError = taskTitleError!= null && title.isNotBlank(),
                supportingText = { Text(text = taskTitleError.orEmpty()) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = direction,
                onValueChange = {direction = it},
                label = { Text(text = "Description") },
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Due Date",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "5",
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = {isTaskDateDialogOpen = true}) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Priority",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Priority.entries.forEach{priority ->
                    PriorityButton(
                        modifier = Modifier.weight(1f),
                        label = priority.title,
                        backgroundColor =priority.color ,
                        borderColor = if (priority == Priority.LOW)Color.White else Color.Transparent,
                        labelColor = if (priority == Priority.LOW)Color.White else Color.White.copy(alpha = 0.7f),
                        onClick = { },
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Related to subject",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "English",
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            }
            Button(
                enabled = taskTitleError == null,
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                Text(
                    text = "Save"
                )
            }


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenTopBar(
    isTaskExist: Boolean,
    isComplete:Boolean,
    checkBoxBorderColor: Color,
    onBackButtonClick:()->Unit,
    onDeleteButtonClick:()->Unit,
    onChickBoxClick:()->Unit

    ) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        title = {
            Text(
                text = "Task",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            if(isTaskExist){
                TaskCheckBox(
                  isComplete = isComplete,
                    borderColor = checkBoxBorderColor,
                    onCheckBoxClick = onChickBoxClick
                )
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}
@Composable
private fun PriorityButton(
    modifier: Modifier,
    label:String,
    backgroundColor:Color,
    borderColor:Color,
    labelColor: Color,
    onClick:()->Unit
){
    Box(
        modifier = modifier
            .background(backgroundColor)
            .clickable {onClick()}
            .padding(5.dp)
            .border(1.dp,borderColor, RoundedCornerShape(5.dp))
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = label,
            color = labelColor
        )

    }

}




















