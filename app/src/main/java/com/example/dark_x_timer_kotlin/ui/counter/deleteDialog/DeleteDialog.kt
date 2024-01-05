package com.example.dark_x_timer_kotlin.ui.counter.deleteDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dark_x_timer_kotlin.data.SolveTimeItem
import com.example.dark_x_timer_kotlin.ui.AppViewModelProvider
import com.example.dark_x_timer_kotlin.ui.theme.PastelRed
import java.util.Date

@Composable
fun DeleteDialog(
    modifier: Modifier = Modifier,
    item: SolveTimeItem,
    onDismiss: () -> Unit = {},
    viewModel: DeleteViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
) {
    val state by viewModel.uiState.collectAsState()
    viewModel.setItem(item)

    val sdf = java.text.SimpleDateFormat.getDateInstance(
        java.text.SimpleDateFormat.SHORT
    )

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
                text = "Delete",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = PastelRed
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(8.dp)) {
                Row {
                    Text(
                        text = "Name: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    state.item?.let {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        text = "Time: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    state.item?.let {
                        Text(
                            text = it.time,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        text = "Date: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    state.item?.let {
                        val date = Date(it.date)
                        Text(
                            text = sdf.format(date),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Are you sure you want to delete this record?",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = PastelRed,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = {
                    viewModel.delete()
                    onDismiss()
                }) {
                    Text(text = "DELETE", color = PastelRed)
                }
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}
