package com.example.danceclub.ui.screens.trainings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danceclub.R
import com.example.danceclub.data.model.Training

@Composable
fun DetailItem(
    contentPadding: PaddingValues,
    training: Training,
    changeVisibility: () -> Unit
) {
    BackHandler { changeVisibility() }
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
                    text = "Время",
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 24.sp)
                )
                Text(
                    text = "fgdf",
                    //text = item.time.toString(),
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 16.sp)
                )

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "dfgdf",
            //text =  "${item.price} ₽",
            color = Color.Black,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Image(
                painter = painterResource(R.drawable.profile_image), // rememberAsyncImagePainter(image)
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Column {
                Text(
                    text = "dfgdf",
                    //text = item.teacher,
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 24.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "dfgdf",
                    //text = item.info,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }
    }

}