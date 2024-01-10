package com.example.dark_x_timer_kotlin.ui.counter.historySheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dark_x_timer_kotlin.ui.AppViewModelProvider
import com.example.dark_x_timer_kotlin.ui.counter.deleteDialog.DeleteDialog
import com.example.dark_x_timer_kotlin.ui.theme.PastelRed
import java.util.Date

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
                item = state.value.deleteItem!!,
                onDismiss = viewModel::hideDeleteConfirmationDialog
            )
        }
    }

    Column(modifier = Modifier.padding(20.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "History", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.weight(1f))
            AssistChip(onClick = { /*TODO*/ }, label = { Text(text = "Filter") }, leadingIcon = {
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
            Card(
                modifier = Modifier.padding(vertical = 10.dp),
                elevation = CardDefaults.elevatedCardElevation()
            ) {
                ListItem(
                    headlineContent = {
                        Text(text = it.time)
                    },
                    supportingContent = {
                        Text(text = it.name)
                    },
                    trailingContent = {
                        val date = Date(it.date)
                        Row {
                            Text(
                                text = sdf.format(date),
                                modifier = Modifier.align(CenterVertically)
                            )
                            IconButton(onClick = { viewModel.setForDeletion(it) }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null,
                                    tint = PastelRed
                                )
                            }
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}