package com.example.danceclub.ui.screens.profile

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danceclub.R
import com.example.danceclub.data.model.Person
import com.example.danceclub.ui.theme.DanceClubTheme
import com.example.danceclub.ui.utils.PreviewLightDark

@Composable
fun ProfileScreen(
    person: Person,
    onNavigateToTrainings: () -> Unit,
) {

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
                painter = painterResource(id = R.drawable.profile_image),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
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