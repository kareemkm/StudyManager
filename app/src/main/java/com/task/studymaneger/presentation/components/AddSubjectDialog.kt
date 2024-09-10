package com.task.studymaneger.presentation.components

import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.task.studymaneger.doman.model.Subject

@Composable
fun AddSubjectDialog(
    isOpen:Boolean,
    title:String,
    selectedColors:List<Color>,
    subjectName:String,
    goalHours: String,
    onColorChange:(List<Color>) -> Unit,
    onSubjectNameChange:(String) ->Unit,
    onGoalHorsChange:(String) -> Unit,
    onDismissRequest:()-> Unit,
    onConfirmButtonClick:()-> Unit
){
    var subjectNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var goalHoursError by rememberSaveable { mutableStateOf<String?>(null) }

    subjectNameError = when{
        subjectName.isBlank() -> "Please Enter Subject Name"
        subjectName.length < 2 -> "Subject Name is Too Short"
        subjectName.length > 20 -> "Subject Name is Too Long"
        else -> null
    }
    goalHoursError = when{
        goalHours.isBlank() -> "Please Enter Goal Study Hours"
        goalHours.toFloatOrNull() == null -> "Invalid Number"
        goalHours.toFloat() < 1f -> "Pleas set at least 1 hours"
        goalHours.toFloat() > 1000f -> "Please set a maximum of 1000 hours"
        else -> null
    }

    if (isOpen){
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = title
                )
            },
            text = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Subject.subjectCartsColors.forEach{colors ->
                            Box(modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 2.dp ,
                                    if (colors == selectedColors)Color.DarkGray else Color.Transparent,
                                    shape = CircleShape
                                )
                                .background(brush = Brush.verticalGradient(colors))
                                .clickable { onColorChange(colors) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = subjectName,
                        onValueChange = onSubjectNameChange,
                        label = { Text(text = "Subject Name") },
                        singleLine = true,
                        isError = subjectNameError!= null && subjectName.isNotBlank(),
                        supportingText = {Text(text = subjectNameError.orEmpty())}
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = goalHours,
                        onValueChange = onGoalHorsChange,
                        label = { Text(text = "Goal Study Hours") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = goalHoursError!= null && goalHours.isNotBlank(),
                        supportingText = {Text(text = goalHoursError.orEmpty())}
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                    enabled =subjectNameError == null && goalHoursError == null
                ) {
                    Text(text = "Save")
                }

            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest,
                ) {
                    Text(text = "Cancel")
                }

            }
        )
    }
}