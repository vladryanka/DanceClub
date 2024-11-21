package com.example.danceclub.ui.screens.trainings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.danceclub.R
import com.example.danceclub.ui.theme.DanceClubTheme
import com.example.danceclub.ui.utils.PreviewLightDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingsScreen(
    trainingScreenViewModel: TrainingScreenViewModel = viewModel(),
    onNavigateUpToProfile: () -> Unit
) {


    BackHandler { onNavigateUpToProfile() }
    val isItem1Visible = remember { mutableStateOf(false) }
    fun changeVisibility() {
        isItem1Visible.value = !isItem1Visible.value
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = stringResource(R.string.back),
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    onNavigateUpToProfile()
                                }
                        )
                        Image(
                            painter = painterResource(R.drawable.logo_image),
                            contentDescription = stringResource(
                                R.string.logo
                            ), modifier = Modifier
                                .size(42.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                stringResource(R.string.app_name),
                                color = Color.Black,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(fontSize = 24.sp)
                            )
                            Text(
                                text = stringResource(R.string.location),
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                    }
                }
            )
        }
    ) { contentPadding ->


        when (isItem1Visible.value) {
            true -> {

                val currentTraining = trainingScreenViewModel.currentTraining.value
                if (currentTraining != null)
                    DetailItem(
                        contentPadding,
                        currentTraining, ::changeVisibility,
                        trainingScreenViewModel::singInTraining,
                        trainingScreenViewModel.getCurrentPerson().id

                    )
            }

            false -> {
                TrainingCardsItem(
                    contentPadding,
                    trainingScreenViewModel::updateCurrentTrainings,
                    ::changeVisibility,
                    trainingScreenViewModel::currentMonthTrainings
                )
            }
        }

    }

}

@PreviewLightDark
@Composable
fun ProfileScreenPreview() {
    DanceClubTheme {
        Surface {
            TrainingsScreen(
                onNavigateUpToProfile = {},
            )
        }
    }
}