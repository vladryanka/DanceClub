package com.example.danceclub.ui.screens.trainings

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danceclub.R
import com.example.danceclub.data.model.Training
import java.time.LocalDate

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingCardsScreen(
    contentPadding: PaddingValues,
    onNavigateToDetail: (Training) -> Unit
) {
    val months = listOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf(months[0]) }
    val isChecked: Boolean by remember { mutableStateOf(false) }
    val trainingList = listOf(Training(22,"name","description",
        LocalDate.of(2024,4,2),
            true,9,10))
    Column(
        modifier = Modifier
            .padding(top = contentPadding.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            Box {
                TextField(
                    readOnly = true,
                    value = selectedMonth,
                    onValueChange = {},
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.clip(RoundedCornerShape(60.dp))
                ) {
                    months.forEach { month ->
                        DropdownMenuItem(onClick = {
                            selectedMonth = month
                            expanded = false
                        }, text = { Text(month) })
                    }
                }
            }
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(trainingList) { _, item ->
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(Color.LightGray)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = {
                            onNavigateToDetail(item)
                        })

                ) {
                    Card {
                        Row {
                            Icon(
                                imageVector = if (isChecked)
                                    Icons.Default.CheckCircle
                                else Icons.Outlined.Circle,
                                contentDescription = stringResource(R.string.icon_check)
                            )
                            Text("Пн, 7 октября в 19:00") // Заменить на дату секций и время
                        }
                    }
                    Text(text = item.name, style = TextStyle(fontSize = 24.sp))

                    Row {
                        Image(
                            painter = painterResource(R.drawable.profile_image), // rememberAsyncImagePainter(image)
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Column {
                            Text(
                                text = "gfd",
                                //text = item.teacher,
                                color = Color.Black,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(fontSize = 24.sp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "gfd",//text = item.info,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                    }
                    Text(
                        text = "gfd",//text = item.price.toString(),
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}