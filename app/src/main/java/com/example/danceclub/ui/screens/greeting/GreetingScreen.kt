package com.example.danceclub.ui.screens.greeting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danceclub.R
import com.example.danceclub.ui.theme.DanceClubTheme
import com.example.danceclub.ui.utils.PreviewLightDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingScreen(
    onNavigateToSignIn: () -> Unit,
    onNavigateToRegistration: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(R.string.greeting), color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    )
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.dance_image),
                contentDescription = stringResource(
                    id = R.string.app_name
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { onNavigateToSignIn() }) {
                Text(
                    text = stringResource(id = R.string.enter), color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onNavigateToRegistration() }
            ) {
                Text(
                    text = stringResource(id = R.string.registration), color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun GreetingScreenPreview() {
    DanceClubTheme {
        Surface {
            GreetingScreen(
                onNavigateToSignIn = {},
                onNavigateToRegistration = {},
            )
        }
    }
}