package com.example.danceclub.ui.screens.trainings

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.danceclub.data.model.Section
import com.example.danceclub.ui.theme.DanceClubTheme
import com.example.danceclub.ui.utils.PreviewLightDark
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingsScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToDetail: (Section) -> Unit
) {
    val isItem1Visible = remember { mutableStateOf(true) }
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
                                    onNavigateToProfile()
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


        /**/

        when (isItem1Visible.value) {
            true -> DetailScreen(
                contentPadding, Section(2,"Dance sport","Zumba",true,"Яна",
                    700, LocalTime.of(11,30),
                    LocalDate.of(2024, 10, 13),10)
            )

            false -> TrainingCardsScreen(
                contentPadding, onNavigateToDetail
            )
        }

    }
}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingCardsScreen(
    contentPadding: PaddingValues,
    onNavigateToDetail: (Section) -> Unit
) {
    val months = listOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf(months[0]) }
    var isChecked: Boolean by remember { mutableStateOf(false) }
    val trainingList = listOf(
        Section(1,"Pole sport","Streching (Растяжка)",true,
            "Настя", 1500,  LocalTime.of(15,30),
            LocalDate.of(2024, 10, 9), 10 ),
        Section(2,"Dance sport","Zumba",true,"Яна",
            700, LocalTime.of(11,30),
            LocalDate.of(2024, 10, 13),10),
        Section(3,"Hole dance sport","Hole dance",true,
            "Маша", 1000,  LocalTime.of(10,0),
            LocalDate.of(2024, 10, 20), 10)
        )
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
                                text = item.teacher,
                                color = Color.Black,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(fontSize = 24.sp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.info,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                    }
                    Text(
                        text = item.price.toString(),
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

@Composable
fun DetailScreen(
    contentPadding:PaddingValues,
    item:Section
){
    Column(modifier = Modifier
        .padding(top = contentPadding.calculateTopPadding(), start = 16.dp)){

        Text(
            text = item.name,
            color = Color.Black,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()){
            Column(modifier = Modifier
                .weight(1f) ) {
                Text(
                    text = "Дата",
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 24.sp)
                )
                Text(
                    text = item.date.toString(),
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 16.sp)
                )

            }
            Column(modifier = Modifier
                .weight(1f) ) {
                Text(
                    text = "Время",
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 24.sp)
                )
                Text(
                    text = item.time.toString(),
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 16.sp)
                )

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text =  "${item.price} ₽",
            color = Color.Black,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row{
            Image(
                painter = painterResource(R.drawable.profile_image), // rememberAsyncImagePainter(image)
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Column {
                Text(
                    text = item.teacher,
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 24.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.info,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@PreviewLightDark
@Composable
fun ProfileScreenPreview() {
    DanceClubTheme {
        Surface {
            TrainingsScreen(
                onNavigateToProfile = {},
                onNavigateToDetail = {},
            )
        }
    }
}