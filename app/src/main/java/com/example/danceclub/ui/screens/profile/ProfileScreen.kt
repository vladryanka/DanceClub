package com.example.danceclub.ui.screens.profile

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.danceclub.R
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Training
import com.example.danceclub.ui.theme.DanceClubTheme
import com.example.danceclub.ui.utils.PreviewLightDark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = viewModel(),
    person: Person,
    onNavigateToTrainings: () -> Unit,
) {

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var signedTrainingList by remember { mutableStateOf<List<Training>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    var imageFromServer by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    val result = viewModel.saveImage(it, context.contentResolver)
                    if (result != null)
                        snackbarHostState.showSnackbar(
                            result,
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )

                }
            }
        }
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            imageFromServer = viewModel.getImage()
            Log.d("Doing", imageFromServer.toString())
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val trainingList = viewModel.getListNamesOfTraining()
            signedTrainingList = trainingList ?: emptyList()
        }
    }

    BackHandler {
        // no back navigation
        // may save settings and then close app
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 64.dp, end = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    imageFromServer ?: selectedImageUri ?: R.drawable.profile_image
                ),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable {
                        when {
                            ContextCompat.checkSelfPermission(
                                context as Activity,
                                Manifest.permission.MANAGE_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                Toast
                                    .makeText(context, "Gallery run", Toast.LENGTH_LONG)
                                    .show()
                            }

                            else -> {
                                getContent.launch(
                                    PickVisualMediaRequest(
                                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }
                        }
                    },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${person.surname} ${person.name} ${person.patronimic}",
                    style = TextStyle(fontSize = 20.sp),
                    color = Color.Black
                )
                Text(
                    text = "${person.birth_date}",
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.Black
                )
                Text(
                    text = person.phone,
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Тренировки",
                modifier = Modifier.clickable(onClick = { onNavigateToTrainings() }),
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    fontSize = 18.sp
                ),
                color = Color.Blue
            )
        }
        Text(
            text = "Мои тренировки:",
            style = TextStyle(
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(8.dp),
            color = Color.Black
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (signedTrainingList.isEmpty()) {
                item {
                    Text(
                        text = "Записей нет",
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                itemsIndexed(signedTrainingList) { _, item ->
                    Row {
                        Text(
                            text = item.name,
                            style = TextStyle(fontSize = 16.sp),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = item.date.toString(),
                            style = TextStyle(fontSize = 16.sp),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }


}

@PreviewLightDark
@Composable
fun ProfileScreenPreview() {
    DanceClubTheme {
        Surface {
            /*ProfileScreen(
                onNavigateToTrainings = {},
                person = TODO()
            )*/
        }
    }
}