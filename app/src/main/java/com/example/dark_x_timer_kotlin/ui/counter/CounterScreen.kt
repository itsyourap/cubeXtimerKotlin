package com.example.dark_x_timer_kotlin.ui.counter

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dark_x_timer_kotlin.ui.theme.Dark_x_timer_kotlinTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dark_x_timer_kotlin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(
    viewModel: CounterViewModel = viewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_name), Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                },
            )
        }
    ) {
        Body(
            viewModel = viewModel, modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun Body(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
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

        if (uiState.running && uiState.paused)
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
                    tint = Color.Red,
                )
            }
            IconButton(onClick = onResume) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Resume",
                    tint = Color.Blue,
                )
            }
            IconButton(onClick = onSave) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Save",
                    tint = Color.Green,
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


