package com.example.examples.persistence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examples.ui.theme.ExamplesTheme
import com.example.examples.ui.theme.Red
import com.example.examples.ui.theme.Yellow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersistenceActivity : ComponentActivity() {

    private lateinit var persistenceViewModel: PersistenceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExamplesTheme {

                persistenceViewModel = hiltViewModel()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Todo(persistenceViewModel = persistenceViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskField(
    persistenceViewModel: PersistenceViewModel
) {

    val selectedPriority = persistenceViewModel.priority
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxWidth()
            .pointerInput(Unit){
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = persistenceViewModel.text,
            onValueChange = { persistenceViewModel.handleTextChange(it) },
            label = {
                Text(text = "Task")
            },
            placeholder = {
                Text(
                    text = "Your next task here",
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray
                )
            },
            minLines = 3,
            maxLines = 5,
            keyboardOptions = KeyboardOptions.Default.copy(),
            keyboardActions = KeyboardActions.Default
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Priority : ")
            SingleChoiceSegmentedButtonRow() {
                for (i in 0..2) {
                    val currentPriority = Priority.values()[i]
                    SegmentedButton(
                        modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                        selected = currentPriority == selectedPriority,
                        onClick = { persistenceViewModel.handlePriorityChange(currentPriority) },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = i,
                            count = 3
                        ),
                        colors = SegmentedButtonDefaults.colors().copy(
                            activeContainerColor = if (i == 0) Color.LightGray else if (i == 1) Yellow else Red,
                            activeContentColor = if (i == 0) Color.Black else if (i == 1) Color.Black else Color.White,
                        )
                    ) {
                        Text(
                            text = currentPriority.toString(),
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .wrapContentSize(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                focusManager.clearFocus()
                persistenceViewModel.handleSaveTask()
            }
        ) {
            Text(
                text = "Save Task",
                fontSize = 12.sp
            )
        }

    }
}

@Composable
fun CustomBadge(
    text: String,
    backgroundColor: Color,
    textColor: Color = Color.White,
    borderRadius: Float = 8.dp.value, // Default border radius
    padding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
) {
    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(borderRadius))
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor)
    }
}

@Composable
fun TaskCard(
    persistenceViewModel: PersistenceViewModel,
    task: Task
) {
    Surface(
        color = when (task.priority) {
            Priority.LOW -> Color.LightGray
            Priority.MEDIUM -> Yellow
            else -> Red
        },
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()

    ) {

        Column(
            horizontalAlignment = Alignment.Start
        ) {

            if(task.isCompleted){
                CustomBadge(
                    text = "Completed",
                    backgroundColor = Color(0xFF90D26D),
                    textColor = Color.Black,
                    borderRadius = 8.dp.value, // Custom border radius
                    padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp) // Custom padding
                )
            }

            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = task.text,
                    color = when (task.priority) {
                        Priority.LOW -> Color.Black
                        Priority.MEDIUM -> Color.Black
                        else -> Color.White
                    }
                )

                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { persistenceViewModel.handleCompletedCheckbox(task, it) }
                )

                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { persistenceViewModel.handleDeleteTask(task) }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }

    }
}

@Composable
fun TaskList(
    persistenceViewModel: PersistenceViewModel
) {
    val tasks = persistenceViewModel.tasksState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(tasks.value) { task ->
            TaskCard(persistenceViewModel = persistenceViewModel, task = task)
        }
    }
}

@Composable
fun Todo(
    persistenceViewModel: PersistenceViewModel
) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TaskField(persistenceViewModel)

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )

        TaskList(persistenceViewModel)
    }
}