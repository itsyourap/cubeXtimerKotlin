package com.example.dark_x_timer_kotlin.ui.counter.historySheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dark_x_timer_kotlin.ui.AppViewModelProvider
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

    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = "History", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(10.dp))
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
                            Text(text = sdf.format(date))
                            IconButton(onClick = { /*TODO*/ }) {
                                 Icon(Icons.Outlined.Delete, contentDescription = null)
                            }
                        }
                    }
                )
            }
        }
    }
}