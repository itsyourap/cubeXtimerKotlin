package com.example.dark_x_timer_kotlin.ui.counter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dark_x_timer_kotlin.R
import com.example.dark_x_timer_kotlin.ui.theme.Dark_x_timer_kotlinTheme
import com.example.dark_x_timer_kotlin.ui.theme.PastelBlue
import com.example.dark_x_timer_kotlin.ui.theme.PastelGreen
import com.example.dark_x_timer_kotlin.ui.theme.PastelRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(
    viewModel: CounterViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_name), Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.showBottomSheet() }) {
                        Icon(
                            imageVector = Icons.Filled.History,
                            contentDescription = "History",
                        )
                    }
                }
            )
        }
    ) { it ->
        if (uiState.isBottomSheetVisible) {
            ModalBottomSheet(onDismissRequest = { viewModel.hideBottomSheet() }) {
                val list = (0..100).map { "Solve History $it" }
                LazyColumn {
                    item {
                        Text(
                            text = "History",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    items(list.size) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = list[it],
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                    }
                }
            }
        }
        Body(
            viewModel = viewModel,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun Body(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                if (uiState.running && !uiState.paused) {
                    viewModel.onPause()
                } else if (!uiState.running) {
                    viewModel.onStart()
                }
            },
        verticalArrangement = Arrangement.Center,
    ) {
        TimerCard(
            time = uiState.time, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        AnimatedVisibility(uiState.running && uiState.paused) {
            ActionCard(
                onReset = {
                    viewModel.onReset()
                },
                onResume = {
                    viewModel.onResume()
                },
                onSave = { },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }

}

@Composable
fun TimerCard(time: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        // How to make the text size adapt to the size of the card?
        Text(
            text = time,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ActionCard(
    modifier: Modifier = Modifier,
    onReset: () -> Unit,
    onResume: () -> Unit,
    onSave: () -> Unit,
) {
    Card(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.padding(16.dp),
        ) {
            IconButton(onClick = onReset) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Reset",
                    tint = PastelRed,
                )
            }
            IconButton(onClick = onResume) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Resume",
                    tint = PastelBlue,
                )
            }
            IconButton(onClick = onSave) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Save",
                    tint = PastelGreen,
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerCardPreview() {
    Dark_x_timer_kotlinTheme {
        Surface {
            TimerCard("00:00:00")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionCardPreview() {
    Dark_x_timer_kotlinTheme {
        Surface {
            ActionCard(
                onReset = {},
                onResume = {},
                onSave = {},
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    Dark_x_timer_kotlinTheme {
        CounterScreen()
    }
}


