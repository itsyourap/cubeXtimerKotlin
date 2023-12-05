package com.example.dark_x_timer_kotlin.ui.counter.saveDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dark_x_timer_kotlin.ui.AppViewModelProvider
import com.example.dark_x_timer_kotlin.ui.theme.PastelRed

@Composable
fun SaveDialog(
    modifier: Modifier = Modifier,
    time: String = "00:00:00",
    onDismiss: () -> Unit = {},
    viewModel: SaveViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
) {
    val state by viewModel.uiState.collectAsState()

    viewModel.setTime(time)


    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Save",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                Row {
                    Text(
                        text = "Time: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = state.time,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        text = "Cube Type",
                        modifier = Modifier.align(
                            CenterVertically
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box {
                        OutlinedButton(
                            onClick = viewModel::toggleDropdown,
                        ) {
                            Text(text = state.cubeType.name)
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Expand",
                            )
                        }
                        DropdownMenu(
                            expanded = state.isDropdownExpanded,
                            onDismissRequest = viewModel::toggleDropdown
                        ) {
                            viewModel.cubeTypes.forEach { cubeType ->
                                TextButton(onClick = {
                                    viewModel.onCubeTypeChanged(cubeType)
                                    viewModel.toggleDropdown()
                                }) {
                                    Text(text = cubeType)
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = state.name,
                    label = { Text("Name") },
                    singleLine = true,
                    onValueChange = viewModel::onNameChanged,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = {
                    viewModel.save()
                    onDismiss()
                }) {
                    Text(text = "OK")
                }
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel", color = PastelRed)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaveDialogPreview() {
    SaveDialog()
}

