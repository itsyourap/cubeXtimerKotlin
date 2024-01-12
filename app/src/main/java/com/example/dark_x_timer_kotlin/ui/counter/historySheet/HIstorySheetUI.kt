package com.example.dark_x_timer_kotlin.ui.counter.historySheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dark_x_timer_kotlin.data.CubeType
import com.example.dark_x_timer_kotlin.ui.AppViewModelProvider
import com.example.dark_x_timer_kotlin.ui.counter.deleteDialog.DeleteDialog
import com.example.dark_x_timer_kotlin.ui.theme.PastelRed
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryBottomSheet(
    viewModel: HistoryViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val state = viewModel.stateFlow.collectAsState()
    val sdf = java.text.SimpleDateFormat.getDateInstance(
        java.text.SimpleDateFormat.SHORT
    )

    if (state.value.isDeleteConfirmationDialogVisible) {
        Dialog(onDismissRequest = viewModel::hideDeleteConfirmationDialog) {
            DeleteDialog(
                item = state.value.deleteItem!!, onDismiss = viewModel::hideDeleteConfirmationDialog
            )
        }
    }

    if (state.value.isFilterDialogVisible) {
        Dialog(onDismissRequest = viewModel::hideFilterDialog) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Filter Cube Type", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier
                            .height(300.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        viewModel.cubeTypes.forEach { cubeTypeStr ->
                            val cubeType = CubeType.valueOf(cubeTypeStr)
                            var checked = (viewModel.selectedCubeTypes.contains(cubeType))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp)
                                    .toggleable(
                                        value = checked,
                                        onValueChange = {
                                            if (it) viewModel.selectedCubeTypes.add(cubeType)
                                            else viewModel.selectedCubeTypes.remove(cubeType)
                                            checked = it
                                        },
                                        role = Role.Checkbox
                                    ),
                                verticalAlignment = CenterVertically,
                            ) {
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = null
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = cubeTypeStr)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = viewModel::hideFilterDialog) {
                            Text(text = "Save")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = viewModel::uncheckAllFilters) {
                            Text(text = "Uncheck All")
                        }
                    }
                }
            }
        }
    }

    Column(modifier = Modifier.padding(20.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "History", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.weight(1f))
            FilterChip(
                selected = !viewModel.selectedCubeTypes.isEmpty(),
                onClick = viewModel::showFilterDialog,
                label = { Text(text = "Filter") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.FilterAlt,
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        contentDescription = "Filter Chip"
                    )
                })
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (state.value.itemList.isEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Empty",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        state.value.itemList.forEach {
            if (viewModel.selectedCubeTypes.isEmpty() || viewModel.selectedCubeTypes.contains(it.cubeType)) {
                Card(
                    modifier = Modifier.padding(vertical = 10.dp),
                    elevation = CardDefaults.elevatedCardElevation()
                ) {
                    ListItem(headlineContent = {
                        Text(text = it.time)
                    }, supportingContent = {
                        Text(text = it.name)
                    }, trailingContent = {
                        val date = Date(it.date)
                        Row {
                            Text(
                                text = sdf.format(date), modifier = Modifier.align(CenterVertically)
                            )
                            IconButton(onClick = { viewModel.setForDeletion(it) }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null,
                                    tint = PastelRed
                                )
                            }
                        }
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}