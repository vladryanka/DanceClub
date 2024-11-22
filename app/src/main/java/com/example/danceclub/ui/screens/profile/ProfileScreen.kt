package com.example.danceclub.ui.screens.profile

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
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
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Training
import com.example.danceclub.ui.theme.DanceClubTheme
import com.example.danceclub.ui.utils.PreviewLightDark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

@SuppressLint("RememberReturnType")
@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = viewModel(),
    person: Person,
    onNavigateToTrainings: () -> Unit,
) {

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var signedTrainingList by remember { mutableStateOf<List<Training>>(emptyList()) }
    var imageBitmapFromServer by remember { mutableStateOf<ImageBitmap?>(null) }

    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            imageBitmapFromServer = viewModel.getImage()
            Log.d("Doing", "ImageBitmapFromServer: $imageBitmapFromServer")
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        val result = viewModel.saveImage(it, contentResolver)
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
    )

    // permission launcher for Android 9 and 10
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                pickImageLauncher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
                Toast
                    .makeText(context, "Gallery run", Toast.LENGTH_LONG)
                    .show()
            } else {
                // Permission not granted
                // Handling is optional
                // Recommended to show user a warning and let try again
            }
        }
    )

    LaunchedEffect(Unit) {
        val trainingList = viewModel.getListNamesOfTraining()
        signedTrainingList = trainingList
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

            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .clickable {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            pickImageLauncher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        } else {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
            ) {
                if (imageBitmapFromServer != null)
                    Image(
                        bitmap = imageBitmapFromServer!!,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape),
                        contentScale = ContentScale.Crop
                    )
            }

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
                fontSize = 24.sp,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.padding(8.dp),
            color = Color.Black
        )

        LazyColumn {
            if (signedTrainingList.isEmpty()) {
                item {
                    Text(
                        text = "Записей нет",
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            } else {
                itemsIndexed(signedTrainingList) { _, item ->
                    Row(modifier = Modifier.padding(8.dp)) {
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
            ProfileScreen(
                person = Person(
                    name = "Steve",
                    surname = "Jobs",
                    patronimic = "Second",
                    phone = "911",
                    birth_date = LocalDate.now(),
                ),
                onNavigateToTrainings = {},
            )
        }
    }
}