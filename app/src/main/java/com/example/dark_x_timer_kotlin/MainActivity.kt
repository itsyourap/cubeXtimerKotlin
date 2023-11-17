package com.example.dark_x_timer_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dark_x_timer_kotlin.ui.theme.Dark_x_timer_kotlinTheme
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dark_x_timer_kotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

fun formatTime(time: Long): String {
    val hours = time / 3600
    val minutes = (time % 3600) / 60
    val seconds = time % 60
    return "%02d:%02d:%02d".format(hours, minutes, seconds)
}

@Composable
fun MainScreen() {
    var time: Long by rememberSaveable { mutableLongStateOf(0L) }
    var isRunning by rememberSaveable { mutableStateOf(false) }
    var isPaused by rememberSaveable { mutableStateOf(false) }

    var timer: Timer? by rememberSaveable { mutableStateOf(null) }

    val onStart: () -> Unit = {
        isRunning = true

        timer = Timer(
            "Timer",
            false,

            )

        timer?.scheduleAtFixedRate(
            0, 1000,
        ) {
            time += 1
        }
    }

    val onPause: () -> Unit = {
        timer?.cancel()
        isPaused = true
    }

    val onResume: () -> Unit = {
        timer = Timer(
            "Timer",
            false,

            )
        timer?.scheduleAtFixedRate(
            0, 1000,
        ) {
            time += 1
        }
        isPaused = false
    }

    val onReset: () -> Unit = {
        timer?.cancel()
        isRunning = false
        isPaused = false
        time = 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        TimerCard(
            time = formatTime(time), modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        ActionCard(
            running = isRunning,
            paused = isPaused,
            onStart = onStart,
            onPause = onPause,
            onReset = onReset,
            onResume = onResume,
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
            style = typography.headlineLarge,
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
    running: Boolean = false,
    paused: Boolean = false,
    onStart: () -> Unit,
    onPause: () -> Unit,
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
            if (!running && !paused) IconButton(onClick = onStart) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Start",
                    tint = Color.Blue,
                )
            }
            else if (running && !paused) IconButton(onClick = onPause) {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = "Pause",
                    tint = Color.Red,
                )
            }
            else {
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
                onStart = {},
                onPause = {},
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
        MainScreen()
    }
}


