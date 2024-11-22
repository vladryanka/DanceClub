package com.example.danceclub.ui.screens.trainings

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danceclub.data.model.Training
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DetailItem(
    contentPadding: PaddingValues,
    training: Training,
    changeVisibility: () -> Unit,
    singInTraining: suspend (String) -> String?,
    personId: String,
    isSignedInResult: Boolean
) {
    BackHandler { changeVisibility() }
    val snackbarHostState = remember { SnackbarHostState() }
    var isSignedIn by remember { mutableStateOf(isSignedInResult) }

    Column(
        modifier = Modifier
            .padding(top = contentPadding.calculateTopPadding(), start = 16.dp)
    ) {

        Text(
            text = training.name,
            color = Color.Black,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Дата",
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 24.sp)
                )
                Text(
                    text = training.date.toString(),
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 16.sp)
                )

            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Осталось мест",
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 24.sp)
                )
                Text(
                    text = "${training.freeSpace}/${training.space}",
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 16.sp)
                )

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${training.price} ₽",
            color = Color.Black,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {

            Column {
                Text(
                    text = training.trainerName,
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 24.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = training.description,
                    color = Color.Gray,
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                var result: String? = null
                if (!isSignedIn) {
                    result = singInTraining(personId)
                    isSignedIn = true
                }
                withContext(Dispatchers.Main) {
                    if (result != null) {
                        Log.d("Doing", result.toString())
                        snackbarHostState.showSnackbar(
                            result.toString(),
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }
                }


            }

        }) {
            Text(
                if (isSignedIn) "Вы записаны" else "Записаться",
                color = Color.Black, textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 24.sp)
            )
        }
    }

}